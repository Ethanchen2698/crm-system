package com.ethan.crmsystem.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @version : customer.java,2019/11/10 16:57 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
@Table
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private String name;

    private String phonenum;

    private String address;

    private String regionId;

    private LocalDateTime updateTime;

    private String userId;

    private LocalDateTime creatTime;
}
