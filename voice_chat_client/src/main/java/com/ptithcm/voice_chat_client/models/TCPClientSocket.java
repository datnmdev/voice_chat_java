/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.models;

import com.google.gson.Gson;
import com.ptithcm.voice_chat_client.store.ThreadStore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.swing.JOptionPane;

/**
 *
 * @author minhd
 */
public class TCPClientSocket {
    private ClientInfo clientInfo;
    private ConcurrentMap<String,String> responseData;
    private Socket socket;
    private KeyPair RSAKeyPair;
    private PublicKey communicationKey;

//    Constructors
    public TCPClientSocket(Socket socket) throws Exception {
        this.socket = socket;
        this.responseData = new ConcurrentHashMap<>();
        socket.setKeepAlive(true);
        this.RSAKeyPair = RSA.generateKeyPair(2048);
        receive();
    }

//    Methods
    public <T> EventGeneric<T> registerEvent(EventGeneric<T> eventGeneric) {
        return eventGeneric;
    }
    
    public void receive() {
        ThreadStore.executorService.submit(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (!Thread.interrupted()) {
                    if (socket.getInputStream().available() > 0) {
                        String data = in.readLine();
                        if (!data.isBlank()) {
                            String json = null;
                            if (communicationKey == null) {
                                json = new String(Base64.getDecoder().decode(data));
                            } else {
                                json = RSA.decryptData(data, RSAKeyPair.getPrivate(), 2048);
                            }
                            responseData.put(new Gson().fromJson(json, Response.class).getHeader(), json);
                        }
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "1", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "2", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    public void send(Request request) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream());
            if (communicationKey != null) {
                out.println(RSA.encryptData(new Gson().toJson(request), communicationKey, 2048));
            } else {
                out.println(Base64.getEncoder().encodeToString(new Gson().toJson(request).getBytes()));
            }   
            out.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    Getters and setters
    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }
    
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public KeyPair getRSAKeyPair() {
        return RSAKeyPair;
    }

    public void setRSAKeyPair(KeyPair RSAKeyPair) {
        this.RSAKeyPair = RSAKeyPair;
    }

    public PublicKey getCommunicationKey() {
        return communicationKey;
    }

    public void setCommunicationKey(PublicKey communicationKey) {
        this.communicationKey = communicationKey;
    }

    public ConcurrentMap<String, String> getResponseData() {
        return responseData;
    }

    public void setResponseData(ConcurrentMap<String, String> responseData) {
        this.responseData = responseData;
    }
}
