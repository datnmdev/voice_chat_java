/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.models;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author minhd
 */
public class Checker {
    public static String connectNetwork() throws UnknownHostException {
        String localhost = InetAddress.getLocalHost().getHostAddress();
        if (localhost.equals("127.0.0.1")) {
            return null;
        } else {
            return localhost;
        }
    }
}
