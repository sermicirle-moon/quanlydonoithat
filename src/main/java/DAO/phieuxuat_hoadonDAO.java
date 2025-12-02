/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.hoadon;
import Models.phieuxuat_hoadon;
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
public class phieuxuat_hoadonDAO {
    private Connection conn;

    public phieuxuat_hoadonDAO(Connection conn) {
        this.conn = conn;
    }
    
    public boolean insert(int mapx, int mahoadon) {
        String sql = "INSERT INTO phieuxuat_hoadon (mapx, mahoadon) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mapx);
            ps.setInt(2, mahoadon);

            int n = ps.executeUpdate();
            return n > 0; // true nếu insert thành công
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public List<hoadon> getHoaDonByMapx(int mapx) throws SQLException {
        List<hoadon> list = new ArrayList<>();

        String sql = """
            SELECT hd.mahoadon, hd.ngayxuathd, hd.tongtien, hd.makh
            FROM phieuxuat_hoadon pxhd
            INNER JOIN hoadon hd ON pxhd.mahoadon = hd.mahoadon
            WHERE pxhd.mapx = ?
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mapx);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            hoadon hd = new hoadon();
            hd.setMahoadon(rs.getInt("mahoadon"));
            hd.setNgayxuathd(rs.getDate("ngayxuathd"));
            hd.setTongtien(rs.getDouble("tongtien"));
            hd.setMakhachhang(rs.getInt("makh"));

            list.add(hd);
        }

        return list;
    }
}
