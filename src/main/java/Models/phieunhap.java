/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class phieunhap {
    private int maphieu;
    private String ngaynhap;
    private int manv;
    private int macc;
    private double tongtien;

    public phieunhap() {
    }

    public phieunhap(int maphieu, String ngaynhap, int manv, int macc, double tongtien) {
        this.maphieu = maphieu;
        this.ngaynhap = ngaynhap;
        this.manv = manv;
        this.macc = macc;
        this.tongtien = tongtien;
    }

    public int getMaphieu() {
        return maphieu;
    }

    public String getNgaynhap() {
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

    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public void setMacc(int macc) {
        this.macc = macc;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }
    
}
