/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class chitietphieuxuat {
    private int mapx;
    private int masp;
    private int soluong;
    private double thanhtien;

    public chitietphieuxuat() {
    }

    public chitietphieuxuat(int mapx, int masp, int soluong, double thanhtien) {
        this.mapx = mapx;
        this.masp = masp;
        this.soluong = soluong;
        this.thanhtien = thanhtien;
    }

    public int getMapx() {
        return mapx;
    }

    public void setMapx(int mapx) {
        this.mapx = mapx;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(double thanhtien) {
        this.thanhtien = thanhtien;
    }
    
    
}
