package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.RequestContext;
import com.ethan.crmsystem.common.ResponseConstants;
import com.ethan.crmsystem.infra.domain.*;
import com.ethan.crmsystem.infra.mapper.AfterSalesMapper;
import com.ethan.crmsystem.infra.mapper.CustomerMapper;
import com.ethan.crmsystem.infra.mapper.EquipmentMapper;
import com.ethan.crmsystem.infra.mapper.ReturnVisitMapper;
import com.ethan.crmsystem.infra.repository.*;
import com.ethan.crmsystem.utils.LocalDateTimeHelper;
import com.ethan.crmsystem.web.model.CustomerForm;
import com.ethan.crmsystem.web.model.CustomerModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @version : CustomerService.java,2019/11/10 21:20 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Service
@Slf4j
public class CustomerService {

    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper;

    private MetaRegionRepository metaRegionRepository;

    private UserRepository userRepository;

    private EquipmentRepository equipmentRepository;

    private AfterSalesInfoRepository afterSalesInfoRepository;

    private ReturnVisitInfoRepository returnVisitInfoRepository;

    private EquipmentMapper equipmentMapper;

    private ReturnVisitMapper returnVisitMapper;

    private RequestContext requestContext;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<CustomerModel> findCustomerTable(CustomerForm customerForm){

        User user = requestContext.getRequestUser();

        customerForm = dealFormData(customerForm);
        List<Customer> customerList = customerMapper.findCustomerInfoByCondition(customerForm,user.getRoleId());

        List<CustomerModel> customerModels = new ArrayList<>();
        customerList.forEach(customer -> {
            CustomerModel customerModel = new CustomerModel();
            String equipmentCount = equipmentMapper.findEquipmentCountByCode(customer.getCode(),user.getRoleId());
            if (equipmentCount == null){
                equipmentCount = "0";
            }
            String returnVisitCount = returnVisitMapper.findReturnVisitCountByCode(customer.getCode(),user.getRoleId());
            if (returnVisitCount == null){
                returnVisitCount = "0";
            }
            String creatTime = customer.getCreatTime().format(format);

            customerModel.setId(customer.getId());
            customerModel.setCode(customer.getCode());
            customerModel.setName(customer.getName());
            customerModel.setAddress(customer.getAddress());
            customerModel.setPhonenum(customer.getPhonenum());
            customerModel.setRegion(customer.getRegionId());
            customerModel.setCreatTime(creatTime);
            customerModel.setEquipmentCount(equipmentCount);
            customerModel.setReturnVisitCount(returnVisitCount);

            customerModels.add(customerModel);
        });
        return customerModels;
    }

    public String countCustomer(CustomerForm customerForm){

        User user = requestContext.getRequestUser();

        customerForm = dealFormData(customerForm);
        String count = customerMapper.findCountByCondition(customerForm,user.getRoleId());

        return count;
    }

    public String findCustomerCode(String code) {

        if (code == null  || "undefined".equals(code)){
            return null;
        }
        Customer customer = customerRepository.findByCode(code);
        if (customer != null ){
            return customer.getCode();
        }
        return null;
    }

    @Transactional(rollbackOn = Exception.class)
    public String addCustomer(CustomerModel customerModel) {

        Customer customer = new Customer();
        customer = dealCustomer(customer,customerModel);
        customer.setCreatTime(LocalDateTime.now());

        customerRepository.save(customer);

        return ResponseConstants.SUCCESS;
    }

    @Transactional(rollbackOn = Exception.class)
    public String updateCustomer(CustomerModel customerModel) {

        Customer customer = customerRepository.findById(customerModel.getId()).get();
        // 如果前端更新customerCode或者地区，则下列三种也要更新
        if (!StringUtils.equals(customer.getCode(),customerModel.getCode()) || !StringUtils.equals(customer.getRegionId(),customerModel.getRegion())){
            User user = requestContext.getRequestUser();
            Equipment equipment = equipmentRepository.findByCustomerCode(customer.getCode());
            equipment.setCustomerCode(customerModel.getCode());
            equipment.setRegionId(customerModel.getRegion());
            equipment.setUserId(user.getId());
            equipment.setUpdateTime(LocalDateTime.now());
            equipmentRepository.save(equipment);

            AfterSalesInfo afterSalesInfo = afterSalesInfoRepository.findByCustomerCode(customer.getCode());
            afterSalesInfo.setCustomerCode(customerModel.getCode());
            afterSalesInfo.setRegionId(customerModel.getRegion());
            afterSalesInfo.setUserId(user.getId());
            afterSalesInfo.setUpdateTime(LocalDateTime.now());
            afterSalesInfoRepository.save(afterSalesInfo);

            ReturnVisitInfo returnVisitInfo = returnVisitInfoRepository.findByCustomerCode(customer.getCode());
            returnVisitInfo.setCustomerCode(customerModel.getCode());
            returnVisitInfo.setRegionId(customerModel.getRegion());
            returnVisitInfo.setUserId(user.getId());
            afterSalesInfo.setUpdateTime(LocalDateTime.now());
            returnVisitInfoRepository.save(returnVisitInfo);
        }

        customer = dealCustomer(customer,customerModel);
        customer.setUpdateTime(LocalDateTime.now());

        customerRepository.save(customer);

        return ResponseConstants.SUCCESS;
    }

