package com.applet.common.strategy;

import com.ykly.app.utils.SpringUtil;

import java.util.Map;

/**
 * @description: 策略上下文
 * @author: LUNING
 * @create: 2019/4/8 9:45 AM
 */
public class HandlerContext {

//    private Map<String, Class> handlerMap;
//
//    public HandlerContext(Map<String, Class> handlerMap) {
//        this.handlerMap = handlerMap;
//    }
//
//    public AbstractHandler getInstance(String type) {
//        Class clazz = handlerMap.get(type);
//        if (null == clazz) {
//            throw new IllegalArgumentException("not found handler for type :" + type);
//        }
//        return (AbstractHandler) SpringUtil.getBean(clazz);
//    }

    private Map<String, Object> handlerMap;

    public HandlerContext(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractHandler getInstance(String type) {
        Object object = handlerMap.get(type);
        if (null == object) {
            throw new IllegalArgumentException("not found handler for type :" + type);
        }
        return (AbstractHandler) SpringUtil.getBean(object.getClass());
    }
}
