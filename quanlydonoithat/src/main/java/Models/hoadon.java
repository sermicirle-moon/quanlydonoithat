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
public class hoadon {
    private int mahoadon;
    private Date ngayxuathd;
    private double tongtien;
    private String trangthai;
    private int makh;

    public hoadon() {
    }

    public hoadon(int mahoadon, int makh, Date ngayxuathd, double tongtien, String trangthai) {
        this.mahoadon = mahoadon;
        this.makh = makh;
        this.ngayxuathd = ngayxuathd;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public int getMahoadon() {
        return mahoadon;
    }

    public Date getNgayxuathd() {
        return ngayxuathd;
    }
    
    
    public double getTongtien() {
        return tongtien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public int getMakh() {
        return makh;
    }

    public void setMahoadon(int mahoadon) {
        this.mahoadon = mahoadon;
    }

    public void setNgayxuathd(Date ngayxuathd) {
        this.ngayxuathd = ngayxuathd;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public void setmakh(int makh) {
        this.makh = makh;
    }
    public int getMakhachhang(){
        return makh;
    }
    public void setMakhachhang(int makh){
        this.makh=makh;
    }
}
