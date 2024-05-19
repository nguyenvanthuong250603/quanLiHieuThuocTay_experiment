package experiment_UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.beancontext.BeanContext;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.record.HideObjRecord;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.Thuoc_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thuoc;
import experiment_UI.Generate_All.CustomTableCellRenderer;

import static experiment_UI.Generate_All.*;

public class BanThuocKeLaiDon_UI {
	private Object[][] object_custommer;
	private JTextField jtextMaKH;
	private JCheckBox cb;
	private JPanel sell;
	private Object[][] object_sell;
	private JButton btnTimKH;
	private JComboBox<String> optionGender;
	private JTextField textFind;
	private Object[][] object_find;
	private DefaultTableModel model;
	private JTable table;
	private DefaultTableModel model_product;
	private JTable table_product;
	private String maNhanVien;
	private JLabel labelTotal;
	private HoaDon_DAO hoaDon_DAO = new HoaDon_DAO();
	private NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
	private KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
	private ChiTietHoaDon_DAO chiTietHoaDon_DAO = new ChiTietHoaDon_DAO();
	private Thuoc_DAO thuoc_DAO = new Thuoc_DAO();
	private TimKhach_UI timKhach = new TimKhach_UI();
	private double result;
	private TimThuoc_UI timThuoc = new TimThuoc_UI();

	public JPanel getBanThuocKeLaiDon(String maNV) {
		JPanel keLaiDon = new JPanel(new BorderLayout());
		createTiTlePage(keLaiDon, "BÁN THUỐC THEO ĐƠN KÊ LẠI");
		keLaiDon.add(table_Exchange(), BorderLayout.CENTER);
		keLaiDon.add(getEast(), BorderLayout.EAST);
		this.maNhanVien = maNV;
		hidden(false);
		handleActionCheckbox();

		ArrayList<HoaDon> hDons = hoaDon_DAO.getHoaDons();
		hienBangDuLieu(hDons);
		enterTimKhach();
		setDefaultText(maNhanVien);
		suKienTable();
		return keLaiDon;
	}

	private void handleActionCheckbox() {
		cb.addActionListener(e -> {

			if (cb.isSelected()) {
				hidden(true);
			} else {

				hidden(false);
			}
		});

	}

	public void hidden(boolean value) {
		cb.setSelected(value);
		btnTimKH.setEnabled(value);
		jtextMaKH.setEditable(value);

		for (int i = 0; i < object_custommer.length; i++) {
			if (object_custommer[i][1] instanceof JTextField) {

				((JTextField) object_custommer[i][1]).setEditable(value);
			} else {

				((JComboBox) object_custommer[i][1]).setEnabled(value);
			}

		}

	}

	public JPanel getEast() {
		JPanel center = new JPanel(new BorderLayout());
		center.add(inf_custommer(), BorderLayout.NORTH);
		center.add(inf_sell(), BorderLayout.CENTER);
		center.add(footer_East(), BorderLayout.SOUTH);
		return center;
	}

	public JPanel inf_custommer() {
		JPanel inf = new JPanel(new BorderLayout());
//	west
		createTitle(inf, "Thông tin khách hàng ");
		JPanel check = new JPanel();
		check.setLayout(new BoxLayout(check, BoxLayout.X_AXIS));
		cb = new JCheckBox();
		cb.setBorder(new EmptyBorder(0, 0, 0, 10));
		check.add(cb);
		check.setBorder(new EmptyBorder(0, 30, 0, 0));
		check.add(sampleModel2("Lưu thông tin khách hàng"));

//	center		
		JPanel input = new JPanel();
		input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
		JPanel t = new JPanel(new BorderLayout());
		t.setBorder(new EmptyBorder(0, 30, 0, 30));
		t.add(sampleModel2("Số Điện Thoại"), BorderLayout.WEST);
		t.add(jtextMaKH = new JTextField(15), BorderLayout.CENTER);
		btnTimKH = createButtonBanThuocKeLaiDon("", "gift\\add.png");
		btnTimKH.setPreferredSize(new Dimension(80, 30));
		t.add(btnTimKH, BorderLayout.AFTER_LINE_ENDS);
		input.add(t);

		String[] gt = { "Nam", "Nữ" };
		Object[][] trage = { { "Mã Khách Hàng", new JTextField() }, { "Tên KH", new JTextField() },
				{ "Tuổi", new JTextField() }, { "Giới tính", optionGender = new JComboBox(gt) },
				{ "Xếp hạng", new JTextField() } };
		object_custommer = trage;
		for (Object[] objects : object_custommer) {
			if (objects[1] instanceof JTextField) {

				input.add(createNameAndTextField2(((JTextField) objects[1]), objects[0].toString()));
			} else {
				input.add(createJcombobox2(objects[0].toString(), (JComboBox) objects[1]));

			}
		}
		inf.add(check, BorderLayout.NORTH);
		inf.add(input, BorderLayout.CENTER);

		return inf;

	}

