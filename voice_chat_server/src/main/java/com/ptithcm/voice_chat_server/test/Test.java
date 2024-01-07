/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.test;

import com.ptithcm.voice_chat_server.models.RSA;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyPair;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author minhd
 */
public class Test {
    public static void main(String[] args) {
        try {
            KeyPair keyPair = RSA.generateKeyPair(2048);
            String encrypt = RSA.encryptData("Mùa thu đang bắt đầu rải một vẻ đẹp lãng mạn và dịu dàng khắp các ngõ phố của Hà Nội. Những tia nắng vàng nhẹ nhàng chiếu rọi từ trên cao, làm lung linh những hàng cây phố, cùng những tán lá rụng như bức tranh sơn thủy hữu tình. Trên những con đường Hà Nội, không khí mát lành của mùa thu tràn ngập, mang theo hương thơm của hoa cúc và dưa hấu từ những gánh hàng rong. Những chiếc lá vàng rơi nhẹ nhàng, như những cánh hoa bay lượn trong không khí, tạo nên một khung cảnh thơ mộng và lãng mạn. Các công viên và hồ trong thành phố cũng trở nên lãng mạn hơn trong mùa thu. Những hàng cây xanh mướt bỗng chốc biến thành những tấm thảm vàng rực rỡ, tạo nên một cảnh quan tuyệt đẹp. Các cặp đôi đi dạo bên nhau, tận hưởng không khí thanh bình và hài hòa của mùa thu. Có những người ngồi dưới bóng cây, thưởng thức cà phê nóng và nhâm nhi những chiếc bánh quy giòn tan, tạo nên một khoảnh khắc yên bình và thư giãn. Buổi tối, ánh đèn vàng lung linh của phố phường làm cho Hà Nội trở nên ấm áp và lãng mạn hơn bao giờ hết. Những quán cafe sân vườn hay những quán phở nổi tiếng trở thành điểm đến lý tưởng cho những người muốn thư giãn sau một ngày làm việc. Tiếng cười, tiếng đàn guitar nhẹ nhàng và những cuộc trò chuyện vui vẻ tràn đầy không gian. Mùa thu ở Hà Nội là thời điểm tuyệt vời để thưởng thức cảnh quan tuyệt đẹp, hòa mình vào không gian yên bình và lãng mạn. Hà Nội trong mùa thu là một hòn ngọc lung linh giữa phố phường, mang đến cho mọi người những trải nghiệm đáng nhớ và ngọt ngào.", keyPair.getPublic(), 2048);
            System.out.println(encrypt);
            System.out.println(RSA.decryptData(encrypt, keyPair.getPrivate(), 2048));
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
