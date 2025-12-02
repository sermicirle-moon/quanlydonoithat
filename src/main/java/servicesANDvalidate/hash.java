/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicesANDvalidate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Admin
 */
public class hash {
    public static String MD5(String input) {
        try {
            // Tạo đối tượng MessageDigest với thuật toán MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // Mã hóa input thành byte array
            byte[] messageDigest = md.digest(input.getBytes());
            
            // Chuyển byte array thành số BigInteger
            BigInteger number = new BigInteger(1, messageDigest);
            
            // Chuyển thành chuỗi hex (16 ký tự)
            String hashtext = number.toString(16);
            
            // Thêm số 0 ở đầu nếu cần để đủ 32 ký tự
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Phương thức kiểm tra mật khẩu
    public static boolean validatePassword(String inputPassword, String storedHash) {
        String inputHash = MD5(inputPassword);
        return inputHash.equals(storedHash);
    }
    
    // Phương thức mã hóa password (dùng cho đăng ký)
    public static String hashPassword(String password) {
        return MD5(password);
    }
}
