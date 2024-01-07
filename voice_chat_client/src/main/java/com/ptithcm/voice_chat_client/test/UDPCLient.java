/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_client.test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 *
 * @author minhd
 */
public class UDPCLient {

    public static void main(String[] args) {
        DatagramSocket socket = null;
        byte[] receiveData = new byte[1024];
        try {

            while (true) {
                // Tạo socket cho client
                socket = new DatagramSocket();

                // Chuỗi cần gửi đến server
                Scanner sc = new Scanner(System.in);
                String message = sc.nextLine();

                // Chuyển chuỗi thành mảng byte
                byte[] sendData = message.getBytes();

                // Địa chỉ IP của server (localhost ở đây)
                InetAddress serverAddress = InetAddress.getByName("192.168.1.12");

                // Tạo gói tin để gửi dữ liệu
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9999);

                // Gửi gói tin đến server
                socket.send(sendPacket);

                while (true) {
                    // Tạo gói tin để nhận dữ liệu
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                    // Nhận dữ liệu từ client
                    socket.receive(receivePacket);

                    // Lấy dữ liệu từ gói tin nhận được và in ra màn hình
                    String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Received from server: " + receivedMessage);
                    System.out.println("ip server: " + receivePacket.getAddress().getHostAddress());
                    System.out.println("ip server: " + receivePacket.getAddress().getCanonicalHostName());
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
