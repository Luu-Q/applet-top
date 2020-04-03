package com.applet.scoreHandler;

import java.util.concurrent.*;

public class CachedData {

    private static ConcurrentHashMap<String, FutureTask> fileNameData = new ConcurrentHashMap();
    private static ExecutorService exec = Executors.newCachedThreadPool();

    public String processFile(String fName) throws ExecutionException, InterruptedException {
        FutureTask data = fileNameData.get(fName);
        //“偶然”-- 1000个线程同时到这里，同时发现data为null
        if (data == null) {
            data = newFutureTask(fName);
            FutureTask old = fileNameData.putIfAbsent(fName, data);
            if (old != null) {
                data = old;
            } else {
                exec.execute(data);
            }
        }
        String d = (String) data.get();
        //process with data
        return d;
    }

    private FutureTask newFutureTask(final String file) {
        return new FutureTask(new Callable<String>() {
            public String call() {
                return readFromFile(file);
            }

            private String readFromFile(String file) {
                //todo 处理业务
                return "test";
            }
        });
    }

}