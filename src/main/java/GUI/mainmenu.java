/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.chitietphieunhapDAO;
import DAO.nhacungcapDAO;
import DAO.nhanvienDAO;
import DAO.sanphamDAO;
import Models.goiysanpham;
import Models.nhanvienComboBox;
import Models.nhacungcapComboBox;
import database.dbconnection;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import servicesANDvalidate.validate;
import DAO.chitietphieunhapDAO;
import DAO.phieunhapDAO;
import Models.chitietphieu;
import Models.phieunhap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import servicesANDvalidate.services;

/**
 *
 * @author Admin
 */
public class mainmenu extends javax.swing.JFrame {
    /**
     * Creates new form mainmenu
     */
    public mainmenu() throws SQLException {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainPanel.setPreferredSize(new Dimension(1280, 720));
        mainPanel.setMaximumSize(new Dimension(1280, 720));
        mainPanel.setMinimumSize(new Dimension(1280, 720));
        jPanel3.setLayout(new java.awt.CardLayout());
        jPanel3.add(panelphieunhap, "PHIEU_NHAP");
        jPanel3.add(jPanel4, "SAN PHAM");
        CardLayout cl = (CardLayout) (jPanel3.getLayout());
        cl.show(jPanel3, "SAN PHAM");
        cbsanpham.setEditable(true);
        loadSanPham();
        cbnhanvien.setEditable(true);
        loadnhanvien();
        cbnhacungcap.setEditable(true);
        loadnhacungcap();
        tbchitietphieunhap.setModel(new DefaultTableModel(
            new Object[][] {}, // không có hàng mặc định
            new String [] {"Mã phiếu", "Mã sản phẩm", "Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"}
        ));
        tblphieunhap.setModel(new DefaultTableModel(
            new Object[][] {},
            new String [] {"Mã phiếu", "Ngày Nhập", "Nhân Viên", "Nhà Cung Cấp", "Tổng Tiền"}
        ));
        txtdatechooser.setDate(new java.util.Date());
        loadPhieuNhap();
        
    }
    
    private void loadSanPham(){
        cbsanpham.removeAllItems();
        sanphamDAO spDAO=new sanphamDAO(dbconnection.getConnection());
        List<goiysanpham> listGoiY= spDAO.getAllForComboBox();
        for (goiysanpham p : listGoiY) {
            cbsanpham.addItem(p);
        }
        AutoCompleteDecorator.decorate(cbsanpham);
    }
    
    private void loadnhanvien() throws SQLException{
        cbnhanvien.removeAllItems();
        nhanvienDAO nvDAO=new nhanvienDAO(dbconnection.getConnection());
        List<nhanvienComboBox> listCombo = nvDAO.getForComboBox();
        for(nhanvienComboBox p : listCombo){
            cbnhanvien.addItem(p);
        }
        AutoCompleteDecorator.decorate(cbnhanvien);
    }
    
    private void loadnhacungcap() throws SQLException{
        cbnhacungcap.removeAllItems();
        nhacungcapDAO nccDAO=new nhacungcapDAO(dbconnection.getConnection());
        List<nhacungcapComboBox> listComboBox = nccDAO.getnccForComboBox();
        for(nhacungcapComboBox p : listComboBox){
            cbnhacungcap.addItem(p);
        }
        AutoCompleteDecorator.decorate(cbnhacungcap);
    }
    
    private void Enabled(){
        txtmaphieunhap.setEnabled(false);
        cbnhacungcap.setEnabled(false);
        cbnhanvien.setEnabled(false);
        txtdatechooser.setEnabled(false);
    }
    
    private void Abled(){
        txtmaphieunhap.setEnabled(true);
        cbnhacungcap.setEnabled(true);
        cbnhanvien.setEnabled(true);
        txtdatechooser.setEnabled(true);
    }
    
