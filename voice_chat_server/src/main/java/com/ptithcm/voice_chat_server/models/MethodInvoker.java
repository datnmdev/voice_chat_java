/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.models;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JOptionPane;

/**
 *
 * @author minhd
 */
public class MethodInvoker {
    public static void invokeMethodWithParameters(MethodInfo methodInfo, Object... parameters) {
        try {
            // Lấy ra đối tượng Class của lớp
            Class<?> clazz = Class.forName(methodInfo.getClassName());

            // Sử dụng getDeclaredConstructor() để lấy đối tượng Constructor
            Constructor<?> constructor = clazz.getDeclaredConstructor();

            // Cho phép truy cập private constructor (nếu có)
            constructor.setAccessible(true);

            // Gọi newInstance() từ Constructor để tạo đối tượng
            Object instance = constructor.newInstance();
            
            // Tạo một mảng Class để đại diện cho kiểu dữ liệu của từng tham số
            Class<?>[] parameterTypes = new Class[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }

            // Lấy ra đối tượng Method của phương thức dựa trên tên và kiểu tham số
            Method method = clazz.getMethod(methodInfo.getMethodName(), parameterTypes);

            // Gọi phương thức trên đối tượng cụ thể với các giá trị tham số
            method.invoke(instance, parameters);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchMethodException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SecurityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InstantiationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvocationTargetException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }
}
