package com.ethan.crmsystem.web.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : ReturnVisitModel.java,2019/11/14 19:30 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class ReturnVisitModel {

    private Integer id;

    private String customerCode;

    private String visitTime;

    private String visitStaff;

    private String visitRecord;

    private String note;
}
