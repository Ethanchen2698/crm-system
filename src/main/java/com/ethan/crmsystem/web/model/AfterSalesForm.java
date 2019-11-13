package com.ethan.crmsystem.web.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : AfterSalesForm.java,2019/11/13 22:13 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class AfterSalesForm {

    private String customerCode;

    private String equipmentId;

    private String dealStaff;

    private String[] dateRange;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer page;

    private Integer pageSize;

    private Integer offset;
}
