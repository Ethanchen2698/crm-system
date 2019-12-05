package com.ethan.crmsystem.infra.mapper;

import com.ethan.crmsystem.infra.domain.Customer;
import com.ethan.crmsystem.web.model.CustomerForm;
import com.ethan.crmsystem.web.model.StatisticsForm;
import com.ethan.crmsystem.web.model.StatisticsModel;
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

//    List<StatisticsModel> findStatisticsData(@Param("statisticsForm") StatisticsForm statisticsForm,@Param("roleId") Integer roleId);

    String findStatisticsData(@Param("value") String value,@Param("level") String level);
}
