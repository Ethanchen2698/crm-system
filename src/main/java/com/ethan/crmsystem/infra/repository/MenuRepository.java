package com.ethan.crmsystem.infra.repository;

import com.ethan.crmsystem.infra.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: MenuRepository.java, 2018/8/6 16:14 $
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
