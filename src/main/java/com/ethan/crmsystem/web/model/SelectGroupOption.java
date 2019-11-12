package com.ethan.crmsystem.web.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Description: 配合element-ui 分组
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: SelectGroupOption.java, 2018/7/31 16:29 $
 */
@Data
public class SelectGroupOption {

    private String label;

    private String value;

    private String extension;

    private List<SelectGroupOption> children;

}
