package com.ethan.crmsystem.mapper.bean;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : customer.java,2019/11/10 16:57 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class CustomerBean {

    private Integer id;

    private String code;

    private String name;

    private String phonenum;

    private String address;

    private String region_id;

    private LocalDateTime update_time;

    private String user_id;

    private LocalDateTime creat_time;
}
