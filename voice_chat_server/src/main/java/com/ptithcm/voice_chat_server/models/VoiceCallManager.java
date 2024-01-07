/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author minhd
 */
public class VoiceCallManager {
    private VoiceCall currentCall;
    private Map<String, List<VoiceCall>> voiceCallMap;
    
//    Constructors
    public VoiceCallManager() {
        this.voiceCallMap = new HashMap<>();
    }
    
//    Methods
    public void createSingleVoiceCall(VoiceCall voiceCall) {
        currentCall = voiceCall;
    }
    
    public void recordCallHistory(String uuid, VoiceCall voiceCall) {
        if (!voiceCallMap.containsKey(uuid)) {
            voiceCallMap.put(uuid, new ArrayList<>());
        }
        voiceCallMap.get(uuid).add(voiceCall);
    }
    
    public void endSingleVoiceCall() {
        currentCall = null;
    }
    
//    Getters and setters
    public VoiceCall getCurrentCall() {
        return currentCall;
    }

    public void setCurrentCall(VoiceCall currentCall) {
        this.currentCall = currentCall;
    }

    public Map<String, List<VoiceCall>> getVoiceCallMap() {
        return voiceCallMap;
    }

    public void setVoiceCallMap(Map<String, List<VoiceCall>> voiceCallMap) {
        this.voiceCallMap = voiceCallMap;
    }
}
