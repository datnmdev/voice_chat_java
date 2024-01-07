/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.models;

import com.ptithcm.voice_chat_client.store.CallStore;
import com.ptithcm.voice_chat_client.store.SocketStore;
import com.ptithcm.voice_chat_client.store.ThreadStore;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author minhd
 */
public class VoiceCallControls {
    private UDPServerSocket udpss;
    private Sound sound;

//    Constructors
    public VoiceCallControls(UDPServerSocket udpss) {
        this.udpss = udpss;
        this.sound = new Sound();
    }

//    Methods
    public void start() throws LineUnavailableException {
//        Open microphone
        sound.openMicrophone();

//        Open speaker
        sound.openSpeaker();

//        Send audio data
        ThreadStore.addTask("Send-Audio-Data", ThreadStore.executorService.submit(() -> {
            byte[] buffer = new byte[2048];
            ClientInfo receiver = CallStore.voiceCall.getFrom().getUuid().equals(SocketStore.tcpClientSocket.getClientInfo().getUuid()) ? CallStore.voiceCall.getTo() : CallStore.voiceCall.getFrom();
            while (!Thread.interrupted()) {
                try {
                    sound.getTargetDataLine().read(buffer, 0, buffer.length);
                    udpss.send(buffer, InetAddress.getByName(receiver.getIp()), 1111);
                } catch (UnknownHostException ex) {
                    break;
                } catch (IOException ex) {
                    break;
                } catch (Exception ex) {
                    break;
                }
            }
        }));

//        Process the received audio data
        ThreadStore.addTask("Process-Received-Audio-Data", ThreadStore.executorService.submit(() -> {
            byte[] buffer = new byte[2048];
            while (!Thread.interrupted()) {
                try {
                    udpss.receive(buffer);
                    sound.getSourceDataLine().write(buffer, 0, buffer.length);
                } catch (IOException ex) {
                    break;
                } catch (Exception ex) {
                    break;
                }
            }
        }));
    }
    
    public void end() {
        udpss.getDatagramSocket().close();
        sound.closeMicrophone();
        sound.closeSpeaker();
    }
    
//    Getters and setters
    public UDPServerSocket getUdpss() {
        return udpss;
    }

    public void setUdpss(UDPServerSocket udpss) {
        this.udpss = udpss;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }
}
