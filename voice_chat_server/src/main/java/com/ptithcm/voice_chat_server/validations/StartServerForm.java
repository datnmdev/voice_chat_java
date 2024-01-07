/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.validations;

import com.ptithcm.voice_chat_server.exceptions.InvalidInputException;
import com.ptithcm.voice_chat_server.views.frame.MainFrame;

/**
 *
 * @author minhd
 */
public class StartServerForm {
    private MainFrame mainFrame;

    public StartServerForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    public void validate() throws InvalidInputException {
        if (mainFrame.getTxtPort().getText().length() == 0) {
            throw new InvalidInputException("Vui lòng nhập cổng");
        } else if (!mainFrame.getTxtPort().getText().matches("\\d+")) {
            throw new InvalidInputException("Cổng là một số nguyên");
        }
    }
}
