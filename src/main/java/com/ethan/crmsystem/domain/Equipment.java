package com.ethan.crmsystem.domain;

import com.ethan.crmsystem.common.UUIDGenerator;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    public Equipment(){this.id = UUIDGenerator.random();}

    /**
     * UUID
     */
    @Id
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
