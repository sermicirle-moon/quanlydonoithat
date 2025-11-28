/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.hoadon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class hoadonDAO {
    private Connection conn;

    public hoadonDAO(Connection conn) {
        this.conn = conn;
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
}
