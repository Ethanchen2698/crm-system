package com.ethan.crmsystem.mapper;

import com.ethan.crmsystem.mapper.bean.ReturnVisitBean;
import com.ethan.crmsystem.web.model.ReturnVisitForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @version : ReturnVisitMapper.java,2019/11/14 19:32 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Mapper
public interface ReturnVisitMapper {

    List<ReturnVisitBean> findReturnVisitLogByCondition(@Param("returnVisitForm")ReturnVisitForm returnVisitForm,
                                                        @Param("roleId") Integer roelId);

    String findCountByCondition(@Param("returnVisitForm") ReturnVisitForm returnVisitForm,
                                @Param("roleId") Integer roelId);
}