    private double tinhTongTienTable() {
        DefaultTableModel model = (DefaultTableModel) tbchitietphieunhap.getModel();
        double tong = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Object thanhtienObj = model.getValueAt(i, 4);
            if (thanhtienObj != null) {
                tong += Double.parseDouble(thanhtienObj.toString());
            }
        }
        return tong;
    }
    
    private void loadPhieuNhap() throws SQLException{
        phieunhapDAO phieunhapdao=new phieunhapDAO(dbconnection.getConnection());
        List<phieunhap> list = phieunhapdao.getAll();
        DefaultTableModel model = (DefaultTableModel) tblphieunhap.getModel();
        model.setRowCount(0);

        for (phieunhap pn : list) {
            model.addRow(new Object[]{
                pn.getMaphieu(),
                pn.getNgaynhap(), 
                pn.getTennv(),
                pn.getTenncc(),
                pn.getTongtien()
            });
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

        themchitietphieu = new javax.swing.JDialog();
        pnchitietphieu = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtmaphieunhap = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtphieunhapsoluong = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbchitietphieunhap = new javax.swing.JTable();
        btnphieunhapthem = new javax.swing.JButton();
        btnphieunhapluu = new javax.swing.JButton();
        btnphieunhapxoa = new javax.swing.JButton();
        btnphieunhaphuy = new javax.swing.JButton();
        cbsanpham = new javax.swing.JComboBox<>();
        cbnhanvien = new javax.swing.JComboBox<>();
        cbnhacungcap = new javax.swing.JComboBox<>();
        btnphieunhapsua = new javax.swing.JButton();
        btnphieunhapxoabang = new javax.swing.JButton();
        txtdatechooser = new com.toedter.calendar.JDateChooser();
        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnsanpham = new javax.swing.JButton();
        btnhoadon = new javax.swing.JButton();
        btnphieunhap = new javax.swing.JButton();
        btnphieuxuat = new javax.swing.JButton();
        btnbaocao = new javax.swing.JButton();
        btntaikhoan = new javax.swing.JButton();
        btnnhasanxuat = new javax.swing.JButton();
        btndonvi = new javax.swing.JButton();
        btnnhanvien = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panelphieunhap = new javax.swing.JPanel();
        btnthemphieunhap = new javax.swing.JButton();
        btnxemphieunhap = new javax.swing.JButton();
        btnxoaphieunhap = new javax.swing.JButton();
        tbphieunhap = new javax.swing.JScrollPane();
        tblphieunhap = new javax.swing.JTable();

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Thêm phiếu nhập hàng");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mã phiếu:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Ngày nhập:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Nhà cung cấp:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Nhân viên:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Thêm sản phẩm:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Số lượng:");

        tbchitietphieunhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã phiếu", "Mã sản phẩm", "Sản  phẩm", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane2.setViewportView(tbchitietphieunhap);

        btnphieunhapthem.setText("thêm");
        btnphieunhapthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnphieunhapthemActionPerformed(evt);
            }
        });

        btnphieunhapluu.setText("Lưu");
        btnphieunhapluu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnphieunhapluuActionPerformed(evt);
            }
        });

        btnphieunhapxoa.setText("xóa");
        btnphieunhapxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnphieunhapxoaActionPerformed(evt);
            }
        });

        btnphieunhaphuy.setText("hủy");
        btnphieunhaphuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnphieunhaphuyActionPerformed(evt);
            }
        });

        btnphieunhapsua.setText("sửa");
        btnphieunhapsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnphieunhapsuaActionPerformed(evt);
            }
        });

        btnphieunhapxoabang.setText("xóa bảng");
        btnphieunhapxoabang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnphieunhapxoabangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnchitietphieuLayout = new javax.swing.GroupLayout(pnchitietphieu);
        pnchitietphieu.setLayout(pnchitietphieuLayout);
        pnchitietphieuLayout.setHorizontalGroup(
            pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnchitietphieuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnphieunhapluu)
                .addGap(35, 35, 35)
                .addComponent(btnphieunhaphuy)
                .addGap(47, 47, 47))
            .addGroup(pnchitietphieuLayout.createSequentialGroup()
                .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnchitietphieuLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnchitietphieuLayout.createSequentialGroup()
                                .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(btnphieunhapthem))
                                .addGap(18, 18, 18)
                                .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtphieunhapsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnchitietphieuLayout.createSequentialGroup()
                                        .addComponent(btnphieunhapxoa)
                                        .addGap(50, 50, 50)
                                        .addComponent(btnphieunhapsua)
                                        .addGap(57, 57, 57)
                                        .addComponent(btnphieunhapxoabang))
                                    .addComponent(cbsanpham, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel2)
                            .addGroup(pnchitietphieuLayout.createSequentialGroup()
                                .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel3))
                                .addGap(32, 32, 32)
                                .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnchitietphieuLayout.createSequentialGroup()
                                            .addComponent(txtmaphieunhap, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel4)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtdatechooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(cbnhacungcap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(pnchitietphieuLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        pnchitietphieuLayout.setVerticalGroup(
            pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnchitietphieuLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel2)
                .addGap(49, 49, 49)
                .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnchitietphieuLayout.createSequentialGroup()
                        .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtmaphieunhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(27, 27, 27)
                        .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbnhacungcap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtphieunhapsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnphieunhapthem)
                            .addComponent(btnphieunhapxoa)
                            .addComponent(btnphieunhapsua)
                            .addComponent(btnphieunhapxoabang))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(pnchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnphieunhapluu)
                            .addComponent(btnphieunhaphuy))
                        .addGap(22, 22, 22))
                    .addGroup(pnchitietphieuLayout.createSequentialGroup()
                        .addComponent(txtdatechooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout themchitietphieuLayout = new javax.swing.GroupLayout(themchitietphieu.getContentPane());
        themchitietphieu.getContentPane().setLayout(themchitietphieuLayout);
        themchitietphieuLayout.setHorizontalGroup(
            themchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnchitietphieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        themchitietphieuLayout.setVerticalGroup(
            themchitietphieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnchitietphieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnsanpham.setText("sản phẩm");
        btnsanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsanphamActionPerformed(evt);
            }
        });

        btnhoadon.setText("hóa đơn");

        btnphieunhap.setText("phiếu nhập");
        btnphieunhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnphieunhapActionPerformed(evt);
            }
        });

        btnphieuxuat.setText("phiếu xuất");

        btnbaocao.setText("báo cáo");

        btntaikhoan.setText("tài khoản");

        btnnhasanxuat.setText("nhà cung cấp");

        btndonvi.setText("đơn vị vận chuyển");
        btndonvi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndonviActionPerformed(evt);
            }
        });

        btnnhanvien.setText("nhân viên");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnsanpham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnhoadon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnphieuxuat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnphieunhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnbaocao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnnhasanxuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnnhanvien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btndonvi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btntaikhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnsanpham)
                .addGap(18, 18, 18)
                .addComponent(btnhoadon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnphieunhap)
                .addGap(18, 18, 18)
                .addComponent(btnphieuxuat)
                .addGap(18, 18, 18)
                .addComponent(btnbaocao)
                .addGap(18, 18, 18)
                .addComponent(btnnhasanxuat)
                .addGap(18, 18, 18)
                .addComponent(btndonvi)
                .addGap(18, 18, 18)
                .addComponent(btnnhanvien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btntaikhoan)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new java.awt.CardLayout());

        btnthemphieunhap.setText("thêm phiếu nhập hàng");
        btnthemphieunhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemphieunhapActionPerformed(evt);
            }
        });

        btnxemphieunhap.setText("xem phiếu nhập hàng");
        btnxemphieunhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxemphieunhapActionPerformed(evt);
            }
        });

        btnxoaphieunhap.setText("xóa phiếu nhập hàng");
        btnxoaphieunhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaphieunhapActionPerformed(evt);
            }
        });

        tblphieunhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "mã phiếu", "ngày nhập", "nhân viên", "nhà cung cấp", "tổng tiền"
            }
        ));
        tbphieunhap.setViewportView(tblphieunhap);

        javax.swing.GroupLayout panelphieunhapLayout = new javax.swing.GroupLayout(panelphieunhap);
        panelphieunhap.setLayout(panelphieunhapLayout);
        panelphieunhapLayout.setHorizontalGroup(
            panelphieunhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelphieunhapLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelphieunhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbphieunhap)
                    .addGroup(panelphieunhapLayout.createSequentialGroup()
                        .addComponent(btnthemphieunhap)
                        .addGap(27, 27, 27)
                        .addComponent(btnxemphieunhap)
                        .addGap(39, 39, 39)
                        .addComponent(btnxoaphieunhap)
                        .addGap(0, 453, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelphieunhapLayout.setVerticalGroup(
            panelphieunhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelphieunhapLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelphieunhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthemphieunhap)
                    .addComponent(btnxemphieunhap)
                    .addComponent(btnxoaphieunhap))
                .addGap(18, 18, 18)
                .addComponent(tbphieunhap, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelphieunhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelphieunhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.add(jPanel4, "card3");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnphieunhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnphieunhapActionPerformed
        CardLayout cl = (CardLayout)(jPanel3.getLayout());
        cl.show(jPanel3, "PHIEU_NHAP");
    }//GEN-LAST:event_btnphieunhapActionPerformed

    private void btnsanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsanphamActionPerformed

    }//GEN-LAST:event_btnsanphamActionPerformed

    private void btndonviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndonviActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btndonviActionPerformed

    private void btnthemphieunhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemphieunhapActionPerformed
        if (themchitietphieu == null || !themchitietphieu.isDisplayable()) {
            themchitietphieu = new JDialog(this, "Thêm phiếu nhập", true);
            themchitietphieu.getContentPane().add(pnchitietphieu); // panel chứa form chi tiết
            themchitietphieu.pack();
            themchitietphieu.setLocationRelativeTo(this);
            themchitietphieu.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        }
        
        Abled();
        txtmaphieunhap.setText(null);
        DefaultTableModel model = (DefaultTableModel) tbchitietphieunhap.getModel();
        model.setRowCount(0);
        cbnhanvien.setSelectedItem(null);
        cbnhacungcap.setSelectedItem(null);
        cbsanpham.setSelectedItem(null);
        txtphieunhapsoluong.setText(null);
        themchitietphieu.setVisible(true);
    }//GEN-LAST:event_btnthemphieunhapActionPerformed

    private void btnphieunhapluuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnphieunhapluuActionPerformed
        String maphieuText = txtmaphieunhap.getText().trim();
        int maphieu = Integer.parseInt(maphieuText);
        java.util.Date utilDate = txtdatechooser.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        nhanvienComboBox nv= (nhanvienComboBox) cbnhanvien.getSelectedItem();
        int manv = nv.getManv();
        nhacungcapComboBox ncc= (nhacungcapComboBox) cbnhacungcap.getSelectedItem();
        int mancc = ncc.getId();
        double tongtien = tinhTongTienTable();
        chitietphieunhapDAO chitietphieuDAO=new chitietphieunhapDAO(dbconnection.getConnection());
        phieunhapDAO phieunhapDAO= new phieunhapDAO(dbconnection.getConnection());
        try{
            if(chitietphieuDAO.isMaPhieuExit(maphieu)){
                JOptionPane.showMessageDialog(this, "Mã phiếu đã tồn tại trong cơ sở dữ liệu!");
                return;
            }
            else if(!chitietphieuDAO.isMaPhieuExit(maphieu)){
                boolean them=phieunhapDAO.themPhieuNhap(maphieu, sqlDate , manv, mancc, tongtien);
                if(them){
                    JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thành công!");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thất bại!");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DefaultTableModel model = (DefaultTableModel) tbchitietphieunhap.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            int masp = (int) model.getValueAt(i, 1);
            int soluong = (int) model.getValueAt(i, 3);
            double dongia = (double) model.getValueAt(i, 4);
            double thanhtien = (double) model.getValueAt(i, 5);

            try {
                chitietphieuDAO.themChiTietPhieu(maphieu, masp, soluong, dongia, thanhtien);
            } catch (SQLException ex) {
                Logger.getLogger(mainmenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            loadPhieuNhap();
            themchitietphieu.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_btnphieunhapluuActionPerformed

    private void btnphieunhapthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnphieunhapthemActionPerformed
        String maphieuText = txtmaphieunhap.getText().trim();
        String soluongText = txtphieunhapsoluong.getText().trim();
        goiysanpham sp = (goiysanpham) cbsanpham.getSelectedItem();
        
        StringBuilder errors = new StringBuilder();
        if (validate.isEmpty(maphieuText)) {
            errors.append("- Mã phiếu không được để trống.\n");
        } else if (!validate.isInteger(maphieuText)) {
            errors.append("- Mã phiếu phải là số nguyên.\n");
        } else if (!validate.isPositiveInteger(maphieuText)) {
            errors.append("- Mã phiếu phải là số nguyên dương.\n");
        }

        if (validate.isEmpty(soluongText)) {
            errors.append("- Số lượng không được để trống.\n");
        } else if (!validate.isInteger(soluongText)) {
            errors.append("- Số lượng phải là số nguyên.\n");
        } else if (!validate.isPositiveInteger(soluongText)) {
            errors.append("- Số lượng phải là số nguyên dương.\n");
        }

        if (sp == null) {
            errors.append("- Chưa chọn sản phẩm.\n");
        }
        if (errors.length()>0) {
            JOptionPane.showMessageDialog(this, errors.toString(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int maphieu = Integer.parseInt(maphieuText);
        chitietphieunhapDAO chitietphieuDAO=new chitietphieunhapDAO(dbconnection.getConnection());
        try{
            if(chitietphieuDAO.isMaPhieuExit(maphieu)){
                JOptionPane.showMessageDialog(this, "Mã phiếu đã tồn tại trong cơ sở dữ liệu!");
                return;
            }
            else{
                Enabled();
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        int soluong = Integer.parseInt(soluongText);
        int masp = sp.getId();
        String tensp = sp.getName();
        double dongia = sp.getDongia();
        double tong = soluong * dongia;

        DefaultTableModel model=(DefaultTableModel) tbchitietphieunhap.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int maspInTable = (int) model.getValueAt(i, 1);
            if (maspInTable == masp) {
                JOptionPane.showMessageDialog(this, 
                    "Sản phẩm này đã có trong phiếu!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        model.addRow(new Object[]{maphieu, masp, tensp, soluong, dongia, tong});
    }//GEN-LAST:event_btnphieunhapthemActionPerformed

    private void btnphieunhapsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnphieunhapsuaActionPerformed
        Abled();
    }//GEN-LAST:event_btnphieunhapsuaActionPerformed

    private void btnphieunhapxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnphieunhapxoaActionPerformed
        services.removeSelectedRows(tbchitietphieunhap, this);
    }//GEN-LAST:event_btnphieunhapxoaActionPerformed

    private void btnphieunhapxoabangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnphieunhapxoabangActionPerformed
        services.clearTable(tbchitietphieunhap);
    }//GEN-LAST:event_btnphieunhapxoabangActionPerformed

    private void btnphieunhaphuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnphieunhaphuyActionPerformed
        themchitietphieu.dispose();
    }//GEN-LAST:event_btnphieunhaphuyActionPerformed

    private void btnxemphieunhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxemphieunhapActionPerformed
        int row = tblphieunhap.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một phiếu nhập!");
            return;
        }
        // Lấy dữ liệu
        int maPhieu = Integer.parseInt(tblphieunhap.getValueAt(row, 0).toString());
        Object dateObj = tblphieunhap.getValueAt(row, 1);
        String tenNhanVien = tblphieunhap.getValueAt(row, 2).toString();
        String tenNhaCungCap = tblphieunhap.getValueAt(row, 3).toString();
        // Mở dialog
        themchitietphieu.pack();
        themchitietphieu.setLocationRelativeTo(mainPanel);
        themchitietphieu.setVisible(true);
        // Gán mã phiếu
        txtmaphieunhap.setText(String.valueOf(maPhieu));
        txtdatechooser.setDate((java.util.Date) dateObj);
        for (int i = 0; i < cbnhanvien.getItemCount(); i++) {
            nhanvienComboBox nv = (nhanvienComboBox) cbnhanvien.getItemAt(i);
            if (nv.getTennv().equals(tenNhanVien)) {
                cbnhanvien.setSelectedIndex(i);
                break;
            }
        }
        // ---- Gán nhà cung cấp ----
        for (int i = 0; i < cbnhacungcap.getItemCount(); i++) {
            nhacungcapComboBox ncc = (nhacungcapComboBox) cbnhacungcap.getItemAt(i);
            if (ncc.getName().equals(tenNhaCungCap)) {
                cbnhacungcap.setSelectedIndex(i);
                break;
            }
        }
        
        DefaultTableModel model = (DefaultTableModel) tbchitietphieunhap.getModel();
        model.setRowCount(0);
        chitietphieunhapDAO dao= new chitietphieunhapDAO(dbconnection.getConnection());
        try {
            List<chitietphieu> list = dao.getByMaPhieu(maPhieu);
            for (chitietphieu ct : list) {
                model.addRow(new Object[]{
                    ct.getMaphieu(),
                    ct.getMasp(),
                    ct.getTensp(), 
                    ct.getSoluong(),
                    ct.getDongia(),
                    ct.getThanhtien()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết phiếu: " + e.getMessage());
            e.printStackTrace();
        }
        Enabled();
        cbsanpham.setEditable(false);
        txtphieunhapsoluong.setEnabled(false);
        btnphieunhapthem.setEnabled(false);
        btnphieunhapsua.setEnabled(false);
        btnphieunhapxoabang.setEnabled(false);
        btnphieunhapxoa.setEnabled(false);
        btnphieunhapluu.setEnabled(false);
    }//GEN-LAST:event_btnxemphieunhapActionPerformed

    private void btnxoaphieunhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaphieunhapActionPerformed
        int[] selectedRows = tblphieunhap.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn phiếu nào!");
            return;
        }

        List<Integer> listMaPhieu = new ArrayList<>();
        for (int row : selectedRows) {
            int maPhieu = (int) tblphieunhap.getValueAt(row, 0);
            listMaPhieu.add(maPhieu);
        }

        try {
            phieunhapDAO dao = new phieunhapDAO(dbconnection.getConnection());
            boolean ok = dao.xoaNhieuPhieuNhap(listMaPhieu);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                // Load lại bảng phiếu nhập
            } else {
                JOptionPane.showMessageDialog(this, "Có phiếu xóa không thành công!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa phiếu: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            loadPhieuNhap();
        } catch (SQLException ex) {
            Logger.getLogger(mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnxoaphieunhapActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new mainmenu().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(mainmenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbaocao;
    private javax.swing.JButton btndonvi;
    private javax.swing.JButton btnhoadon;
    private javax.swing.JButton btnnhanvien;
    private javax.swing.JButton btnnhasanxuat;
    private javax.swing.JButton btnphieunhap;
    private javax.swing.JButton btnphieunhaphuy;
    private javax.swing.JButton btnphieunhapluu;
    private javax.swing.JButton btnphieunhapsua;
    private javax.swing.JButton btnphieunhapthem;
    private javax.swing.JButton btnphieunhapxoa;
    private javax.swing.JButton btnphieunhapxoabang;
    private javax.swing.JButton btnphieuxuat;
    private javax.swing.JButton btnsanpham;
    private javax.swing.JButton btntaikhoan;
    private javax.swing.JButton btnthemphieunhap;
    private javax.swing.JButton btnxemphieunhap;
    private javax.swing.JButton btnxoaphieunhap;
    private javax.swing.JComboBox<nhacungcapComboBox> cbnhacungcap;
    private javax.swing.JComboBox<nhanvienComboBox> cbnhanvien;
    private javax.swing.JComboBox<goiysanpham> cbsanpham;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelphieunhap;
    private javax.swing.JPanel pnchitietphieu;
    private javax.swing.JTable tbchitietphieunhap;
    private javax.swing.JTable tblphieunhap;
    private javax.swing.JScrollPane tbphieunhap;
    private javax.swing.JDialog themchitietphieu;
    private com.toedter.calendar.JDateChooser txtdatechooser;
    private javax.swing.JTextField txtmaphieunhap;
    private javax.swing.JTextField txtphieunhapsoluong;
    // End of variables declaration//GEN-END:variables

    private boolean validate(String maphieu) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
