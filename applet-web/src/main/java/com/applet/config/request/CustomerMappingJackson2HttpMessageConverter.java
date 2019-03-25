package com.applet.config.request;


import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

/**
 * 处理@RequestBody注解为String的情况，只支持接收单个参数的情况
 */
public class CustomerMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Class<?> deseriClazz = getClazz(clazz);
        Object param = super.readInternal(deseriClazz, inputMessage);
        return getTrueObject(clazz, param);

    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Type deseriType = getType(type);
        Object param = super.read(deseriType, contextClass, inputMessage);
        return getTrueObject(type, param);
    }

    /**
     * 通过返回参数类型决定是否处理参数，如果是String类型的参数，将解析后的HashMap里的值返回（只支持单个参数）
     *
     * @param type  返回参数类型
     * @param param 参数值
     * @return 实际参数值
     */
    private Object getTrueObject(Type type, Object param) {
        if (type == String.class) {
            Object backParam = null;
            if (param != null && param instanceof LinkedHashMap) {
                LinkedHashMap paramMap = (LinkedHashMap) param;
                if (paramMap.size() == 1) {
                    backParam = paramMap.get(paramMap.keySet().iterator().next());
                }
            }
            param = backParam;
        }
        return param;
    }

    /**
     * 获取解析参数用的Type
     *
     * @param type 参数类型
     * @return
     */
    private Type getType(Type type) {
        Type deseriClazz;
        if (type == String.class) {
            //jackson不支持String默认用LinkedHashMap
            deseriClazz = LinkedHashMap.class;
        } else {
            deseriClazz = type;
        }
        return deseriClazz;
    }

    /**
     * 获取解析参数用的Type
     *
     * @param clazz 参数类型
     * @return
     */
    private Class<?> getClazz(Class<?> clazz) {
        Class<?> deseriClazz;
        if (clazz == String.class) {
            //jackson不支持String默认用LinkedHashMap
            deseriClazz = LinkedHashMap.class;
        } else {
            deseriClazz = clazz;
        }
        return deseriClazz;
    }

}
