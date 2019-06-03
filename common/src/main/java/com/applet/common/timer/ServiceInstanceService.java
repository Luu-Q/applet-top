package com.applet.common.timer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @Author LUNING
 * @Description 解决分布式定时任务多点执行的问题
 * @create 2019-05-14 10:28
 * @return
 **/
@Component
@Slf4j
public class ServiceInstanceService {

    @Value("${server.port}")
    private String serverPort;

    public int getServiceIndex(List<String> servers) {
        if (servers == null || servers.size() == 0) {
            throw new RuntimeException("getServiceIndex：服务列表为空");
        }

        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            int i = 0;
            for (String server : servers) {
                log.info("server = {}", server);
                String[] ipAndPort = server.split(":");
                if (ip.equals(ipAndPort[0]) && serverPort.equals(ipAndPort[1])) {
                    return i;
                }
                i++;
            }
        } catch (UnknownHostException e) {
            log.error("getServiceIndex：获取本机IP异常", e);
        }
        return -1;
    }

    public int getServiceCount(List<String> servers) {
        return servers.size();
    }

}
