/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.store;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author minhd
 */
public class ThreadStore {
    public static ExecutorService executorService = Executors.newCachedThreadPool();
    public static Map<String, Future<?>> futureMap = new HashMap<>();
    
    public static void addTask(String taskName, Future<?> future) {
        if (futureMap.containsKey(taskName)) {
            futureMap.remove(taskName).cancel(true);
        }
        
        futureMap.put(taskName, future);
    }
    
    public static void endTask(String taskName) {
        futureMap.remove(taskName).cancel(true);
        
    }
}
