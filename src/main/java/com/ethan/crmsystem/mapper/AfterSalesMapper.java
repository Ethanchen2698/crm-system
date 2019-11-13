package com.ethan.crmsystem.mapper;

import com.ethan.crmsystem.mapper.bean.AfterSalesBean;
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

    List<AfterSalesBean> findAfterSalesLogByCondition(@Param("afterSalesForm") AfterSalesForm afterSalesForm,
                                                      @Param("roleId") Integer roelId);

    String findCountByCondition(@Param("afterSalesForm") AfterSalesForm afterSalesForm,
                                @Param("roleId") Integer roelId);
}
