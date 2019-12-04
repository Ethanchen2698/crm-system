package com.ethan.crmsystem.infra.repository;

import com.ethan.crmsystem.infra.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description:
 *
 * @version : CustomerRepository.java,2019/11/10 21:01 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findByCode(String code);

    void deleteByCode(String code);
}
