package com.ethan.crmsystem.domain;

import com.ethan.crmsystem.common.UUIDGenerator;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: User.java, 2018/8/7 9:29 $
 */
@Data
@Entity
@Table
public class User {

    public User() {
        this.id = UUIDGenerator.random();
    }

    @Id
    private String id;

    private String loginName;

    private String fullName;

    private String subDepartments;

    private Integer roleId;

    private String mobile;

    private String email;

    private String password;

    private String type;

    public User(User user) {
        this.id = user.getId();
        this.loginName = user.getLoginName();
        this.password = user.getPassword();
    }

}
