/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.validations;

import com.ptithcm.voice_chat_client.exceptions.InvalidInputException;
import com.ptithcm.voice_chat_client.frame.MainFrame;

/**
 *
 * @author minhd
 */
public class ConnectToServerForm {
    private MainFrame mainFrame;

    public ConnectToServerForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    public void validate() throws InvalidInputException {
        if (mainFrame.getTxtHost().getText().length() == 0) {
            throw new InvalidInputException("The host field cannot be empty");
        } else if (mainFrame.getTxtPort().getText().length() == 0) {
            throw new InvalidInputException("The port field cannot be empty");
        } else if (!mainFrame.getTxtPort().getText().matches("\\d+")) {
            throw new InvalidInputException("The port field must be an integer");
        } else if (mainFrame.getTxtName().getText().length() == 0) {
            throw new InvalidInputException("The name field cannot be empty");
        }
    }
}
