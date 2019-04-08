package com.applet.common.strategy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
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

    @Order(97)
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        Map<String, Class> handlerMap = Maps.newHashMapWithExpectedSize(3);
//        ClassScaner.scan(SCORE_HANDLER, HandlerType.class).forEach(clazz -> {
//            String type = clazz.getAnnotation(HandlerType.class).value();
//            handlerMap.put(type, clazz);
//        });
        HandlerContext handlerContext = new HandlerContext(handlerBeans);
        configurableListableBeanFactory.registerSingleton(HandlerContext.class.getName(), handlerContext);
    }

    @Order(98)
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext() != null) {
            handlerBeans = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(HandlerType.class);
//            Map<String, Object> beans = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(HandlerType.class);
//            Set<Map.Entry<String, Object>> entries = beans.entrySet();
//            entries.forEach(obj ->{
//                String type = obj.getClass().getAnnotation(HandlerType.class).value();
//                handlerBeans.put(type, obj.getClass());
//            });
        }
    }
}
