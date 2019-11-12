package com.ethan.crmsystem.mapper;

import com.ethan.crmsystem.mapper.bean.EquipmentBean;
import com.ethan.crmsystem.web.model.EquipmentForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @version : EquipmentMapper.java,2019/11/12 19:51 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Mapper
public interface EquipmentMapper {

    List<EquipmentBean> findEquipmentByCondition(@Param("equipmentForm") EquipmentForm equipmentForm,
                                                 @Param("roleId") Integer roelId);

    String findCountByCondition(@Param("equipmentForm") EquipmentForm equipmentForm,
                                @Param("roleId") Integer roelId);
}
