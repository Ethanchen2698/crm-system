package com.ethan.crmsystem.infra.repository;

import com.ethan.crmsystem.infra.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: UserRepository.java, 2018/8/7 9:31 $
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByRoleId(Integer roleId);

    Optional<User> findByLoginName(String loginName);

}
