package com.ptithcm.voice_chat_server.models;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.KeyPair;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author minhd
 */
public class TCPServerSocket {
    private ServerSocket serverSocket;
    private TCPClientSocketManager tcpClientSocketManager;
    private KeyPair RSAKeyPair;
    private String password;
    private int port;

//    Constructors
    public TCPServerSocket(int port) throws IOException, Exception {
        this.serverSocket = new ServerSocket(port);
        this.tcpClientSocketManager = new TCPClientSocketManager(this);
        this.RSAKeyPair = RSA.generateKeyPair(2048);
        this.port = port;
    }
    
//    Getters and setters
    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    
    public void setServerSocket(ServerSocket serverSocket) {    
        this.serverSocket = serverSocket;
    }

    public TCPClientSocketManager getTcpClientSocketManager() {
        return tcpClientSocketManager;
    }

    public void setTcpClientSocketManager(TCPClientSocketManager tcpClientSocketManager) {
        this.tcpClientSocketManager = tcpClientSocketManager;
    }
    
    public KeyPair getRSAKeyPair() {
        return RSAKeyPair;
    }

    public void setRSAKeyPair(KeyPair RSAKeyPair) {
        this.RSAKeyPair = RSAKeyPair;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
