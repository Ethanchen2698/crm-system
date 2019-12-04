package com.ethan.crmsystem.infra.mapper;

import com.ethan.crmsystem.infra.domain.AfterSalesInfo;
import com.ethan.crmsystem.infra.domain.Customer;
import com.ethan.crmsystem.web.model.AfterSalesForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @version : AfterSalesMapper.java,2019/11/13 22:15 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Mapper
public interface AfterSalesMapper {

    List<AfterSalesInfo> findAfterSalesLogByCondition(@Param("afterSalesForm") AfterSalesForm afterSalesForm,
                                                      @Param("roleId") Integer roelId);

    String findCountByCondition(@Param("afterSalesForm") AfterSalesForm afterSalesForm,
                                @Param("roleId") Integer roelId);

    List<AfterSalesInfo> findAfterSaleByCustomerCode(@Param("customerCode") String customerCode, @Param("roleId") Integer roleId);

    String findAfterSaleCountByEquipmentId(@Param("equipmentId")String equipmentId, @Param("roleId")Integer roleId);
}
