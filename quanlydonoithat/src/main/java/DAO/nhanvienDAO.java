/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import Models.nhanvien;
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
public class nhanvienDAO {
     private Connection conn;

    public nhanvienDAO(Connection conn) {
        this.conn = conn;
    }

    // 1. Lấy tất cả khách hàng
    public List<nhanvien> getAll() throws SQLException {
        List<nhanvien> list = new ArrayList<>();
        String sql = "SELECT * FROM nhanvien";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            nhanvien kh = new nhanvien();
            kh.setManv(rs.getInt("manv"));
            kh.setTennv(rs.getString("tennv"));
            kh.setSdtnv(rs.getString("sdtnv"));
            list.add(kh);
        }

        rs.close();
        st.close();
        return list;
    }

    // 2. Thêm khách hàng mới
    public boolean insert(nhanvien nv) throws SQLException {
    String sql = "INSERT INTO nhanvien(manv, tennv, sdtnv) VALUES(?, ?, ?)";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setInt(1, nv.getManv());
    ps.setString(2, nv.getTennv());
    ps.setString(3, nv.getSdtnv());
    int row = ps.executeUpdate();
    ps.close();
    return row > 0;
}

    // 3. Cập nhật khách hàng
    public boolean update(nhanvien nv) throws SQLException {
        String sql = "UPDATE nhanvien SET tennv=?, sdtnv=? WHERE manv=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nv.getTennv());
        ps.setString(2, nv.getSdtnv());
        ps.setInt(3, nv.getManv());

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 4. Xóa khách hàng theo makh
    public boolean delete(int manv) throws SQLException {
        String sql = "DELETE FROM nhanvien WHERE manv=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, manv);

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

}
