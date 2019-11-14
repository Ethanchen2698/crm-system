package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.RequestContext;
import com.ethan.crmsystem.common.ResponseConstants;
import com.ethan.crmsystem.domain.AfterSalesInfo;
import com.ethan.crmsystem.domain.User;
import com.ethan.crmsystem.mapper.AfterSalesMapper;
import com.ethan.crmsystem.mapper.bean.AfterSalesBean;
import com.ethan.crmsystem.repository.AfterSalesInfoRepository;
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

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<AfterSalesModel> findEquipmentTable(AfterSalesForm afterSalesForm) {

        User user = requestContext.getRequestUser();
        afterSalesForm = dealFormData(afterSalesForm);

        List<AfterSalesBean> afterSalesBeans = afterSalesMapper.findAfterSalesLogByCondition(afterSalesForm,user.getRoleId());

        List<AfterSalesModel> afterSalesModels = new ArrayList<>();
        afterSalesBeans.forEach(afterSalesBean -> {
            AfterSalesModel afterSalesModel = new AfterSalesModel();
            String dealTime = afterSalesBean.getDeal_time().format(formatter);

            afterSalesModel.setId(afterSalesBean.getId());
            afterSalesModel.setCustomerCode(afterSalesBean.getCustomer_code());
            afterSalesModel.setEquipmentId(afterSalesBean.getEquipment_id());
            afterSalesModel.setDealStaff(afterSalesBean.getDeal_staff());
            afterSalesModel.setDealTime(dealTime);
            afterSalesModel.setSaleRecord(afterSalesBean.getSale_record());
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

        AfterSalesInfo afterSalesInfo = afterSalesInfoRepository.findById(Integer.valueOf(afterSalesModel.getId())).get();
        afterSalesInfo = dealAfterSalesLog(afterSalesInfo,afterSalesModel);
        afterSalesInfo.setCreatTime(LocalDateTime.now());

        afterSalesInfoRepository.save(afterSalesInfo);

        return ResponseConstants.SUCCESS;
    }

    public String updateAfterSalesLog(AfterSalesModel afterSalesModel) {

        AfterSalesInfo afterSalesInfo = new AfterSalesInfo();
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

    private AfterSalesInfo dealAfterSalesLog(AfterSalesInfo afterSalesInfo, AfterSalesModel afterSalesModel){

        User user = requestContext.getRequestUser();

        LocalDateTime dealTime = LocalDateTime.parse(afterSalesModel.getDealTime(),formatter);

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
}
