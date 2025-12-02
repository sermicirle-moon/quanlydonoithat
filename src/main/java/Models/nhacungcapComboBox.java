/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Admin
 */
public class nhacungcapComboBox {
    private int mancc;
    private String tenncc;

    public nhacungcapComboBox() {
    }

    public nhacungcapComboBox(int id, String name) {
        this.mancc = id;
        this.tenncc = name;
    }

    public int getId() {
        return mancc;
    }

    public void setId(int id) {
        this.mancc = id;
    }

    public String getName() {
        return tenncc;
    }

    public void setName(String name) {
        this.tenncc = name;
    }
    @Override
    public String toString(){
        return tenncc;
    }
}
