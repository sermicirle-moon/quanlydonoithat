/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class loaisanpham {
    private int maloai;
    private String tenloai;
    private String mota;

    public loaisanpham() {
    }

    public loaisanpham(int maloai, String tenloai, String mota) {
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.mota = mota;
    }

    public int getMaloai() {
        return maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public String getMota() {
        return mota;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

       
}
