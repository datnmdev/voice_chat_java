/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ptithcm.voice_chat_server;

import com.ptithcm.voice_chat_server.models.Processor;
import com.ptithcm.voice_chat_server.views.frame.MainFrame;
/**
 *
 * @author minhd
 */
public class Voice_chat_server {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Processor.processAnnotations();
                MainFrame mainFrame = new MainFrame();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
            }
        });
    }
}
