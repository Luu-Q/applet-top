package com.applet.sms.timer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: todo
 * @author: LUNING
 * @create: 2019-05-14 10:51
 */
@Component
public class TestTimer {

    @Value("#{'${timer.sms-test.servers}'.split(',')}")
    private List<String> servers;

    @Scheduled(cron = "${timer.sms-test.cron}")
    public void sendDataSchedule() {

    }
}
