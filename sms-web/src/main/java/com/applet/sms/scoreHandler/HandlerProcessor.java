package com.applet.sms.scoreHandler;

import com.applet.common.strategy.HandlerContext;
import com.applet.common.strategy.HandlerType;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 扫描HandlerType，初始化HandlerContext
 * @author: LUNING
 * @create: 2019/4/8 9:41 AM
 */
@Component
public class HandlerProcessor implements BeanFactoryPostProcessor {


    private static final String SCORE_HANDLER = "com.applet.sms.scoreHandler";

    public static Map<String, Class> handlerMap = new ConcurrentHashMap<String, Class>();

    static {
        //反射工具包，指明扫描路径
        Reflections reflections = new Reflections(SCORE_HANDLER);
        //获取带HandlerType注解的类
        Set<Class<?>> classList = reflections.getTypesAnnotatedWith(HandlerType.class);
        classList.forEach(clazz -> {
            String type = clazz.getAnnotation(HandlerType.class).value();
            handlerMap.put(type, clazz);
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        HandlerContext handlerContext = new HandlerContext(handlerMap);
        configurableListableBeanFactory.registerSingleton(HandlerContext.class.getName(), handlerContext);
    }

}
