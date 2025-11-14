/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class sanpham {
    private int masp;
    private String tensp;
    private String maloai;
    private int soluong;
    private double giaban;

    public sanpham() {
    }

    public sanpham(int masp, String tensp, String maloai, int soluong, float giaban) {
        this.masp = masp;
        this.tensp = tensp;
        this.maloai = maloai;
        this.soluong = soluong;
        this.giaban = giaban;
    }

    public int getMasp() {
        return masp;
    }

    public String getTensp() {
        return tensp;
    }

    public String getMaloai() {
        return maloai;
    }

    public int getSoluong() {
        return soluong;
    }

    public double getGiaban() {
        return giaban;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setGiaban(float giaban) {
        this.giaban = giaban;
    }
}
