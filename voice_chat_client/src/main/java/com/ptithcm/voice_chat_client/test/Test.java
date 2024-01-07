/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.test;

import com.ptithcm.voice_chat_client.models.Sound;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author minhd
 */
public class Test {
    public static void main(String[] args) {
        try {
            Sound sound = new Sound();
            sound.playSound("C:\\Users\\minhd\\Documents\\NetBeansProjects\\voice_chat_client\\src\\main\\resources\\audios\\incomming-call.wav");
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
