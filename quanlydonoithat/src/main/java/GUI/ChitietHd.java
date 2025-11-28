/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import DAO.chitiethoadonDAO;
import DAO.hoadonDAO;
import DAO.khachhangDAO;
import DAO.sanphamDAO;
import Models.chitiethoadon;
import Models.goiykhachhang;
import Models.goiysanpham;
import Models.hoadon;
import Process.validate;
import database.dbconnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;



/**
 *
 * @author Admin
 */
public class ChitietHd extends javax.swing.JPanel {
    private hoadon hoadonsua;
    
    /**
     * Creates new form ChitietHd
     */
    public ChitietHd() throws SQLException {
        initComponents();
        cbkh.setEditable(true);
        loadkhachhang();
        cbsp.setEditable(true);
        loadsp();
        Editmode(false);
    }
    public ChitietHd(hoadon hd) throws SQLException {
        initComponents();
        cbkh.setEditable(true);
        loadkhachhang();
        cbsp.setEditable(true);
        loadsp();
        this.hoadonsua = hd;
        
        Editmode(true); // Chế độ sửa
        loadDuLieuHoaDon(hd);
    }
    private void Editmode(boolean isEdit) {
        if (isEdit) {
            //KHÓA CÁC TRƯỜNG KHÔNG CHO SỬA
            txtmahd.setEditable(false);
            txtmahd.setEnabled(false);
            cbkh.setEnabled(false);
            dtnxh.setEnabled(false);

            // CHỈ CHO PHÉP SỬA SẢN PHẨM VÀ SỐ LƯỢNG
            cbsp.setEnabled(true);
            txtsl.setEnabled(true);
            btthemsp.setEnabled(true);
            btsuasp.setEnabled(true);
            btxoasp.setEnabled(true);

        } else {

            // MỞ TẤT CẢ TRƯỜNG CHO TẠO MỚI
            txtmahd.setEditable(true);
            txtmahd.setEnabled(true);
            cbkh.setEnabled(true);
            dtnxh.setEnabled(true);
            cbsp.setEnabled(true);
            txtsl.setEnabled(true);
        }
    }
    private void loadDuLieuHoaDon(hoadon hd) {
        try {
            // SET DỮ LIỆU HÓA ĐƠN VÀO FORM
            txtmahd.setText(String.valueOf(hd.getMahoadon()));
            dtnxh.setDate(hd.getNgayxuathd());

            // SET KHÁCH HÀNG
            for (int i = 0; i < cbkh.getItemCount(); i++) {
                goiykhachhang kh = (goiykhachhang) cbkh.getItemAt(i);
                if (kh.getIdkh() == hd.getMakhachhang()) {
                    cbkh.setSelectedIndex(i);
                    break;
                }
            }

            // LOAD CHI TIẾT HÓA ĐƠN VÀO TABLE
            loadChiTietHoaDon(hd.getMahoadon());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void loadChiTietHoaDon(int maHD) {
        try {
            Connection conn = dbconnection.getConnection();
            chitiethoadonDAO cthdDAO = new chitiethoadonDAO(conn);
            List<chitiethoadon> listCTHD = cthdDAO.getByMaHD(maHD);
            
            DefaultTableModel model = (DefaultTableModel) tbcthd.getModel();
            model.setRowCount(0);
            
            for (chitiethoadon ct : listCTHD) {
                Object[] rowData = {
                    ct.getMahoadon(),
                    ct.getMasp(),
                    ct.getSoluong(),
                    ct.getTongtien()
                };
                model.addRow(rowData);
            }
            
            conn.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void loadkhachhang() throws SQLException{
        cbkh.removeAllItems();
        khachhangDAO khDAO=new khachhangDAO(dbconnection.getConnection());
        List<goiykhachhang> listComboBox = khDAO.getkhForComboBox();
        for(goiykhachhang p : listComboBox){
            cbkh.addItem(p);
        }
        AutoCompleteDecorator.decorate(cbkh);
    }
    private void loadsp() throws  SQLException{
        cbsp.removeAllItems();
        sanphamDAO spDAO=new sanphamDAO(dbconnection.getConnection());
        List<goiysanpham> listComboBox = spDAO.getspForComboBox();
        for(goiysanpham p : listComboBox){
            cbsp.addItem(p);
        }
        AutoCompleteDecorator.decorate(cbsp);
    }
    private Object[] ktra(){
    try {
        // 1. KIỂM TRA ĐÃ CHỌN SẢN PHẨM CHƯA
        if (cbsp.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!");
            return null;
        }

        // 2. LẤY THÔNG TIN SẢN PHẨM
        goiysanpham selectedSP = (goiysanpham) cbsp.getSelectedItem();
        int masp = selectedSP.getId();
        String tensp = selectedSP.getName();
        double giaban = selectedSP.getgiaban();

        // 3. KIỂM TRA SỐ LƯỢNG - DÙNG VALIDATION CÓ SẴN
        String soLuongStr = txtsl.getText();
        
        
        if (soLuongStr.isEmpty()||!validate.isNumeric(soLuongStr)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!");
            txtsl.requestFocus();
            return null;
        }

        
        if (!validate.isPositiveNumber(soLuongStr)) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên lớn hơn 0!");
            txtsl.requestFocus();
            return null;
        }

        int soLuong = Integer.parseInt(soLuongStr);

        // 4. KIỂM TRA TỒN KHO - DÙNG VALIDATION CÓ SẴN
        if (!validate.isQuantityAvailable(masp, soLuong)) {
            int soLuongHienCo = validate.getCurrentQuantity(masp);
            JOptionPane.showMessageDialog(this, 
                "Số lượng không đủ!\n\n" +
                "Số lượng mua: " + soLuong + "\n" +
                "Số lượng hiện có: " + soLuongHienCo);
            txtsl.requestFocus();
            return null;
        }

        // 5. KIỂM TRA MÃ ĐƠN HÀNG - DÙNG VALIDATION CÓ SẴN
        String madh = txtmahd.getText();
        if (madh.isEmpty()||!validate.isNumeric(madh)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại mã đơn hàng!");
            txtmahd.setEditable(true);
            txtmahd.requestFocus();
            return null;
        }

        // 6. TRẢ VỀ TẤT CẢ DỮ LIỆU ĐÃ KIỂM TRA
        return new Object[] {
            selectedSP,    // [0] - goiysanpham
            masp,          // [1] - int
            tensp,         // [2] - String  
            giaban,        // [3] - double
            soLuong,       // [4] - int
            madh      // [5] - String
        };

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi kiểm tra dữ liệu: " + e.getMessage());
        return null;
    }
}
    
    private void themsp() {
    // GỌI METHOD KIỂM TRA CHUNG (ĐÃ CÓ VALIDATION)
    txtmahd.setEditable(false);
    Object[] dulieu = ktra();
    if (dulieu == null) return;

    try {
        // LẤY DỮ LIỆU ĐÃ ĐƯỢC KIỂM TRA
        goiysanpham selectedSP = (goiysanpham) dulieu[0];
        int masp = (int) dulieu[1];
        String tensp = (String) dulieu[2];
        double dongia = (double) dulieu[3];
        int soLuongmua = (int) dulieu[4];
        String madh = (String) dulieu[5];

        // TÍNH THÀNH TIỀN
        double thanhTien = dongia * soLuongmua;
        //ktra sp tồn tại
        DefaultTableModel model = (DefaultTableModel) tbcthd.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int maspTrongBang = Integer.parseInt(model.getValueAt(i, 1).toString());
            if (maspTrongBang == masp) {
                JOptionPane.showMessageDialog(null, "Sản phẩm đã tồn tại");
                return;
            }
        }
        // THÊM VÀO BẢNG
        Object[] rowData = {
            madh,        // Mã đơn hàng
            masp,             // Mã sản phẩm
            soLuongmua,       // Số lượng
            thanhTien         // Thành tiền
        };
        
        model.addRow(rowData);

        // CLEAR VÀ THÔNG BÁO
        txtsl.setText("");
        txtsl.requestFocus();

        JOptionPane.showMessageDialog(this," Đã thêm sản phẩm!\n");
        
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, " Lỗi khi thêm: " + e.getMessage());
    }
}
    private void suasp() {
    // KIỂM TRA ĐÃ CHỌN DÒNG TRONG BẢNG
    int selectedRow = tbcthd.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa trong bảng!");
        return;
    }

    // GỌI METHOD KIỂM TRA CHUNG 
    Object[] dulieu = ktra();
    if (dulieu == null) return;

    try {
        // LẤY DỮ LIỆU ĐÃ ĐƯỢC KIỂM TRA
        int masp = (int) dulieu[1];
        int soluongmoi = (int) dulieu[4];
        double dongia = (double) dulieu[3];

        // KIỂM TRA MÃ SẢN PHẨM CÓ KHỚP KHÔNG
        int maspht = Integer.parseInt(tbcthd.getValueAt(selectedRow, 1).toString());
        if (masp != maspht) {
            JOptionPane.showMessageDialog(this, "Không thể thay đổi sản phẩm khác! Vui lòng chọn đúng sản phẩm cần sửa.");
            return;
        }

        // TÍNH LẠI THÀNH TIỀN
        double thanhTienmoi = dongia * soluongmoi;

        // CẬP NHẬT BẢNG
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tbcthd.getModel();
        model.setValueAt(soluongmoi, selectedRow, 2);    // Số lượng
        model.setValueAt(thanhTienmoi, selectedRow, 3);  // Thành tiền

        // THÔNG BÁO
        JOptionPane.showMessageDialog(this,"Đã cập nhật!\n");
           
        // CLEAR
        txtsl.setText("");
        txtsl.requestFocus();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi sửa: " + e.getMessage());
    }
}
    private void xoasp() {
    try {
        //  KIỂM TRA ĐÃ CHỌN DÒNG TRONG BẢNG CHƯA
        int selectedRow = tbcthd.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần xóa");
            return;
        }
        
        // XÁC NHẬN XÓA
        int confirm = JOptionPane.showConfirmDialog(this,"ban muon xoa k","thong bao",JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            //  THỰC HIỆN XÓA
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tbcthd.getModel();
            model.removeRow(selectedRow);

            //CLEAR DỮ LIỆU NHẬP
            txtsl.setText("");
            cbsp.setSelectedIndex(-1);
            JOptionPane.showMessageDialog(null,"Xóa thành công");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi xóa sản phẩm: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    private double tinhTongTien() {
    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tbcthd.getModel();
    double tongTien = 0;
    
    for (int i = 0; i < model.getRowCount(); i++) {
        double thanhTien = Double.parseDouble(model.getValueAt(i, 3).toString());
        tongTien += thanhTien;
    }
    
    return tongTien;
}
    private void luuHoaDon() {
        if (hoadonsua != null) {
            // CHẾ ĐỘ SỬA
            capNhatHoaDon();
        } else {
            // CHẾ ĐỘ THÊM MỚI
            taoHoaDonMoi();
        }
    }

    private void taoHoaDonMoi() {
        try {
            // 1. KIỂM TRA ĐIỀU KIỆN TRƯỚC KHI TẠO
            if (tbcthd.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Hóa đơn chưa có sản phẩm nào!");
                return;
            }

            // 2. KIỂM TRA MÃ HÓA ĐƠN ĐÃ TỒN TẠI CHƯA
            String maHD = txtmahd.getText().trim();
            Connection connect = dbconnection.getConnection();
            hoadonDAO checkmahd = new hoadonDAO(connect);
            boolean hdExists = checkmahd.isMaHDExists(Integer.parseInt(maHD));
            connect.close();

            if (hdExists) {
                JOptionPane.showMessageDialog(this, "Mã hóa đơn " + maHD + " đã tồn tại! Vui lòng chọn mã khác.");
                txtmahd.setEditable(true);
                txtmahd.requestFocus();
                return;
            }

            // 3. KIỂM TRA DỮ LIỆU FORM
            if (dtnxh.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày xuất hàng!");
                return;
            }

            if (cbkh.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!");
                return;
            }

            // 4. LẤY THÔNG TIN TỪ FORM
            java.util.Date utilDate = dtnxh.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            goiykhachhang kh = (goiykhachhang) cbkh.getSelectedItem();
            int makh = kh.getIdkh();
            double tongTien = tinhTongTien();

            // 5. TẠO ĐỐI TƯỢNG HÓA ĐƠN
            hoadon hoadon = new hoadon();
            hoadon.setMahoadon(Integer.parseInt(maHD));
            hoadon.setNgayxuathd(sqlDate);
            hoadon.setTongtien(tongTien);
            hoadon.setTrangthai("Phiếu Xuất");
            hoadon.setmakh(makh);

            // 6. LƯU VÀO DATABASE - DÙNG TRANSACTION
            Connection conn = null;
            boolean success = false;

            try {
                conn = dbconnection.getConnection();
                conn.setAutoCommit(false);

                hoadonDAO hdDAO = new hoadonDAO(conn);
                chitiethoadonDAO cthdDAO = new chitiethoadonDAO(conn);

                // THÊM HÓA ĐƠN CHÍNH
                boolean themHD = hdDAO.themHoaDon(hoadon);
                if (!themHD) {
                    throw new SQLException("Lỗi khi thêm hóa đơn chính");
                }

                // THÊM CHI TIẾT HÓA ĐƠN
                DefaultTableModel model = (DefaultTableModel) tbcthd.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String maHDCT = model.getValueAt(i, 0).toString();
                    int maSP = Integer.parseInt(model.getValueAt(i, 1).toString());
                    int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
                    double thanhTien = Double.parseDouble(model.getValueAt(i, 3).toString());

                    chitiethoadon ct = new chitiethoadon();
                    ct.setMahoadon(Integer.parseInt(maHDCT));
                    ct.setMasp(maSP);
                    ct.setSoluong(soLuong);
                    ct.setTongtien(thanhTien);

                    boolean themCT = cthdDAO.insert(ct);
                    if (!themCT) {
                        throw new SQLException("Lỗi khi thêm chi tiết hóa đơn dòng " + (i + 1));
                    }
                }

                conn.commit();
                success = true;

            } catch (SQLException e) {
                if (conn != null) {
                    conn.rollback();
                }
                throw e;
            } finally {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            }

            // 7. THÔNG BÁO VÀ ĐÓNG FORM
            if (success) {
                JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!");

                // ĐÓNG FORM
                java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void capNhatHoaDon() {
        try {
            // 1. KIỂM TRA ĐIỀU KIỆN TRƯỚC KHI CẬP NHẬT
            if (tbcthd.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Hóa đơn chưa có sản phẩm nào!");
                return;
            }

            // 2. LẤY THÔNG TIN TỪ FORM
            String maHD = txtmahd.getText().trim();
            double tongTien = tinhTongTien();

            // 3. CẬP NHẬT VÀO DATABASE - DÙNG TRANSACTION
            Connection conn = null;
            boolean success = false;

            try {
                conn = dbconnection.getConnection();
                conn.setAutoCommit(false);

                chitiethoadonDAO cthdDAO = new chitiethoadonDAO(conn);
                hoadonDAO hdDAO = new hoadonDAO(conn);

                // XÓA CHI TIẾT HÓA ĐƠN CŨ
                boolean xoaCTHD = cthdDAO.deleteAll(Integer.parseInt(maHD));
                if (!xoaCTHD) {
                    throw new SQLException("Lỗi khi xóa chi tiết hóa đơn cũ");
                }

                // THÊM CHI TIẾT HÓA ĐƠN MỚI
                DefaultTableModel model = (DefaultTableModel) tbcthd.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String maHDCT = model.getValueAt(i, 0).toString();
                    int maSP = Integer.parseInt(model.getValueAt(i, 1).toString());
                    int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
                    double thanhTien = Double.parseDouble(model.getValueAt(i, 3).toString());

                    chitiethoadon ct = new chitiethoadon();
                    ct.setMahoadon(Integer.parseInt(maHDCT));
                    ct.setMasp(maSP);
                    ct.setSoluong(soLuong);
                    ct.setTongtien(thanhTien);

                    boolean themCT = cthdDAO.insert(ct);
                    if (!themCT) {
                        throw new SQLException("Lỗi khi thêm chi tiết hóa đơn dòng " + (i + 1));
                    }
                }

                // CẬP NHẬT TỔNG TIỀN HÓA ĐƠN
                boolean capNhatHD = hdDAO.UpdateTT(Integer.parseInt(maHD), tongTien);
                if (!capNhatHD) {
                    throw new SQLException("Lỗi khi cập nhật tổng tiền hóa đơn");
                }

                conn.commit();
                success = true;

            } catch (SQLException e) {
                if (conn != null) {
                    conn.rollback();
                }
                throw e;
            } finally {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            }

            // 4. THÔNG BÁO VÀ ĐÓNG FORM
            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công!");

                // ĐÓNG FORM
                java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbsp = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtmahd = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtsl = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbkh = new javax.swing.JComboBox<>();
        btthemsp = new javax.swing.JButton();
        btsuasp = new javax.swing.JButton();
        btxoasp = new javax.swing.JButton();
        btthoat = new javax.swing.JButton();
        btluuhd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbcthd = new javax.swing.JTable();
        dtnxh = new org.jdesktop.swingx.JXDatePicker();

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chi tiết hóa đơn");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Tên sản phẩm");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Mã đơn hàng");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("ngày xuất hàng");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Số lượng");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Khách hàng");

        btthemsp.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btthemsp.setText("Thêm ");
        btthemsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btthemspActionPerformed(evt);
            }
        });

