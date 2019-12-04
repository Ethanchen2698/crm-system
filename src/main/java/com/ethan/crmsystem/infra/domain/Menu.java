package com.ethan.crmsystem.infra.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: Menu.java, 2018/8/6 16:11 $
 */
@Entity
@Table
@Data
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private String name;
}
