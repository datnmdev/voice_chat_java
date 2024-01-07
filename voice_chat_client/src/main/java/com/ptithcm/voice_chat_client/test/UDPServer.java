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
public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            // Tạo socket ở cổng 9876
            socket = new DatagramSocket(9999);

            // Tạo mảng byte để lưu dữ liệu từ gói tin nhận được
            byte[] receiveData = new byte[1024];

            System.out.println("Server is running...");

            while (true) {
                // Tạo gói tin để nhận dữ liệu
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Nhận dữ liệu từ client
                socket.receive(receivePacket);

                // Lấy dữ liệu từ gói tin nhận được và in ra màn hình
                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from client: " + receivedMessage);
                System.out.println("ip client: " + receivePacket.getAddress().getHostAddress());
                System.out.println("ip client: " + receivePacket.getAddress().getCanonicalHostName());
                
//                Gửi dữ liệu cho client
                System.out.println("Nhap tin nhan muon gui den client: ");
                Scanner sc = new Scanner(System.in);
                String responseMessage = sc.nextLine();
                byte[] sendData = responseMessage.getBytes();

                // Lấy địa chỉ và cổng của client từ gói tin nhận được
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Tạo gói tin để gửi dữ liệu về cho client
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);

                // Gửi gói tin về cho client
                socket.send(sendPacket);
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
