package com.gan.wcareuserservice.controller;

import com.gan.wcareuserservice.common.ControllerUtil;
import com.gan.wcareuserservice.entity.WcCustomer;
import com.gan.wcareuserservice.service.WcCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class WcCustomerController {

    @Autowired
    WcCustomerService service;

    @CrossOrigin
    @GetMapping
    private List<WcCustomer> findAll() {
        return service.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @ResponseBody
    private WcCustomer findOne (@PathVariable("id") int id) {
        return service.find(id);
    }

    @CrossOrigin
    @PostMapping
    private ResponseEntity<?> create(@RequestBody WcCustomer entity) {
        Object result = service.create(entity);
        return ControllerUtil.getResponseEntityForCreate(result);
    }

    @CrossOrigin
    @PutMapping
    private WcCustomer update(@RequestBody WcCustomer entity) {
        return service.update(entity);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    private WcCustomer delete (@PathVariable("id") int id) {
        return service.delete(id);
    }

}
