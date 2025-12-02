/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicesANDvalidate;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class services {
    public static void removeSelectedRows(JTable table, Component parent) {
        int[] selectedRows=table.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn ít nhất 1 dòng để xóa!");
            return;
        }
        DefaultTableModel model=(DefaultTableModel) table.getModel();
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            model.removeRow(selectedRows[i]);
        }
    }
    public static void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
}
