package com.ethan.crmsystem.infra.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : ReturnVisitInfo.java,2019/11/10 17:20 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
@Table
@Entity
public class ReturnVisitInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String customerCode;

    private LocalDateTime visitTime;

    private String visitStaff;

    private String visitRecord;

    private String note;

    private String regionId;

    private LocalDateTime updateTime;

    private String userId;

    private LocalDateTime creatTime;
}
