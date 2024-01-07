/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.models;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author minhd
 */
public class UDPServerSocket {
    private DatagramSocket datagramSocket;
    private int port;
    
//    Constructors
    public UDPServerSocket(DatagramSocket datagramSocket, int port) {
        this.datagramSocket = datagramSocket;
        this.port = port;
    }
    
//    Methods
    public void receive(byte[] buffer) throws IOException {
        datagramSocket.receive(new DatagramPacket(buffer, buffer.length));
    }
    
    public void send(byte[] buffer, InetAddress inetAddress, int port) throws IOException {
        datagramSocket.send(new DatagramPacket(buffer, buffer.length, inetAddress, port));
    }
    
//    Getters and setters
    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
