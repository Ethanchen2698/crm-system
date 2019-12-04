package com.ethan.crmsystem.infra.repository;

import com.ethan.crmsystem.infra.domain.MetaRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: MetaRegionRepository.java, 2018/7/30 14:19 $
 */
@Repository
public interface MetaRegionRepository extends JpaRepository<MetaRegion, String> {

    /**
     * 按树形层级查找行政区
     */
    List<MetaRegion> findByLevel(String level);

    /**
     * 根据编码查找行政区
     *
     * @param code 编码
     * @return 行政区对象
     */
    Optional<MetaRegion> findByCode(String code);

    /**
     * 根据编码查找数量
     *
     * @param code 编码
     * @return 数量
     */
    Integer countByCode(String code);

    List<MetaRegion> findByParent(String parentId);

    Optional<MetaRegion> findByName(String name);
}