	public JPanel inf_sell() {
		JPanel sell = new JPanel();
		createTitle(sell, "Thông tin hóa đơn");
		Box box = Box.createVerticalBox();

//		sell.setLayout(new BoxLayout(sell, BoxLayout.Y_AXIS));
		String[] pttt = { "Tiền mặt", "Chuyển khoản", "Quẹt thẻ" };

		Object[][] trage = { { "Mã HĐ", new JLabel() }, { "Ngày tạo", new JTextField() },
				{ "Khách cần trả", new JTextField(25) }, { "Khấu trừ", new JLabel() }, { "PTTT", new JComboBox(pttt) },
				{ "Khách đưa", new JTextField() } };

		object_sell = trage;
		for (Object[] objects : object_sell) {
			if (objects[1] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel2(objects[0].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(5, 20, 5, 0));
				t.add((Component) objects[1], BorderLayout.CENTER);
				box.add(t);

			} else {
				box.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));

			}
		}

		labelTotal = sampleModel("SỐ TIỀN TRẢ LẠI : ");
		labelTotal.setPreferredSize(new Dimension(200, 50));
		labelTotal.setFont(new Font("Arial", Font.ITALIC, 18));
		labelTotal.setForeground(Color.RED);
		labelTotal.setBorder(new EmptyBorder(20, 0, 0, 20));
		box.add(labelTotal);
		sell.add(box);
		return sell;
	}

	public JPanel table_Exchange() {
		JPanel Jpanel_table = new JPanel(new BorderLayout());
		Jpanel_table.add(findID(), BorderLayout.NORTH);
		Jpanel_table.add(table_information(), BorderLayout.CENTER);
		Jpanel_table.add(footer_Exchange(), BorderLayout.SOUTH);
		return Jpanel_table;
	}

	public JPanel findID() {
		JPanel find = new JPanel();
		createTitle(find, "Tìm kiếm hóa đơn");
		find.setLayout(new GridLayout(2, 1, 5, 5));

		JPanel jpanelTim = new JPanel();
		jpanelTim.setLayout(new BorderLayout());
		textFind = new JTextField();
		jpanelTim.add(textFind, BorderLayout.CENTER);

		jpanelTim.add(createButtonBanThuocKeLaiDon("Tìm", ""), BorderLayout.EAST);

		JPanel jpanelLoc = new JPanel(new BorderLayout());
		JPanel jpanelLocChinhSize = new JPanel();
		jpanelLocChinhSize.setLayout(new BoxLayout(jpanelLocChinhSize, BoxLayout.X_AXIS));
		String[] optionHoaDon = { "", "Hóa đơn bán hàng", "Hóa đơn bán lẻ" };
		Object[][] trage = { { "Số điện thoại", new JTextField(10) },
				{ "Loại hóa đơn", new JComboBox<String>(optionHoaDon) } };
		object_find = trage;
		for (Object[] objects : trage) {
			if (objects[1] instanceof JTextField) {
				jpanelLocChinhSize.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));
			} else {
				jpanelLocChinhSize.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));
			}
			jpanelLocChinhSize.add(Box.createHorizontalStrut(5));
		}

		jpanelLoc.add(jpanelLocChinhSize, BorderLayout.CENTER);
		jpanelLoc.add(createButtonBanThuocKeLaiDon("Lọc", ""), BorderLayout.EAST);

		find.add(jpanelTim);
		find.add(jpanelLoc);

		return find;
	}

	public JPanel table_information() {
		JPanel managerment = new JPanel();
		managerment.setLayout(new GridLayout(2, 1));
		JPanel container = new JPanel(new BorderLayout());
		createTitle(container, "Danh sách hóa đơn");
		String[] column = { "Mã Hóa Đơn", "Nhân viên", "Khách hàng", "Ngày mua", "Tổng tiền" };

		model = new DefaultTableModel(column, 0);
		table = new JTable(model);
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table);
		container.add(scoll);

		JPanel container2 = new JPanel(new BorderLayout());
		String[] column2 = { "Mã Thuốc", "Tên thuốc", "Đơn vị", "Số lượng", "Giá", "Thành tiền" };
		createTitle(container2, "Danh sách sản phẩm");
		model_product = new DefaultTableModel(column2, 0);
		table_product = new JTable(model_product);
		table_product.setShowGrid(false);
		table_product.setShowVerticalLines(false);
		table_product.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll2 = new JScrollPane(table_product);
		container2.add(scoll2);

		managerment.add(container);
		managerment.add(container2);
		return managerment;
	}

	public JPanel footer_Exchange() {

		JPanel footer = new JPanel();
		JButton btn = null;
		String[] object = {  "Xóa Thuốc", "gift\\trash-bin.png", "Thêm thuốc", "gift\\them.png" };
		for (int i = 0; i < object.length; i += 2) {
			btn = createButtonBanThuocKeLaiDon(object[i], object[i + 1]);
			footer.add(btn);
		}

		return footer;

	}

	public JPanel footer_East() {
		JPanel footer = new JPanel();
		JButton btn = null;
		String[] object = {"Xóa trắng", "gift\\reset.png", "Tạo hóa đơn", "gift\\thanhtoan.png" };
		for (int i = 0; i < object.length; i += 2) {
			btn = createButtonBanThuocKeLaiDon(object[i], object[i + 1]);
			
			footer.add(btn);
		}

		return footer;
	}

	public void hienBangDuLieu(ArrayList<HoaDon> hDons) {

		for (HoaDon hoaDon : hDons) {
			if (!hoaDon.getTinhTrang().equals("")) {
				NhanVien nv = getNV(hoaDon.getMaNV().getMaNV());
				Object[] row = { hoaDon.getMaHD(), nv.getHoTen(), hoaDon.getMaKh().getMaKH(),
						formatTime(hoaDon.getNgayTaoHoaDon()), hoaDon.getTongTien() };
				model.addRow(row);

			}
		}

		table.setModel(model);
		if (table.getRowCount() > 0) {
			table.setRowSelectionInterval(0, 0);
			String maKH = table.getValueAt(table.getSelectedRow(), 2) == null ? ""
					: table.getValueAt(table.getSelectedRow(), 2).toString();
			
			if(!maKH.equals("")) {
				cb.setSelected(true);
				hidden(true);
			}
			String maHD = table.getValueAt(table.getSelectedRow(), 0).toString();
			((JTextField) object_sell[2][1]).setText(table.getValueAt(0, 4).toString());
			defaultMouse(model_product, table_product, maHD, maKH, table);
		}
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				model_product.setRowCount(0);
				int index = table.getSelectedRow();
				((JTextField) object_sell[2][1]).setText(table.getValueAt(index, 4).toString());
				String maKH = table.getValueAt(index, 2) == null ? "" : table.getValueAt(index, 2).toString();
				String maHD = table.getValueAt(index, 0).toString();
				defaultMouse(model_product, table_product, maHD, maKH, table);
				if(!maKH.equals("")) {
					cb.setSelected(true);
					hidden(true);
				}

			}
		});

	}

	private void defaultMouse(DefaultTableModel model_product, JTable table_product, String maHD, String maKH,
			JTable table) {

		ArrayList<ChiTietHoaDon> lChiTietHoaDons = chiTietHoaDon_DAO.getcChiTietHoaDons(maHD);

		for (ChiTietHoaDon ct : lChiTietHoaDons) {
			Thuoc th = thuoc_DAO.getThuocByID(ct.getMaThuoc().getMaThuoc());
			Object[] row_product = { ct.getMaThuoc().getMaThuoc(), th.getTenThuoc(), th.getDonVi(),
					ct.getSoLuongThuoc(), th.getGia(), ct.getThanhTien() };
			model_product.addRow(row_product);

		}
		table_product.setModel(model_product);

	}

	public void timHoaDon() {
		String ma = textFind.getText();
		HoaDon hd = hoaDon_DAO.getHoaDonByID(ma);
		model.setRowCount(0);
		model_product.setRowCount(0);
		if (hd != null) {
			NhanVien nv = getNV(hd.getMaNV().getMaNV());
			Object[] row = { hd.getMaHD(), nv.getHoTen(), hd.getMaKh().getMaKH(), formatTime(hd.getNgayTaoHoaDon()),
					hd.getTongTien() };
			model.addRow(row);
			table.setModel(model);

			ArrayList<ChiTietHoaDon> lChiTietHoaDons = hd.getListChiTietHoaDon();
			for (ChiTietHoaDon ct : lChiTietHoaDons) {
				Thuoc th = thuoc_DAO.getThuocByID(ct.getMaThuoc().getMaThuoc());
				Object[] row_product = { ct.getMaThuoc().getMaThuoc(), th.getTenThuoc(), th.getDonVi(),
						ct.getSoLuongThuoc(), th.getGia(), ct.getThanhTien() };
				model_product.addRow(row_product);

			}
			table_product.setModel(model_product);

			((JTextField) object_sell[2][1]).setText(hd.getTongTien() + "");
			table.setRowSelectionInterval(0, 0);
			if (hd.getMaKh().getMaKH() != null) {
				hidden(true);
				cb.setSelected(true);
				KhachHang kh = getKH(hd.getMaKh().getMaKH(), "");

				String xh = kh.getXepHang();
				if (xh.equals("Đồng")) {
					((JLabel) object_sell[3][1]).setText("0%");
				} else if (xh.equals("Bạc")) {
					((JLabel) object_sell[3][1]).setText("1%");
				} else if (xh.equals("Vàng")) {
					((JLabel) object_sell[3][1]).setText("2%");
				} else if (xh.equals("Bạch kim")) {
					((JLabel) object_sell[3][1]).setText("3%");
				} else if (xh.equals("Kim cương")) {
					((JLabel) object_sell[3][1]).setText("4.5%");
				}

				((JTextField) object_custommer[0][1]).setText(kh.getMaKH());
				((JTextField) object_custommer[1][1]).setText(kh.getTenKH());
				jtextMaKH.setText(kh.getsDT());
				((JTextField) object_custommer[2][1]).setText(kh.getTuoi() + "");
				((JComboBox) object_custommer[3][1]).setSelectedItem(transGender(kh.isGioiTinh()));
				((JTextField) object_custommer[4][1]).setText(kh.getXepHang());

			}
		} else {
			JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn trong hệ thống");
			ArrayList<HoaDon> hDons = hoaDon_DAO.getHoaDons();
			hienBangDuLieu(hDons);
		}

	}

	public void timHoaDonTheoSoDT() {
		if (regexLoc()) {

			String sdt = getValueStringInJTextField(object_find[0][1]);
			String optionHd = getValueInComboBox((JComboBox) object_find[1][1]);
			int lhd;
			if (!optionHd.equals("")) {

				lhd = optionHd.equals("Hóa đơn bán hàng") ? 1 : 0;

			} else {

				lhd = 4;
			}
			model.setRowCount(0);
			model_product.setRowCount(0);

			ArrayList<HoaDon> list_hd = hoaDon_DAO.getHoaDonToLuuTam(sdt, lhd);
			hienBangDuLieu(list_hd);

		}

	}

	public boolean regexLoc() {
		String sdt = ((JTextField) object_find[0][1]).getText();
		String loaiHd = getValueInComboBox((JComboBox) object_find[1][1]);

		if (!sdt.equals("") && !loaiHd.equals("")) {
			if (!sdt.matches("0\\d{9}")) {

				if (sdt.equals("")) {
					JOptionPane.showMessageDialog(null, "Bạn chưa nhập số điện thoại của khách hàng");
					((JTextField) object_find[0][1]).requestFocus();
					return false;
				} else {
					JOptionPane.showMessageDialog(null, "Nhập sai định dạng số điện thoại");
					((JTextField) object_find[0][1]).requestFocus();
					return false;
				}

			}
		} else if (sdt.equals("") && loaiHd.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa số điện thoại và loại hóa đơn của khách hàng");
			((JTextField) object_find[0][1]).requestFocus();
			return false;
		}
		return true;
	}

	public void xoaTrang() {
		setDefaultText(maNhanVien);
		hidden(false);
		((JTextField) object_custommer[0][1]).setText("");
		((JTextField) object_custommer[1][1]).setText("");
		((JComboBox) object_custommer[3][1]).setSelectedIndex(0);
		((JTextField) object_custommer[2][1]).setText("");
		((JTextField) object_custommer[4][1]).setText("");
		jtextMaKH.setText("");

		cb.setSelected(false);

		((JTextField) object_sell[2][1]).setText("");
		((JLabel) object_sell[3][1]).setText("");
		((JComboBox) object_sell[4][1]).setSelectedIndex(0);
		((JTextField) object_sell[5][1]).setText("");

		labelTotal.setText("SỐ TIỀN TRẢ LẠI : ");
		textFind.setText("");
		model.setRowCount(0);
		ArrayList<HoaDon> hDons = hoaDon_DAO.getHoaDons();
		model_product.setRowCount(0);
		hienBangDuLieu(hDons);
	}

	public void timTheoSdt() {
		String sdt = jtextMaKH.getText();

		if (sdt.matches("0\\d+{9}")) {
			KhachHang kh = khachHang_DAO.getKhachHangByID("", sdt);

			if (kh.getMaKH() != null) {
				((JTextField) object_custommer[0][1]).setText(kh.getMaKH());
				((JTextField) object_custommer[1][1]).setText(kh.getTenKH());
				((JTextField) object_custommer[2][1]).setText(kh.getTuoi() + "");
				((JComboBox) object_custommer[3][1]).setSelectedItem(transGender(kh.isGioiTinh()));
				((JTextField) object_custommer[4][1]).setText(kh.getXepHang());
				String xh = kh.getXepHang();

				if (xh.equals("Đồng")) {
					((JLabel) object_sell[3][1]).setText("0%");
				} else if (xh.equals("Bạc")) {
					((JLabel) object_sell[3][1]).setText("1%");
				} else if (xh.equals("Vàng")) {
					((JLabel) object_sell[3][1]).setText("2%");
				} else if (xh.equals("Bạch kim")) {
					((JLabel) object_sell[3][1]).setText("3%");
				} else if (xh.equals("Kim cương")) {
					((JLabel) object_sell[3][1]).setText("4.5%");
				}

			} else {
				int value = JOptionPane.showConfirmDialog(null,
						"Khách hàng chưa có trong hệ thống , bạn có muốn thêm khách", "Lưu ý",
						JOptionPane.YES_NO_OPTION);
				if (value == JOptionPane.YES_OPTION) {
					timKhach.getTimKhach(jtextMaKH, (JTextField) object_custommer[0][1],
							(JTextField) object_custommer[1][1], (JTextField) object_custommer[2][1],
							(JComboBox) object_custommer[3][1], (JTextField) object_custommer[4][1],
							(JLabel) object_sell[3][1], jtextMaKH);

				}
			}
		} else {
			xoaTrang();
			JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ , số phải có 10 số");
		}

	}

	public void enterTimKhach() {
		ActionListener enter3 = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					timTheoSdt();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		};
		jtextMaKH.addActionListener(enter3);

		ActionListener enter2 = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					caculateResult();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		};
		((JTextField) object_sell[2][1]).addActionListener(enter2);
		((JTextField) object_sell[5][1]).addActionListener(enter2);
		FocusListener calculation = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				try {
					caculateResult();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		};

		((JTextField) object_sell[2][1]).addFocusListener(calculation);
		((JTextField) object_sell[5][1]).addFocusListener(calculation);
	}

	private void caculateResult() {
		String getValue1 = ((JTextField) object_sell[2][1]).getText();
		String getValue2 = ((JTextField) object_sell[5][1]).getText();
		if (!getValue1.isEmpty() && !getValue2.isEmpty()) {
			double value1 = Double.parseDouble(getValue1);
			double value2 = Double.parseDouble(getValue2);
			double giampt = 0;
			String giam = ((JLabel) object_sell[3][1]).getText();
			if (giam.equals("0%")) {
				giampt = 0;
			} else if (giam.equals("1%")) {
				giampt = 1;
			} else if (giam.equals("2%")) {
				giampt = 2;
			} else if (giam.equals("3%")) {
				giampt = 3;
			} else if (giam.equals("4.5%")) {
				giampt = 4.5;
			}
			double truTien = (value1 / 100) * giampt;
			result = value2 - value1 + truTien;
			labelTotal.setText("SỐ TIỀN TRẢ LẠI : " + formatValueDouble(result) + "VND");
		}
	}

	public void setDefaultText(String ma) {

		((JLabel) object_sell[0][1]).setText(generateCustomerCode(ma));
		((JLabel) object_sell[0][1]).setFont(new Font("Arial", Font.BOLD, 15));
		((JTextField) object_sell[1][1]).setText(formatTime(LocalDate.now()));

		((JTextField) object_sell[1][1]).setFont(new Font("Arial", Font.BOLD, 15));

	}

	public void xoaThuoc() {
		int index = table_product.getSelectedRow();
		if (index > 0) {
			int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa thuốc khỏi đơn thuốc đang tạo",
					"Lưu ý", JOptionPane.YES_NO_OPTION);
			if (recomment == JOptionPane.YES_OPTION) {
				model_product.removeRow(index);
				((JTextField) object_sell[2][1]).setText(tinhLaiTien() + "");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn thuốc để xóa khỏi hóa đơn");
		}
	}

	private void suKienTable() {
		model_product.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int row = e.getFirstRow();
					int column = e.getColumn();
					if (column == 3) {
						Thuoc th = thuoc_DAO
								.getThuocByID(table_product.getValueAt(table_product.getSelectedRow(), 0).toString());
						int newQuantity = Integer.parseInt(model_product.getValueAt(row, column).toString());
						model_product.setValueAt(th.getGia() * newQuantity, row, 5);
						((JTextField) object_sell[2][1]).setText(tinhLaiTien() + "");
					}
				}

			}
		});
	}

	private double tinhLaiTien() {
		double result = 0;
		for (int i = 0; i < table_product.getRowCount(); i++) {

			result += Double.parseDouble(table_product.getValueAt(i, 5).toString());
		}
		return result;
	}



	public HoaDon getHoaDon() {
		ArrayList<ChiTietHoaDon> listCtHD = new ArrayList<ChiTietHoaDon>();

		for (int i = 0; i < table_product.getRowCount(); i++) {
			HoaDon hd = new HoaDon(getValueStringInJlabel(object_sell[0][1]));
			Thuoc th = new Thuoc(table_product.getValueAt(i, 0).toString());
			int soLuong = Integer.parseInt(table_product.getValueAt(i, 3).toString());

			double thanhTien = Double.parseDouble(table_product.getValueAt(i, 5).toString());
			ChiTietHoaDon ct = new ChiTietHoaDon(hd, th, soLuong, thanhTien);

			listCtHD.add(ct);
		}

		String maHD = getValueStringInJlabel(object_sell[0][1]);
		NhanVien nv = new NhanVien(maNhanVien);

		String tenKh = getValueStringInJTextField(object_custommer[1][1]);
		String hinhThucThanhToan = ((JComboBox) object_sell[4][1]).getSelectedItem().toString();
		Boolean loaiHoaDon;

		loaiHoaDon = cb.isSelected() ? true : false;

		String tinhTrang;
		KhachHang kh = new KhachHang(getValueStringInJTextField(object_custommer[0][1]));

		if (loaiHoaDon == true) {
			kh = new KhachHang(getValueStringInJTextField(object_custommer[0][1]));
		} else {
			kh = new KhachHang("");
		}
		tinhTrang = "Bán ra";

		double tongTien = Double.parseDouble(getValueStringInJTextField(object_sell[2][1]));
		HoaDon hd = new HoaDon(maHD, nv, kh, tenKh, hinhThucThanhToan, LocalDate.now(), loaiHoaDon, tinhTrang, tongTien,
				"", listCtHD);
		return hd;
	}

	public void thayDoiSoLuongThuoc() {
		for (int i = 0; i < table_product.getRowCount(); i++) {
			String maThuoc = table_product.getValueAt(i, 0).toString();
			int soLuong = Integer.parseInt(table_product.getValueAt(i, 3).toString());
			Thuoc th = thuoc_DAO.getThuocByID(maThuoc);

			soLuong = th.getSoLuong() - soLuong;
			thuoc_DAO.suaThuoc(new Thuoc(), soLuong, maThuoc);
		}
	}

	public void thayDoiDiemXepHang() {
		String maKh = jtextMaKH.getText();
		int dtl = (int) tinhLaiTien() / 1000;
		KhachHang kh = khachHang_DAO.getKhachHangByID(maKh, "");
		int tinhDiem = kh.getDiemThanhVien() + dtl;
		String xepHang;
		if (tinhDiem <= 500) {
			xepHang = "Đồng";
		} else if (tinhDiem > 500 && tinhDiem <= 2000) {
			xepHang = "Bạc";
		} else if (tinhDiem > 2000 && tinhDiem <= 6000) {
			xepHang = "Vàng";
		} else if (tinhDiem > 6000 && tinhDiem <= 10000) {
			xepHang = "Bạch kim";
		} else {
			xepHang = "Kim cương";
		}
		if (khachHang_DAO.updateKhachHangXepHang(tinhDiem, xepHang, maKh)) {

		} else {
			System.out.println("sai");
		}
	}

	public void taoHoaDon() {
		HoaDon hd = getHoaDon();
		double khachDua = Double.parseDouble(((JTextField) object_sell[5][1]).getText());
		if (hoaDon_DAO.themHoaDon(hd)) {
			
			if (!cb.isSelected()&&hd.getMaKh().getMaKH().equals("")) {
				JOptionPane.showMessageDialog(null, "Tạo hóa đơn thành công");
				generateInvoiceBanLe(hd, hd.getTongTien(), khachDua, result);
			}else {
				if(cb.isSelected()&&!jtextMaKH.getText().equals("")) {
				String khuyenMai = ((JLabel) object_sell[3][1]).getText();
				JOptionPane.showMessageDialog(null, "Tạo hóa đơn thành công");
				generateInvoice(hd, hd.getTongTien() ,khachDua,khuyenMai,result);
				}
				else {
					JOptionPane.showMessageDialog(null, "Bạn chưa nhập thông tin khách hàng");
				}
			}
			thayDoiDiemXepHang();
			thayDoiSoLuongThuoc();
			xoaTrang();
		} else {
			System.out.println("Loi");
		}
	}
	private boolean regexInHoaDon() {
		String tienDua = ((JTextField) object_sell[5][1]).getText();
	
	 if (!tienDua.matches("\\d+")) {
			if (tienDua.equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn chưa nhập tiền khách đưa");
				((JTextField) object_sell[5][1]).requestFocus();
				return false;
			} else {
				JOptionPane.showMessageDialog(null, "Tiền phải được nhập là chữ số");
				((JTextField) object_sell[5][1]).requestFocus();
				return false;
			}

		}

		Double tienKhachPhaiTra = Double.parseDouble(((JTextField) object_sell[2][1]).getText());
		if (Double.parseDouble(tienDua) < tienKhachPhaiTra) {
			JOptionPane.showMessageDialog(null, "Tiền khách trả phải lớn hơn tiền khách phải trả");
			((JTextField) object_sell[5][1]).requestFocus();
			return false;
		}

		return true;
	}
	private JButton createButtonBanThuocKeLaiDon(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(180, 40));
		btn.addActionListener(e -> {
			if (nameButton.equals("Xóa trắng") ) {
				xoaTrang();
			} else if (nameButton.equals("Tìm")) {
				timHoaDon();
			} else if (nameButton.equals("Lọc")) {
				timHoaDonTheoSoDT();
			} else if (nameButton.equals("") && pathIcon.equals("gift\\add.png")) {
				timKhach.getTimKhach(jtextMaKH, (JTextField) object_custommer[0][1],
						(JTextField) object_custommer[1][1], (JTextField) object_custommer[2][1],
						(JComboBox) object_custommer[3][1], (JTextField) object_custommer[4][1],
						(JLabel) object_sell[3][1], jtextMaKH);

			} else if (nameButton.equals("Thêm thuốc")) {
				if (table_product.getRowCount() > 0) {
					timThuoc.getTimThuoc(model_product, table_product);

				} else {
					JOptionPane.showMessageDialog(null, "Bạn phải chọn hóa đơn để thực hiện chức năng");
				}
			} else if (nameButton.equals("Xóa Thuốc")) {
				xoaThuoc();
			} else if (nameButton.equals("Tạo hóa đơn")) {
				if(regexInHoaDon())
					taoHoaDon();
			} else {
				System.out.println(nameButton);
			}
		});
		return btn;
	}
}
