/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.controllers;

import com.ptithcm.voice_chat_server.annotations.Body;
import com.ptithcm.voice_chat_server.annotations.Controller;
import com.ptithcm.voice_chat_server.annotations.RequestMapping;
import com.ptithcm.voice_chat_server.enums.ResponseStatus;
import com.ptithcm.voice_chat_server.models.ClientInfo;
import com.ptithcm.voice_chat_server.models.Empty;
import com.ptithcm.voice_chat_server.models.Message;
import com.ptithcm.voice_chat_server.models.Packet;
import com.ptithcm.voice_chat_server.models.Response;
import com.ptithcm.voice_chat_server.models.TCPClientSocket;
import com.ptithcm.voice_chat_server.store.SocketStore;
import com.ptithcm.voice_chat_server.store.ViewStore;
import com.ptithcm.voice_chat_server.views.ui.ClientUI;
import com.ptithcm.voice_chat_server.views.ui.MessageUI;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author minhd
 */
@Controller
public class ClientController {
    @RequestMapping(header = "disconnect")
    public void disconnect(@Body String uuid, TCPClientSocket tcpcs) {
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().remove(uuid);

//        Request the client to update the list of connected clients
        List<TCPClientSocket> tcpcses = SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().values().stream().filter((_tcpcs) -> !_tcpcs.getClientInfo().getUuid().equals(tcpcs.getClientInfo().getUuid())).toList();
        tcpcses.forEach((_tcpcs) -> {
            _tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("Re-render-Connecting-Clients", SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().values().stream().filter((__tcpcs) -> !__tcpcs.getClientInfo().getUuid().equals(_tcpcs.getClientInfo().getUuid())).map((__tcpcs) -> __tcpcs.getClientInfo()).toList())), true);
        });

//        Re-render UI
        ViewStore.manageFrame.getPnlConnectedClients()
                .removeAll();
        SocketStore.tcpServerSocket.getTcpClientSocketManager()
                .getClientSocketMap().values().stream().filter((_tcpcs) -> !_tcpcs.getClientInfo().getUuid().equals(tcpcs.getClientInfo().getUuid())).forEach((_tcpsc) -> {
            ViewStore.manageFrame.getPnlConnectedClients().add(new ClientUI(_tcpsc.getClientInfo().getName(), _tcpsc.getClientInfo().getIp()));
        }
        );
        ViewStore.manageFrame.getPnlConnectedClients()
                .setPreferredSize(new Dimension((int) ViewStore.manageFrame.getPnlConnectedClients()
                        .getPreferredSize().getWidth(), ClientUI.height * SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().values().size()));
        ViewStore.manageFrame.getPnlConnectedClients()
                .revalidate();
        ViewStore.manageFrame.getPnlConnectedClients()
                .repaint();

//        Write log
        ViewStore.manageFrame.getPnlActionsOfSystem()
                .add(new MessageUI("/icons/_16x16/red_dot.png", String.format("The client with the address %s (%s) has disconnected", tcpcs.getClientInfo().getIp(), tcpcs.getClientInfo().getUuid())));
        ViewStore.manageFrame.getPnlActionsOfSystem()
                .setPreferredSize(new Dimension((int) ViewStore.manageFrame.getPnlActionsOfSystem()
                        .getPreferredSize().getWidth(), (int) ViewStore.manageFrame.getPnlActionsOfSystem()
                                .getPreferredSize().getHeight() + MessageUI.height));
        ViewStore.manageFrame.getPnlActionsOfSystem()
                .revalidate();
        ViewStore.manageFrame.getPnlActionsOfSystem()
                .repaint();
        ViewStore.manageFrame.getScrollActionsOfSystem()
                .getVerticalScrollBar().setValue(ViewStore.manageFrame.getScrollActionsOfSystem().getVerticalScrollBar().getMaximum());
    }

    @RequestMapping(header = "Get-Connecting-Clients")
    public void getConnectingClients(@Body Empty body, TCPClientSocket tcpcs) {
        List<ClientInfo> clientInfos = SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().values().stream().filter((_tcpcs) -> !_tcpcs.getClientInfo().getUuid().equals(tcpcs.getClientInfo().getUuid())).map((_tcpcs) -> _tcpcs.getClientInfo()).toList();
        tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("Get-Connecting-Clients", clientInfos)), true);
    }
    
    @RequestMapping(header = "Update-Profile")
    public void updateProfile(@Body String name, TCPClientSocket tcpcs) {
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(tcpcs.getClientInfo().getUuid()).getClientInfo().setName(name);
        tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("Update-Profile", new Empty())), true);
    }

    @RequestMapping(header = "Send-Message")
    public void sendMessage(@Body Message message, TCPClientSocket tcpcs) {
            message.setReceptionTime(new Date());

            if (!SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(message.getSender().getUuid()).getMessageMap().containsKey(message.getReceiver().getUuid())) {
                SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(message.getSender().getUuid()).getMessageMap().put(message.getReceiver().getUuid(), new ArrayList<>());
            }
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(message.getSender().getUuid()).getMessageMap().get(message.getReceiver().getUuid()).add(message);

            if (!SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(message.getReceiver().getUuid()).getMessageMap().containsKey(message.getSender().getUuid())) {
                SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(message.getReceiver().getUuid()).getMessageMap().put(message.getSender().getUuid(), new ArrayList<>());
            }
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(message.getReceiver().getUuid()).getMessageMap().get(message.getSender().getUuid()).add(message);

//            Request the client to update the list of messages
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(message.getSender().getUuid()).send(new Response(ResponseStatus.OK.getValue(), new Packet("Send-Message", new Empty())), true);
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(message.getSender().getUuid()).send(new Response(ResponseStatus.OK.getValue(), new Packet("Incoming-Message", SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(message.getSender().getUuid()).getMessageMap().get(message.getReceiver().getUuid()))), true);
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(message.getReceiver().getUuid()).send(new Response(ResponseStatus.OK.getValue(), new Packet("Incoming-Message", SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(message.getSender().getUuid()).getMessageMap().get(message.getReceiver().getUuid()))), true);
    }
}
