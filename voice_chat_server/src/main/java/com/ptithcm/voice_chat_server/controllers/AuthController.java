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
import com.ptithcm.voice_chat_server.views.ui.ClientUI;
import com.ptithcm.voice_chat_server.views.ui.MessageUI;
import java.awt.Dimension;
import java.util.List;

/**
 *
 * @author minhd
 */
@Controller
public class AuthController {

    @RequestMapping(header = "Authentication")
    public void authentication(@Body String password, TCPClientSocket tcpcs) {
        if (password.equals(SocketStore.tcpServerSocket.getPassword())) {
            tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("Authentication", tcpcs.getClientInfo())), true);

//            Request the client to update the list of connected clients
            List<TCPClientSocket> tcpcses = SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().values().stream().filter((_tcpcs) -> !_tcpcs.getClientInfo().getUuid().equals(tcpcs.getClientInfo().getUuid())).toList();
            tcpcses.forEach((_tcpcs) -> {
                _tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("Re-render-Connecting-Clients", SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().values().stream().filter((__tcpcs) -> !__tcpcs.getClientInfo().getUuid().equals(_tcpcs.getClientInfo().getUuid())).map((__tcpcs) -> __tcpcs.getClientInfo()).toList())), true);
            });

//            Re-render UI
            ViewStore.manageFrame.getPnlConnectedClients().removeAll();
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().values().forEach((client) -> {
                ViewStore.manageFrame.getPnlConnectedClients().add(new ClientUI(client.getClientInfo().getName(), client.getClientInfo().getIp()));
            });
            ViewStore.manageFrame.getPnlConnectedClients().setPreferredSize(new Dimension((int) ViewStore.manageFrame.getPnlConnectedClients().getPreferredSize().getWidth(), ClientUI.height * SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().values().size()));
            ViewStore.manageFrame.getPnlConnectedClients().revalidate();
            ViewStore.manageFrame.getPnlConnectedClients().repaint();

//            Write log
            ViewStore.manageFrame.getPnlActionsOfSystem().add(new MessageUI("/icons/_16x16/green_dot.png", String.format("The client with the address %s (%s) has successfully authenticated", tcpcs.getClientInfo().getIp(), tcpcs.getClientInfo().getUuid())));
            ViewStore.manageFrame.getPnlActionsOfSystem().setPreferredSize(new Dimension((int) ViewStore.manageFrame.getPnlActionsOfSystem().getPreferredSize().getWidth(), (int) ViewStore.manageFrame.getPnlActionsOfSystem().getPreferredSize().getHeight() + MessageUI.height));
            ViewStore.manageFrame.getPnlActionsOfSystem().revalidate();
            ViewStore.manageFrame.getPnlActionsOfSystem().repaint();
            ViewStore.manageFrame.getScrollActionsOfSystem().getVerticalScrollBar().setValue(ViewStore.manageFrame.getScrollActionsOfSystem().getVerticalScrollBar().getMaximum());
        } else {
            tcpcs.send(new Response(ResponseStatus.UNAUTHORIZATION.getValue(), new Packet("Authentication", tcpcs.getClientInfo())), true);
//            Write log
            ViewStore.manageFrame.getPnlActionsOfSystem().add(new MessageUI("/icons/_16x16/shield.png", String.format("The client with the address %s (%s) failed authentication", tcpcs.getClientInfo().getIp(), tcpcs.getClientInfo().getUuid())));
            ViewStore.manageFrame.getPnlActionsOfSystem().setPreferredSize(new Dimension((int) ViewStore.manageFrame.getPnlActionsOfSystem().getPreferredSize().getWidth(), (int) ViewStore.manageFrame.getPnlActionsOfSystem().getPreferredSize().getHeight() + MessageUI.height));
            ViewStore.manageFrame.getPnlActionsOfSystem().revalidate();
            ViewStore.manageFrame.getPnlActionsOfSystem().repaint();
            ViewStore.manageFrame.getScrollActionsOfSystem().getVerticalScrollBar().setValue(ViewStore.manageFrame.getScrollActionsOfSystem().getVerticalScrollBar().getMaximum());
        }
    }
}
