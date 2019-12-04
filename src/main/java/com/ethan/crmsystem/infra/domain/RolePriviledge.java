package com.ethan.crmsystem.infra.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Description:
 *
 * @version : rolePriviledge.java,2019/11/7 22:23 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
@Table
@Entity
public class RolePriviledge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer roleId;

    private String metaRegionId;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "metaRegionId", updatable = false, insertable = false)
    private MetaRegion metaRegion;
}
