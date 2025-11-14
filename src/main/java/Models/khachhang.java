/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class khachhang {
    private int makh;
    private String tenkh;
    private String sdtkh;
    private String loaikh;

    public khachhang() {
    }

    public khachhang(int makh, String tenkh, String sdtkh, String loaikh) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.sdtkh = sdtkh;
        this.loaikh = loaikh;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getSdtkh() {
        return sdtkh;
    }

    public void setSdtkh(String sdtkh) {
        this.sdtkh = sdtkh;
    }

    public String getLoaikh() {
        return loaikh;
    }

    public void setLoaikh(String loaikh) {
        this.loaikh = loaikh;
    }
    
    
}
