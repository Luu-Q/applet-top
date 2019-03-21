package com.applet.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestScheduled {

    @Scheduled(cron = "0 0/1 * * * ?")
    public void sendDataSchedule() {
        log.info("---------------------------------sendDataSchedule--------------------------------");
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void sendDataSchedule2() {
        log.info("---------------------------------sendDataSchedule2-------------------------------");
    }
}
