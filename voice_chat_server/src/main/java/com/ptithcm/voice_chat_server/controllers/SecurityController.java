/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.controllers;

import com.ptithcm.voice_chat_server.annotations.Body;
import com.ptithcm.voice_chat_server.annotations.Controller;
import com.ptithcm.voice_chat_server.annotations.RequestMapping;
import com.ptithcm.voice_chat_server.enums.ResponseStatus;
import com.ptithcm.voice_chat_server.models.Packet;
import com.ptithcm.voice_chat_server.models.Response;
import com.ptithcm.voice_chat_server.models.TCPClientSocket;
import com.ptithcm.voice_chat_server.store.SocketStore;
import com.ptithcm.voice_chat_server.store.ViewStore;
import com.ptithcm.voice_chat_server.views.ui.MessageUI;
import java.awt.Dimension;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 *
 * @author minhd
 */
@Controller
public class SecurityController {
    @RequestMapping(header = "TCP-Key-Exchange")
    public void tcpKeyExchange(@Body String encodedKey, TCPClientSocket tcpcs) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            tcpcs.setCommunicationKey(keyFactory.generatePublic(new X509EncodedKeySpec(
                    Base64.getDecoder().decode(encodedKey))));
            tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("TCP-Key-Exchange", 
                    Base64.getEncoder().encodeToString(SocketStore.tcpServerSocket.getRSAKeyPair().getPublic().getEncoded()))), false);
            
//            Write log
            ViewStore.manageFrame.getPnlActionsOfSystem().add(new MessageUI("/icons/_16x16/key.png", String.format("The secure connection between the server and client with the address %s (%s) has been successfully established", tcpcs.getClientInfo().getIp(), tcpcs.getClientInfo().getUuid())));
            ViewStore.manageFrame.getPnlActionsOfSystem().setPreferredSize(new Dimension((int) ViewStore.manageFrame.getPnlActionsOfSystem().getPreferredSize().getWidth(), (int) ViewStore.manageFrame.getPnlActionsOfSystem().getPreferredSize().getHeight() + MessageUI.height));    
            ViewStore.manageFrame.getPnlActionsOfSystem().revalidate();
            ViewStore.manageFrame.getPnlActionsOfSystem().repaint();
            ViewStore.manageFrame.getScrollActionsOfSystem().getVerticalScrollBar().setValue(ViewStore.manageFrame.getScrollActionsOfSystem().getVerticalScrollBar().getMaximum());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            tcpcs.send(new Response(ResponseStatus.ERROR.getValue(), null), false);
            
//            Write log
            ViewStore.manageFrame.getPnlActionsOfSystem().add(new MessageUI("/icons/_16x16/banned.png", String.format("The establishment of a secure connection between the server and client with the address %s (%s) encountered an error.", tcpcs.getClientInfo().getIp(), tcpcs.getClientInfo().getUuid())));
            ViewStore.manageFrame.getPnlActionsOfSystem().setPreferredSize(new Dimension((int) ViewStore.manageFrame.getPnlActionsOfSystem().getPreferredSize().getWidth(), (int) ViewStore.manageFrame.getPnlActionsOfSystem().getPreferredSize().getHeight() + MessageUI.height));
            ViewStore.manageFrame.getPnlActionsOfSystem().revalidate();
            ViewStore.manageFrame.getPnlActionsOfSystem().repaint();
            ViewStore.manageFrame.getScrollActionsOfSystem().getVerticalScrollBar().setValue(ViewStore.manageFrame.getScrollActionsOfSystem().getVerticalScrollBar().getMaximum());
        }
    }
}
