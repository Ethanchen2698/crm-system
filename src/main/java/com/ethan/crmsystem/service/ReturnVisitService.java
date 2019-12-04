package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.RequestContext;
import com.ethan.crmsystem.common.ResponseConstants;
import com.ethan.crmsystem.infra.domain.ReturnVisitInfo;
import com.ethan.crmsystem.infra.domain.User;
import com.ethan.crmsystem.infra.mapper.ReturnVisitMapper;
import com.ethan.crmsystem.infra.repository.ReturnVisitInfoRepository;
import com.ethan.crmsystem.infra.repository.UserRepository;
import com.ethan.crmsystem.utils.LocalDateTimeHelper;
import com.ethan.crmsystem.web.model.ReturnVisitForm;
import com.ethan.crmsystem.web.model.ReturnVisitModel;
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
 * @version : ReturnVisitService.java,2019/11/14 19:31 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Service
@Slf4j
public class ReturnVisitService {

    private ReturnVisitMapper returnVisitMapper;

    private ReturnVisitInfoRepository returnVisitInfoRepository;

    private RequestContext requestContext;

    private UserRepository userRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<ReturnVisitModel> findEquipmentTable(ReturnVisitForm returnVisitForm) {

        User user = requestContext.getRequestUser();
        returnVisitForm = dealFormData(returnVisitForm);

        List<ReturnVisitInfo> returnVisitBeans = returnVisitMapper.findReturnVisitLogByCondition(returnVisitForm,user.getRoleId());

        List<ReturnVisitModel> returnVisitModels = new ArrayList<>();
        returnVisitBeans.forEach(returnVisitBean -> {
            ReturnVisitModel returnVisitModel = new ReturnVisitModel();
            String visitTime = returnVisitBean.getVisitTime().format(formatter);

            returnVisitModel.setId(returnVisitBean.getId());
            returnVisitModel.setCustomerCode(returnVisitBean.getCustomerCode());
            returnVisitModel.setVisitStaff(returnVisitBean.getVisitStaff());
            returnVisitModel.setVisitTime(visitTime);
            returnVisitModel.setVisitRecord(returnVisitBean.getVisitRecord());
            returnVisitModel.setNote(returnVisitBean.getNote());

            returnVisitModels.add(returnVisitModel);
        });

        return returnVisitModels;
    }

    public String countReturnVisit(ReturnVisitForm returnVisitForm) {

        User user = requestContext.getRequestUser();
        returnVisitForm = dealFormData(returnVisitForm);

        String count = returnVisitMapper.findCountByCondition(returnVisitForm,user.getRoleId());

        return count;
    }

    public String addReturnVisitLog(ReturnVisitModel returnVisitModel) {

        ReturnVisitInfo returnVisitInfo = new ReturnVisitInfo();
        returnVisitInfo = dealReturnVisitLog(returnVisitInfo,returnVisitModel);
        returnVisitInfo.setCreatTime(LocalDateTime.now());
        returnVisitInfo.setCustomerCode(returnVisitModel.getCustomerCode());
        returnVisitInfo.setRegionId(returnVisitModel.getRegion());

        returnVisitInfoRepository.save(returnVisitInfo);

        return ResponseConstants.SUCCESS;
    }

    public String updateReturnVisitLog(ReturnVisitModel returnVisitModel) {

        ReturnVisitInfo returnVisitInfo = returnVisitInfoRepository.findById(returnVisitModel.getId()).get();
        returnVisitInfo = dealReturnVisitLog(returnVisitInfo,returnVisitModel);
        returnVisitInfo.setUpdateTime(LocalDateTime.now());

        returnVisitInfoRepository.save(returnVisitInfo);

        return ResponseConstants.SUCCESS;
    }

    @Transactional(rollbackOn = Exception.class)
    public String deleteReturnVisitLog(String id) {

        if (StringUtils.isEmpty(id)){
            return ResponseConstants.FAILURE;
        }
        returnVisitInfoRepository.deleteById(Integer.valueOf(id));

        return ResponseConstants.SUCCESS;
    }

    public List<ReturnVisitModel> findReturnVisitByCustomerCode(String customerCode) {

        if (customerCode == null  || "undefined".equals(customerCode)){
            return null;
        }
        User user = requestContext.getRequestUser();
        List<ReturnVisitInfo> returnVisitBeans = returnVisitMapper.findReturnVisitByCustomerCode(customerCode,user.getRoleId());
        if (returnVisitBeans == null){
            return null;
        }

        List<ReturnVisitModel> returnVisitModels = new ArrayList<>();
        returnVisitBeans.forEach(returnVisitBean -> {
            ReturnVisitModel returnVisitModel = new ReturnVisitModel();

            String creatTime = returnVisitBean.getCreatTime().format(formatter);
            String updateTime = null;
            if (returnVisitBean.getUpdateTime() != null){
                updateTime = returnVisitBean.getUpdateTime().format(formatter);
            }
            Optional<User> optionalUser = userRepository.findById(returnVisitBean.getUserId());
            String visitTime = returnVisitBean.getVisitTime().format(formatter);

            returnVisitModel.setId(returnVisitBean.getId());
            returnVisitModel.setCustomerCode(returnVisitBean.getCustomerCode());
            returnVisitModel.setVisitStaff(returnVisitBean.getVisitStaff());
            returnVisitModel.setVisitTime(visitTime);
            returnVisitModel.setVisitRecord(returnVisitBean.getVisitRecord());
            returnVisitModel.setNote(returnVisitBean.getNote());
            returnVisitModel.setCreatTime(creatTime);
            returnVisitModel.setUpdateTime(updateTime);
            returnVisitModel.setUserName(optionalUser.get().getFullName());

            returnVisitModels.add(returnVisitModel);
        });

        return returnVisitModels;
    }

    private ReturnVisitInfo dealReturnVisitLog(ReturnVisitInfo returnVisitInfo, ReturnVisitModel returnVisitModel){

        User user = requestContext.getRequestUser();

        LocalDateTime visitTime = LocalDateTimeHelper.parse(returnVisitModel.getVisitTime());

        returnVisitInfo.setVisitStaff(returnVisitModel.getVisitStaff());
        returnVisitInfo.setVisitTime(visitTime);
        returnVisitInfo.setVisitRecord(returnVisitModel.getVisitRecord());
        returnVisitInfo.setNote(returnVisitModel.getNote());
        returnVisitInfo.setUserId(user.getId());

        return returnVisitInfo;
    }

    private ReturnVisitForm dealFormData(ReturnVisitForm returnVisitForm){

        if (returnVisitForm.getDateRange() != null && returnVisitForm.getDateRange().length != 0){
            LocalDateTime beginTime = LocalDateTimeHelper.parse(returnVisitForm.getDateRange()[0]);
            LocalDateTime endTime = LocalDateTimeHelper.parse(returnVisitForm.getDateRange()[1]).plusDays(1);

            returnVisitForm.setStartTime(beginTime);
            returnVisitForm.setEndTime(endTime);
        }

        returnVisitForm.setOffset((returnVisitForm.getPage() - 1) * returnVisitForm.getPageSize());

        return returnVisitForm;
    }

    @Autowired
    public void setReturnVisitMapper(ReturnVisitMapper returnVisitMapper) {
        this.returnVisitMapper = returnVisitMapper;
    }

    @Autowired
    public void setReturnVisitInfoRepository(ReturnVisitInfoRepository returnVisitInfoRepository) {
        this.returnVisitInfoRepository = returnVisitInfoRepository;
    }

    @Autowired
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
