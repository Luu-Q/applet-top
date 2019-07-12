package com.applet.sms.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.applet.sms.entity.dto.SkillUpgrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/applet-zuul")
@Controller
@Configuration
public class TestController {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/router")
    @ResponseBody
    public String router() {
        RestTemplate tpl = getRestTemplate();
        String json = tpl.getForObject("http://first-police/call/1", String.class);
        return json;
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/list")
    @ResponseBody
    public String serviceCount() {
        List<String> names = discoveryClient.getServices();
        for (String serviceId : names) {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
            System.out.println(serviceId + ": " + instances.size());
        }
        return "";
    }

    @GetMapping("/meta")
    @ResponseBody
    public String getMetadata() {
        List<ServiceInstance> instances = discoveryClient.getInstances("clean-applet");
        for (ServiceInstance ins : instances) {
            String name = ins.getMetadata().get("clean-applet");
            System.out.println(ins.getPort() + "---" + name);
        }
        return "";
    }

    public static void main(String[] args) {
        // 集合1
        List<SkillUpgrade> lists = new ArrayList<>();
        SkillUpgrade s = new SkillUpgrade();
        s.setLv(1);
        s.setAppearNum(100);
        lists.add(s);
        SkillUpgrade s2 = new SkillUpgrade();
        s2.setLv(2);
        s2.setAppearNum(200);
        lists.add(s2);
        // 集合1
        List<SkillUpgrade> listx = new ArrayList<>();
        SkillUpgrade x = new SkillUpgrade();
        x.setLv(1);
        x.setSelectNum(1100);
        listx.add(x);
        SkillUpgrade x2 = new SkillUpgrade();
        x2.setLv(2);
        x2.setSelectNum(1200);
        listx.add(x2);
        // 把list转map,{k=lv,vaule=并为自身}  . SkillUpgrade->SkillUpgrade或Function.identity()
        Map<Integer, SkillUpgrade> map = listx.stream()
                .collect(Collectors.toMap(SkillUpgrade::getLv, SkillUpgrade -> SkillUpgrade));
        System.out.println("map:=" + map);
        // 合并
        lists.forEach(n -> {
            // 如果等级一致
            if (map.containsKey(n.getLv())) {
                SkillUpgrade obj = map.get(n.getLv());
                // 把数量复制过去
                n.setSelectNum(obj.getSelectNum());
            }
        });
        System.out.println("lists:=" + lists);
        // 重复问题
        Map<Integer, SkillUpgrade> keyRedo = listx.stream()
                .collect(Collectors.toMap(SkillUpgrade::getLv, Function.identity(), (key1, key2) -> key2));
        // 方式二：指定实例的map
        Map<Integer, SkillUpgrade> linkedHashMap = listx.stream().collect(Collectors.toMap(SkillUpgrade::getLv,
                SkillUpgrade -> SkillUpgrade, (key1, key2) -> key2, LinkedHashMap::new));
    }

    @GetMapping("/test-zuul")
    @ResponseBody
    public String testZuul() {
        return "testZuul";
    }

}
