package com.ethan.crmsystem.repository;

import com.ethan.crmsystem.domain.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @version : EquipmentRepository.java,2019/11/10 21:03 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,String> {
}
