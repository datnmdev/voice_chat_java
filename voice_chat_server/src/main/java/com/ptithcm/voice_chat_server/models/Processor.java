/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.models;

import com.ptithcm.voice_chat_server.annotations.Body;
import com.ptithcm.voice_chat_server.annotations.Controller;
import com.ptithcm.voice_chat_server.annotations.RequestMapping;
import com.ptithcm.voice_chat_server.store.ReflectionStore;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

/**
 *
 * @author minhd
 */
public class Processor {

    public static void processAnnotations() {
        Map<String, MethodInfo> methodInfoMap = new HashMap<>();
        String packageName = "com.ptithcm.voice_chat_server.controllers"; // Thay thế bằng gói của bạn

        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class); // Thay thế MyAnnotation bằng annotation hoặc sử dụng getSubTypesOf(Class<?> type)

        for (Class<?> clazz : classes) {
            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();

                for (Annotation annotation : annotations) {
                    if (annotation instanceof RequestMapping) {
                        RequestMapping requestMapping = (RequestMapping) annotation;
                        
                        Parameter bodyParameter = null;
                        Parameter[] parameters = method.getParameters();
                        for (Parameter parameter : parameters) {
                            Annotation[] parameterAnnotations = parameter.getAnnotations();

                            for (Annotation parameterAnnotation : parameterAnnotations) {
                                if (parameterAnnotation instanceof Body) {
                                    bodyParameter = parameter;
                                }
                            }
                        }
                        
                        if (bodyParameter != null) {
                            methodInfoMap.put(requestMapping.header(), 
                                    new MethodInfo(clazz.getName(), method.getName(), 
                                            bodyParameter.getParameterizedType()));
                        }
                    }
                }

            }
        }
        ReflectionStore.methodInfoMap = methodInfoMap;
    }
}
