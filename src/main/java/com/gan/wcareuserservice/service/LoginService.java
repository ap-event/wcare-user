package com.gan.wcareuserservice.service;

import com.gan.wcareuserservice.common.CommonConstants;
import com.gan.wcareuserservice.common.LogUtil;
import com.gan.wcareuserservice.common.LoginUtil;
import com.gan.wcareuserservice.controller.WcUsersController;
import com.gan.wcareuserservice.entity.WcBusinessManager;
import com.gan.wcareuserservice.entity.WcCustomer;
import com.gan.wcareuserservice.entity.WcUsers;
import com.gan.wcareuserservice.entity.WcWealthManager;
import com.gan.wcareuserservice.model.LoginInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public static final Logger logger = LoggerFactory.getLogger(WcUsersController.class);

    @Autowired
    WcUsersService usersService;

    @Autowired
    WcBusinessManagerService businessManagerService;

    @Autowired
    WcCustomerService customerService;

    @Autowired
    WcWealthManagerService wealthManagerService;

    public void processLogin (LoginInfo loginInfo) {
        LogUtil.log("LoginService : doLogin started : " + loginInfo.getLoginId());

        loginInfo.clear();

        WcUsers wcUsers = usersService.fetchUserByLoginId(loginInfo.getLoginId());

        if (wcUsers == null) {
            LogUtil.log("LoginService : login failed : User doesn't exists");
            populateReturnCode (loginInfo, CommonConstants.LOGIN_RETURN_CODE_400, CommonConstants.LOGIN_RETURN_MSG_Invalid_LoginId);
        } else {
            if (LoginUtil.match(loginInfo.getPassword(), wcUsers.getPassword())) {
                LogUtil.log("LoginService : login success");
                populateReturnCode (loginInfo, CommonConstants.LOGIN_RETURN_CODE_200, CommonConstants.LOGIN_RETURN_MSG_Sucess);

                //Process
                processLoginSuccess(loginInfo, wcUsers);
            } else {
                LogUtil.log("LoginService : login failed : Invalid password");
                populateReturnCode (loginInfo, CommonConstants.LOGIN_RETURN_CODE_401, CommonConstants.LOGIN_RETURN_MSG_Invalid_Password);
            }
        }
    }

    private void processLoginSuccess(LoginInfo loginInfo, WcUsers wcUsers) {
        LoginUtil.populateRole(wcUsers.getRole(), loginInfo);

        loginInfo.setUserLoginEmailId(wcUsers.getEmailId());
        loginInfo.setUserLoginName(wcUsers.getUserName());
        loginInfo.setUserRole(wcUsers.getRole());

        if (loginInfo.isUserRoleCustomer()) {
            populateCustomer(loginInfo, wcUsers);
        } else if (loginInfo.isUserRoleBuisnessManager()) {
            populateBusinessManager(loginInfo, wcUsers);
        } else if (loginInfo.isUserRoleWealthManager()) {
            populateWealthManager(loginInfo, wcUsers);
        }
    }

    private void populateCustomer(LoginInfo loginInfo, WcUsers wcUsers) {
        WcCustomer entity = customerService.findOneByWcUserId(wcUsers.getId());
        if (entity == null) {
            LogUtil.log("LoginService : login failed : User/Customer doesn't exists");
            populateReturnCode (loginInfo, CommonConstants.LOGIN_RETURN_CODE_400, CommonConstants.LOGIN_RETURN_MSG_Invalid_LoginId);
        } else {
            loginInfo.setUserDisplayId(String.valueOf(entity.getId()));
            loginInfo.setUserDisplayName(entity.getFirstName() + " " + entity.getLastName());

            loginInfo.setValidLogin(true);
        }
    }

    private void populateWealthManager(LoginInfo loginInfo, WcUsers wcUsers) {
        WcWealthManager entity = wealthManagerService.findOneByWcUserId(wcUsers.getId());
        if (entity == null) {
            LogUtil.log("LoginService : login failed : User/WealthManager doesn't exists");
            populateReturnCode (loginInfo, CommonConstants.LOGIN_RETURN_CODE_400, CommonConstants.LOGIN_RETURN_MSG_Invalid_LoginId);
        } else {
            loginInfo.setUserDisplayId(String.valueOf(entity.getId()));
            loginInfo.setUserDisplayName(entity.getFirstName() + " " + entity.getLastName());

            loginInfo.setValidLogin(true);
        }
    }

    private void populateBusinessManager(LoginInfo loginInfo, WcUsers wcUsers) {
        WcBusinessManager entity = businessManagerService.findOneByWcUserId(wcUsers.getId());
        if (entity == null) {
            LogUtil.log("LoginService : login failed : User/BusinessManager doesn't exists");
            populateReturnCode (loginInfo, CommonConstants.LOGIN_RETURN_CODE_400, CommonConstants.LOGIN_RETURN_MSG_Invalid_LoginId);
        } else {
            loginInfo.setUserDisplayId(String.valueOf(entity.getId()));
            loginInfo.setUserDisplayName(entity.getFirstName() + " " + entity.getLastName());

            loginInfo.setValidLogin(true);
        }
    }

    private void populateReturnCode(LoginInfo loginInfo, String returnCode, String returnMessage) {
        loginInfo.setReturnCode(returnCode);
        loginInfo.setReturnMessage(returnMessage);
    }


}
