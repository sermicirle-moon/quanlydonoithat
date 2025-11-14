/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class dbconnection {
    public static Connection getConnection() {
        String host="localhost";
        String port="1433";
        String dbname="QLdonoithat";
        String user="sa";
        String pass="Xenlulozo#8011";
        String url = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + dbname + ";encrypt=false;trustServerCertificate=true";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // bắt buộc với driver cũ
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
