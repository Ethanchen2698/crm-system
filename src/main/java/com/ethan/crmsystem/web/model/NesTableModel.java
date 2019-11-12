package com.ethan.crmsystem.web.model;

import lombok.Data;

import java.util.List;

/**
 * Description: nestable结构数据
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: NesTableModel.java, 2018/8/1 14:12 $
 */
@Data
public class NesTableModel {

    private String leftOption;

    private String rightOption;

    private boolean showPlus;

    private boolean showEdit;

    private boolean showTrash;

    private String value;

    private String level;

    private List<NesTableModel> children;

}
