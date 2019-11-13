package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.RequestContext;
import com.ethan.crmsystem.common.ResponseConstants;
import com.ethan.crmsystem.domain.Equipment;
import com.ethan.crmsystem.domain.User;
import com.ethan.crmsystem.mapper.EquipmentMapper;
import com.ethan.crmsystem.mapper.bean.EquipmentBean;
import com.ethan.crmsystem.repository.EquipmentRepository;
import com.ethan.crmsystem.utils.LocalDateTimeHelper;
import com.ethan.crmsystem.web.model.EquipmentForm;
import com.ethan.crmsystem.web.model.EquipmentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @version : EquipmentService.java,2019/11/12 19:57 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Service
public class EquipmentService {

    private EquipmentMapper equipmentMapper;

    private RequestContext requestContext;

    private EquipmentRepository equipmentRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<EquipmentModel> findCustomerTable(EquipmentForm equipmentForm) {

        User user = requestContext.getRequestUser();
        equipmentForm = dealFormData(equipmentForm);

        List<EquipmentBean> equipmentBeans = equipmentMapper.findEquipmentByCondition(equipmentForm,user.getRoleId());

        List<EquipmentModel> equipmentModels = new ArrayList<>();
        equipmentBeans.forEach(equipmentBean -> {
            EquipmentModel equipmentModel = new EquipmentModel();
            String insTime = equipmentBean.getInstallation_time().format(formatter);

            equipmentModel.setId(equipmentBean.getId());
            equipmentModel.setCustomerCode(equipmentBean.getCustomer_code());
            equipmentModel.setDeviceName(equipmentBean.getDevice_name());
            equipmentModel.setInsAddress(equipmentBean.getInstallation_address());
            equipmentModel.setInsStaff(equipmentBean.getInstallation_staff());
            equipmentModel.setInsTime(insTime);
            equipmentModel.setPrice(equipmentBean.getPrice());
            equipmentModel.setSaleStaff(equipmentBean.getSales_staff());

            equipmentModels.add(equipmentModel);
        });
        return equipmentModels;
    }

    public String countEquipment(EquipmentForm equipmentForm) {

        User user = requestContext.getRequestUser();
        equipmentForm = dealFormData(equipmentForm);

        String count = equipmentMapper.findCountByCondition(equipmentForm,user.getRoleId());

        return count;
    }

    @Transactional(rollbackOn = Exception.class)
    public String addEquipment(EquipmentModel equipmentModel) {

        Equipment equipment = new Equipment();
        equipment.setCustomerCode(equipmentModel.getCustomerCode());
        equipment = dealEuqipment(equipment,equipmentModel);
        equipment.setCreatTime(LocalDateTime.now());

        equipmentRepository.save(equipment);

        return ResponseConstants.SUCCESS;
    }

    @Transactional(rollbackOn = Exception.class)
    public String updateEquipment(EquipmentModel equipmentModel) {

        Optional<Equipment> equipmentOptional = equipmentRepository.findById(equipmentModel.getId());
        Equipment equipment = equipmentOptional.get();
        equipment = dealEuqipment(equipment,equipmentModel);
        equipment.setUpdateTime(LocalDateTime.now());

        equipmentRepository.save(equipment);

        return ResponseConstants.SUCCESS;
    }

    @Transactional(rollbackOn = Exception.class)
    public String deleteEquipment(String id) {

        equipmentRepository.deleteById(id);

        return ResponseConstants.SUCCESS;
    }

    private Equipment dealEuqipment(Equipment equipment,EquipmentModel equipmentModel){

        User user = requestContext.getRequestUser();

        LocalDateTime insTime = LocalDateTime.parse(equipmentModel.getInsTime(),formatter);

        equipment.setDeviceName(equipmentModel.getDeviceName());
        equipment.setInstallationAddress(equipmentModel.getInsAddress());
        equipment.setInstallationTime(insTime);
        equipment.setPrice(equipmentModel.getPrice());
        equipment.setSalesStaff(equipmentModel.getSaleStaff());
        equipment.setInstallationStaff(equipmentModel.getInsStaff());
        equipment.setUserId(user.getId());

        return equipment;
    }

    private EquipmentForm dealFormData(EquipmentForm equipmentForm){

        if (equipmentForm.getDateRange() != null && equipmentForm.getDateRange().length != 0){
            LocalDateTime beginTime = LocalDateTimeHelper.parse(equipmentForm.getDateRange()[0]);
            LocalDateTime endTime = LocalDateTimeHelper.parse(equipmentForm.getDateRange()[1]).plusDays(1);

            equipmentForm.setStartTime(beginTime);
            equipmentForm.setEndTime(endTime);
        }

        equipmentForm.setOffset((equipmentForm.getPage() - 1) * equipmentForm.getPageSize());

        return equipmentForm;
    }

    @Autowired
    public void setEquipmentMapper(EquipmentMapper equipmentMapper) {
        this.equipmentMapper = equipmentMapper;
    }

    @Autowired
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Autowired
    public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }
}
