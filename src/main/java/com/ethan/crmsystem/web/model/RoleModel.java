package com.ethan.crmsystem.web.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Description:角色页面表单
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: RoleModel.java, 2018/8/6 16:43 $
 */
@Data
public class RoleModel {

    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private List<String> menus;

    @NotEmpty
    private List<String> rolePrividges;

}
