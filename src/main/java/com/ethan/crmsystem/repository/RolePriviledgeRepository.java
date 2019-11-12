package com.ethan.crmsystem.repository;

import com.ethan.crmsystem.domain.RolePriviledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @author <a href="mailto:dengwq@hzwesoft.com">dengwq</a>
 * @version $Id: RolePriviledgeRepository.java, 2019/3/6 0006 17:14 $
 */
@Repository
public interface RolePriviledgeRepository extends JpaRepository<RolePriviledge, Integer> {

    void deleteByRoleId(Integer roleId);

    List<RolePriviledge> findByRoleId(Integer roleId);

    RolePriviledge findByMetaRegionIdAndRoleId(String value, Integer regionId);
}
