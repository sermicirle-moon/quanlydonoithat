/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.goiysanpham;
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
        String sql = "SELECT * FROM sanpham";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            sanpham sp = new sanpham();
            sp.setMasp(rs.getInt("masp"));
            sp.setTensp(rs.getString("tensp"));
            sp.setMaloai(rs.getInt("maloai"));
            sp.setSoluong(rs.getInt("soluong"));
            sp.setGiaban((float) rs.getDouble("giaban"));
            sp.setGianhap((float) rs.getDouble("gianhap"));
            sp.setTrangthai(rs.getString("trangthai"));
            list.add(sp);
        }

        rs.close();
        st.close();
        return list;
    }

    // 2. Thêm sản phẩm
    public boolean insert(sanpham sp) throws SQLException {
        String sql = "INSERT INTO sanpham(masp, tensp, maloai, soluong, giaban, gianhap) VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,sp.getMasp());
        ps.setString(2, sp.getTensp());
        ps.setInt(3, sp.getMaloai());
        ps.setInt(4, sp.getSoluong());
        ps.setDouble(5, sp.getGianhap());
        ps.setDouble(6, sp.getGiaban());
        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 3. Cập nhật sản phẩm
    public boolean update(sanpham sp) throws SQLException {
        String sql = "UPDATE sanpham SET tensp=?, maloai=?, soluong=?, giaban=?, gianhap=?, trangthai=? WHERE masp=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, sp.getTensp());
        ps.setInt(2, sp.getMaloai());
        ps.setInt(3, sp.getSoluong());
        ps.setDouble(4, sp.getGianhap());
        ps.setDouble(5, sp.getGiaban());
        ps.setString(6, sp.getTrangthai());
        ps.setInt(7, sp.getMasp());

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 4. Xóa sản phẩm theo masp
    public boolean delete(int masp) throws SQLException {
        String sql = "DELETE FROM sanpham WHERE masp=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, masp);

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 5. Tìm sản phẩm theo masp
    public sanpham findById(int masp) throws SQLException {
        String sql = "SELECT * FROM sanpham WHERE masp=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, masp);
        ResultSet rs = ps.executeQuery();

        sanpham sp = null;
        if (rs.next()) {
            sp = new sanpham();
            sp.setMasp(rs.getInt("masp"));
            sp.setTensp(rs.getString("tensp"));
            sp.setMaloai(rs.getInt("maloai"));
            sp.setSoluong(rs.getInt("soluong"));
            sp.setGianhap((float) rs.getDouble("gianhap"));
            sp.setGiaban((float) rs.getDouble("giaban"));
            sp.setTrangthai(rs.getString("trangthai"));
        }

        rs.close();
        ps.close();
        return sp;
    }
    
    public List<goiysanpham> getAllForComboBox() {
        List<goiysanpham> list = new ArrayList<>();
        String sql = "SELECT masp, tensp, gianhap FROM sanpham ORDER BY tensp";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("masp");
                String name = rs.getString("tensp");
                double dongia=rs.getDouble("gianhap");
                list.add(new goiysanpham(id, name, dongia));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean isMaspExist(int masp) {
        String sql = "SELECT COUNT(*) AS count FROM sanpham WHERE masp = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, masp);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
