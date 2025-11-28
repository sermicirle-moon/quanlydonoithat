/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class goiykhachhang {
    private int makh;
    private String namekh;

    public goiykhachhang(int makh, String namekh) {
        this.makh = makh;
        this.namekh = namekh;
    }

    public int getIdkh() {
        return makh;
    }

    public void setIdkh(int idkh) {
        this.makh = idkh;
    }

    public String getNamekh() {
        return namekh;
    }

    public void setNamekh(String namekh) {
        this.namekh = namekh;
    }

    
    @Override
    public String toString() {
        return namekh;
    }
}
