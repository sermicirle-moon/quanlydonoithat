/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class chitietphieunhapDAO {
    private Connection conn;

    public chitietphieunhapDAO(Connection conn) {
        this.conn = conn;
    }
    
    public boolean isMaPhieuExit(int maPhieu) throws SQLException{
        String sql="select count(*) from phieunhap where maphieu=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setInt(1, maPhieu);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            return rs.getInt(1)>0;
        }
        return false;
    }
}
