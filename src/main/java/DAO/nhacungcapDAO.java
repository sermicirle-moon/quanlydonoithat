/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.nhacungcap;
import Models.nhacungcapComboBox;
import database.dbconnection;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author Admin
 */
public class nhacungcapDAO {
      private Connection conn;

    public nhacungcapDAO(Connection conn) {
        this.conn = conn;
    }

    public List<nhacungcap> getAll() throws SQLException {
        List<nhacungcap> list = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            nhacungcap ncc = new nhacungcap();
            ncc.setMancc(rs.getInt("mancc"));
            ncc.setTenncc(rs.getString("tenncc"));
            ncc.setDiachincc(rs.getString("diachincc"));
            ncc.setSdtncc(rs.getString("sdtncc"));
            list.add(ncc);
        }

        rs.close();
        st.close();
        return list;
    }

    public boolean insert(nhacungcap ncc) throws SQLException {
        String sql = "INSERT INTO nhacungcap(tenncc, diachincc, sdtncc) VALUES(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, ncc.getTenncc());
        ps.setString(2, ncc.getDiachincc());
        ps.setString(3, ncc.getSdtncc());

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    public boolean update(nhacungcap ncc) throws SQLException {
        String sql = "UPDATE nhacungcap SET tenncc=?, diachincc=?, sdtncc=? WHERE mancc=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, ncc.getTenncc());
        ps.setString(2, ncc.getDiachincc());
        ps.setString(3, ncc.getSdtncc());
        ps.setInt(4, ncc.getMancc());

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    public boolean delete(int mancc) throws SQLException {
        String sql = "DELETE FROM nhacungcap WHERE mancc=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mancc);

        int row = ps.executeUpdate();
        ps.close();
        return row > 0;
    }

    public nhacungcap findById(int mancc) throws SQLException {
        String sql = "SELECT * FROM nhacungcap WHERE mancc=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mancc);
        ResultSet rs = ps.executeQuery();

        nhacungcap ncc = null;
        if (rs.next()) {
            ncc = new nhacungcap();
            ncc.setMancc(rs.getInt("mancc"));
            ncc.setTenncc(rs.getString("tenncc"));
            ncc.setDiachincc(rs.getString("diachincc"));
            ncc.setSdtncc(rs.getString("sdtncc"));
        }

        rs.close();
        ps.close();
        return ncc;
    }
    
    public List<nhacungcapComboBox> getnccForComboBox() throws SQLException{
        List<nhacungcapComboBox> listncc = new ArrayList<>();
        String sql="select mancc, tenncc from nhacungcap order by tenncc";
        try(PreparedStatement ps=conn.prepareStatement(sql);
                ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                int id=rs.getInt("mancc");
                String name=rs.getString("tenncc");
                listncc.add(new nhacungcapComboBox(id,name));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return listncc;
    }
}
