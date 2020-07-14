package com.gan.wcareuserservice.jpa;

import com.gan.wcareuserservice.entity.WcBusinessManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WcBusinessManagerRepository extends CrudRepository<WcBusinessManager, Integer> {

    public WcBusinessManager findOneByWcUserId(Integer wcUserId);

}

