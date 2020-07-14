package com.gan.wcareuserservice.controller;

import com.gan.wcareuserservice.model.LoginInfo;
import com.gan.wcareuserservice.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/public/login")
public class LoginController {

    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService service;

    @PostMapping
    @ResponseBody
    private LoginInfo doLogin(@RequestBody LoginInfo loginInfo) {
        service.processLogin(loginInfo);
        return loginInfo;
    }

}
