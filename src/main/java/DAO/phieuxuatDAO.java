/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.chitiethoadon;
import Models.phieuxuat;
import java.rmi.ConnectIOException;
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
public class phieuxuatDAO {
    private Connection conn;

    public phieuxuatDAO(Connection conn) {
        this.conn = conn;
    }
    
    public boolean insertPhieuXuat(phieuxuat px) {
        String sql = "INSERT INTO phieuxuat (mapx, ngayxuat, madvvc, tongtien, trangthai) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, px.getMapx());
            ps.setDate(2, new java.sql.Date(px.getNgayxuat().getTime()));
            ps.setInt(3, px.getMadvvc());
            ps.setDouble(4, px.getTongtien());
            ps.setString(5, px.getTrangthai());

            int n = ps.executeUpdate();
            return n > 0; // true nếu insert thành công
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean existsMapx(int mapx) {
        String sql = "SELECT COUNT(*) FROM phieuxuat WHERE mapx = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mapx);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // true nếu đã tồn tại
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean updateTongTien(int mapx, double tongTien) throws SQLException {
        String sql = "UPDATE phieuxuat SET tongtien = ? WHERE mapx = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, tongTien);
            ps.setInt(2, mapx);
            int updated = ps.executeUpdate();
            return updated > 0;
        }
    }
    
    public List<phieuxuat> getAll() throws SQLException {
        List<phieuxuat> list = new ArrayList<>();
        String sql = "SELECT * FROM phieuxuat";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                phieuxuat px = new phieuxuat();
                px.setMapx(rs.getInt("mapx"));
                px.setNgayxuat(rs.getDate("ngayxuat"));
                px.setMadvvc(rs.getInt("madvvc"));
                px.setTongtien(rs.getDouble("tongtien"));
                px.setTrangthai(rs.getString("trangthai"));
                list.add(px);
            }
        }
        return list;
    }
    
    public phieuxuat getPhieuXuatById(int mapx) throws SQLException {
        String sql = "SELECT * FROM phieuxuat WHERE mapx = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mapx);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new phieuxuat(
                rs.getInt("mapx"),
                rs.getDate("ngayxuat"),
                rs.getInt("madvvc"),
                rs.getDouble("tongtien"),
                rs.getString("trangthai")
            );
        }
        return null;
    }
    
    public void updatePhieuXuatAndHoaDon(int mapx, String trangThaiPhieu, String trangThaiHD) throws SQLException {
        // 1. Cập nhật trạng thái phiếu xuất
        conn.setAutoCommit(false);
        try{
            String sqlPX = "UPDATE phieuxuat SET trangthai = ? WHERE mapx = ?";
            try (PreparedStatement psPX = conn.prepareStatement(sqlPX)) {
                psPX.setString(1, trangThaiPhieu);
                psPX.setInt(2, mapx);
                psPX.executeUpdate();
            }

            // 2. Lấy danh sách mã hóa đơn liên quan
            String sqlGetHD = "SELECT mahoadon FROM phieuxuat_hoadon WHERE mapx = ?";
            List<Integer> listMaHD = new ArrayList<>();
            try (PreparedStatement psGet = conn.prepareStatement(sqlGetHD)) {
                psGet.setInt(1, mapx);
                try (ResultSet rs = psGet.executeQuery()) {
                    while (rs.next()) {
                        listMaHD.add(rs.getInt("mahoadon"));
                    }
                }
            }

            chitiethoadonDAO cthdDAO = new chitiethoadonDAO(conn);
            sanphamDAO spDAO = new sanphamDAO(conn);
            // 3. Cập nhật trạng thái hóa đơn
            String sqlHD = "UPDATE hoadon SET trangthai = ? WHERE mahoadon = ?";
            try (PreparedStatement psHD = conn.prepareStatement(sqlHD)) {
                for (int mahd : listMaHD) {
                    psHD.setString(1, trangThaiHD);
                    psHD.setInt(2, mahd);
                    psHD.executeUpdate();

                    // lấy chi tiết hóa đơn
                    List<chitiethoadon> ctHD = cthdDAO.getByMaHD(mahd);
                    for(chitiethoadon ct: ctHD){
                        spDAO.congSoLuong(ct.getMasp(), ct.getSoluong());
                    }
                }
            }
            conn.commit(); 
        }catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
