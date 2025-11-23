/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.donvivanchuyen;
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
public class donvivanchuyenDAO {
    private Connection conn;

    public donvivanchuyenDAO(Connection conn) {
        this.conn = conn;
    }
    
     public List<donvivanchuyen> getAll() throws SQLException {
        List<donvivanchuyen> list = new ArrayList<>();
        String sql = "SELECT * FROM donvivanchuyen";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            donvivanchuyen dv = new donvivanchuyen();
            dv.setMadvvc(rs.getInt("madvvc"));
            dv.setTendvvc(rs.getString("tendvvc"));
            dv.setSdtdvvc(rs.getString("sodienthoai"));
            dv.setDiachidvvc(rs.getString("diachi"));
            list.add(dv);
        }

        return list;
    }

    // 2. Lấy theo ID
    public donvivanchuyen getById(int id) throws SQLException {
        String sql = "SELECT * FROM donvivanchuyen WHERE madvvc = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            donvivanchuyen dv = new donvivanchuyen();
            dv.setMadvvc(rs.getInt("madvvc"));
            dv.setTendvvc(rs.getString("tendvvc"));
            dv.setSdtdvvc(rs.getString("sodienthoai"));
            dv.setDiachidvvc(rs.getString("diachi"));
            return dv;
        }

        return null;
    }

    // 3. Thêm mới
    public boolean insert(donvivanchuyen dv) throws SQLException {
        String sql = """
                     INSERT INTO donvivanchuyen (madvvc, tendvvc, sodienthoai, diachi)
                     VALUES (?, ?, ?, ?)
                     """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, dv.getMadvvc());
        ps.setString(2, dv.getTendvvc());
        ps.setString(3, dv.getSdtdvvc());
        ps.setString(4, dv.getDiachidvvc());

        return ps.executeUpdate() > 0;
    }

    // 4. Cập nhật
    public boolean update(donvivanchuyen dv) throws SQLException {
        String sql = """
                     UPDATE donvivanchuyen
                     SET tendvvc = ?, sodienthoai = ?, diachi = ?
                     WHERE madvvc = ?
                     """;

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, dv.getTendvvc());
        ps.setString(2, dv.getSdtdvvc());
        ps.setString(3, dv.getDiachidvvc());
        ps.setInt(4, dv.getMadvvc());

        return ps.executeUpdate() > 0;
    }

    // 5. Xóa theo ID
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM donvivanchuyen WHERE madvvc = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        return ps.executeUpdate() > 0;
    }
    
    public boolean exists(int madvvc) throws SQLException {
        String sql = "SELECT COUNT(*) FROM donvivanchuyen WHERE madvvc = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, madvvc);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
}