    @Transactional(rollbackOn = Exception.class)
    public String deleteCustomer(String code) {

        if (StringUtils.isEmpty(code)){
            return ResponseConstants.FAILURE;
        }
        customerRepository.deleteByCode(code);
        equipmentRepository.deleteByCustomerCode(code);
        afterSalesInfoRepository.deleteByCustomerCode(code);
        returnVisitInfoRepository.deleteByCustomerCode(code);

        return ResponseConstants.SUCCESS;
    }

    public CustomerModel findCustomerInfoByCode(String code) {

        if (code == null  || "undefined".equals(code)){
            return null;
        }
        Customer customer = customerRepository.findByCode(code);

        if (customer == null){
            return null;
        }

        CustomerModel customerModel = new CustomerModel();
        String creatTime = customer.getCreatTime().format(format);
        String updateTime = null;
        if (customer.getUpdateTime() != null){
            updateTime = customer.getUpdateTime().format(format);
        }
        Optional<User> user = userRepository.findById(customer.getUserId());

        customerModel.setId(customer.getId());
        customerModel.setCode(customer.getCode());
        customerModel.setName(customer.getName());
        customerModel.setAddress(customer.getAddress());
        customerModel.setPhonenum(customer.getPhonenum());
        customerModel.setRegion(customer.getRegionId());
        customerModel.setCreatTime(creatTime);
        customerModel.setUpdateTime(updateTime);
        customerModel.setUserName(user.get().getFullName());

        return customerModel;
    }

    private Customer dealCustomer(Customer customer,CustomerModel customerModel){

        User user = requestContext.getRequestUser();

        customer.setCode(customerModel.getCode());
        customer.setName(customerModel.getName());
        customer.setPhonenum(customerModel.getPhonenum());
        customer.setAddress(customerModel.getAddress());
        customer.setRegionId(customerModel.getRegion());
        customer.setUserId(user.getId());

        return customer;
    }

    private CustomerForm dealFormData(CustomerForm customerForm){

        if (customerForm.getDateRange() != null && customerForm.getDateRange().length != 0){
            LocalDateTime beginTime = LocalDateTimeHelper.parse(customerForm.getDateRange()[0]);
            LocalDateTime endTime = LocalDateTimeHelper.parse(customerForm.getDateRange()[1]).plusDays(1);

            customerForm.setStartTime(beginTime);
            customerForm.setEndTime(endTime);
        }

        if ("3".equals(customerForm.getLevel())){
            customerForm.getRegionIds().add(customerForm.getRegion());
        }else if ("2".equals(customerForm.getLevel())){
            Optional<MetaRegion> metaRegions = metaRegionRepository.findById(customerForm.getRegion());
            metaRegions.get().getChildren().forEach(metaRegion -> {
                customerForm.getRegionIds().add(metaRegion.getId());
            });
        }else if ("1".equals(customerForm.getLevel())){
            Optional<MetaRegion> metaRegionlist = metaRegionRepository.findById(customerForm.getRegion());
            metaRegionlist.get().getChildren().forEach(metaRegions -> {
                metaRegions.getChildren().forEach(metaRegion -> {
                    customerForm.getRegionIds().add(metaRegion.getId());
                });
            });
        }

        customerForm.setOffset((customerForm.getPage() - 1) * customerForm.getPageSize());

        return customerForm;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Autowired
    public void setMetaRegionRepository(MetaRegionRepository metaRegionRepository) {
        this.metaRegionRepository = metaRegionRepository;
    }

    @Autowired
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Autowired
    public void setAfterSalesInfoRepository(AfterSalesInfoRepository afterSalesInfoRepository) {
        this.afterSalesInfoRepository = afterSalesInfoRepository;
    }

    @Autowired
    public void setReturnVisitInfoRepository(ReturnVisitInfoRepository returnVisitInfoRepository) {
        this.returnVisitInfoRepository = returnVisitInfoRepository;
    }

    @Autowired
    public void setReturnVisitMapper(ReturnVisitMapper returnVisitMapper) {
        this.returnVisitMapper = returnVisitMapper;
    }

    @Autowired
    public void setEquipmentMapper(EquipmentMapper equipmentMapper) {
        this.equipmentMapper = equipmentMapper;
    }
}
