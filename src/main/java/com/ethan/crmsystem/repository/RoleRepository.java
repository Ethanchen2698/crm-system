package com.ethan.crmsystem.repository;

import com.ethan.crmsystem.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: RoleRepository.java, 2018/8/6 16:47 $
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);

}
