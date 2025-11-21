/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.loaisanpham;
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
public class loaisanphamDAO {
     private Connection conn;

    public loaisanphamDAO(Connection conn) {
        this.conn = conn;
    }

    // 1. Lấy tất cả loại sản phẩm
    public List<loaisanpham> getAll() throws SQLException {
        List<loaisanpham> list = new ArrayList<>();
        String sql = "SELECT * FROM loaisanpham";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            loaisanpham lsp = new loaisanpham(
                rs.getInt("maloai"),
                rs.getString("tenloai"),
                rs.getString("mota")
            );
            list.add(lsp);
        }

        rs.close();
        st.close();
        return list;
    }

    // 2. Thêm loại sản phẩm mới
    public boolean insert(loaisanpham lsp) throws SQLException {
        String sql = "INSERT INTO loaisanpham(maloai, tenloai, mota) VALUES (?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, lsp.getMaloai());
        pst.setString(2, lsp.getTenloai());
        pst.setString(3, lsp.getMota());

        int row = pst.executeUpdate();
        pst.close();
        return row > 0;
    }

    // 3. Cập nhật loại sản phẩm
    public boolean update(loaisanpham lsp) throws SQLException {
        String sql = "UPDATE loaisanpham SET tenloai=?, mota=? WHERE maLoai=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, lsp.getTenloai());
        pst.setString(2, lsp.getMota());
        pst.setInt(3, lsp.getMaloai());
        int row = pst.executeUpdate();
        pst.close();
        return row > 0;
    }

    // 4. Xóa loại sản phẩm
    public boolean delete(String maLoai) throws SQLException {
        String sql = "DELETE FROM loaiSanPham WHERE maLoai=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, maLoai);

        int row = pst.executeUpdate();
        pst.close();
        return row > 0;
    }

    // 5. Tìm theo mã loại
    public loaisanpham findById(String maLoai) throws SQLException {
        String sql = "SELECT * FROM LoaiSanPham WHERE MaLoai=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, maLoai);
        ResultSet rs = pst.executeQuery();

        loaisanpham lsp = null;
        if (rs.next()) {
            lsp = new loaisanpham(
                rs.getInt("MaLoai"),
                rs.getString("TenLoai"),
                rs.getString("MoTa")
            );
        }

        rs.close();
        pst.close();
        return lsp;
    }
    
    public boolean isMaLoai(int Maloai) throws SQLException{
        String sql="select count(*) from loaisanpham where maloai=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setInt(1, Maloai);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            return rs.getInt(1)>0;
        }
        return false;
    }
    
    public boolean xoaNhieuLoaiSanPham(List<Integer> listmaloai) throws SQLException{
        boolean AllDeleted=true;
        try{
            conn.setAutoCommit(false);
            String sql = "DELETE FROM loaisanpham WHERE maloai = ?";
            PreparedStatement ps=conn.prepareStatement(sql);
            for(int maloai:listmaloai){
                ps.setInt(1, maloai);
                int n = ps.executeUpdate();
                if (n == 0) AllDeleted = false;
            }
            conn.commit(); // commit transaction
            ps.close();
        } catch (SQLException e) {
            conn.rollback(); // rollback nếu có lỗi
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
        return AllDeleted;
    }
}
