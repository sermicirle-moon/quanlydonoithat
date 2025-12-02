/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import DAO.chitiethoadonDAO;
import DAO.hoadonDAO;
import DAO.sanphamDAO;
import Models.chitiethoadon;
import Models.hoadon;
import database.dbconnection;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;






/**
 *
 * @author Admin
 */
public class Hoadon extends javax.swing.JPanel {
    private TableRowSorter< javax.swing.table.TableModel> sorterHD;
    /**
     * Creates new form Hoadon
     */
    public Hoadon() {
        initComponents();
        loadhoadon();
        setupTableSearch();
    }

    private void showChiTietHoaDonDialog() throws SQLException {
    // Tạo dialog
    JDialog dialog = new JDialog();
    dialog.setTitle("Tạo Hóa Đơn Chi Tiết");
    dialog.setModal(true);
    dialog.setLayout(new BorderLayout());
    
    // Tạo panel chi tiết
    ChitietHd chiTietPanel = new ChitietHd();
    
    // Thêm panel vào dialog
    dialog.add(chiTietPanel, BorderLayout.CENTER);
    
    // Cài đặt dialog
    dialog.setSize(800, 700);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
    
    loadhoadon();
}
    
    
    private void loadhoadon() {
        try {
            // Lấy kết nối và DAO
            Connection conn = dbconnection.getConnection();
            hoadonDAO hdDAO = new hoadonDAO(conn);

            // Lấy danh sách hóa đơn từ database
            List<hoadon> listHD = hdDAO.getAll();

            // Lấy model của table
            DefaultTableModel model = (DefaultTableModel) tbhd.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ


            // Thêm dữ liệu vào table
            for (hoadon hd : listHD) {
                Object[] rowData = {
                    hd.getMahoadon(),
                    hd.getMakhachhang(), // hoặc getTenkh() nếu có
                    hd.getNgayxuathd(),
                    String.format("%,.0f kVND", hd.getTongtien()),
                    hd.getTrangthai()
                };
                model.addRow(rowData);
            }

            // Đóng connection
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void showChiTietHoaDonDialog(hoadon hd) throws SQLException {
        // Tạo dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa Hóa Đơn - Mã: " + hd.getMahoadon());
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());

        // Tạo panel chi tiết với chế độ sửa
        ChitietHd chiTietPanel = new ChitietHd(hd);

        // Thêm panel vào dialog
        dialog.add(chiTietPanel, BorderLayout.CENTER);

        // Cài đặt dialog
        dialog.setSize(800, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        // Load lại danh sách hóa đơn sau khi đóng dialog
        loadhoadon();
    }
    private void suahd(){
        try {
        int selectedRow = tbhd.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần sửa!");
            return;
        }
        
        // Lấy mã hóa đơn từ dòng được chọn
        int maHoaDon = Integer.parseInt(tbhd.getValueAt(selectedRow, 0).toString());
        
        // Kiểm tra trạng thái hóa đơn - không cho sửa nếu đã hoàn thành
        String trangThai = tbhd.getValueAt(selectedRow, 4).toString();
        // Lấy thông tin hóa đơn từ database
        Connection conn = dbconnection.getConnection();
        hoadonDAO hdDAO = new hoadonDAO(conn);
        hoadon hd = hdDAO.getByMaHD(maHoaDon);
        conn.close();
        
        if (hd == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!");
            return;
        }
        
        // Hiển thị dialog chỉnh sửa
        showChiTietHoaDonDialog(hd);
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Lỗi khi mở form sửa: " + ex.getMessage());
        ex.printStackTrace();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        ex.printStackTrace();
    }
    }
    
    private void huyHoaDon() {
        try {
            int selectedRow = tbhd.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần hủy!");
                return;
            }

            int maHoaDon = Integer.parseInt(tbhd.getValueAt(selectedRow, 0).toString());
            String trangThai = tbhd.getValueAt(selectedRow, 4).toString();

            if ("Hủy".equals(trangThai) || "Đã Giao".equals(trangThai)) {
                JOptionPane.showMessageDialog(this, "Không thể hủy hóa đơn này!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn muốn HỦY hóa đơn này?",
                "Xác nhận hủy",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            Connection conn = null;
            boolean success = false;

            try {
                conn = dbconnection.getConnection();
                conn.setAutoCommit(false);

                hoadonDAO hdDAO = new hoadonDAO(conn);
                chitiethoadonDAO cthdDAO = new chitiethoadonDAO(conn);
                sanphamDAO spDAO = new sanphamDAO(conn);

                // 1. LẤY DANH SÁCH CHI TIẾT HÓA ĐƠN
                List<chitiethoadon> listCT = cthdDAO.getByMaHD(maHoaDon);

                // 2. CỘNG TRẢ KHO
                for (chitiethoadon ct : listCT) {
                    boolean traKho = spDAO.congSoLuong(ct.getMasp(), ct.getSoluong());
                    if (!traKho) {
                        throw new SQLException("Không thể cộng kho SP: " + ct.getMasp());
                    }
                }

                // 3. CẬP NHẬT TRẠNG THÁI HÓA ĐƠN → HỦY
                boolean updateStatus = hdDAO.updateTrangThai(maHoaDon, "Hủy");
                if (!updateStatus) {
                    throw new SQLException("Không thể cập nhật trạng thái hóa đơn");
                }

                conn.commit();
                success = true;

            } catch (SQLException e) {
                if (conn != null) conn.rollback();
                throw e;
            } finally {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "Hủy hóa đơn thành công!");
                loadhoadon();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void setupTableSearch() {
        // Khởi tạo TableRowSorter
        sorterHD = new TableRowSorter<>(tbhd.getModel());
        tbhd.setRowSorter(sorterHD);
        txtkmsp.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = txtkmsp.getText();
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

        PnHoadon = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btTaohd = new javax.swing.JButton();
        btSuahd = new javax.swing.JButton();
        btnHuyhd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbhd = new javax.swing.JTable();
        txtkmsp = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Hóa đơn");

        btTaohd.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btTaohd.setText("Tạo hóa đơn");
        btTaohd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTaohdActionPerformed(evt);
            }
        });

        btSuahd.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btSuahd.setText("Sửa ");
        btSuahd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSuahdActionPerformed(evt);
            }
        });

        btnHuyhd.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnHuyhd.setText("Hủy");
        btnHuyhd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyhdActionPerformed(evt);
            }
        });

        tbhd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Mã khách hàng", "Ngày xuất hd", "Tổng tiền", "Trạng thái"
            }
        ));
        jScrollPane1.setViewportView(tbhd);

        txtkmsp.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtkmsp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtkmsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkmspActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tìm kiếm:");

        javax.swing.GroupLayout PnHoadonLayout = new javax.swing.GroupLayout(PnHoadon);
        PnHoadon.setLayout(PnHoadonLayout);
        PnHoadonLayout.setHorizontalGroup(
            PnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnHoadonLayout.createSequentialGroup()
                .addGroup(PnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnHoadonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(PnHoadonLayout.createSequentialGroup()
                        .addGroup(PnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PnHoadonLayout.createSequentialGroup()
                                .addGap(158, 158, 158)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PnHoadonLayout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(btTaohd)
                                .addGap(53, 53, 53)
                                .addComponent(btSuahd)
                                .addGap(72, 72, 72)
                                .addComponent(btnHuyhd)
                                .addGap(87, 87, 87)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtkmsp, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 279, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PnHoadonLayout.setVerticalGroup(
            PnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnHoadonLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(PnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btTaohd)
                    .addComponent(btSuahd)
                    .addComponent(btnHuyhd)
                    .addComponent(txtkmsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PnHoadon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PnHoadon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btTaohdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTaohdActionPerformed
        try {
            // TODO add your handling code here:
            showChiTietHoaDonDialog();
        } catch (SQLException ex) {
            Logger.getLogger(Hoadon.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btTaohdActionPerformed

    private void txtkmspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkmspActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtkmspActionPerformed

    private void btSuahdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSuahdActionPerformed
         suahd();
    }//GEN-LAST:event_btSuahdActionPerformed

    private void btnHuyhdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyhdActionPerformed
        huyHoaDon();
    }//GEN-LAST:event_btnHuyhdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnHoadon;
    private javax.swing.JButton btSuahd;
    private javax.swing.JButton btTaohd;
    private javax.swing.JButton btnHuyhd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbhd;
    private javax.swing.JTextField txtkmsp;
    // End of variables declaration//GEN-END:variables
}
