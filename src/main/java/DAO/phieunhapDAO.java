/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import Models.phieunhap;
import Models.sanpham;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class phieunhapDAO {
    private Connection conn;

    public phieunhapDAO(Connection conn) {
        this.conn = conn;
    }
    
    public boolean themPhieuNhap(int maphieu, java.util.Date ngaynhap, int manv, int mancc, double tongtien) throws SQLException {
        String sql = "INSERT INTO phieunhap (maphieu, ngaynhap, manv, mancc, tongtien) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, maphieu);
            java.sql.Date sqlDate = new java.sql.Date(ngaynhap.getTime());
            pst.setDate(2, sqlDate);
            pst.setInt(3, manv);
            pst.setInt(4, mancc);
            pst.setDouble(5, tongtien);

            int result = pst.executeUpdate();
            return result > 0;
        }
    }
    
    public List<phieunhap> getAll() throws SQLException{
        List<phieunhap> list = new ArrayList<>();
        String sql = "SELECT phieunhap.maphieu, phieunhap.ngaynhap, nhanvien.tennv, nhacungcap.tenncc, phieunhap.tongtien "
           + "FROM phieunhap "
           + "JOIN nhanvien ON phieunhap.manv = nhanvien.manv "
           + "JOIN nhacungcap ON phieunhap.mancc = nhacungcap.mancc";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            phieunhap pn = new phieunhap();
            pn.setMaphieu(rs.getInt("maphieu"));
            java.sql.Date sqlDate = rs.getDate("ngaynhap");
            pn.setNgaynhap(rs.getDate("ngaynhap"));
            pn.setTennv(rs.getString("tennv"));
            pn.setTenncc(rs.getString("tenncc"));
            pn.setTongtien(rs.getFloat("tongtien"));
            list.add(pn);
        }

        rs.close();
        st.close();
        return list;
    }
}
