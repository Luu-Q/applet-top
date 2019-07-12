package com.applet.sms.controller;

import com.applet.sms.client.SmsClient;
import com.applet.sms.entity.dto.SmsConfigBean;
import com.applet.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


@RestController
@RequestMapping("/sms")
public class SmsController implements SmsClient {

    @Autowired
    SmsService smsService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    SmsConfigBean smsConfigBean;

    @GetMapping(value = "/test")
    public String test(@RequestParam(value = "name") String name) {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(address.getHostName());//主机名
        System.out.println(address.getCanonicalHostName());//主机别名
        System.out.println(address.getHostAddress());//获取IP地址
        return smsService.test(name) + ";prot:" + request.getLocalPort() + ";ip:" + address.getHostAddress() + "-" + address.getHostName() + "-" + address.getCanonicalHostName();
    }

    @GetMapping(value = "/configing")
    public SmsConfigBean configing() {
        String enp = smsConfigBean.getEnp();
        return smsConfigBean;
    }

    public String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

}
