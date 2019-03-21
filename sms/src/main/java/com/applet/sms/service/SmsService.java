package com.applet.sms.service;

import org.springframework.stereotype.Service;


@Service
public class SmsService {

    public String test(String name) {

        return "hello world!!!"+name;
    }

}
