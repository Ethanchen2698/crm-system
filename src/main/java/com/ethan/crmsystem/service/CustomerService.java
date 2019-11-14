package com.ethan.crmsystem.service;

import com.ethan.crmsystem.common.RequestContext;
import com.ethan.crmsystem.common.ResponseConstants;
import com.ethan.crmsystem.domain.Customer;
import com.ethan.crmsystem.domain.MetaRegion;
import com.ethan.crmsystem.domain.User;
import com.ethan.crmsystem.mapper.CustomerMapper;
import com.ethan.crmsystem.mapper.bean.CustomerBean;
import com.ethan.crmsystem.repository.CustomerRepository;
import com.ethan.crmsystem.repository.MetaRegionRepository;
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

    private RequestContext requestContext;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<CustomerModel> findCustomerTable(CustomerForm customerForm){

        User user = requestContext.getRequestUser();

        customerForm = dealFormData(customerForm);
        List<CustomerBean> customerList = customerMapper.findCustomerInfoByCondition(customerForm,user.getRoleId());

        List<CustomerModel> customerModels = new ArrayList<>();
        customerList.forEach(customer -> {
            CustomerModel customerModel = new CustomerModel();
            String creatTime = customer.getCreat_time().format(format);

            customerModel.setId(customer.getId());
            customerModel.setCode(customer.getCode());
            customerModel.setName(customer.getName());
            customerModel.setAddress(customer.getAddress());
            customerModel.setPhonenum(customer.getPhonenum());
            customerModel.setRegion(customer.getRegion_id());
            customerModel.setCreatTime(creatTime);

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

    public String findCustomerByCode(String code) {

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

        return ResponseConstants.SUCCESS;
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
}
