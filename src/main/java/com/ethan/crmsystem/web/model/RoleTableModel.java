package com.ethan.crmsystem.web.model;

import lombok.Data;

import java.util.List;

/**
 * Description: 角色列表表格元素
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: RoleTableModel.java, 2018/8/6 17:22 $
 */
@Data
public class RoleTableModel {

    private Integer id;

    private String name;

    private List<String> menuNames;

    private List<String> menuIds;

    private List<String> priviledges;

    private List<SelectGroupOption> roleRegions;

}
