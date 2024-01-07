/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.models;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author minhd
 */
public class RSA {
     // Tạo cặp khóa RSA
    public static KeyPair generateKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    // Mã hóa dữ liệu sử dụng khóa công khai
    public static String encryptData(String data, PublicKey publicKey, int keySize) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Kích thước tối đa của dữ liệu mà khóa RSA có thể xử lý
        int maxBlockSize = getMaxBlockSize(keySize);

        // Chia văn bản thành các khối nhỏ hơn kích thước tối đa
        byte[] dataBytes = data.getBytes("UTF-8");
        int offset = 0;
        StringBuilder encryptedText = new StringBuilder();

        while (offset < dataBytes.length) {
            int blockSize = Math.min(maxBlockSize, dataBytes.length - offset);
            byte[] block = new byte[blockSize];
            System.arraycopy(dataBytes, offset, block, 0, blockSize);

            // Mã hóa từng khối dữ liệu
            byte[] encryptedBlock = cipher.doFinal(block);
            
            // Chuyển đổi thành chuỗi Base64 và thêm vào kết quả
            encryptedText.append(Base64.getEncoder().encodeToString(encryptedBlock));
            encryptedText.append(" ");

            offset += blockSize;
        }

        return Base64.getEncoder().encodeToString(encryptedText.toString().getBytes());
    }

    // Giải mã dữ liệu sử dụng khóa riêng tư
    public static String decryptData(String encryptedData, PrivateKey privateKey, int keySize) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        String[] splitedData = new String(Base64.getDecoder().decode(encryptedData)).split(" ");
        StringBuilder decryptedText = new StringBuilder();
        
        for (String encryptedText : splitedData) {
            decryptedText.append(new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText))));
        }
        
        return decryptedText.toString();
    }
    
//    Kích thước tối đa mà khoá có thể mã hoá cho mỗi lần
    private static int getMaxBlockSize(int keySize) {
        return keySize/8 - 11;
    }
}
