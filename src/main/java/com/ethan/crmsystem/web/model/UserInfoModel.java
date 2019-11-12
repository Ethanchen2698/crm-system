package com.ethan.crmsystem.web.model;

import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: UserInfoModel.java, 2018/8/8 9:58 $
 */
@Data
public class UserInfoModel {

    private String id;

    private List<String> privilege;

    private String token;

    private String loginName;

    private String fullName;

}
