package com.applet.controller;

import com.applet.common.redis.RedisTemplateUtils;
import com.applet.common.result.ResultModel;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/redis")
public class RedisController {

    @Autowired
    RedisTemplateUtils redisTemplateUtils;
    @Autowired
    RedisTemplate redisTemplate;

//    @RedisLockAnnoation(keys = {"#lockname"})
    @GetMapping("/locktest")
    public ResultModel<?> locktest(@RequestParam(name = "lockname") String watchkeys) {

        String re = "";

        int valint = (int) redisTemplateUtils.get(watchkeys);
        String userinfo = getRandomString(6);

        if (valint <= 100 && valint >= 1) {

            redisTemplateUtils.decr("watchkeys", 1);

            String failuserifo = "succ" + userinfo;
            String failinfo1 = "用户：" + failuserifo + "商品抢购成功";
            re = failinfo1;
            redisTemplateUtils.setIfAbsent(failuserifo, failinfo1);


        } else {
            String failuserifo = "kcfail" + userinfo;
            String failinfo1 = "用户：" + failuserifo + "商品被抢购完毕，抢购失败";
            re = failinfo1;
            redisTemplateUtils.setIfAbsent(failuserifo, failinfo1);
        }

        return ResultModel.succWithData(re);
    }

    @GetMapping("/j")
    public ResultModel<?> j() {
        final String watchkeys = "watchkeys";
        String userinfo = getRandomString(6);
        String re = "";
        try {
            redisTemplateUtils.watch(watchkeys);

            int valint = (int) redisTemplateUtils.get(watchkeys);

            if (valint <= 100 && valint >= 1) {

                redisTemplateUtils.multi();
                redisTemplateUtils.decr("watchkeys", 1);

                List<Object> list = redisTemplateUtils.exec();

                if (list == null || list.size() == 0) {

                    String failuserifo = "fail" + userinfo;
                    String failinfo = "用户：" + failuserifo + "商品争抢失败，抢购失败";
                    /* 抢购失败业务逻辑 */
                    redisTemplateUtils.setIfAbsent(failuserifo, failinfo);
                    re = failinfo;
                } else {
                    List<String> list1 = Lists.newArrayList();
                    for (Object succ : list) {
                        String succuserifo = "succ" + succ.toString() + userinfo;
                        String succinfo = "用户：" + succuserifo + "抢购成功，当前抢购成功人数:"
                                + (1 - (valint - 100));
                        /* 抢购成功业务逻辑 */
                        redisTemplateUtils.setIfAbsent(succuserifo, succinfo);
                        list1.add(succinfo);
                    }
                    return ResultModel.succWithData(list1);

                }

            } else {
                String failuserifo = "kcfail" + userinfo;
                String failinfo1 = "用户：" + failuserifo + "商品被抢购完毕，抢购失败";
                System.out.println(failinfo1);
                re = failinfo1;
                redisTemplateUtils.setIfAbsent(failuserifo, failinfo1);
                // Thread.sleep(500);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultModel.succWithData(re);
    }


    public String getRandomString(int length) {
        //length是随机字符串长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
