package com.applet.config.redis.redislock;


import org.apache.ibatis.javassist.ClassClassPath;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;

public class ReflectParamNames {
    private static final Logger log = LoggerFactory.getLogger(ReflectParamNames.class);
    private static ClassPool pool = ClassPool.getDefault();

    static {
        ClassClassPath classPath = new ClassClassPath(ReflectParamNames.class);
        pool.insertClassPath(classPath);
    }

    public static String[] getNames(String className, String methodName) {
        CtClass cc = null;
        try {
            cc = pool.get(className);
            CtMethod cm = cc.getDeclaredMethod(methodName);
            // 使用javaassist的反射方法获取方法的参数名
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr == null) return new String[0];

            int begin = 0;

            String[] paramNames = new String[cm.getParameterTypes().length];
            int count = 0;
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;

            for (int i = 0; i < attr.tableLength(); i++) {
                //  为什么 加这个判断，发现在windows 跟linux执行时，参数顺序不一致，通过观察，实际的参数是从this后面开始的
                if (attr.variableName(i).equals("this")) {
                    begin = i;
                    break;
                }
            }

            for (int i = begin + 1; i <= begin + paramNames.length; i++) {
                paramNames[count] = attr.variableName(i);
                count++;
            }
            return paramNames;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cc != null) cc.detach();
            } catch (Exception e2) {
                log.error(e2.getMessage());
            }


        }
        return new String[0];
    }
}