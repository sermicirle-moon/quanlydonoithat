/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import database.dbconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class validate {
    
    //ko de trong
    public static boolean isEmpty(String text){
        return text !=null && !text.isEmpty();
    }
    
    //chi nhap so va lon hon 0
    public static boolean isPositiveNumber(String text){
        try{
            int n=Integer.parseInt(text);
            return n>0;
        }catch(NumberFormatException e){
            return false;
        }
    }
    public static boolean isNumeric(String text) {
    if (text == null || text.isEmpty()) {
        return false;
    }
    return text.matches("\\d+"); // Chỉ cho phép số từ 0-9
    }
    public static boolean isValidPhoneNumber(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return text.matches("\\d{10}"); // Phải có đúng 10 chữ số
    }
    public static boolean isDuplicateCode(String code, String tableName, String columnName) {
         if (code.isEmpty()) {
            return false;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbconnection.getConnection();
            
            String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, code);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi kiểm tra trùng mã: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    public static boolean isQuantityAvailable(int maSP, int soLuongMua) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbconnection.getConnection();
            String sql = "SELECT soluong FROM sanpham WHERE masp = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maSP);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                int soLuongHienCo = rs.getInt("soluong");
                return soLuongMua <= soLuongHienCo;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static int getCurrentQuantity(int maSP) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbconnection.getConnection();
            String sql = "SELECT soluong FROM sanpham WHERE masp = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maSP);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("soluong");
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        }
    
    
}
