/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.models;

import java.util.Date;

/**
 *
 * @author minhd
 */
public class Message {
    private ClientInfo sender;
    private ClientInfo receiver;
    private String content;
    private Date sendingTime;
    private Date receptionTime;
    
//    Constructors
    public Message() {
    }
    
    public Message(ClientInfo sender, ClientInfo receiver, String content, Date sendingTime, Date receptionTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sendingTime = sendingTime;
        this.receptionTime = receptionTime;
    }
    
//    Getters and setters
    public ClientInfo getSender() {
        return sender;
    }

    public void setSender(ClientInfo sender) {
        this.sender = sender;
    }

    public ClientInfo getReceiver() {
        return receiver;
    }

    public void setReceiver(ClientInfo receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    public Date getReceptionTime() {
        return receptionTime;
    }

    public void setReceptionTime(Date receptionTime) {
        this.receptionTime = receptionTime;
    }
}
