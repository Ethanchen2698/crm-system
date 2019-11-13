package com.ethan.crmsystem.mapper.bean;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : AfterSalesBean.java,2019/11/13 22:15 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class AfterSalesBean {

    private Integer id;

    private String customer_code;

    private String equipment_id;

    private String deal_staff;

    private LocalDateTime deal_time;

    private String sale_record;

    private String note;

    private LocalDateTime update_time;

    private String user_id;

    private LocalDateTime creat_time;
}
