/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.nhanvien;
import Models.nhanvienComboBox;
import database.dbconnection;
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

    // 1. Lấy tất cả nhân viên
    public List<nhanvien> getAll() throws SQLException {
        List<nhanvien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            nhanvien nv = new nhanvien();
            nv.setManv(rs.getInt("manv"));
            nv.setTennv(rs.getString("tennv"));
            nv.setSdtnv(rs.getString("sdtnv"));
            list.add(nv);
        }

        rs.close();
        st.close();
        return list;
    }

    // 2. Thêm nhân viên mới
    public boolean insert(nhanvien nv) throws SQLException {
        String sql = "INSERT INTO NhanVien(tennv, sdtnv) VALUES(?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nv.getTennv());
        ps.setString(2, nv.getSdtnv());

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 3. Cập nhật nhân viên
    public boolean update(nhanvien nv) throws SQLException {
        String sql = "UPDATE NhanVien SET tennv=?, sdtnv=? WHERE manv=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nv.getTennv());
        ps.setString(2, nv.getSdtnv());
        ps.setInt(3, nv.getManv());

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 4. Xóa nhân viên theo manv
    public boolean delete(int manv) throws SQLException {
        String sql = "DELETE FROM NhanVien WHERE manv=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, manv);

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // 5. Tìm nhân viên theo manv
    public nhanvien findById(int manv) throws SQLException {
        String sql = "SELECT * FROM NhanVien WHERE manv=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, manv);
        ResultSet rs = ps.executeQuery();

        nhanvien nv = null;
        if (rs.next()) {
            nv = new nhanvien();
            nv.setManv(rs.getInt("manv"));
            nv.setTennv(rs.getString("tennv"));
            nv.setSdtnv(rs.getString("sdtnv"));
        }

        rs.close();
        ps.close();
        return nv;
    }
    
    public List<nhanvienComboBox> getForComboBox() throws SQLException{
        List<nhanvienComboBox> list= new ArrayList<>();
        String sql="select manv, tennv from nhanvien order by tennv";
        
        try (PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                int id= rs.getInt("manv");
                String name=rs.getString("tennv");
                list.add(new nhanvienComboBox(id,name));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
                }
        return list;
    }
}
