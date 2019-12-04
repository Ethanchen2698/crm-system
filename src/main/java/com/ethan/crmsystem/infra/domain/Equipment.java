package com.ethan.crmsystem.infra.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : equipment.java,2019/11/10 17:03 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
@Table
@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String customerCode;

    private String deviceName;

    private String installationAddress;

    private LocalDateTime installationTime;

    private String price;

    private String salesStaff;

    private String installationStaff;

    private String regionId;

    private LocalDateTime updateTime;

    private String userId;

    private LocalDateTime creatTime;
}
