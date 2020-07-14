package com.gan.wcareuserservice.jpa;

import com.gan.wcareuserservice.entity.WcWealthManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WcWealthManagerRepository extends CrudRepository<WcWealthManager, Integer> {

    public WcWealthManager findOneByWcUserId(Integer wcUserId);


}

