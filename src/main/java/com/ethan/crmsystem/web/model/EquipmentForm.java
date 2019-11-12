package com.ethan.crmsystem.web.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : EquipmentForm.java,2019/11/12 19:54 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class EquipmentForm {

    private String customerCode;

    private String deviceName;

    private String price;

    private String saleStaff;

    private String insStaff;

    private String[] dateRange;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer page;

    private Integer pageSize;

    private Integer offset;
}
