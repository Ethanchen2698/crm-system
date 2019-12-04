package com.ethan.crmsystem.infra.repository;

import com.ethan.crmsystem.infra.domain.ReturnVisitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @version : ReturnVisitInfoRepository.java,2019/11/10 21:05 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Repository
public interface ReturnVisitInfoRepository extends JpaRepository<ReturnVisitInfo,Integer> {

    ReturnVisitInfo findByCustomerCode(String code);

    void deleteByCustomerCode(String code);
}
