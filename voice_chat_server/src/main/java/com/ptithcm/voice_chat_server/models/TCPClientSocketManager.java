/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.models;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author minhd
 */
public class TCPClientSocketManager {
    private TCPServerSocket tcpServerSocket;
    private Map<String, TCPClientSocket> clientSocketMap;
    private ExecutorService executorService;
    
//    Constructors
    public TCPClientSocketManager(TCPServerSocket tcpServerSocket) {
        this.tcpServerSocket = tcpServerSocket;
        this.clientSocketMap = new HashMap<>();
        this.executorService = Executors.newCachedThreadPool();
        listenConnectingRequest();
    }
    
//    Methods
    private void listenConnectingRequest() {
        executorService.submit(() -> {
            while (true) {
                System.out.println("Waiting for a client ...");
                Socket clientSocket = tcpServerSocket.getServerSocket().accept();
                System.out.println("Client accepted: " + clientSocket);
                TCPClientSocket tcpClientSocket = new TCPClientSocket(clientSocket);
                clientSocketMap.put(tcpClientSocket.getClientInfo().getUuid(), tcpClientSocket);
            }
        });
    }
    
    public void stopListenConnectingRequest() {
        executorService.shutdown();
    }

//    Getters and setters
    public TCPServerSocket getTcpServerSocket() {
        return tcpServerSocket;
    }
    
    public void setTcpServerSocket(TCPServerSocket tcpServerSocket) {    
        this.tcpServerSocket = tcpServerSocket;
    }

    public Map<String, TCPClientSocket> getClientSocketMap() {
        return clientSocketMap;
    }

    public void setClientSocketMap(Map<String, TCPClientSocket> clientSocketMap) {
        this.clientSocketMap = clientSocketMap;
    }
}
