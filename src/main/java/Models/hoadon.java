/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class hoadon {
    private int mahoadon;
    private String ngayxuathd;
    private double tongtien;
    private String trangthai;
    private int makhachhang;

    public hoadon() {
    }

    public hoadon(int mahoadon, String ngayxuathd, double tongtien, String trangthai, int makhachhang) {
        this.mahoadon = mahoadon;
        this.ngayxuathd = ngayxuathd;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
        this.makhachhang = makhachhang;
    }

    public int getMahoadon() {
        return mahoadon;
    }

    public String getNgayxuathd() {
        return ngayxuathd;
    }

    public double getTongtien() {
        return tongtien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public int getMakhachhang() {
        return makhachhang;
    }

    public void setMahoadon(int mahoadon) {
        this.mahoadon = mahoadon;
    }

    public void setNgayxuathd(String ngayxuathd) {
        this.ngayxuathd = ngayxuathd;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public void setMakhachhang(int makhachhang) {
        this.makhachhang = makhachhang;
    }
    
    
}
