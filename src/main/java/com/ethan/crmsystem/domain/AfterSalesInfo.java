package com.ethan.crmsystem.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : AfterSalesInfo.java,2019/11/10 17:16 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
@Table
@Entity
public class AfterSalesInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String customerCode;

    private String equipmentId;

    private String dealStaff;

    private LocalDateTime dealTime;

    private String saleRecord;

    private String note;

    private String regionId;

    private LocalDateTime updateTime;

    private String userId;

    private LocalDateTime creatTime;
}
