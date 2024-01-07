/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ptithcm.voice_chat_server.enums;

/**
 *
 * @author minhd
 */
public enum Headers {
    KEY_EXCHANGE("Key-Exchange");

    private String value;
    
//    Constructors
    private Headers(String value) {
        this.value = value;
    }
    
//    Methods
    public String getValue() {
        return value;
    }
}
