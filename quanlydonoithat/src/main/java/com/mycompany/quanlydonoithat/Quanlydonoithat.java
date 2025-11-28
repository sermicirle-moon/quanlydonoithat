/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quanlydonoithat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class Quanlydonoithat {
    public static Connection getConnection() {
        String host="localhost";
        String port="1433";
        String dbname="QLdonoithat";
        String user="sa";
        String pass="123";
        String url = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + dbname + ";encrypt=false;trustServerCertificate=true";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // bắt buộc với driver cũ
            return DriverManager.getConnection(url, user, pass);
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Đã đóng kết nối.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Không thể kết nối database!");
        }
    }
}
