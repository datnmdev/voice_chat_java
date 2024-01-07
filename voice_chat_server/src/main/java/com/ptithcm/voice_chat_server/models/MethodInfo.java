/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.models;

import java.lang.reflect.Type;

/**
 *
 * @author minhd
 */
public class MethodInfo {
    private String className;
    private String methodName;
    private Type bodyParameterType;

//    Constructors
    public MethodInfo(String className, String methodName, Type bodyParameterType) {
        this.className = className;
        this.methodName = methodName;
        this.bodyParameterType = bodyParameterType;
    }
    
//    Getters and setters
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Type getBodyParameterType() {
        return bodyParameterType;
    }

    public void setBodyParameterType(Type bodyParameterType) {
        this.bodyParameterType = bodyParameterType;
    }
}
