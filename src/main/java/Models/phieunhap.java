/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class phieunhap {
    private int maphieu;
    private Date ngaynhap;
    private int manv;
    private String tennv;
    private int macc;
    private String tenncc;
    private double tongtien;

    public phieunhap() {
    }

    public phieunhap(int maphieu, Date ngaynhap, int manv, String tennv, int macc, String tenncc, double tongtien) {
        this.maphieu = maphieu;
        this.ngaynhap = ngaynhap;
        this.manv = manv;
        this.tennv = tennv;
        this.macc = macc;
        this.tenncc = tenncc;
        this.tongtien = tongtien;
    }

    

    public int getMaphieu() {
        return maphieu;
    }

    public Date getNgaynhap() {
        return ngaynhap;
    }

    public int getManv() {
        return manv;
    }

    public int getMacc() {
        return macc;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setMaphieu(int maphieu) {
        this.maphieu = maphieu;
    }

    public void setNgaynhap(Date ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public void setMacc(int macc) {
        this.macc = macc;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getTenncc() {
        return tenncc;
    }

    public void setTenncc(String tenncc) {
        this.tenncc = tenncc;
    }
    
    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }
    
}
