/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicesANDvalidate;

import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author Admin
 */
public class validateEmail {
    public static final Color COLOR_VALID = new Color(0, 102, 204); // Xanh dương
    public static final Color COLOR_DEFAULT = new Color(0, 0, 0);   // Đen (mặc định)
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        String em = email;
        return em.contains("@") && 
               em.contains(".") && 
               em.indexOf("@") < em.lastIndexOf(".");
    }
    
    public static String checkEmail(String email) {
        if (email == null || email.isEmpty()) return "Email không được trống";
        if (!email.contains("@")) return "Email phải có @";
        if (!email.contains(".")) return "Email phải có .";
        if (email.indexOf("@") > email.lastIndexOf(".")) return "@ phải đứng trước .";
        if (email.contains(" ")) return "Email không được có khoảng trắng";
        return "OK";
    }
    
    // ========== VALIDATE PASSWORD ==========
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) return false;
        
        boolean hasUpper = false;
        boolean hasDigit = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        
        return hasUpper && hasDigit;
    }
    
    // ========== VALIDATE PASSWORD VỚI ĐỔI MÀU LABEL ==========
    public static void validatePasswordSimple(String password,JLabel labelDigit,JLabel labelUpper,JLabel labelLength) {
        // 1. Kiểm tra có số
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }
        
        // 2. Kiểm tra có chữ hoa
        boolean hasUpper = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
                break;
            }
        }
        
        // 3. Kiểm tra độ dài
        boolean hasLength = password.length() >= 6;
        
        // Đổi màu label
        labelDigit.setForeground(hasDigit ? COLOR_VALID : COLOR_DEFAULT);
        labelUpper.setForeground(hasUpper ? COLOR_VALID : COLOR_DEFAULT);
        labelLength.setForeground(hasLength ? COLOR_VALID : COLOR_DEFAULT);
    }
    
    public static boolean isPasswordAllValid(String password) {
        if (password == null || password.length() < 6) return false;
        
        boolean hasDigit = false;
        boolean hasUpper = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) hasDigit = true;
            if (Character.isUpperCase(c)) hasUpper = true;
        }
        
        return hasDigit && hasUpper;
    }
}

