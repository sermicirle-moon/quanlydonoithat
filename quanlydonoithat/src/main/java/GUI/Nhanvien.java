/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;


import DAO.nhanvienDAO;
import Models.nhanvien;
import static Process.validate.isNumeric;
import static Process.validate.isPositiveNumber;
import static Process.validate.isValidPhoneNumber;
import database.dbconnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Admin
 */
public class Nhanvien extends javax.swing.JPanel {
    private TableRowSorter< javax.swing.table.TableModel> sorterHD;
    
    public Nhanvien() {
        initComponents();
        loadChiTietNhanvien();
        setupTableSearch();
    }
    private void loadChiTietNhanvien() {
        try {
            Connection conn = dbconnection.getConnection();
            nhanvienDAO nvDAO = new nhanvienDAO(conn);
            List<nhanvien> listnv = nvDAO.getAll();
            
            DefaultTableModel model = (DefaultTableModel) tbnv.getModel();
            model.setRowCount(0);
            
            for (nhanvien nv : listnv) {
                Object[] rowData = {
                    nv.getManv(),
                    nv.getTennv(),
                    nv.getSdtnv()
                };
                model.addRow(rowData);
            }
            
            conn.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void themnv(){
        if (txtmanv.getText().isEmpty() || txtht.getText().isEmpty() || txtsdt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
        }
        if(!isPositiveNumber(txtmanv.getText())){
            JOptionPane.showMessageDialog(this, "Mã nhân viên phải là số nguyên dương");
            txtmanv.requestFocus();
            return;
        }
        if(!isNumeric(txtmanv.getText())||!isNumeric(txtsdt.getText())){
            JOptionPane.showMessageDialog(this, "Sdt phải là số nguyên");
            txtsdt.requestFocus();
            txtmanv.requestFocus();
            return;
        }
        if(!isValidPhoneNumber(txtsdt.getText())){
            JOptionPane.showMessageDialog(this, "Sdt phải đủ 10 số");
            txtsdt.requestFocus();
            return;
        }
        try {
            Connection conn = dbconnection.getConnection();
            nhanvienDAO nvDAO = new nhanvienDAO(conn);
            nhanvien nv = new nhanvien();
            
            nv.setManv(Integer.parseInt(txtmanv.getText()));
            nv.setTennv(txtht.getText());
            nv.setSdtnv(txtsdt.getText());

            if (nvDAO.insert(nv)) {
                JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công");
                loadChiTietNhanvien();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm nhân viên: " + e.getMessage());
        }
    }
    
    private void clearForm() {
    txtmanv.setText("");
    txtht.setText("");
    txtsdt.setText("");
    
}
    
    private void suanv(){
        int selectedRow = tbnv.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa");
            return;
        }
        if(!isPositiveNumber(txtmanv.getText())){
            JOptionPane.showMessageDialog(this, "Mã nhân viên phải là số nguyên dương");
            txtmanv.requestFocus();
            return;
        }
        if(!isNumeric(txtmanv.getText())||!isNumeric(txtsdt.getText())){
            JOptionPane.showMessageDialog(this, "Sdt phải là số nguyên");
            txtsdt.requestFocus();
            txtmanv.requestFocus();
            return;
        }
        if(!isValidPhoneNumber(txtsdt.getText())){
            JOptionPane.showMessageDialog(this, "Sdt phải đủ 10 số");
            txtsdt.requestFocus();
            return;
        }
        try {
            Connection conn = dbconnection.getConnection();
            nhanvienDAO nvDAO = new nhanvienDAO(conn);

            nhanvien nv = new nhanvien();
            nv.setManv(Integer.parseInt(txtmanv.getText()));
            nv.setTennv(txtht.getText());
            nv.setSdtnv(txtsdt.getText());

            if (nvDAO.update(nv)) {
                JOptionPane.showMessageDialog(this, "Sửa nhân viên thành công");
                loadChiTietNhanvien();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa nhân viên thất bại");
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi sửa nhân viên: " + e.getMessage());
        }
    }
    
    private void xoanv(){
        int selectedRow = tbnv.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa");
            return;
        }
        try {
            Connection conn = dbconnection.getConnection();
            nhanvienDAO nvDAO = new nhanvienDAO(conn);
            
            int manv = Integer.parseInt(tbnv.getValueAt(selectedRow, 0).toString());
            
            if (nvDAO.delete(manv)) {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công");
                loadChiTietNhanvien();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại");
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa nhân viên: " + e.getMessage());
        }
    }
    
    private void setupTableSearch() {
        // Khởi tạo TableRowSorter
        sorterHD = new TableRowSorter<>(tbnv.getModel());
        tbnv.setRowSorter(sorterHD);
        txtTK.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = txtTK.getText();
                if (text.trim().length() == 0) {
                    sorterHD.setRowFilter(null);
                } else {
                    javax.swing.RowFilter<javax.swing.table.TableModel, Object> rf = javax.swing.RowFilter.regexFilter("(?i)" + text);
                    sorterHD.setRowFilter(rf);
                }
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtmanv = new javax.swing.JTextField();
        txtht = new javax.swing.JTextField();
        txtsdt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbnv = new javax.swing.JTable();
        btThemnv = new javax.swing.JButton();
        btSuanv = new javax.swing.JButton();
        btXoanv = new javax.swing.JButton();
        txtTK = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nhân viên");

        jLabel2.setText("Mã nhân viên");

        jLabel3.setText("Họ tên");

        jLabel4.setText("Số đt");

        tbnv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ tên ", "Số đt"
            }
        ));
        tbnv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbnvMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbnv);

        btThemnv.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btThemnv.setText("Thêm");
        btThemnv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemnvActionPerformed(evt);
            }
        });

        btSuanv.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btSuanv.setText("Sửa");
        btSuanv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSuanvActionPerformed(evt);
            }
        });

        btXoanv.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btXoanv.setText("Xóa");
        btXoanv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXoanvActionPerformed(evt);
            }
        });

        txtTK.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtTK.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTK.setText("Tìm kiếm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btThemnv, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtmanv, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                    .addComponent(txtsdt)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(btSuanv, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtht, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(btXoanv, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(289, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtmanv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtht, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btThemnv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btSuanv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btXoanv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btThemnvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemnvActionPerformed
        // TODO add your handling code here:
        themnv();
    }//GEN-LAST:event_btThemnvActionPerformed

    private void btSuanvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSuanvActionPerformed
        // TODO add your handling code here:
        suanv();
    }//GEN-LAST:event_btSuanvActionPerformed

    private void btXoanvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXoanvActionPerformed
        // TODO add your handling code here:
        xoanv();
    }//GEN-LAST:event_btXoanvActionPerformed

    private void tbnvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbnvMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbnv.getSelectedRow();
        if (selectedRow >= 0) {
            // Lấy dữ liệu từ bảng và hiển thị lên textfield
            txtmanv.setText(tbnv.getValueAt(selectedRow, 0).toString());
            txtht.setText(tbnv.getValueAt(selectedRow, 1).toString());
            txtsdt.setText(tbnv.getValueAt(selectedRow, 2).toString());
        }
        
    }//GEN-LAST:event_tbnvMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btSuanv;
    private javax.swing.JButton btThemnv;
    private javax.swing.JButton btXoanv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbnv;
    private javax.swing.JTextField txtTK;
    private javax.swing.JTextField txtht;
    private javax.swing.JTextField txtmanv;
    private javax.swing.JTextField txtsdt;
    // End of variables declaration//GEN-END:variables
}
