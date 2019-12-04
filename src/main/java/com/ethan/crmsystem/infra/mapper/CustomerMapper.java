package com.ethan.crmsystem.infra.mapper;

import com.ethan.crmsystem.infra.domain.Customer;
import com.ethan.crmsystem.web.model.CustomerForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @version : CustomerMapper.java,2019/11/10 22:29 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Mapper
public interface CustomerMapper {

    List<Customer> findCustomerInfoByCondition(@Param("customerForm")CustomerForm customerForm,
                                               @Param("roleId") Integer roleId);

    String findCountByCondition(@Param("customerForm")CustomerForm customerForm,
                                @Param("roleId") Integer roleId);
}
