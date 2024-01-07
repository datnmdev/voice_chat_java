/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ptithcm.voice_chat_server.store.ReflectionStore;
import com.ptithcm.voice_chat_server.store.SocketStore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PublicKey;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

/**
 *
 * @author minhd
 */
public class TCPClientSocket {
    private ClientInfo clientInfo;
    private Map<String, List<Message>> messageMap;
    private VoiceCallManager voiceCallManager;
    private Socket socket;
    private List<Request> requests;
    private PublicKey communicationKey;
    private ExecutorService executorService;

//    Constructors
    public TCPClientSocket(Socket socket) {
        String rdUUID = UUID.randomUUID().toString();
        this.clientInfo = new ClientInfo(rdUUID, rdUUID, socket.getInetAddress().getHostAddress());
        this.messageMap = new HashMap<>();
        this.voiceCallManager = new VoiceCallManager();
        this.socket = socket;
        this.requests = new ArrayList<>();
        this.executorService = Executors.newCachedThreadPool();
        receive();
        handleRequests();
    }

//    Methods
    public void send(Response response, boolean encrypt) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            if (encrypt) {
                out.println(RSA.encryptData(new Gson().toJson(response), communicationKey, 2048));
            } else {
                out.println(Base64.getEncoder().encodeToString(new Gson().toJson(response).getBytes()));
            }
            out.flush();
        } catch (IOException ex) {
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().remove(clientInfo.getUuid());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void receive() {
        executorService.submit(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
                while (true) {
                    String data = in.readLine();
                    if (!data.isBlank()) {
                        String json = null;
                        if (communicationKey == null) {
                            json = new String(Base64.getDecoder().decode(data));
                        } else {
                            json = RSA.decryptData(data, SocketStore.tcpServerSocket
                                    .getRSAKeyPair().getPrivate(), 2048);
                        }
                        System.out.println(json);
                        String header = new Gson().fromJson(json, Request.class).getHeader();
                        requests.add( new Gson().fromJson(json, TypeToken.getParameterized(
                                Request.class, ReflectionStore.methodInfoMap.get(header)
                                        .getBodyParameterType()).getType()));
                    }
                    Thread.sleep(Duration.ZERO);
                }
            } catch (Exception e) {
                SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap()
                        .remove(clientInfo.getUuid());
            }
        });
    }

    private void handleRequests() {
        executorService.submit(() -> {
            while (true) {
                try {
                    if (!requests.isEmpty()) {
                        Request request = requests.removeFirst();
                        MethodInvoker.invokeMethodWithParameters(ReflectionStore
                                .methodInfoMap.get(request.getHeader()), request.getBody(), 
                                this);
                    }
                    Thread.sleep(Duration.ZERO);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

//    Getters and setters
    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public Map<String, List<Message>> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(Map<String, List<Message>> messageMap) {
        this.messageMap = messageMap;
    }
    
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PublicKey getCommunicationKey() {
        return communicationKey;
    }

    public void setCommunicationKey(PublicKey communicationKey) {
        this.communicationKey = communicationKey;
    }

    public VoiceCallManager getVoiceCallManager() {
        return voiceCallManager;
    }

    public void setVoiceCallManager(VoiceCallManager voiceCallManager) {
        this.voiceCallManager = voiceCallManager;
    }
}
