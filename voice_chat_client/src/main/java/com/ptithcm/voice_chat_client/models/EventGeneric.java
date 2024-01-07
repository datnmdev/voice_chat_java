/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ptithcm.voice_chat_client.interfaces.HandleEventFunction;
import com.ptithcm.voice_chat_client.store.ThreadStore;
import java.util.concurrent.Future;

/**
 *
 * @author minhd
 */
public class EventGeneric<T> {
    private TCPClientSocket tcpcs;
    private Class<?> bodyClass;

//    Constructors
    public EventGeneric(TCPClientSocket tcpcs, Class<?> bodyClass) {
        this.tcpcs = tcpcs;
        this.bodyClass = bodyClass;
    }

//    Methods
    public void on(String event, HandleEventFunction<T> handleEventFunction) {
        Future<?> future = ThreadStore.executorService.submit(() -> {
            while (!Thread.interrupted()) {
                if (tcpcs.getResponseData().containsKey(event)) {
                    Response<T> response = new Gson().fromJson(tcpcs.getResponseData().remove(event), TypeToken.getParameterized(Response.class, bodyClass).getType());
                    handleEventFunction.apply(response.getBody());
                }
            }
        });
        
        ThreadStore.addTask(event, future);
    }
}
