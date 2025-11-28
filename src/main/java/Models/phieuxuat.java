/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class phieuxuat {
    private int mapx;
    private Date ngayxuat;
    private int madvvc;
    private double tongtien;
    private String trangthai;

    public phieuxuat() {
    }

    public phieuxuat(int mapx, Date ngayxuat, int madvvc, double tongtien, String trangthai) {
        this.mapx = mapx;
        this.ngayxuat = ngayxuat;
        this.madvvc = madvvc;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public int getMapx() {
        return mapx;
    }

    public void setMapx(int mapx) {
        this.mapx = mapx;
    }

    public Date getNgayxuat() {
        return ngayxuat;
    }

    public void setNgayxuat(Date ngayxuat) {
        this.ngayxuat = ngayxuat;
    }

    public int getMadvvc() {
        return madvvc;
    }

    public void setMadvvc(int madvvc) {
        this.madvvc = madvvc;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
    
    
}
