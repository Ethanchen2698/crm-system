package com.ethan.crmsystem.infra.mapper;

import com.ethan.crmsystem.web.model.UserInfoModel;
import com.ethan.crmsystem.web.model.UserTableModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: UserModelMapper.java, 2018/8/7 16:13 $
 */
@Mapper
public interface UserModelMapper {

    List<UserTableModel> findTable(@Param("name") String name,  @Param("roleId") Integer roleId,
                                   @Param("limit") Integer limit,
                                   @Param("offset") Integer offset);

    Integer count(@Param("name") String name, @Param("unitId") String unitId,
                  @Param("roleId") Integer roleId);

    UserTableModel findByLoginName(String loginName);

    UserInfoModel loadUserInfo(String userId);
}
