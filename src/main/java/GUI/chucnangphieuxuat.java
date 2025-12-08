/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import DAO.chitiethoadonDAO;
import DAO.chitietphieuxuatDAO;
import DAO.donvivanchuyenDAO;
import DAO.hoadonDAO;
import DAO.phieuxuatDAO;
import DAO.phieuxuat_hoadonDAO;
import Models.chitiethoadon;
import Models.chitietphieuxuat;
import Models.donvivanchuyen;
import Models.hoadon;
import Models.phieuxuat;
import database.dbconnection;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import servicesANDvalidate.validate;

/**
 *
 * @author Admin
 */
public class chucnangphieuxuat extends javax.swing.JPanel {
    private TableRowSorter<DefaultTableModel> sorter;
    private TableRowSorter<DefaultTableModel> sorterPX;
    /**
     * Creates new form phieuxuat
     */
    public chucnangphieuxuat() throws SQLException {
        initComponents();
        loadHoaDon();
        tblpx_hd.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{
            },
            new String[]{
                "mã hóa đơn","ngày xuất","tổng tiền","mã khách hàng"
            }
        ));
        tblspPX.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{
            },
            new String[]{
                "tên sản phẩm ","số lượng","tổng tiền"
            }
        ));
        txtngayxuat.setDate(new java.util.Date());
        cbdvvc.setEditable(true);
        loaddvvc();
        loadphieuxuat();
        DefaultTableModel model=(DefaultTableModel) tblhoadonPX.getModel();
        sorter=new TableRowSorter<>(model);
        tblhoadonPX.setRowSorter(sorter);
        txtthemhoadon.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text=txtthemhoadon.getText();
                if (text.trim().length()==0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        createPopupMenu();
        DefaultTableModel phieuxuatmodel=(DefaultTableModel) tblphieuxuat.getModel();
        sorterPX=new TableRowSorter<>(phieuxuatmodel);
        tblphieuxuat.setRowSorter(sorterPX);
        txttimkiempx.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e){ filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e){ filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e){ filter(); }

            private void filter() {
                String text=txttimkiempx.getText();
                if (text.trim().length()==0) {
                    sorterPX.setRowFilter(null);
                } else {
                    sorterPX.setRowFilter(RowFilter.regexFilter("(?i)"+text));
                }
            }
        });
    }
    
    private void createPopupMenu() {
        // Tạo popup menu
        JPopupMenu popupHuy=new JPopupMenu();
        popupHuy.setLayout(new GridLayout(0, 1));

        // Nút 1: Hủy hóa đơn
        JButton btnHuyHD=new JButton("Hủy hóa đơn");
        popupHuy.add(btnHuyHD);

        // Nút 2: Trả lại hóa đơn
        JButton btnTraLaiHD = new JButton("Trả lại hóa đơn");
        popupHuy.add(btnTraLaiHD);
        btnHuyHD.addActionListener(this::handleHuyHD);
        btnTraLaiHD.addActionListener(this::handleTraLaiHD);
        popupHuy.add(btnHuyHD);
        popupHuy.add(btnTraLaiHD);

        // Hiển thị popup khi click nút
        btnhuyphieuxuat.addActionListener(e -> popupHuy.show(btnhuyphieuxuat,0,btnhuyphieuxuat.getHeight()));
    }
    
    //Event cho nút hủy phiếu xuất và hủy hóa đơn
    private void handleHuyHD(ActionEvent e) {
        int[] selectedRows = tblphieuxuat.getSelectedRows();
        if (selectedRows.length==0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn ít nhất 1 phiếu xuất.");
            return;
        }

        try {
            phieuxuatDAO dao=new phieuxuatDAO(dbconnection.getConnection());
            for (int row:selectedRows) {
                int mapx=(int) tblphieuxuat.getValueAt(row, 0);
                dao.updatePhieuXuatAndHoaDon(mapx, "Hủy", "Hủy");              
            }
            JOptionPane.showMessageDialog(this, "Hủy phiếu xuất thành công!");
            loadphieuxuat();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi hủy phiếu xuất!");
        }
    }
    
    //Event hủy phiếu xuất nhưng trả lại trạng thái cho hóa đơn
    private void handleTraLaiHD(ActionEvent e) {
        int[] selectedRows=tblphieuxuat.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn ít nhất 1 phiếu xuất.");
            return;
        }

        try {
            phieuxuatDAO dao= new phieuxuatDAO(dbconnection.getConnection());
            for (int row : selectedRows) {
                int mapx = (int) tblphieuxuat.getValueAt(row, 0);
                dao.updatePhieuXuatAndHoaDon(mapx, "Hủy", "Phiếu Xuất");
            }
            JOptionPane.showMessageDialog(this, "Trả lại phiếu xuất thành công!");
            loadphieuxuat(); // Load lại bảng phiếu xuất
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi trả lại phiếu xuất!");
        }
    }
    
    private void clearForm() {
        txtmapx.setText("");             
        txtngayxuat.setDate(null);
        cbdvvc.setSelectedIndex(-1);   
        DefaultTableModel modelHD = (DefaultTableModel) tblpx_hd.getModel();
        modelHD.setRowCount(0);
        DefaultTableModel modelSP = (DefaultTableModel) tblspPX.getModel();
        modelSP.setRowCount(0);
    }
    
    private void setEditableForm(boolean editable) {
        txtmapx.setEditable(editable);
        txtngayxuat.setEnabled(editable);
        cbdvvc.setEnabled(editable);
        tblpx_hd.setEnabled(editable);
        tblspPX.setEnabled(editable);
        btnluuchitietpx.setEnabled(editable);
        btnthemchitietpx.setEnabled(editable);
        btnxoachitietpx.setEnabled(editable);
        tblhoadonPX.setEnabled(editable);
    }
    
    //load hóa đơn vào bảng hóa đơn có trạng thái "Phiếu Xuất" để cho vào chi tiết hóa đơn
    private void loadHoaDon() throws SQLException{
        String trangthai="Phiếu Xuất";
        DefaultTableModel model = (DefaultTableModel) tblhoadonPX.getModel();
        model.setRowCount(0);
        hoadonDAO dao=new hoadonDAO(dbconnection.getConnection());
        List<hoadon> listhd= dao.getByTrangThai(trangthai);
        for(hoadon hd:listhd){
            model.addRow(new Object[]{
                hd.getMahoadon(),
                hd.getNgayxuathd(),
                hd.getTongtien(),
                hd.getTrangthai(),
                hd.getMakhachhang()
            });
        }
    }
    
    //Combobox đơn vị vận chuyển
    private void loaddvvc() throws SQLException{
        cbdvvc.removeAllItems();
        donvivanchuyenDAO dvvcDAO=new donvivanchuyenDAO(dbconnection.getConnection());
        List<donvivanchuyen> listCombo = dvvcDAO.getAll();
        for(donvivanchuyen p : listCombo){
            cbdvvc.addItem(p);
        }
        AutoCompleteDecorator.decorate(cbdvvc);
    }
    
    //load phiếu xuất vào table cửa phiếu xuất
    private void loadphieuxuat() throws SQLException{
        DefaultTableModel model = (DefaultTableModel) tblphieuxuat.getModel();
        model.setRowCount(0);
        phieuxuatDAO pxDAO = new phieuxuatDAO(dbconnection.getConnection());
        List<phieuxuat> listPX = pxDAO.getAll();
        for (phieuxuat px : listPX) {
            model.addRow(new Object[]{
                px.getMapx(),
                px.getNgayxuat(),
                px.getMadvvc(),
                px.getTongtien(),
                px.getTrangthai()
            });
        }
    }
    
    private void xemPhieuXuat(int mapx) {
        try {
            // DAO
            phieuxuat_hoadonDAO pxhdDAO = new phieuxuat_hoadonDAO(dbconnection.getConnection());
            chitietphieuxuatDAO ctpDAO = new chitietphieuxuatDAO(dbconnection.getConnection());
            phieuxuatDAO pxDAO = new phieuxuatDAO(dbconnection.getConnection());

            // Lấy thông tin phiếu xuât 
            phieuxuat px = pxDAO.getPhieuXuatById(mapx);//phiếu xuất theo mã phiếu xuất đã chọn
            txtmapx.setText(String.valueOf(px.getMapx()));
            txtngayxuat.setDate(px.getNgayxuat());
            for (int i = 0; i < cbdvvc.getItemCount(); i++) {
                if (cbdvvc.getItemAt(i).getMadvvc() == px.getMadvvc()) {
                    cbdvvc.setSelectedIndex(i);
                    break;
                }
            }

            //Load bảng hóa đơn
            DefaultTableModel modelHD = (DefaultTableModel) tblpx_hd.getModel();
            modelHD.setRowCount(0);

            List<hoadon> listHD = pxhdDAO.getHoaDonByMapx(mapx);
            for (hoadon hd : listHD) {
                modelHD.addRow(new Object[]{
                    hd.getMahoadon(),
                    hd.getNgayxuathd(),
                    hd.getTongtien(),
                    hd.getMakhachhang()
                });
            }

            // Load bảng sản phẩm
            DefaultTableModel modelSP = (DefaultTableModel) tblspPX.getModel();
            modelSP.setRowCount(0);

            List<Object[]> listSP = ctpDAO.getCTByMapx(mapx);
            for (Object[] row : listSP) {
                modelSP.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xem phiếu xuất!");
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

        Dialogchitietphieuxuat = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtmapx = new javax.swing.JTextField();
        txtthemhoadon = new javax.swing.JTextField();
        txtngayxuat = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblhoadonPX = new javax.swing.JTable();
        btnthemchitietpx = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblpx_hd = new javax.swing.JTable();
        btnxoachitietpx = new javax.swing.JButton();
        btnluuchitietpx = new javax.swing.JButton();
        btnhuychitietpx = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblspPX = new javax.swing.JTable();
        cbdvvc = new javax.swing.JComboBox<>();
        btnthemphieuxuat = new javax.swing.JButton();
        btnxemphieuxuat = new javax.swing.JButton();
        btnhuyphieuxuat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblphieuxuat = new javax.swing.JTable();
        btndagiao = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txttimkiempx = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("CHI TIẾT PHIẾU XUẤT");

        jLabel2.setText("Mã phiếu xuất:");

        jLabel3.setText("Ngày xuất:");

        jLabel4.setText("Đơn vị vận chuyển:");

        jLabel5.setText("Thêm hóa đơn:");

        txtthemhoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtthemhoadonActionPerformed(evt);
            }
        });

        tblhoadonPX.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "mã hóa đơn", "ngày xuất", "tổng tiền", "trạng thái", "mã khách hàng"
            }
        ));
        jScrollPane2.setViewportView(tblhoadonPX);

        btnthemchitietpx.setText("Thêm");
        btnthemchitietpx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemchitietpxActionPerformed(evt);
            }
        });

        tblpx_hd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "mã hóa đơn", "ngày xuất", "tổng tiền", "mã khách hàng"
            }
        ));
        jScrollPane3.setViewportView(tblpx_hd);

        btnxoachitietpx.setText("Xóa");
        btnxoachitietpx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoachitietpxActionPerformed(evt);
            }
        });

        btnluuchitietpx.setText("Lưu");
        btnluuchitietpx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnluuchitietpxActionPerformed(evt);
            }
        });

        btnhuychitietpx.setText("hủy");
        btnhuychitietpx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhuychitietpxActionPerformed(evt);
            }
        });

        tblspPX.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "tên sản phẩm", "số lượng", "thành tiền"
            }
        ));
        jScrollPane4.setViewportView(tblspPX);

        javax.swing.GroupLayout DialogchitietphieuxuatLayout = new javax.swing.GroupLayout(Dialogchitietphieuxuat.getContentPane());
        Dialogchitietphieuxuat.getContentPane().setLayout(DialogchitietphieuxuatLayout);
        DialogchitietphieuxuatLayout.setHorizontalGroup(
            DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogchitietphieuxuatLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogchitietphieuxuatLayout.createSequentialGroup()
                        .addGroup(DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtmapx, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtthemhoadon)
                            .addComponent(cbdvvc, 0, 301, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(DialogchitietphieuxuatLayout.createSequentialGroup()
                        .addGroup(DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtngayxuat, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(148, 148, 148))))
            .addGroup(DialogchitietphieuxuatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogchitietphieuxuatLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnluuchitietpx)
                .addGap(46, 46, 46)
                .addComponent(btnhuychitietpx)
                .addGap(49, 49, 49))
            .addGroup(DialogchitietphieuxuatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(DialogchitietphieuxuatLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(btnthemchitietpx)
                .addGap(39, 39, 39)
                .addComponent(btnxoachitietpx)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(DialogchitietphieuxuatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4))
        );
        DialogchitietphieuxuatLayout.setVerticalGroup(
            DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogchitietphieuxuatLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DialogchitietphieuxuatLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addGroup(DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(txtmapx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtngayxuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbdvvc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtthemhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthemchitietpx)
                    .addComponent(btnxoachitietpx))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DialogchitietphieuxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnluuchitietpx)
                    .addComponent(btnhuychitietpx))
                .addGap(22, 22, 22))
        );

        btnthemphieuxuat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnthemphieuxuat.setText("Thêm");
        btnthemphieuxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemphieuxuatActionPerformed(evt);
            }
        });

        btnxemphieuxuat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnxemphieuxuat.setText("Xem");
        btnxemphieuxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxemphieuxuatActionPerformed(evt);
            }
        });

        btnhuyphieuxuat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnhuyphieuxuat.setText("Hủy ");
        btnhuyphieuxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhuyphieuxuatActionPerformed(evt);
            }
        });

        tblphieuxuat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã phiếu", "Ngày xuất", "Đơn vị vận chuyển", "Tổng tiền", "Trạng thái"
            }
        ));
        jScrollPane1.setViewportView(tblphieuxuat);

        btndagiao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btndagiao.setText("Đã giao");
        btndagiao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndagiaoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Tìm kiếm:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1064, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttimkiempx, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnthemphieuxuat)
                        .addGap(68, 68, 68)
                        .addComponent(btnxemphieuxuat)
                        .addGap(68, 68, 68)
                        .addComponent(btnhuyphieuxuat)
                        .addGap(62, 62, 62)
                        .addComponent(btndagiao)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthemphieuxuat)
                    .addComponent(btnxemphieuxuat)
                    .addComponent(btnhuyphieuxuat)
                    .addComponent(btndagiao))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txttimkiempx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnthemphieuxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemphieuxuatActionPerformed
        clearForm();
        setEditableForm(true);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.5);
        int height = screenSize.height - 100;
        Dialogchitietphieuxuat.setSize(width, height);
        Dialogchitietphieuxuat.setLocationRelativeTo(null);
        Dialogchitietphieuxuat.setModal(true);
        Dialogchitietphieuxuat.setVisible(true);
        
    }//GEN-LAST:event_btnthemphieuxuatActionPerformed

    private void btnthemchitietpxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemchitietpxActionPerformed
        //select nhiều hóa đơn available
        int[] selectedRows = tblhoadonPX.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn ít nhất 1 hóa đơn.");
            return;
        }

        DefaultTableModel modelTren = (DefaultTableModel) tblhoadonPX.getModel();// Bảng hóa đơn available
        DefaultTableModel modelDuoi = (DefaultTableModel) tblpx_hd.getModel(); // Bảng hóa đơn đã chọn
        DefaultTableModel modelChiTiet = (DefaultTableModel) tblspPX.getModel(); // Bảng chi tiết phiếu xuất

        // chuyển hóa đơn từ bảng trên xuống bảng dưới
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            int viewRow = selectedRows[i];
            int modelRow = tblhoadonPX.convertRowIndexToModel(viewRow); //Chuyển chỉ số hàng ở giao diện (view index) sang chỉ số hàng ở model (model index).
            //lấy các trường thông tin của Bảng Trên
            int maHD = (int) modelTren.getValueAt(modelRow, 0); 
            Object ngay = modelTren.getValueAt(modelRow, 1);
            Object tong = modelTren.getValueAt(modelRow, 2);
            Object makh = modelTren.getValueAt(modelRow, 4);
            modelDuoi.addRow(new Object[]{ maHD, ngay, tong, makh });//thêm thông tin vào bảng bên dưới
            modelTren.removeRow(modelRow);//xóa đi thông tin đã thêm ở bảng bên trên
        }

        // Lấy mã hóa đơn ở bảng bên dưới
        List<Integer> allMaHD = new ArrayList<>();
        for (int i = 0; i < modelDuoi.getRowCount(); i++) {
            allMaHD.add((int) modelDuoi.getValueAt(i, 0));
        }

        // Lấy sản phẩm của các hóa đơn - cộng gộp với nhau nếu trùng
        try {
            chitiethoadonDAO ctDao = new chitiethoadonDAO(dbconnection.getConnection());
            Map<Integer, chitiethoadon> mapCT = new HashMap<>(); //hashmap  lưu <mã sản phẩm, sản phẩm trong chi tiết hóa đơn>

            for (int maHD : allMaHD) {
                List<chitiethoadon> listCT = ctDao.getByMaHoaDon(maHD);// với những hóa đơn đã chọn lấy ra sản phẩm trong chi tiết hóa đơn
                for (chitiethoadon ct : listCT) {
                    int masp = ct.getMasp();//lấy ra từng mã sản phẩm của list sản phẩm
                    if (mapCT.containsKey(masp)) {//nếu mã sản phẩm đã tồn tại trong map
                        chitiethoadon exist = mapCT.get(masp);
                        exist.setSoluong(exist.getSoluong() + ct.getSoluong());//cộng số lượng
                        exist.setTongtien(exist.getTongtien() + ct.getTongtien());//cộng tổng tiền
                    } else {// nếu chưa xuất hiện sản phẩm thì thêm mới
                        mapCT.put(masp, new chitiethoadon(
                            ct.getMahoadon(),
                            ct.getMasp(),
                            ct.getTensp(),
                            ct.getSoluong(),
                            ct.getTongtien()
                        ));
                    }
                }
            }

            // cập nhật bảng chi tiết sản phẩm
            modelChiTiet.setRowCount(0);
            for (chitiethoadon ct : mapCT.values()) {
                modelChiTiet.addRow(new Object[]{
                    ct.getTensp(),
                    ct.getSoluong(),
                    ct.getTongtien()
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy chi tiết hóa đơn!");
        }
    }//GEN-LAST:event_btnthemchitietpxActionPerformed

    private void btnluuchitietpxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnluuchitietpxActionPerformed
        String mapxText = txtmapx.getText();
        java.util.Date ngayxuatText = txtngayxuat.getDate();

        // Validate dữ liệu đầu vào
        if (validate.isEmpty(mapxText)) {
            JOptionPane.showMessageDialog(this, "Mã phiếu xuất không được để trống!");
            return;
        }
        if (!validate.isPositiveInteger(mapxText)) {
            JOptionPane.showMessageDialog(this, "Mã phiếu xuất phải là số nguyên dương!");
            return;
        }
        if (ngayxuatText == null) {
            JOptionPane.showMessageDialog(this, "Hãy chọn ngày xuất!");
            return;
        }
        int mapx = Integer.parseInt(mapxText);
        java.sql.Date sqlDate = new java.sql.Date(ngayxuatText.getTime());

        donvivanchuyen dv = (donvivanchuyen) cbdvvc.getSelectedItem();
        if (dv == null || dv.getMadvvc() <= 0) {
            JOptionPane.showMessageDialog(this, "Đơn vị vận chuyển không hợp lệ!");
            return;
        }
        int madvvc = dv.getMadvvc();

        DefaultTableModel modelDuoi = (DefaultTableModel) tblpx_hd.getModel();
        DefaultTableModel modelChiTiet = (DefaultTableModel) tblspPX.getModel();
        if (modelDuoi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có hóa đơn nào để lưu.");
            return;
        }

        try {
            phieuxuatDAO pxDAO = new phieuxuatDAO(dbconnection.getConnection());
            chitietphieuxuatDAO ctpDAO = new chitietphieuxuatDAO(dbconnection.getConnection());
            phieuxuat_hoadonDAO pxhdDAO = new phieuxuat_hoadonDAO(dbconnection.getConnection());
            hoadonDAO hdDAO = new hoadonDAO(dbconnection.getConnection());
            chitiethoadonDAO ctDao = new chitiethoadonDAO(dbconnection.getConnection());

            // Kiểm tra trùng mã phiếu xuất
            if (pxDAO.existsMapx(mapx)) {
                JOptionPane.showMessageDialog(this, "Mã phiếu xuất đã tồn tại!");
                return;
            }

            // Insert phiếu xuất
            phieuxuat px = new phieuxuat();
            px.setMapx(mapx);
            px.setNgayxuat(sqlDate);
            px.setMadvvc(madvvc);
            px.setTongtien(0);
            px.setTrangthai("Chờ duyệt");
            pxDAO.insertPhieuXuat(px);
            
            double tongTienPX = 0;
            // Insert phiếu xuất + phiếu xuất_hóa đơn + chi tiết phiếu xuất
            for (int i = 0; i < modelDuoi.getRowCount(); i++) {
                int maHD = (int) modelDuoi.getValueAt(i, 0);

                pxhdDAO.insert(mapx, maHD); // Insert vào phieuxuat_hoadon
                hdDAO.updateTrangThai(maHD, "Chờ duyệt"); // Update trạng thái hóa đơn

                List<chitiethoadon> listCT = ctDao.getByMaHoaDon(maHD);
                for (chitiethoadon ct : listCT) {
                    chitietphieuxuat ctpx = new chitietphieuxuat();
                    ctpx.setMapx(mapx);
                    ctpx.setMasp(ct.getMasp());
                    ctpx.setSoluong(ct.getSoluong());
                    ctpx.setThanhtien(ct.getTongtien());
                    ctpDAO.insertCTPhieuXuat(ctpx);

                    tongTienPX += ct.getTongtien();
                }
            }

            // cập nhật tổng tiền phiếu xuất
            pxDAO.updateTongTien(mapx, tongTienPX);
            modelDuoi.setRowCount(0);
            modelChiTiet.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Lưu phiếu xuất thành công!");
            loadphieuxuat();
            Dialogchitietphieuxuat.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu phiếu xuất!");
        }
    }//GEN-LAST:event_btnluuchitietpxActionPerformed

    private void btnxoachitietpxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoachitietpxActionPerformed
        DefaultTableModel modelDuoi=(DefaultTableModel) tblpx_hd.getModel();
        DefaultTableModel modelTren=(DefaultTableModel) tblhoadonPX.getModel();
        DefaultTableModel modelChiTiet=(DefaultTableModel) tblspPX.getModel();

        int[] selectedRows = tblpx_hd.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn ít nhất 1 hóa đơn để xóa.");
            return;
        }
        try {
            // Xóa hóa đơn ở bảng dưới thêm hóa đơn vào bảng trên
            for (int i = selectedRows.length - 1; i >=0;i--) {
                int row = selectedRows[i];
                int maHD = (int) modelDuoi.getValueAt(row,0);
                Object ngay= modelDuoi.getValueAt(row,1);
                Object tong= modelDuoi.getValueAt(row,2);
                Object makh= modelDuoi.getValueAt(row,3);
                modelTren.addRow(new Object[]{maHD,ngay,tong,"Phiếu Xuất",makh });
                modelDuoi.removeRow(row);
            }
            // Lấy hóa đơn của bảng dưới
            List<Integer> allMaHD = new ArrayList<>();
            for (int i = 0;i<modelDuoi.getRowCount();i++) {
                allMaHD.add((int) modelDuoi.getValueAt(i,0));
            }

            // Lấy sản phẩm từ những hóa đơn ở bảng dưới
            chitiethoadonDAO ctDao = new chitiethoadonDAO(dbconnection.getConnection());
            Map<Integer, chitiethoadon> mapCT = new HashMap<>(); //map lưu thông tin <mã sản phẩm, chitiethoadon>
            for(int maHD:allMaHD) {//duyệt qua các hóa đơn 
                List<chitiethoadon> listCT=ctDao.getByMaHoaDon(maHD);// lấy list sản phẩm thông qua các hóa đơn
                for (chitiethoadon ct:listCT) {
                    int masp=ct.getMasp();// lấy mã sản phẩm trong list
                    if (mapCT.containsKey(masp)) {
                        chitiethoadon exist = mapCT.get(masp); //nếu đã tồn tại thì cộng số lượng và tong tien
                        exist.setSoluong(exist.getSoluong()+ct.getSoluong());
                        exist.setTongtien(exist.getTongtien()+ct.getTongtien());
                    } else {//nếu chưa có thì tạo mới
                        mapCT.put(masp, new chitiethoadon(
                            ct.getMahoadon(),
                            ct.getMasp(),
                            ct.getTensp(),
                            ct.getSoluong(),
                            ct.getTongtien()
                        ));
                    }
                }
            }

            // cập nhật lại bảng chi tiết
            modelChiTiet.setRowCount(0);
            for (chitiethoadon ct:mapCT.values()) {
                modelChiTiet.addRow(new Object[]{ ct.getTensp(),ct.getSoluong(),ct.getTongtien() });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xử lý chi tiết hóa đơn!");
        }
    }//GEN-LAST:event_btnxoachitietpxActionPerformed

    private void btnxemphieuxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxemphieuxuatActionPerformed
        int selectedRow=tblphieuxuat.getSelectedRow();
        if (selectedRow==-1) {
            JOptionPane.showMessageDialog(this,"Hãy chọn một phiếu xuất để xem!");
            return;
        }
        int mapx =(int) tblphieuxuat.getValueAt(selectedRow, 0);
        xemPhieuXuat(mapx); 
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width=(int) (screenSize.width * 0.5);
        int height=screenSize.height - 100;
        Dialogchitietphieuxuat.setSize(width,height);
        Dialogchitietphieuxuat.setLocationRelativeTo(null);
        Dialogchitietphieuxuat.setModal(true);
        setEditableForm(false);
        Dialogchitietphieuxuat.setVisible(true);
    }//GEN-LAST:event_btnxemphieuxuatActionPerformed

    private void btnhuychitietpxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhuychitietpxActionPerformed
        Dialogchitietphieuxuat.dispose();
    }//GEN-LAST:event_btnhuychitietpxActionPerformed

    private void btnhuyphieuxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhuyphieuxuatActionPerformed

    }//GEN-LAST:event_btnhuyphieuxuatActionPerformed

    private void btndagiaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndagiaoActionPerformed
        int[] selectedRows=tblphieuxuat.getSelectedRows();
        if (selectedRows.length==0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn ít nhất 1 phiếu xuất.");
            return;
        }

        try {
            phieuxuatDAO dao= new phieuxuatDAO(dbconnection.getConnection());
            for (int row:selectedRows) {
                int mapx=(int) tblphieuxuat.getValueAt(row, 0);
                dao.updatePhieuXuatAndHoaDon(mapx, "Đã Giao", "Đã Giao");
            }
            JOptionPane.showMessageDialog(this, "Giao phiếu xuất thành công");
            loadphieuxuat();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"Giao phiếu xuất thành công");
        }
    }//GEN-LAST:event_btndagiaoActionPerformed

    private void txtthemhoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtthemhoadonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtthemhoadonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog Dialogchitietphieuxuat;
    private javax.swing.JButton btndagiao;
    private javax.swing.JButton btnhuychitietpx;
    private javax.swing.JButton btnhuyphieuxuat;
    private javax.swing.JButton btnluuchitietpx;
    private javax.swing.JButton btnthemchitietpx;
    private javax.swing.JButton btnthemphieuxuat;
    private javax.swing.JButton btnxemphieuxuat;
    private javax.swing.JButton btnxoachitietpx;
    private javax.swing.JComboBox<donvivanchuyen> cbdvvc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblhoadonPX;
    private javax.swing.JTable tblphieuxuat;
    private javax.swing.JTable tblpx_hd;
    private javax.swing.JTable tblspPX;
    private javax.swing.JTextField txtmapx;
    private com.toedter.calendar.JDateChooser txtngayxuat;
    private javax.swing.JTextField txtthemhoadon;
    private javax.swing.JTextField txttimkiempx;
    // End of variables declaration//GEN-END:variables

}