        btsuasp.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btsuasp.setText("Sửa");
        btsuasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsuaspActionPerformed(evt);
            }
        });

        btxoasp.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btxoasp.setText("Xóa");
        btxoasp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btxoaspActionPerformed(evt);
            }
        });

        btthoat.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btthoat.setText("Thoát");
        btthoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btthoatActionPerformed(evt);
            }
        });

        btluuhd.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btluuhd.setText("Lưu hóa đơn");
        btluuhd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btluuhdActionPerformed(evt);
            }
        });

        tbcthd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã đơn hàng", "Mã sp", "Số lượng", "Thành tiền"
            }
        ));
        jScrollPane1.setViewportView(tbcthd);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(cbsp, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtsl, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(137, 137, 137))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(228, 228, 228))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbkh, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtmahd, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(97, 97, 97)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dtnxh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btthemsp)
                        .addGap(45, 45, 45)
                        .addComponent(btsuasp)
                        .addGap(56, 56, 56)
                        .addComponent(btxoasp)
                        .addGap(76, 76, 76)
                        .addComponent(btluuhd)
                        .addGap(51, 51, 51)
                        .addComponent(btthoat)))
                .addGap(22, 133, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtmahd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtnxh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbkh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel6)))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addComponent(txtsl))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btthemsp)
                    .addComponent(btsuasp)
                    .addComponent(btxoasp)
                    .addComponent(btthoat)
                    .addComponent(btluuhd))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 763, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 747, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btthemspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btthemspActionPerformed
        // TODO add your handling code here:
        themsp();
    }//GEN-LAST:event_btthemspActionPerformed

    private void btsuaspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsuaspActionPerformed
        // TODO add your handling code here:
      suasp();
    }//GEN-LAST:event_btsuaspActionPerformed

    private void btxoaspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btxoaspActionPerformed
        // TODO add your handling code here:
        xoasp();
    }//GEN-LAST:event_btxoaspActionPerformed

    private void btthoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btthoatActionPerformed
        // TODO add your handling code here:
        java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }//GEN-LAST:event_btthoatActionPerformed

    private void btluuhdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btluuhdActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,"Bạn có muốn lưu k?","Xác nhận",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            txtmahd.setEditable(true);
            luuHoaDon();
        }
    
    }//GEN-LAST:event_btluuhdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btluuhd;
    private javax.swing.JButton btsuasp;
    private javax.swing.JButton btthemsp;
    private javax.swing.JButton btthoat;
    private javax.swing.JButton btxoasp;
    private javax.swing.JComboBox<goiykhachhang> cbkh;
    private javax.swing.JComboBox<goiysanpham> cbsp;
    private org.jdesktop.swingx.JXDatePicker dtnxh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbcthd;
    private javax.swing.JTextField txtmahd;
    private javax.swing.JTextField txtsl;
    // End of variables declaration//GEN-END:variables
}
