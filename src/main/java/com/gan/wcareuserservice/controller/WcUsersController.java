package com.gan.wcareuserservice.controller;

import com.gan.wcareuserservice.common.ControllerUtil;
import com.gan.wcareuserservice.entity.WcUsers;
import com.gan.wcareuserservice.service.WcUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class WcUsersController {

    public static final Logger logger = LoggerFactory.getLogger(WcUsersController.class);

    @Autowired
    WcUsersService service;

    @CrossOrigin
    @GetMapping
    private List<WcUsers> findAll() {
        return service.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @ResponseBody
    private WcUsers findOne (@PathVariable("id") int id) {
        return service.find(id);
    }

    @PostMapping
    private ResponseEntity<?>  create(@RequestBody WcUsers entity) {
        Object createResult = service.create(entity);
        return ControllerUtil.getResponseEntityForCreate(createResult);
    }

    @CrossOrigin
    @PutMapping
    private WcUsers update(@RequestBody WcUsers entity) {
        return service.update(entity);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    private WcUsers delete (@PathVariable("id") int id) {
        return service.delete(id);
    }

    @CrossOrigin
    @GetMapping("/delay/{time}")
    private List<WcUsers> findAllWithDelay(@PathVariable("time") long time) {
        return service.findAllWithDelay(time);
    }


}
