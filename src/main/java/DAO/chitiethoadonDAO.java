/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.chitiethoadon;
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
public class chitiethoadonDAO {
    private Connection conn;

    public chitiethoadonDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<chitiethoadon> getByMaHoaDon(int maHD) throws SQLException {
        List<chitiethoadon> list = new ArrayList<>();

        // Join với bảng sanpham để lấy tensp
        String sql = """
            SELECT ct.*, sp.tensp
            FROM chitiethoadon ct
            INNER JOIN sanpham sp ON ct.masp = sp.masp
            WHERE ct.mahoadon = ?
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maHD);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            chitiethoadon ct = new chitiethoadon();
            ct.setMasp(rs.getInt("masp"));
            ct.setSoluong(rs.getInt("soluong"));
            ct.setTongtien(rs.getDouble("tongtien"));
            ct.setTensp(rs.getString("tensp"));
            list.add(ct);
        }
        return list;
    }
}
