/*
 
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template*/
package DAO;

import Models.hoadon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 
@author Admin*/
public class hoadonDAO {
    private Connection conn;

    public hoadonDAO(Connection conn) {
        this.conn = conn;
    }
    // THÊM HÓA ĐƠN MỚI
    public boolean themHoaDon(hoadon hd) throws SQLException {
        String sql = "INSERT INTO hoadon(mahoadon, ngayxuathd, tongtien, trangthai, makh) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, hd.getMahoadon());
            ps.setDate(2, hd.getNgayxuathd());
            ps.setDouble(3, hd.getTongtien());
            ps.setString(4, hd.getTrangthai());
            ps.setInt(5, hd.getMakhachhang());

            int row = ps.executeUpdate();
            return row > 0;
            
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    // LẤY TẤT CẢ HÓA ĐƠN
    public List<hoadon> getAll() throws SQLException {
        List<hoadon> list = new ArrayList<>();
        String sql = "SELECT * FROM hoadon";
        
        Statement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                hoadon hd = new hoadon();
                hd.setMahoadon(rs.getInt("mahoadon"));
                hd.setNgayxuathd(rs.getDate("ngayxuathd"));
                hd.setTongtien(rs.getDouble("tongtien"));
                hd.setTrangthai(rs.getString("trangthai"));
                hd.setmakh(rs.getInt("makh"));
                list.add(hd);
            }
            return list;
            
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }
    }

    // LẤY HÓA ĐƠN THEO MÃ
    public hoadon getByMaHD(int maHD) throws SQLException {
        String sql = "SELECT * FROM hoadon WHERE mahoadon = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            rs = ps.executeQuery();

            if (rs.next()) {
                hoadon hd = new hoadon();
                hd.setMahoadon(rs.getInt("mahoadon"));
                hd.setNgayxuathd(rs.getDate("ngayxuathd"));
                hd.setTongtien(rs.getDouble("tongtien"));
                hd.setTrangthai(rs.getString("trangthai"));
                hd.setmakh(rs.getInt("makh"));
                return hd;
            }
            return null;
            
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }

    // CẬP NHẬT HÓA ĐƠN
    public boolean updateHoaDon(hoadon hd) throws SQLException {
        String sql = "UPDATE hoadon SET ngayxuathd=?, tongtien=?, trangthai=?, makh=? WHERE mahoadon=?";
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setDate(1, hd.getNgayxuathd());
            ps.setDouble(2, hd.getTongtien());
            ps.setString(3, hd.getTrangthai());
            ps.setInt(4, hd.getMakhachhang());
            ps.setInt(5, hd.getMahoadon());

            int row = ps.executeUpdate();
            return row > 0;
            
        } finally {
            if (ps != null) ps.close();
        }
    }

    // XÓA HÓA ĐƠN
    public boolean deleteHoaDon(int maHD) throws SQLException {
        String sql = "DELETE FROM hoadon WHERE mahoadon=?";
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maHD);

            int row = ps.executeUpdate();
            return row > 0;
            
        } finally {
            if (ps != null) ps.close();
        }
    }

    // KIỂM TRA MÃ HÓA ĐƠN ĐÃ TỒN TẠI CHƯA
    public boolean isMaHDExists(int maHD) throws SQLException {
        String sql = "SELECT COUNT(*) FROM hoadon WHERE mahoadon = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
            return false;
            
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }

    // LẤY HÓA ĐƠN THEO MÃ KHÁCH HÀNG
    public List<hoadon> getByMaKH(int maKH) throws SQLException {
        List<hoadon> list = new ArrayList<>();
        String sql = "SELECT * FROM hoadon WHERE makh= ?";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maKH);
            rs = ps.executeQuery();

            while (rs.next()) {
                hoadon hd = new hoadon();
                hd.setMahoadon(rs.getInt("mahoadon"));
                hd.setNgayxuathd(rs.getDate("ngayxuathd"));
                hd.setTongtien(rs.getDouble("tongtien"));
                hd.setTrangthai(rs.getString("trangthai"));
                hd.setMakhachhang(rs.getInt("makh"));
                list.add(hd);
            }
            return list;
            
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }

    // LẤY TỔNG DOANH THU
    public double getTongDoanhThu() throws SQLException {
        String sql = "SELECT SUM(tongtien) as tong FROM hoadon WHERE trangthai = 'Đã thanh toán'";
        Statement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                return rs.getDouble("tong");
            }
            return 0;
            
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }
    }

    public List<hoadon> getByTrangThai(String trangthai) throws SQLException {
        List<hoadon> list = new ArrayList<>();

        String sql = "SELECT * FROM hoadon WHERE trangthai = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangthai);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                hoadon hd = new hoadon();
                hd.setMahoadon(rs.getInt("mahoadon"));
                java.sql.Date sqlDate = rs.getDate("ngayxuathd");
                hd.setNgayxuathd(sqlDate); 
                hd.setTongtien(rs.getDouble("tongtien"));
                hd.setTrangthai(rs.getString("trangthai"));
                hd.setMakhachhang(rs.getInt("makh"));

                list.add(hd);
            }
        }

        return list;
    }

    public boolean updateTrangThai(int mahoadon, String newStatus) throws SQLException {
        String sql = "UPDATE hoadon SET trangthai = ? WHERE mahoadon = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, newStatus);
        ps.setInt(2, mahoadon);
        return ps.executeUpdate() > 0;
    }
    public boolean UpdateTT(int mahoadon, double tongTienMoi) throws SQLException {
    String sql = "UPDATE hoadon SET tongtien = ? WHERE mahoadon = ?";
    PreparedStatement ps = null;
    
    try {
        ps = conn.prepareStatement(sql);
        ps.setDouble(1, tongTienMoi);
        ps.setInt(2, mahoadon);

        int row = ps.executeUpdate();
        return row > 0;
        
    } finally {
        if (ps != null) ps.close();
    }
}
}