package com.ethan.crmsystem.web.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Description: 用户页面表格展示
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: UserTableModel.java, 2018/8/7 15:36 $
 */
@Entity
@Data
public class UserTableModel {

    @Id
    private String id;

    private String loginName;

    private String fullName;

    private String subDepartments;

    private Integer roleId;

    private String roleName;

    private String mobile;

    private String email;

    private String password;

    private String type;

}
