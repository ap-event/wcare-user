package com.gan.wcareuserservice.service;

import com.gan.wcareuserservice.common.CommonConstants;
import com.gan.wcareuserservice.common.DateUtil;
import com.gan.wcareuserservice.common.LogUtil;
import com.gan.wcareuserservice.entity.*;
import com.gan.wcareuserservice.entity.WcCustomer;
import com.gan.wcareuserservice.jpa.WcCustomerRepository;
import com.gan.wcareuserservice.model.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WcCustomerService {

    @Autowired
    WcUsersService usersService;

    @Autowired
    WcCustomerRepository repository;

    @Autowired
    ImageService imageService;

    @Autowired
    WcWealthManagerService  wealthManagerService;

    public List<WcCustomer> findAll() {
        List<WcCustomer> list = new ArrayList();
        repository.findAll().forEach(listItem -> {
                    list.add(listItem);
                    listItem.setStartDateString(DateUtil.convertMMMYYY(listItem.getStartDate()));
                    listItem.setImage(imageService.createImageUrl(listItem.getGender(), listItem.getId(), true));
                    populateWealthManagerName(listItem);
                }
        );
        return list;
    }

    public Object create(WcCustomer wcCustomer) {
        Object result;

        LogUtil.log("WcCustomerService : create : " + wcCustomer.getEmailId());

        //Create User
        WcUsers wcUsers = new WcUsers(wcCustomer.getEmailId(), "wc", wcCustomer.getEmailId(), CommonConstants.ROLE_CUSTOMER);

        result = usersService.create(wcUsers);

        if (result instanceof CustomError) {
            LogUtil.log("WcCustomerService : User not created");
        } else {
            //Copy the userId from the newly created entity
            wcUsers = (WcUsers) result;
            wcCustomer.setWcUserId(wcUsers.getId());

            //Create Customer
            wcCustomer = repository.save(wcCustomer);
            LogUtil.log("WcCustomerService : User created : " + wcUsers.getId());
            result = wcCustomer;
        }

        return result;
    }

    public WcCustomer update(WcCustomer wcCustomer) {
        LogUtil.log("WcCustomerService : update : " + wcCustomer.getId());

        //Update wcusers
        usersService.update(wcCustomer.getWcUserId(), wcCustomer.getEmailId());;

        repository.save(wcCustomer);
        wcCustomer = find(wcCustomer.getId());
        return wcCustomer;
    }

    public WcCustomer find(Integer id) {
        WcCustomer wcCustomer = repository.findById(id).get();
        populateWealthManagerName(wcCustomer);
        return wcCustomer;
    }

    private void populateWealthManagerName(WcCustomer wcCustomer) {
        String welathManagerName = "";
        if (wcCustomer.getWcWealthManagerId() > 0) {
            WcWealthManager wcWealthManager = wealthManagerService.find(wcCustomer.getWcWealthManagerId());
            if (wcWealthManager != null) {
                welathManagerName = wcWealthManager.getFirstName() + " " + wcWealthManager.getLastName();
            }
            wcCustomer.setWealthManagerName(welathManagerName);
        }
    }

    public WcCustomer delete(int id) {

        LogUtil.log("WcCustomerService : delete : " + id);

        WcCustomer wcCustomer = find(id);
        if(wcCustomer != null){
            usersService.delete(wcCustomer.getWcUserId());
            repository.delete(wcCustomer);
        }
        return wcCustomer;
    }

    public WcCustomer findOneByWcUserId(Integer id) {
        return repository.findOneByWcUserId(id);
    }

}
