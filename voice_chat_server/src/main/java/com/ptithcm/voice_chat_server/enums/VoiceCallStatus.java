/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.enums;

/**
 *
 * @author minhd
 */
public enum VoiceCallStatus {
    DIALING(0),
    RINGING(1),
    PROGRESS(2),
    ENDED(3),
    BUSY(4),
    NOT_ANSWERING(5),
    MISSED_CALL(6),
    CANCEL(7);
    
    private int value;

//    Constructors
    private VoiceCallStatus(int value) {
        this.value = value;
    }
    
//    Getters and setters
    public int getValue() {
        return value;
    }
}
