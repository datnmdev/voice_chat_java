/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.models;

/**
 *
 * @author minhd
 */
public class ClientInfo {
    private String name;
    private String uuid;
    private String ip;

//    Constructors
    public ClientInfo() {
        
    }

    public ClientInfo(String name, String uuid, String ip) {
        this.name = name;
        this.uuid = uuid;
        this.ip = ip;
    }
    
//    Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
