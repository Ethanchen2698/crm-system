package com.ethan.crmsystem.mapper;

import com.ethan.crmsystem.mapper.bean.CustomerBean;
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

    List<CustomerBean> findCustomerInfoByCondition(@Param("customerForm")CustomerForm customerForm,
                                                   @Param("roleId") Integer roleId);

    String findCountByCondition(@Param("customerForm")CustomerForm customerForm,
                                @Param("roleId") Integer roleId);
}
