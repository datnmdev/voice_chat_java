/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ptithcm.voice_chat_client.frame;

import com.ptithcm.voice_chat_client.models.ClientInfo;
import com.ptithcm.voice_chat_client.models.Empty;
import com.ptithcm.voice_chat_client.models.EventGeneric;
import com.ptithcm.voice_chat_client.models.Fetch;
import com.ptithcm.voice_chat_client.models.Packet;
import com.ptithcm.voice_chat_client.models.Request;
import com.ptithcm.voice_chat_client.models.Response;
import com.ptithcm.voice_chat_client.models.VoiceCall;
import com.ptithcm.voice_chat_client.store.CallStore;
import com.ptithcm.voice_chat_client.store.SocketStore;
import com.ptithcm.voice_chat_client.store.ViewStore;
import com.ptithcm.voice_chat_client.ui.ChatUI;
import com.ptithcm.voice_chat_client.ui.ClientUI;
import com.ptithcm.voice_chat_client.ui.MyUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author minhd
 */
public class ChatFrame extends javax.swing.JFrame {

    /**
     * Creates new form ChatFrame
     */
    public ChatFrame() {
        initComponents();

        pnlMyInfo.add(new MyUI(SocketStore.tcpClientSocket.getClientInfo().getName(), SocketStore.tcpClientSocket.getClientInfo().getIp()));

        pnlClients.removeAll();
        Response<ClientInfo[]> getConnectingClientsResponse = new Fetch<Empty, ClientInfo[]>(new Request(new Packet("Get-Connecting-Clients", new Empty())), ClientInfo[].class).await().getResponse();
        if (getConnectingClientsResponse.getBody() != null) {
            for (ClientInfo clientInfo : getConnectingClientsResponse.getBody()) {
                ClientUI clientUI = new ClientUI(clientInfo);
                clientUI.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        for (Component comp : pnlClients.getComponents()) {
                            ClientUI clientUI = (ClientUI) comp;
                            clientUI.setBackground(Color.white);
                            clientUI.getLbId().setForeground(Color.black);
                            clientUI.getLbIp().setForeground(Color.green);
                        }
                        pnlChat.removeAll();
                        
                        ChatUI chatUI = new ChatUI(clientInfo);
                        ViewStore.chatUI = chatUI;
                        pnlChat.add(chatUI);
                        
                        pnlChat.revalidate();
                        pnlChat.repaint();
                        
//                        Focus clientUI chosen
                        clientUI.setBackground(Color.cyan);
                        clientUI.getLbId().setForeground(Color.white);
                        clientUI.getLbIp().setForeground(Color.white);
                    }
                });
                pnlClients.add(clientUI);
            }
            pnlClients.setPreferredSize(new Dimension(ClientUI.width, ClientUI.height * getConnectingClientsResponse.getBody().length));
            pnlClients.revalidate();
            pnlClients.repaint();
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (SocketStore.tcpClientSocket != null) {
                    SocketStore.tcpClientSocket.send(new Request(new Packet("disconnect", SocketStore.tcpClientSocket.getClientInfo().getUuid())));
                }
            }
        });

        SocketStore.tcpClientSocket.registerEvent(new EventGeneric<String>(SocketStore.tcpClientSocket, String.class)).on("disconnect", (body) -> {
            JOptionPane.showMessageDialog(this, body, "Error", JOptionPane.ERROR_MESSAGE);
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.setVisible(true);
                }
            });
            dispose();
        });
        
        SocketStore.tcpClientSocket.registerEvent(new EventGeneric<VoiceCall>(SocketStore.tcpClientSocket, VoiceCall.class)).on("Incomming-Call", (body) -> {
            setVisible(false);
            CallStore.voiceCall = body;
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    IcommingCallFrame icommingCallFrame = new IcommingCallFrame(body);
                    icommingCallFrame.setLocationRelativeTo(null);
                    icommingCallFrame.setVisible(true);
                }
            });
        });

        SocketStore.tcpClientSocket.registerEvent(new EventGeneric<ClientInfo[]>(SocketStore.tcpClientSocket, ClientInfo[].class)).on("Re-render-Connecting-Clients", (body) -> {
            pnlClients.removeAll();

            boolean isChatUIRemainAccessible = false;
            ClientInfo[] clientInfos = body;
            for (ClientInfo clientInfo : clientInfos) {
                ClientUI clientUI = new ClientUI(clientInfo);
                
                if (ViewStore.chatUI != null && clientInfo.getUuid().equals(ViewStore.chatUI.getReceiver().getUuid())) {
                    isChatUIRemainAccessible = true;
                    clientUI.setBackground(Color.cyan);
                    clientUI.getLbId().setForeground(Color.white);
                    clientUI.getLbIp().setForeground(Color.white);
                }
                
                clientUI.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        for (Component comp : pnlClients.getComponents()) {
                            ClientUI clientUI = (ClientUI) comp;
                            clientUI.setBackground(Color.white);
                            clientUI.getLbId().setForeground(Color.black);
                            clientUI.getLbIp().setForeground(Color.green);
                        }
                        pnlChat.removeAll();
                        
                        ChatUI chatUI = new ChatUI(clientInfo);
                        ViewStore.chatUI = chatUI;
                        pnlChat.add(chatUI);
                        
                        pnlChat.revalidate();
                        pnlChat.repaint();
                        
//                        Focus clientUI chosen
                        clientUI.setBackground(Color.cyan);
                        clientUI.getLbId().setForeground(Color.white);
                        clientUI.getLbIp().setForeground(Color.white);
                    }
                });
                pnlClients.add(clientUI);
            }
            pnlClients.setPreferredSize(new Dimension(ClientUI.width, ClientUI.height * clientInfos.length));

            if (!isChatUIRemainAccessible) {
                ViewStore.chatUI = null;
                pnlChat.removeAll();
                pnlChat.revalidate();
                pnlChat.repaint();
            }
            
            pnlClients.revalidate();
            pnlClients.repaint();
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlClients = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pnlMyInfo = new javax.swing.JPanel();
        pnlChat = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Voice Chat Client");
        setIconImage(new ImageIcon(getClass().getResource("/icons/_512x512/voice-mail.png")).getImage());
        setPreferredSize(new java.awt.Dimension(1000, 500));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.CardLayout());

        pnlClients.setPreferredSize(new java.awt.Dimension(330, 0));
        jScrollPane1.setViewportView(pnlClients);

        jTabbedPane1.addTab("Clients", jScrollPane1);
        jTabbedPane1.addTab("Groups", jScrollPane2);

        jPanel3.add(jTabbedPane1, "card2");

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        pnlMyInfo.setLayout(new java.awt.CardLayout());
        jPanel1.add(pnlMyInfo, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

        pnlChat.setLayout(new java.awt.CardLayout());
        getContentPane().add(pnlChat, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnlChat;
    private javax.swing.JPanel pnlClients;
    private javax.swing.JPanel pnlMyInfo;
    // End of variables declaration//GEN-END:variables
}
