package com.ethan.crmsystem.web.model;

import lombok.Data;

import java.util.List;

/**
 * Description: 对应element-ui Cascader 级联选择器数据
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: CascadeSelectOption.java, 2018/8/8 14:00 $
 */
@Data
public class CascadeSelectOption {

    private String value;

    private String label;

    private List<CascadeSelectOption> children;

}
