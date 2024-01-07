/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.models;

/**
 *
 * @author minhd
 */
public class Packet<T> {
    private String header;
    private T body;

//    Constructors
    public Packet() {
        
    }
    
    public Packet(String header, T body) {
        this.header = header;
        this.body = body;
    }

    public Packet(Packet<T> packet) {
        if (packet != null) {
            this.header = packet.getHeader();
            this.body = (T) packet.getBody();
        }
    }
    
//    Getters and setters
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
