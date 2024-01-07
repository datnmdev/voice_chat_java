/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.ptithcm.voice_chat_client.ui;

import com.ptithcm.voice_chat_client.models.ClientInfo;
import com.ptithcm.voice_chat_client.models.EventGeneric;
import com.ptithcm.voice_chat_client.models.VoiceCallControls;
import com.ptithcm.voice_chat_client.store.SocketStore;
import javax.swing.JLabel;

/**
 *
 * @author minhd
 */
public class CallerCardUI extends javax.swing.JPanel {

    private ClientInfo clientInfo;
    private VoiceCallControls voiceCallControls;

    /**
     * Creates new form CallerCardUI
     */
    public CallerCardUI(ClientInfo clientInfo) {
        initComponents();
        this.clientInfo = clientInfo;
        lblName.setText(clientInfo.getName());
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
        lblName = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setPreferredSize(new java.awt.Dimension(300, 188));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.CardLayout());

        lblName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblName.setText("Nguyễn Minh Đạt");
        lblName.setPreferredSize(new java.awt.Dimension(37, 32));
        jPanel1.add(lblName, "card2");

        add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/_128x128/user.png"))); // NOI18N
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

//    Getter and setter
    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public VoiceCallControls getVoiceCallControls() {
        return voiceCallControls;
    }

    public void setVoiceCallControls(VoiceCallControls voiceCallControls) {
        this.voiceCallControls = voiceCallControls;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblName;
    // End of variables declaration//GEN-END:variables
}