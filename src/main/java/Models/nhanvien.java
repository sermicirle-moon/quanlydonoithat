/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class nhanvien {
    private int manv;
    private String tennv;
    private String sdtnv;

    public nhanvien() {
    }

    public nhanvien(int manv, String tennv, String sdtnv) {
        this.manv = manv;
        this.tennv = tennv;
        this.sdtnv = sdtnv;
    }

    public int getManv() {
        return manv;
    }

    public String getTennv() {
        return tennv;
    }

    public String getSdtnv() {
        return sdtnv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public void setSdtnv(String sdtnv) {
        this.sdtnv = sdtnv;
    }
    
}
