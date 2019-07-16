package com.applet.sms.controller.push;

import com.applet.sms.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/push")
public class PushController {


    @Autowired
    PushService pushService;

    @GetMapping(value = "/test")
    public String test() {
        pushService.push();
        return "";
    }


}
