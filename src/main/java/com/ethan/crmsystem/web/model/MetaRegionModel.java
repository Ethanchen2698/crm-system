package com.ethan.crmsystem.web.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Description: 对应页面的form列表元素
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: MetaRegionModel.java, 2018/7/31 16:56 $
 */
@Data
public class MetaRegionModel {

    private String id;

    @NotEmpty
    private String parent;

    @NotEmpty
    private String name;

    @NotEmpty
    private String code;

    @NotEmpty
    private String level;

}
