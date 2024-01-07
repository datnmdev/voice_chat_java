/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ptithcm.voice_chat_client.enums;

/**
 *
 * @author minhd
 */
public enum ResponseStatus {
    OK(0),
    UNAUTHORIZATION(1),
    GATEWAY_TIMEOUT(2),
    CONNECT_FAILED(3),
    ERROR(4);
    
    private int value;

//    Constructors
    private ResponseStatus(int value) {
        this.value = value;
    }

//    Methods
    public int getValue() {
        return value;
    }
}
