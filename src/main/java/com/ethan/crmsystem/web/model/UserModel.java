package com.ethan.crmsystem.web.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @version : UserModel.java,2019/11/6 23:58 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class UserModel {

    private String id;

    @NotEmpty
    private String loginName;

    @NotEmpty
    private String fullName;

    @NotEmpty
    private String subDepartments;

    @NotNull
    private Integer roleId;

    private String mobile;

    @Email
    private String email;

    @NotEmpty
    private String type;

}
