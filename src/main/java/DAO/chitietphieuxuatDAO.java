/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.chitietphieuxuat;
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
public class chitietphieuxuatDAO {
    private Connection conn;

    public chitietphieuxuatDAO(Connection conn) {
        this.conn = conn;
    }
    
    public boolean insertCTPhieuXuat(chitietphieuxuat ct) {
        String sql = "INSERT INTO chitietphieuxuat (mapx, masp, soluong, thanhtien) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ct.getMapx());
            ps.setInt(2, ct.getMasp());
            ps.setInt(3, ct.getSoluong());
            ps.setDouble(4, ct.getThanhtien());

            int n = ps.executeUpdate();
            return n > 0; // true nếu insert thành công
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public List<Object[]> getCTByMapx(int mapx) throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT 
                sp.tensp,
                SUM(ct.soluong) AS tongSoLuong,
                SUM(ct.thanhtien) AS tongTien
            FROM chitietphieuxuat ct
            JOIN sanpham sp ON ct.masp = sp.masp
            WHERE ct.mapx = ?
            GROUP BY sp.tensp
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mapx);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Object[]{
                rs.getString("tensp"),
                rs.getInt("tongSoLuong"),
                rs.getDouble("tongTien")
            });
        }

        return list;
    }
}
