package com.gan.wcareuserservice.jpa;

import com.gan.wcareuserservice.entity.WcCustomer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WcCustomerRepository extends CrudRepository<WcCustomer, Integer> {

    public WcCustomer findOneByWcUserId(Integer wcUserId);

}

