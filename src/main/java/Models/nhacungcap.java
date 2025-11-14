/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class nhacungcap {
    private int mancc;
    private String tenncc;
    private String diachincc;
    private String sdtncc;

    public nhacungcap() {
    }

    public nhacungcap(int mancc, String tenncc, String diachincc, String sdtncc) {
        this.mancc = mancc;
        this.tenncc = tenncc;
        this.diachincc = diachincc;
        this.sdtncc = sdtncc;
    }

    public int getMancc() {
        return mancc;
    }

    public String getTenncc() {
        return tenncc;
    }

    public String getDiachincc() {
        return diachincc;
    }

    public String getSdtncc() {
        return sdtncc;
    }

    public void setMancc(int mancc) {
        this.mancc = mancc;
    }

    public void setTenncc(String tenncc) {
        this.tenncc = tenncc;
    }

    public void setDiachincc(String diachincc) {
        this.diachincc = diachincc;
    }

    public void setSdtncc(String sdtncc) {
        this.sdtncc = sdtncc;
    }
    
    
}
