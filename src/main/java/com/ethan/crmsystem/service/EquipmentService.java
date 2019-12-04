package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.RequestContext;
import com.ethan.crmsystem.common.ResponseConstants;
import com.ethan.crmsystem.infra.domain.Equipment;
import com.ethan.crmsystem.infra.domain.User;
import com.ethan.crmsystem.infra.mapper.AfterSalesMapper;
import com.ethan.crmsystem.infra.mapper.EquipmentMapper;
import com.ethan.crmsystem.infra.repository.AfterSalesInfoRepository;
import com.ethan.crmsystem.infra.repository.EquipmentRepository;
import com.ethan.crmsystem.infra.repository.UserRepository;
import com.ethan.crmsystem.utils.LocalDateTimeHelper;
import com.ethan.crmsystem.web.model.EquipmentForm;
import com.ethan.crmsystem.web.model.EquipmentModel;
import org.apache.commons.lang3.StringUtils;
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

    private UserRepository userRepository;

    private AfterSalesMapper afterSalesMapper;

    private AfterSalesInfoRepository afterSalesInfoRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<EquipmentModel> findCustomerTable(EquipmentForm equipmentForm) {

        User user = requestContext.getRequestUser();
        equipmentForm = dealFormData(equipmentForm);

        List<Equipment> equipmentBeans = equipmentMapper.findEquipmentByCondition(equipmentForm,user.getRoleId());

        List<EquipmentModel> equipmentModels = new ArrayList<>();
        equipmentBeans.forEach(equipmentBean -> {
            EquipmentModel equipmentModel = new EquipmentModel();
            String afterSalesCount = afterSalesMapper.findAfterSaleCountByEquipmentId(equipmentBean.getId(),user.getRoleId());
            if (afterSalesCount == null){
                afterSalesCount = "0";
            }
            String insTime = equipmentBean.getInstallationTime().format(formatter);

            equipmentModel.setId(equipmentBean.getId());
            equipmentModel.setCustomerCode(equipmentBean.getCustomerCode());
            equipmentModel.setDeviceName(equipmentBean.getDeviceName());
            equipmentModel.setInsAddress(equipmentBean.getInstallationAddress());
            equipmentModel.setInsStaff(equipmentBean.getInstallationStaff());
            equipmentModel.setInsTime(insTime);
            equipmentModel.setPrice(equipmentBean.getPrice());
            equipmentModel.setSaleStaff(equipmentBean.getSalesStaff());
            equipmentModel.setRegion(equipmentBean.getRegionId());
            equipmentModel.setAfterSalesCount(afterSalesCount);

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
        equipment.setCustomerCode(equipment.getCustomerCode());
        equipment.setRegionId(equipmentModel.getRegion());

        equipmentRepository.save(equipment);

        return ResponseConstants.SUCCESS;
    }

    @Transactional(rollbackOn = Exception.class)
    public String updateEquipment(EquipmentModel equipmentModel) {

        Equipment equipment = equipmentRepository.findById(equipmentModel.getId()).get();
        equipment = dealEuqipment(equipment,equipmentModel);
        equipment.setUpdateTime(LocalDateTime.now());

        equipmentRepository.save(equipment);

        return ResponseConstants.SUCCESS;
    }

    @Transactional(rollbackOn = Exception.class)
    public String deleteEquipment(String id) {

        if (StringUtils.isEmpty(id)){
            return ResponseConstants.FAILURE;
        }
        equipmentRepository.deleteById(id);
        afterSalesInfoRepository.deleteByEquipmentId(id);

        return ResponseConstants.SUCCESS;
    }

    public List<EquipmentModel> findEquipmentInfoByCode(String customerCode) {

        if (customerCode == null  || "undefined".equals(customerCode)){
            return null;
        }
        User user = requestContext.getRequestUser();
        List<Equipment> equipmentBeans = equipmentMapper.findEquipmentInfoByCode(customerCode,user.getRoleId());
        if (equipmentBeans == null){
            return null;
        }

        List<EquipmentModel> equipmentModels = new ArrayList<>();
        equipmentBeans.forEach(equipmentBean -> {
            EquipmentModel equipmentModel = new EquipmentModel();

            String creatTime = equipmentBean.getCreatTime().format(formatter);
            String updateTime = null;
            if (equipmentBean.getUpdateTime() != null){
                updateTime = equipmentBean.getUpdateTime().format(formatter);
            }
            Optional<User> optionalUser = userRepository.findById(equipmentBean.getUserId());
            String insTime = equipmentBean.getInstallationTime().format(formatter);

            equipmentModel.setId(equipmentBean.getId());
            equipmentModel.setCustomerCode(equipmentBean.getCustomerCode());
            equipmentModel.setDeviceName(equipmentBean.getDeviceName());
            equipmentModel.setInsAddress(equipmentBean.getInstallationAddress());
            equipmentModel.setInsStaff(equipmentBean.getInstallationStaff());
            equipmentModel.setInsTime(insTime);
            equipmentModel.setPrice(equipmentBean.getPrice());
            equipmentModel.setSaleStaff(equipmentBean.getSalesStaff());
            equipmentModel.setRegion(equipmentBean.getRegionId());
            equipmentModel.setCreatTime(creatTime);
            equipmentModel.setUpdateTime(updateTime);
            equipmentModel.setUserName(optionalUser.get().getFullName());

            equipmentModels.add(equipmentModel);
        });
        return equipmentModels;
    }

    private Equipment dealEuqipment(Equipment equipment,EquipmentModel equipmentModel){

        User user = requestContext.getRequestUser();

        LocalDateTime insTime = LocalDateTimeHelper.parse(equipmentModel.getInsTime());

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

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAfterSalesInfoRepository(AfterSalesInfoRepository afterSalesInfoRepository) {
        this.afterSalesInfoRepository = afterSalesInfoRepository;
    }

    @Autowired
    public void setAfterSalesMapper(AfterSalesMapper afterSalesMapper) {
        this.afterSalesMapper = afterSalesMapper;
    }
}
