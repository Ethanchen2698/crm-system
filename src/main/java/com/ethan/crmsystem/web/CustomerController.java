package com.ethan.crmsystem.web;

import com.ethan.crmsystem.service.CustomerService;
import com.ethan.crmsystem.web.model.CustomerForm;
import com.ethan.crmsystem.web.model.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @version : CustomerController.java,2019/11/9 15:44 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/findCustomerTable")
    public List<CustomerModel> findCustomerTable(@Validated CustomerForm customerForm){
        return customerService.findCustomerTable(customerForm);
    }

    @PostMapping("/countCustomer")
    public String countCustomer(@Validated CustomerForm customerForm){
        return customerService.countCustomer(customerForm);
    }

    @GetMapping("/findCustomerCode/{code}")
    public String findCustomerCode(@PathVariable String code){
        return customerService.findCustomerCode(code);
    }

    @PostMapping("/addCustomer")
    public String addCustomer(@Validated CustomerModel customerModel){
        return customerService.addCustomer(customerModel);
    }

    @PostMapping("/updateCustomer")
    public String updateCustomer(@Validated CustomerModel customerModel){
        return customerService.updateCustomer(customerModel);
    }

    @DeleteMapping("/deleteCustomer/{code}")
    public String deleteCustomer(@PathVariable String code){
        return customerService.deleteCustomer(code);
    }

    @GetMapping("/findCustomerInfoByCode/{code}")
    public CustomerModel findCustomerInfoByCode(@PathVariable String code){
        return customerService.findCustomerInfoByCode(code);
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
