package com.ethan.crmsystem.mapper.bean;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : EquipmentBean.java,2019/11/12 19:51 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class EquipmentBean {

    private String id;

    private String customer_code;

    private String device_name;

    private String installation_address;

    private LocalDateTime installation_time;

    private String price;

    private String sales_staff;

    private String installation_staff;

    private String region_id;

    private LocalDateTime update_time;

    private String user_id;

    private LocalDateTime creat_time;
}
