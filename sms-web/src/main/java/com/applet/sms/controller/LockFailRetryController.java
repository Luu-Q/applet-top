package com.applet.sms.controller;

import com.applet.common.result.ResultModel;
import com.applet.sms.service.LockFailRetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

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
    public ResultModel lockFailRetry() throws InterruptedException {
        int clientTotal = 100;
        // 同时并发执行的线程数
        int threadTotal = 20;
        int count = 0;
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量，此处用于控制并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);
        //闭锁，可实现计数器递减
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    //执行此方法用于获取执行许可，当总计未释放的许可数不超过200时，
                    //允许通行，否则线程阻塞等待，直到获取到许可。
                    semaphore.acquire();
                    lockFailRetryService.lockFailRetry();
                    //释放许可
                    semaphore.release();
                } catch (Exception e) {
                    //log.error("exception", e);
                    e.printStackTrace();

                }
                //闭锁减一
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();//线程阻塞，直到闭锁值为0时，阻塞才释放，继续往下执行
        executorService.shutdown();

//        lockFailRetryService.lockFailRetry();
        return ResultModel.succ();
    }
}
