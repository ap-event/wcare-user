package com.gan.wcareuserservice.service;

import com.gan.wcareuserservice.common.CommonConstants;
import com.gan.wcareuserservice.common.DateUtil;
import com.gan.wcareuserservice.common.LogUtil;
import com.gan.wcareuserservice.entity.WcUsers;
import com.gan.wcareuserservice.entity.WcWealthManager;
import com.gan.wcareuserservice.jpa.WcWealthManagerRepository;
import com.gan.wcareuserservice.model.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WcWealthManagerService {

    @Autowired
    WcUsersService usersService;

    @Autowired
    WcWealthManagerRepository repository;

    @Autowired
    ImageService imageService;

    public List<WcWealthManager> findAll() {
        List<WcWealthManager> list = new ArrayList();
        repository.findAll().forEach(listItem -> {
                    list.add(listItem);
                    listItem.setStartDateString(DateUtil.convertMMMYYY(listItem.getStartDate()));
                    listItem.setImage(imageService.createImageUrl(listItem.getGender(), listItem.getId(), true));
                }
        );
        return list;
    }

    public Object create(WcWealthManager wcWealthManager) {
        LogUtil.log("WcWealthManagerService : create Started");
        System.out.println("WcWealthManagerService : create :" + wcWealthManager.getEmailId());

        Object result;

        //Create User
        WcUsers wcUsers = new WcUsers(wcWealthManager.getEmailId(), "wc", wcWealthManager.getEmailId(), CommonConstants.ROLE_WEALTH_MANAGER);
        result = usersService.create(wcUsers);

        if (result instanceof CustomError) {
            LogUtil.log("WcWealthManagerService : User not created");
        } else {
            //Copy the userId from the newly created entity
            wcUsers = (WcUsers) result;
            wcWealthManager.setWcUserId(wcUsers.getId());

            //Create Customer
            wcWealthManager = repository.save(wcWealthManager);
            LogUtil.log("WcWealthManagerService : User created : " + wcUsers.getId());
            result = wcWealthManager;
        }

        LogUtil.log("WcWealthManagerService : create Completed");

        return result;
    }

    public WcWealthManager update(WcWealthManager wcWealthManager) {
        System.out.println("WcWealthManagerService : update :" + wcWealthManager.getId());

        repository.save(wcWealthManager);
        wcWealthManager = find(wcWealthManager.getId());
        return wcWealthManager;
    }

    public WcWealthManager find(Integer id) {
        return repository.findById(id).get();
    }

    public WcWealthManager delete(int id) {
        System.out.println("WcWealthManagerService : delete :" + id);

        WcWealthManager wcWealthManager = find(id);
        if(wcWealthManager != null){
            usersService.delete(wcWealthManager.getWcUserId());

            repository.delete(wcWealthManager);
        }
        return wcWealthManager;
    }

    public WcWealthManager findOneByWcUserId(Integer id) {
        return repository.findOneByWcUserId(id);
    }

}
