/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.chitiethoadon;
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
public class chitiethoadonDAO {
    private Connection conn;

    public chitiethoadonDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<chitiethoadon> getByMaHoaDon(int maHD) throws SQLException {
        List<chitiethoadon> list = new ArrayList<>();

        // Join với bảng sanpham để lấy tensp
        String sql = """
            SELECT ct.*, sp.tensp
            FROM chitiethoadon ct
            INNER JOIN sanpham sp ON ct.masp = sp.masp
            WHERE ct.mahoadon = ?
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maHD);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            chitiethoadon ct = new chitiethoadon();
            ct.setMasp(rs.getInt("masp"));
            ct.setSoluong(rs.getInt("soluong"));
            ct.setTongtien(rs.getDouble("tongtien"));
            ct.setTensp(rs.getString("tensp"));
            list.add(ct);
        }
        return list;
    }
    
        public List<chitiethoadon> getAll() throws SQLException {
        List<chitiethoadon> list = new ArrayList<>();
        String sql = "SELECT * FROM chitiethoadon";
        
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            chitiethoadon cthd = new chitiethoadon();
            cthd.setMahoadon(rs.getInt("mahoadon"));
            cthd.setMasp(rs.getInt("masp"));
            cthd.setSoluong(rs.getInt("soluong"));
            cthd.setTongtien(rs.getDouble("tongtien"));
            list.add(cthd);
        }

        rs.close();
        st.close();
        return list;
    }

    // Lấy chi tiết hóa đơn theo mã hóa đơn
    public List<chitiethoadon> getByMaHD(int mahd) throws SQLException {
        List<chitiethoadon> list = new ArrayList<>();
        String sql = "SELECT cthd.*, sp.tensp FROM chitiethoadon cthd " +
                    "JOIN sanpham sp ON cthd.masp = sp.masp " +
                    "WHERE cthd.mahoadon = ?";
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mahd);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            chitiethoadon cthd = new chitiethoadon();
            cthd.setMahoadon(rs.getInt("mahoadon"));
            cthd.setMasp(rs.getInt("masp"));
            cthd.setSoluong(rs.getInt("soluong"));
            
            cthd.setTongtien(rs.getDouble("tongtien"));
            list.add(cthd);
        }

        rs.close();
        ps.close();
        return list;
    }

    // Thêm chi tiết hóa đơn
    public boolean insert(chitiethoadon cthd) throws SQLException {
        String sql = "INSERT INTO chitiethoadon(mahoadon, masp, soluong, tongtien) VALUES(?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, cthd.getMahoadon());
        ps.setInt(2, cthd.getMasp());
        ps.setInt(3, cthd.getSoluong());
        ps.setDouble(4, cthd.getTongtien());

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    // Xóa chi tiết hóa đơn
    public boolean delete(int mahd, int masp) throws SQLException {
        String sql = "DELETE FROM chitiethoadon WHERE mahoadon=? AND masp=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mahd);
        ps.setInt(2, masp);

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }
    public boolean deleteAll(int mahd) throws SQLException {
        String sql = "DELETE FROM chitiethoadon WHERE mahoadon = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mahd);

        int row = ps.executeUpdate();
        ps.close();
        return row >= 0; // Trả về true ngay cả khi không có dòng nào để xóa
    }
}
