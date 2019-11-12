package com.ethan.crmsystem.domain;

import com.ethan.crmsystem.common.UUIDGenerator;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 行政区划
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: MetaRegion.java, 2018/7/30 13:56 $
 */
@Entity
@Data
@Table
public class MetaRegion {

    public MetaRegion() {
        this.id = UUIDGenerator.random();
    }

    /**
     * UUID
     */
    @Id
    private String id;

    private String level;

    private String name;

    /**
     * 父uuid
     */
    private String parent;

    /**
     * 行政区划编码
     */
    private String code;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent")
    private List<MetaRegion> children = new ArrayList<>();

}
