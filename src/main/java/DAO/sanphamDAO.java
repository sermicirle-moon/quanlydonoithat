/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.sanpham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class sanphamDAO {
     private Connection conn;

    public sanphamDAO(Connection conn) {
        this.conn = conn;
    }

    // 1. Lấy tất cả sản phẩm
    public List<sanpham> getAll() throws SQLException {
        List<sanpham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            sanpham sp = new sanpham();
            sp.setMasp(rs.getInt("masp"));
            sp.setTensp(rs.getString("tensp"));
            sp.setMaloai(rs.getString("maloai"));
            sp.setSoluong(rs.getInt("soluong"));
            sp.setGiaban((float) rs.getDouble("giaban"));
            list.add(sp);
        }

        rs.close();
        st.close();
        return list;
    }

    // 2. Thêm sản phẩm mới
    public boolean insert(sanpham sp) throws SQLException {
        String sql = "INSERT INTO SanPham(tensp, maloai, soluong, giaban) VALUES(?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, sp.getTensp());
        ps.setString(2, sp.getMaloai());
        ps.setInt(3, sp.getSoluong());
        ps.setDouble(4, sp.getGiaban());

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 3. Cập nhật sản phẩm
    public boolean update(sanpham sp) throws SQLException {
        String sql = "UPDATE SanPham SET tensp=?, maloai=?, soluong=?, giaban=? WHERE masp=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, sp.getTensp());
        ps.setString(2, sp.getMaloai());
        ps.setInt(3, sp.getSoluong());
        ps.setDouble(4, sp.getGiaban());
        ps.setInt(5, sp.getMasp());

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 4. Xóa sản phẩm theo masp
    public boolean delete(int masp) throws SQLException {
        String sql = "DELETE FROM SanPham WHERE masp=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, masp);

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 5. Tìm sản phẩm theo masp
    public sanpham findById(int masp) throws SQLException {
        String sql = "SELECT * FROM SanPham WHERE masp=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, masp);
        ResultSet rs = ps.executeQuery();

        sanpham sp = null;
        if (rs.next()) {
            sp = new sanpham();
            sp.setMasp(rs.getInt("masp"));
            sp.setTensp(rs.getString("tensp"));
            sp.setMaloai(rs.getString("maloai"));
            sp.setSoluong(rs.getInt("soluong"));
            sp.setGiaban((float) rs.getDouble("giaban"));
        }

        rs.close();
        ps.close();
        return sp;
    }
}
