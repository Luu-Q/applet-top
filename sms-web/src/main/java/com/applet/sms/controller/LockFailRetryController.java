package com.applet.sms.controller;

import com.applet.common.result.ResultModel;
import com.applet.sms.service.LockFailRetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 乐观锁失败重试测试
 * @author: LUNING
 * @create: 2019-05-10 21:23
 */
@RestController
@RequestMapping(value = "/retry")
public class LockFailRetryController {

    @Autowired
    LockFailRetryService lockFailRetryService;

    @GetMapping(value = "/test")
    public ResultModel lockFailRetry() {
        return lockFailRetryService.lockFailRetry();
    }
}
