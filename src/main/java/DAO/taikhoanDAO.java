/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.taikhoan;
import servicesANDvalidate.hash;
import static database.dbconnection.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class taikhoanDAO {
    public boolean checkLogin(String email, String pass) {
        String sql = "SELECT COUNT(*) FROM taikhoan WHERE email = ? AND pass = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String hashedPassword = hash.MD5(pass);
            pstmt.setString(1, email);
            pstmt.setString(2, hashedPassword);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean register(taikhoan taikhoan) {
        String sql = "INSERT INTO taikhoan (email, pass, hoten, rolee, sdt) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Hash mật khẩu trước khi lưu
            String hashedPassword = hash.MD5(taikhoan.getPass());
            
            pstmt.setString(1, taikhoan.getEmail());
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, taikhoan.getName());
            pstmt.setString(4, taikhoan.getRole());
            pstmt.setString(5, taikhoan.getPhone());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Lỗi đăng ký: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM taikhoan WHERE email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra email: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    public taikhoan getTaiKhoanByEmail(String email) {
        taikhoan taikhoan = null;
        String sql = "SELECT * FROM taikhoan WHERE email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    taikhoan = new taikhoan();
                    taikhoan.setEmail(rs.getString("email"));
                    taikhoan.setPass(rs.getString("pass")); // password đã hash
                    taikhoan.setName(rs.getString("hoten"));
                    taikhoan.setRole(rs.getString("rolee"));
                    taikhoan.setPhone(rs.getString("sdt"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy thông tin tài khoản: " + e.getMessage());
            e.printStackTrace();
        }
        return taikhoan;
    }
    public boolean changePassword(String email, String newPassword) {
        String sql = "UPDATE taikhoan SET pass = ? WHERE email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Hash mật khẩu mới
            String hashedPassword = hash.MD5(newPassword);
            
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, email);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Lỗi đổi mật khẩu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateTaiKhoan(taikhoan taikhoan) {
        String sql = "UPDATE taikhoan SET hoten = ?, rolee = ?, sdt = ? WHERE email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, taikhoan.getName());
            pstmt.setString(2, taikhoan.getRole());
            pstmt.setString(3, taikhoan.getPhone());
            pstmt.setString(4, taikhoan.getEmail());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật tài khoản: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteTaiKhoan(String email) {
        String sql = "DELETE FROM taikhoan WHERE email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Lỗi xóa tài khoản: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        String sql = "UPDATE Taikhoan SET pass = ? WHERE email = ?";
    try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)){
        // Kiểm tra mật khẩu cũ
        if (!checkLogin(email, oldPassword)) {
            return false;
        }
        
        String hashedPassword = hash.MD5(newPassword);
            
        st.setString(1, hashedPassword);
        st.setString(2, email);
        
        int result = st.executeUpdate();
        st.close();
        return result > 0;
    } catch (Exception e) {
        System.out.println("Lỗi đổi mật khẩu: " + e.getMessage());
        return false;
    }
}
}
