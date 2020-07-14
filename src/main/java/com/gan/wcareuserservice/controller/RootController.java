package com.gan.wcareuserservice.controller;

import com.gan.wcareuserservice.common.LogUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    private String root () {
        LogUtil.log("RootController called ... ");
        return "Welcome User!!!!";
    }

}
