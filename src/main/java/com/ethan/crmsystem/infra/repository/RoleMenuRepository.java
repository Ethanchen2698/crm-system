package com.ethan.crmsystem.infra.repository;

import com.ethan.crmsystem.infra.domain.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: RoleMenuRepository.java, 2018/8/6 16:50 $
 */
@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Integer> {

    void deleteByRoleId(Integer roleId);

}
