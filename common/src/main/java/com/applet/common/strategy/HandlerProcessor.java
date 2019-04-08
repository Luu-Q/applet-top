package com.applet.common.strategy;

import com.applet.common.scoreHandler.SpringContextHolder;
import com.applet.common.scoreHandler.getContent;
import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: 扫描HandlerType，初始化HandlerContext
 * @author: LUNING
 * @create: 2019/4/8 9:41 AM
 */
@Component
public class HandlerProcessor implements BeanFactoryPostProcessor, ApplicationListener<ContextRefreshedEvent> {


    private static final String SCORE_HANDLER = "com.ykly.app.couponAndscore.scoreHandler";

    Map<String, Object> handlerBeans = null;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Map<String, Class> handlerMap = Maps.newHashMapWithExpectedSize(3);
        ClassScaner.scan(SCORE_HANDLER, HandlerType.class).forEach(clazz -> {
            String type = clazz.getAnnotation(HandlerType.class).value();
            handlerMap.put(type, clazz);
        });
        HandlerContext handlerContext = new HandlerContext(handlerBeans);
        configurableListableBeanFactory.registerSingleton(HandlerContext.class.getName(), handlerContext);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext() != null) {
            handlerBeans = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(HandlerType.class);
            for (Map.Entry<String, Object> entrymap : handlerBeans.entrySet()) {
                try {
                    // 通过反射获取相关的实现类的Object
                    Object object = getContent.getTarget(entrymap.getValue());
                    if (object != null) {
                        AbstractHandler annotationService = (AbstractHandler) object;
                        // 不为空的情况下，获取实现类的注解对象
                        // 并把注解对象的注解字段当做map的Key,实现类Object当做值
                        HandlerType info = annotationService.getClass().getAnnotation(HandlerType.class);
                        getContent.getPersonbeanmap.put(info.value(), object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
