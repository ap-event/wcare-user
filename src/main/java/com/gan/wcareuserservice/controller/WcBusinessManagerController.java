package com.gan.wcareuserservice.controller;

import com.gan.wcareuserservice.entity.WcBusinessManager;
import com.gan.wcareuserservice.service.WcBusinessManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/businessmanager")
public class WcBusinessManagerController {

    @Autowired
    WcBusinessManagerService service;

    @CrossOrigin
    @GetMapping
    private List<WcBusinessManager> findAll() {
        return service.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @ResponseBody
    private WcBusinessManager findOne (@PathVariable("id") int id) {
        return service.find(id);
    }

    @CrossOrigin
    @PostMapping
    private WcBusinessManager create(@RequestBody WcBusinessManager entity) {
        return service.create(entity);
    }

    @CrossOrigin
    @PutMapping
    private WcBusinessManager update(@RequestBody WcBusinessManager entity) {
        return service.update(entity);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    private WcBusinessManager delete (@PathVariable("id") int id) {
        return service.delete(id);
    }
}
