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
public class chitietphieu {
    private int maphieu;
    private int masp;
    private int soluong;
    private double dongia;
    private double thanhtien;

    public chitietphieu() {
    }

    public chitietphieu(int maphieu, int masp, int soluong, double dongia, double thanhtien) {
        this.maphieu = maphieu;
        this.masp = masp;
        this.soluong = soluong;
        this.dongia = dongia;
        this.thanhtien = thanhtien;
    }

    public int getMaphieu() {
        return maphieu;
    }

    public int getMasp() {
        return masp;
    }

    public int getSoluong() {
        return soluong;
    }

    public double getDongia() {
        return dongia;
    }

    public double getThanhtien() {
        return thanhtien;
    }

    public void setMaphieu(int maphieu) {
        this.maphieu = maphieu;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

    public void setThanhtien(double thanhtien) {
        this.thanhtien = thanhtien;
    }
    
}
