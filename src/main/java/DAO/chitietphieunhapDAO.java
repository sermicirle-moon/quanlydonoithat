/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.chitietphieu;
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
public class chitietphieunhapDAO {
    private Connection conn;

    public chitietphieunhapDAO(Connection conn) {
        this.conn = conn;
    }
    
    public boolean isMaPhieuExit(int maPhieu) throws SQLException{
        String sql="select count(*) from phieunhap where maphieu=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setInt(1, maPhieu);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            return rs.getInt(1)>0;
        }
        return false;
    }
    
    public boolean themChiTietPhieu(int maphieu, int masp, int soluong, double dongia, double thanhtien) throws SQLException {
        String sql = "INSERT INTO chitietphieu(maphieu, masp, soluong, dongia, thanhtien) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, maphieu);
        pst.setInt(2, masp);
        pst.setInt(3, soluong);
        pst.setDouble(4, dongia);
        pst.setDouble(5, thanhtien);
        return pst.executeUpdate() > 0;
    }
    
    public List<chitietphieu> getByMaPhieu(int maphieu) throws SQLException {
        List<chitietphieu> list = new ArrayList<>();
        String sql = """
            SELECT c.maphieu, c.masp, s.tensp, c.soluong, c.dongia, c.thanhtien
            FROM chitietphieu c
            JOIN sanpham s ON c.masp = s.masp
            WHERE c.maphieu = ?
        """;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maphieu);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            chitietphieu ct = new chitietphieu(
                rs.getInt("maphieu"),
                rs.getInt("masp"),
                rs.getString("tensp"),
                rs.getInt("soluong"),
                rs.getFloat("dongia"),
                rs.getFloat("thanhtien")
            );
            list.add(ct);
        }
        return list;
    }
    
    public boolean xoaChiTietPhieu(int maPhieu) throws SQLException {
        String sql = "DELETE FROM chitietphieu WHERE maphieu = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maPhieu);
        int n = ps.executeUpdate(); // trả về số bản ghi bị xóa
        ps.close();
        return n > 0; // true nếu có ít nhất 1 bản ghi bị xóa
    }
}
