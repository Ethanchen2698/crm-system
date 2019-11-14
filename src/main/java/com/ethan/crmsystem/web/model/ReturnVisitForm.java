package com.ethan.crmsystem.web.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : ReturnVisitForm.java,2019/11/14 19:31 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class ReturnVisitForm {

    private String customerCode;

    private String visitStaff;

    private String[] dateRange;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer page;

    private Integer pageSize;

    private Integer offset;
}
