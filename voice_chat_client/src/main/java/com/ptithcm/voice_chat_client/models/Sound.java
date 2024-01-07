/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.models;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author minhd
 */
public class Sound {
    private SourceDataLine sourceDataLine;
    private TargetDataLine targetDataLine;

//    Constructors
    public Sound() {

    }

//    Methods
    public void openMicrophone() throws LineUnavailableException {
        // Định dạng âm thanh cho microphone
        AudioFormat audioFormat = getAudioFormat();

        // Mở microphone
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
        targetDataLine.open(audioFormat);
        targetDataLine.start();

        System.out.println("Microphone is open and active.");
    }

    public void closeMicrophone() {
        if (targetDataLine != null && targetDataLine.isOpen()) {
            targetDataLine.stop();
            targetDataLine.close();

            System.out.println("Microphone is closed.");
        }
    }

    public void toggleMicrophone() throws LineUnavailableException {
        if (targetDataLine != null && targetDataLine.isOpen()) {
            targetDataLine.stop();
            targetDataLine.close();

            System.out.println("Microphone is closed.");
        } else {
            openMicrophone();
        }
    }

    public void playSound(String filePath) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // Mở file âm thanh
        File soundFile = new File(filePath);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

        // Lấy thông số của âm thanh
        AudioFormat audioFormat = audioInputStream.getFormat();
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

        // Mở đường dẫn âm thanh
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        // Đọc dữ liệu âm thanh và ghi vào đường dẫn
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = audioInputStream.read(buffer)) != -1) {
            sourceDataLine.write(buffer, 0, bytesRead);
        }

        // Đóng các tài nguyên khi đã xong
        audioInputStream.close();
        sourceDataLine.drain();
        sourceDataLine.close();
    }

    public void openSpeaker() throws LineUnavailableException {
        // Định dạng âm thanh cho loa
        AudioFormat audioFormat = getAudioFormat();

        // Mở loa
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        System.out.println("Speaker is open and active.");
    }

    public void closeSpeaker() {
        if (sourceDataLine != null && sourceDataLine.isOpen()) {
            sourceDataLine.stop();
            sourceDataLine.close();

            System.out.println("Speaker is closed.");
        }
    }

    public void toggleSpeaker() throws LineUnavailableException {
        if (sourceDataLine != null && sourceDataLine.isOpen()) {
            sourceDataLine.stop();
            sourceDataLine.close();

            System.out.println("Speaker is closed.");
        } else {
            openSpeaker();
        }
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 44100.0F;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;

        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

//    Getters and setters
    public SourceDataLine getSourceDataLine() {
        return sourceDataLine;
    }

    public void setSourceDataLine(SourceDataLine sourceDataLine) {
        this.sourceDataLine = sourceDataLine;
    }

    public TargetDataLine getTargetDataLine() {
        return targetDataLine;
    }

    public void setTargetDataLine(TargetDataLine targetDataLine) {
        this.targetDataLine = targetDataLine;
    }
}
