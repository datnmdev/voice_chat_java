/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ptithcm.voice_chat_client.interfaces;

/**
 *
 * @author minhd
 */
@FunctionalInterface
public interface HandleEventFunction<T> {
    void apply(T body);
}
