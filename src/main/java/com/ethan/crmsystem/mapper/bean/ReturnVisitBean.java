package com.ethan.crmsystem.mapper.bean;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : ReturnVisitBean.java,2019/11/14 19:32 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class ReturnVisitBean {

    private Integer id;

    private String customer_code;

    private LocalDateTime visit_time;

    private String visit_staff;

    private String visit_record;

    private String note;

    private LocalDateTime update_time;

    private String user_id;

    private LocalDateTime creat_time;
}
