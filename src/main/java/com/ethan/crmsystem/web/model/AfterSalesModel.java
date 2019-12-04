package com.ethan.crmsystem.web.model;

import lombok.Data;

/**
 * Description:
 *
 * @version : AfterSalesModel.java,2019/11/13 22:12 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class AfterSalesModel {

    private Integer id;

    private String customerCode;

    private String equipmentId;

    private String dealStaff;

    private String dealTime;

    private String saleRecord;

    private String note;

    private String region;

    private String creatTime;

    private String updateTime;

    private String userName;
}
