package com.gan.wcareuserservice.controller;

import com.gan.wcareuserservice.common.ControllerUtil;
import com.gan.wcareuserservice.entity.WcWealthManager;
import com.gan.wcareuserservice.service.WcWealthManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/wealthmanager")
public class WcWealthManagerController {

    @Autowired
    WcWealthManagerService service;

    @CrossOrigin
    @GetMapping
    private List<WcWealthManager> findAll() {
        return service.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @ResponseBody
    private WcWealthManager findOne (@PathVariable("id") int id) {
        return service.find(id);
    }

    @CrossOrigin
    @PostMapping
    private ResponseEntity<?> create(@RequestBody WcWealthManager entity) {
        Object result = service.create(entity);
        return ControllerUtil.getResponseEntityForCreate(result);
    }

    @CrossOrigin
    @PutMapping
    private WcWealthManager update(@RequestBody WcWealthManager entity) {
        return service.update(entity);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    private WcWealthManager delete (@PathVariable("id") int id) {
        return service.delete(id);
    }
}
