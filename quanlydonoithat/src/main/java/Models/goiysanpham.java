/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class goiysanpham {
  
    private int id;
    private String name;
    private double giaban;

    public goiysanpham(int id, String name,double giaban) {
        this.id = id;
        this.name = name;
        this.giaban=giaban;
    }

    public double getgiaban() {
        return giaban;
    }

    public void setgiaban(double giaban) {
        this.giaban = giaban;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name ;
    }
}

