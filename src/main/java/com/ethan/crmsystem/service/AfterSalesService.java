package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.RequestContext;
import com.ethan.crmsystem.common.ResponseConstants;
import com.ethan.crmsystem.infra.domain.AfterSalesInfo;
import com.ethan.crmsystem.infra.domain.User;
import com.ethan.crmsystem.infra.mapper.AfterSalesMapper;
import com.ethan.crmsystem.infra.repository.AfterSalesInfoRepository;
import com.ethan.crmsystem.infra.repository.UserRepository;
import com.ethan.crmsystem.utils.LocalDateTimeHelper;
import com.ethan.crmsystem.web.model.AfterSalesForm;
import com.ethan.crmsystem.web.model.AfterSalesModel;
import lombok.extern.slf4j.Slf4j;
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
 * @version : AfterSalesService.java,2019/11/13 22:15 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Service
@Slf4j
public class AfterSalesService {

    private AfterSalesMapper afterSalesMapper;

    private AfterSalesInfoRepository afterSalesInfoRepository;

    private RequestContext requestContext;

    private UserRepository userRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<AfterSalesModel> findEquipmentTable(AfterSalesForm afterSalesForm) {

        User user = requestContext.getRequestUser();
        afterSalesForm = dealFormData(afterSalesForm);

        List<AfterSalesInfo> afterSalesBeans = afterSalesMapper.findAfterSalesLogByCondition(afterSalesForm,user.getRoleId());

        List<AfterSalesModel> afterSalesModels = new ArrayList<>();
        afterSalesBeans.forEach(afterSalesBean -> {
            AfterSalesModel afterSalesModel = new AfterSalesModel();
            String dealTime = afterSalesBean.getDealTime().format(formatter);

            afterSalesModel.setId(afterSalesBean.getId());
            afterSalesModel.setCustomerCode(afterSalesBean.getCustomerCode());
            afterSalesModel.setEquipmentId(afterSalesBean.getEquipmentId());
            afterSalesModel.setDealStaff(afterSalesBean.getDealStaff());
            afterSalesModel.setDealTime(dealTime);
            afterSalesModel.setSaleRecord(afterSalesBean.getSaleRecord());
            afterSalesModel.setNote(afterSalesBean.getNote());

            afterSalesModels.add(afterSalesModel);
        });

        return afterSalesModels;
    }

    public String countAfterSales(AfterSalesForm afterSalesForm) {

        User user = requestContext.getRequestUser();
        afterSalesForm = dealFormData(afterSalesForm);

        String count = afterSalesMapper.findCountByCondition(afterSalesForm,user.getRoleId());

        return count;
    }

    public String addAfterSalesLog(AfterSalesModel afterSalesModel) {

        AfterSalesInfo afterSalesInfo = new AfterSalesInfo();
        afterSalesInfo = dealAfterSalesLog(afterSalesInfo,afterSalesModel);
        afterSalesInfo.setCreatTime(LocalDateTime.now());
        afterSalesInfo.setCustomerCode(afterSalesModel.getCustomerCode());
        afterSalesInfo.setEquipmentId(afterSalesModel.getEquipmentId());
        afterSalesInfo.setRegionId(afterSalesModel.getRegion());

        afterSalesInfoRepository.save(afterSalesInfo);

        return ResponseConstants.SUCCESS;
    }

    public String updateAfterSalesLog(AfterSalesModel afterSalesModel) {

        AfterSalesInfo afterSalesInfo = afterSalesInfoRepository.findById(Integer.valueOf(afterSalesModel.getId())).get();

        afterSalesInfo = dealAfterSalesLog(afterSalesInfo,afterSalesModel);
        afterSalesInfo.setUpdateTime(LocalDateTime.now());

        afterSalesInfoRepository.save(afterSalesInfo);

        return ResponseConstants.SUCCESS;
    }

    @Transactional(rollbackOn = Exception.class)
    public String deleteAfterSalesLog(String id) {

        if (StringUtils.isEmpty(id)){
            return ResponseConstants.FAILURE;
        }
        afterSalesInfoRepository.deleteById(Integer.valueOf(id));

        return ResponseConstants.SUCCESS;
    }

    public List<AfterSalesModel> findAfterSaleByCustomerCode(String customerCode) {

        if (customerCode == null  || "undefined".equals(customerCode)){
            return null;
        }
        User user = requestContext.getRequestUser();
        List<AfterSalesInfo> afterSalesBeans = afterSalesMapper.findAfterSaleByCustomerCode(customerCode,user.getRoleId());
        if (afterSalesBeans == null){
            return null;
        }

        List<AfterSalesModel> afterSalesModels = new ArrayList<>();
        afterSalesBeans.forEach(afterSalesBean -> {

            AfterSalesModel afterSalesModel = new AfterSalesModel();

            String creatTime = afterSalesBean.getCreatTime().format(formatter);
            String updateTime = null;
            if (afterSalesBean.getUpdateTime() != null){
                updateTime = afterSalesBean.getUpdateTime().format(formatter);
            }
            Optional<User> optionalUser = userRepository.findById(afterSalesBean.getUserId());
            String dealTime = afterSalesBean.getDealTime().format(formatter);

            afterSalesModel.setId(afterSalesBean.getId());
            afterSalesModel.setCustomerCode(afterSalesBean.getCustomerCode());
            afterSalesModel.setEquipmentId(afterSalesBean.getEquipmentId());
            afterSalesModel.setDealStaff(afterSalesBean.getDealStaff());
            afterSalesModel.setDealTime(dealTime);
            afterSalesModel.setSaleRecord(afterSalesBean.getSaleRecord());
            afterSalesModel.setNote(afterSalesBean.getNote());
            afterSalesModel.setCreatTime(creatTime);
            afterSalesModel.setUpdateTime(updateTime);
            afterSalesModel.setUserName(optionalUser.get().getFullName());

            afterSalesModels.add(afterSalesModel);
        });

        return afterSalesModels;
    }

    private AfterSalesInfo dealAfterSalesLog(AfterSalesInfo afterSalesInfo, AfterSalesModel afterSalesModel){

        User user = requestContext.getRequestUser();

        LocalDateTime dealTime = LocalDateTimeHelper.parse(afterSalesModel.getDealTime());

        afterSalesInfo.setDealStaff(afterSalesModel.getDealStaff());
        afterSalesInfo.setDealTime(dealTime);
        afterSalesInfo.setSaleRecord(afterSalesModel.getSaleRecord());
        afterSalesInfo.setNote(afterSalesModel.getNote());
        afterSalesInfo.setUserId(user.getId());

        return afterSalesInfo;
    }

    private AfterSalesForm dealFormData(AfterSalesForm afterSalesForm){

        if (afterSalesForm.getDateRange() != null && afterSalesForm.getDateRange().length != 0){
            LocalDateTime beginTime = LocalDateTimeHelper.parse(afterSalesForm.getDateRange()[0]);
            LocalDateTime endTime = LocalDateTimeHelper.parse(afterSalesForm.getDateRange()[1]).plusDays(1);

            afterSalesForm.setStartTime(beginTime);
            afterSalesForm.setEndTime(endTime);
        }

        afterSalesForm.setOffset((afterSalesForm.getPage() - 1) * afterSalesForm.getPageSize());

        return afterSalesForm;
    }

    @Autowired
    public void setAfterSalesMapper(AfterSalesMapper afterSalesMapper) {
        this.afterSalesMapper = afterSalesMapper;
    }

    @Autowired
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Autowired
    public void setAfterSalesInfoRepository(AfterSalesInfoRepository afterSalesInfoRepository) {
        this.afterSalesInfoRepository = afterSalesInfoRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
