package com.data.mybatisplus.controller;


import com.data.mybatisplus.service.ServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ln
 * @since 2019-06-11
 */
@RestController
@RequestMapping("/test")
public class ServiceItemController {

    @Autowired
    ServiceItemService itemService;

    @GetMapping("/test")
    public Object mybatisplus() {
        return itemService.getById(1);
    }

}

