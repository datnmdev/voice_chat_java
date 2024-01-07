/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.models;

/**
 *
 * @author minhd
 */

public class Response<T> extends Packet<T> {
    private int statusCode;

//    Constructors
    public Response() {
        
    }
    
    public Response(int status,String header, T body) {
        super(header, body);
        this.statusCode = status;
    }
    
    public Response(int status, Packet<T> packet) {
        super(packet);
        this.statusCode = status;
    }
    
//    Getters and setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
