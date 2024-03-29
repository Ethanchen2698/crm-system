package com.ethan.crmsystem.infra.repository;

import com.ethan.crmsystem.infra.domain.AfterSalesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @version : AferSalesInfoRepository.java,2019/11/10 20:59 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Repository
public interface AfterSalesInfoRepository extends JpaRepository<AfterSalesInfo,Integer> {

    AfterSalesInfo findByCustomerCode(String code);

    void deleteByCustomerCode(String code);

    void deleteByEquipmentId(String id);
}
