package com.applet.common.strategy;


import com.applet.common.result.ResultModel;

/**
 * @description: 策略抽象处理器
 * @author: LUNING
 * @create: 2019/4/8 9:39 AM
 */
public abstract class AbstractHandler {

    abstract public ResultModel handler(Class<?> clazz);

    public <T> T get(Class<T> clz,Object o){
        if(clz.isInstance(o)){
            return clz.cast(o);
        }
        return null;
    }
}
