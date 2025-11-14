/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.khachhang;
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
public class khachhangDAO {
        private Connection conn;

    public khachhangDAO(Connection conn) {
        this.conn = conn;
    }

    // 1. Lấy tất cả khách hàng
    public List<khachhang> getAll() throws SQLException {
        List<khachhang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            khachhang kh = new khachhang();
            kh.setMakh(rs.getInt("makh"));
            kh.setTenkh(rs.getString("tenkh"));
            kh.setSdtkh(rs.getString("sdtkh"));
            kh.setLoaikh(rs.getString("loaikh"));
            list.add(kh);
        }

        rs.close();
        st.close();
        return list;
    }

    // 2. Thêm khách hàng mới
    public boolean insert(khachhang kh) throws SQLException {
        String sql = "INSERT INTO KhachHang(tenkh, sdtkh, loaikh) VALUES(?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, kh.getTenkh());
        ps.setString(2, kh.getSdtkh());
        ps.setString(3, kh.getLoaikh());

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 3. Cập nhật khách hàng
    public boolean update(khachhang kh) throws SQLException {
        String sql = "UPDATE KhachHang SET tenkh=?, sdtkh=?, loaikh=? WHERE makh=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, kh.getTenkh());
        ps.setString(2, kh.getSdtkh());
        ps.setString(3, kh.getLoaikh());
        ps.setInt(4, kh.getMakh());

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 4. Xóa khách hàng theo makh
    public boolean delete(int makh) throws SQLException {
        String sql = "DELETE FROM KhachHang WHERE makh=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, makh);

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 5. Tìm khách hàng theo makh
    public khachhang findById(int makh) throws SQLException {
        String sql = "SELECT * FROM KhachHang WHERE makh=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, makh);
        ResultSet rs = ps.executeQuery();

        khachhang kh = null;
        if (rs.next()) {
            kh = new khachhang();
            kh.setMakh(rs.getInt("makh"));
            kh.setTenkh(rs.getString("tenkh"));
            kh.setSdtkh(rs.getString("sdtkh"));
            kh.setLoaikh(rs.getString("loaikh"));
        }

        rs.close();
        ps.close();
        return kh;
    }
}
