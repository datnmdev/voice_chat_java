/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.models;

/**
 *
 * @author minhd
 */
public class Request<T> extends Packet<T> {
    
//    Constructors
    public Request() {
        
    }
    
    public Request(String header, T body) {
        super(header, body);
    }

    public Request(Packet<T> packet) {
        super(packet);
    }
}
