package com.data.mybatisplus.controller;


import com.data.mybatisplus.service.ServiceItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ln
 * @since 2019-06-11
 */
@Api(tags = "教师点评")
@RestController
@RequestMapping("/mybatis")
public class ServiceItemController {

    @Autowired
    ServiceItemService itemService;
    @Autowired
    DiscoveryClient discovery;

    @ApiOperation(value = "调班班级列表", notes = "调班班级列表")
    @GetMapping("/test")
    public Object mybatisplus() {
        List<String> services = discovery.getServices();
        return itemService.getById(1);
    }


    @ApiOperation(value = "调班班级列表1", notes = "调班班级列表1")
    @GetMapping("/hello")
    public Object mybatisplus1() {
        return itemService.getById(1);
    }

}

