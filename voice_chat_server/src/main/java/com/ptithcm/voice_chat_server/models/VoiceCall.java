/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.models;

import java.util.Date;

/**
 *
 * @author minhd
 */
public class VoiceCall {
    private ClientInfo from;
    private ClientInfo to;
    private int status;
    private Date startTime;
    private Date endTime;
    private Date callAnswerTime;

//    Constructors
    public VoiceCall(ClientInfo from, ClientInfo to, int status, Date startTime, Date endTime) {
        this.from = from;
        this.to = to;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
//    Getters and setters
    public ClientInfo getFrom() {
        return from;
    }

    public void setFrom(ClientInfo from) {
        this.from = from;
    }

    public ClientInfo getTo() {
        return to;
    }

    public void setTo(ClientInfo to) {
        this.to = to;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCallAnswerTime() {
        return callAnswerTime;
    }

    public void setCallAnswerTime(Date callAnswerTime) {
        this.callAnswerTime = callAnswerTime;
    }
}
