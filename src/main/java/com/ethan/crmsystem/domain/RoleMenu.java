package com.ethan.crmsystem.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: RoleMenu.java, 2018/8/6 16:46 $
 */
@Entity
@Table
@Data
public class RoleMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer roleId;

    private Integer menuId;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "menuId", updatable = false, insertable = false)
    private Menu menu;

}
