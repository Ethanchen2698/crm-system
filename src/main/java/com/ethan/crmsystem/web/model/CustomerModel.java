package com.ethan.crmsystem.web.model;

import lombok.Data;

/**
 * Description:
 *
 * @version : CustomerInfoModel.java,2019/11/9 15:46 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class CustomerModel {

    private Integer id;

    /**
     * 地区id
     */
    private String region;

    /**
     * 客户编号
     */
    private String code;

    private String name;

    private String phonenum;

    private String address;

    /**
     * 创建客户的时间
     */
    private String creatTime;

    private String updateTime;

    private String userName;

    private String equipmentCount;

    private String returnVisitCount;
}
