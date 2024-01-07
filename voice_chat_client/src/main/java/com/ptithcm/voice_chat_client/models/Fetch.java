/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ptithcm.voice_chat_client.enums.ResponseStatus;
import com.ptithcm.voice_chat_client.store.SocketStore;
import com.ptithcm.voice_chat_client.store.ThreadStore;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author minhd
 */
public class Fetch<R, S> {
    private Request<R> request;
    private Response<S> response;
    private Class<?> bodyClass;

//    Constructors
    public Fetch(Request<R> request, Class<?> bodyClass) {
        this.request = request;
        this.bodyClass = bodyClass;
    }
    
//    Methods
    private void run(boolean isAsync) {
        Future future = ThreadStore.executorService.submit(() -> {
            try {
                if (SocketStore.tcpClientSocket.getCommunicationKey() == null) {
                    send(false);

                } else {
                    send(true);
                }

                while (true) {
                    if (SocketStore.tcpClientSocket.getResponseData().containsKey(request.getHeader())) {
                        response = new Gson().fromJson(SocketStore.tcpClientSocket.getResponseData()
                                .remove(request.getHeader()), TypeToken.getParameterized(
                                        Response.class, bodyClass).getType());
                        break;
                    }
                }
            } catch (Exception e) {
                response = new Response(ResponseStatus.ERROR.getValue(), new Packet("Error", 
                        e.getMessage()));
            }
        });

        if (!isAsync) {
            try {
                future.get();
            } catch (InterruptedException ex) {
                Logger.getLogger(Fetch.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(Fetch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Fetch<R, S> await() {
        run(false);
        return this;
    }
    
    public Fetch<R, S> async() {
        run(true);
        return this;
    }

    private void send(boolean encrypt) throws IOException, Exception {
        System.out.println(new Gson().toJson(request));
        PrintWriter out = new PrintWriter(SocketStore.tcpClientSocket.getSocket().getOutputStream());
        if (encrypt) {
            out.println(RSA.encryptData(new Gson().toJson(request), SocketStore.tcpClientSocket.getCommunicationKey(), 2048));
        } else {
            out.println(Base64.getEncoder().encodeToString(new Gson().toJson(request).getBytes()));
        }
        out.flush();
    }
    
//    Getters and setters
    public Response<S> getResponse() {
        return response;
    }

    public void setResponse(Response<S> response) {
        this.response = response;
    }
}
