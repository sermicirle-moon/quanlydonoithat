/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class donvivanchuyen {
    private int madvvc;
    private String tendvvc;
    private String sdtdvvc;
    private String diachidvvc;

    public donvivanchuyen() {
    }

    public donvivanchuyen(int madvvc, String tendvvc, String sdtdvvc, String diachidvvc) {
        this.madvvc = madvvc;
        this.tendvvc = tendvvc;
        this.sdtdvvc = sdtdvvc;
        this.diachidvvc = diachidvvc;
    }

    public int getMadvvc() {
        return madvvc;
    }

    public void setMadvvc(int madvvc) {
        this.madvvc = madvvc;
    }

    public String getTendvvc() {
        return tendvvc;
    }

    public void setTendvvc(String tendvvc) {
        this.tendvvc = tendvvc;
    }

    public String getSdtdvvc() {
        return sdtdvvc;
    }

    public void setSdtdvvc(String sdtdvvc) {
        this.sdtdvvc = sdtdvvc;
    }

    public String getDiachidvvc() {
        return diachidvvc;
    }

    public void setDiachidvvc(String diachidvvc) {
        this.diachidvvc = diachidvvc;
    }
    
    @Override
    public String toString(){
        return tendvvc;
    }
}
