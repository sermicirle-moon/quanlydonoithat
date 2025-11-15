/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class nhanvienComboBox {
    private int manv;
    private String tennv;

    public nhanvienComboBox() {
    }

    public nhanvienComboBox(int manv, String tennv) {
        this.manv = manv;
        this.tennv = tennv;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }
    @Override
    public String toString(){
        return tennv;
    }
}
