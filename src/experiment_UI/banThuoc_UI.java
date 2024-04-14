package experiment_UI;

import javax.print.attribute.PrintJobAttribute;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.FormSubmitEvent;

import org.apache.commons.math3.stat.descriptive.moment.Skewness;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.itextpdf.text.xml.simpleparser.NewLineHandler;
import com.toedter.calendar.JDateChooser;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.text.Document;

import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.Thuoc_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thuoc;
import experiment_UI.Generate_All.CustomTableCellRenderer;

import static experiment_UI.Generate_All.*;

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

import java.time.LocalDate;
import java.util.ArrayList;

public class BanThuoc_UI {

	private JCheckBox cb;
	private Object[][] object_custommer, object_sell;
	private JComboBox optionGender, optionPTTT, optionDoTuoi;
	private JLabel labelTotal;
	private JButton btnTimKH;
	private JTextField jtextMaKH;

	private JTextField textMaThuocFind = new JTextField();
	private DefaultTableModel model;
	private JTable table;
	private Thuoc_DAO th_DAO = new Thuoc_DAO();
	private TimKhach_UI timKhach = new TimKhach_UI();
	private int giatru = 0;

	private JPanel sell, find;
	private String maThuoc;
	private String maNhanVien;
	private double result;
	private HoaDonLuuTam_UI hoaDonLuuTam = new HoaDonLuuTam_UI();
	private ArrayList<HoaDon> listHD = new ArrayList<HoaDon>();
	public HoaDon_DAO hDao = new HoaDon_DAO();
	private Thuoc_DAO thuoc_DAO = new Thuoc_DAO();
	private KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
	private HoaDonBanThuoc_UI hoaDonBanThuoc_UI = new HoaDonBanThuoc_UI();

	public JPanel getBanThuoc(String maNV) {
		JPanel sellTotal = new JPanel(new BorderLayout());
		JPanel sellManagement = new JPanel(new BorderLayout());

		sellManagement.add(inputSell(), BorderLayout.NORTH);
		sellManagement.add(table_Drug(), BorderLayout.CENTER);
		sellManagement.add(footer_sell(), BorderLayout.SOUTH);
		maNhanVien = maNV;

		enterListen();

		hidden(false);
		handleActionCheckbox();
		setDefaultText(maNhanVien);

		sellTotal.add(sellManagement, BorderLayout.CENTER);
		createTiTlePage(sellTotal, "QUẢN LÍ BÁN THUỐC");
		return sellTotal;
	}

	public void hidden(boolean value) {
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

	public JPanel table_Drug() {
		JPanel Jpanel_table = new JPanel(new BorderLayout());
		createTitle(Jpanel_table, "Thông tin bán thuốc");
		Jpanel_table.add(findID(), BorderLayout.NORTH);
		Jpanel_table.add(table_information(), BorderLayout.CENTER);
		Jpanel_table.add(totalMoney(), BorderLayout.SOUTH);
		return Jpanel_table;
	}

	public JPanel findID() {
		find = new JPanel();
		find.setLayout(new BorderLayout());

		find.add(textMaThuocFind, BorderLayout.CENTER);
		find.setBorder(new EmptyBorder(5, 5, 5, 0));
		JButton timkiemMaThuoc = buttonInPageSell("Tìm kiếm", "");
		find.add(timkiemMaThuoc, BorderLayout.EAST);
		return find;
	}

	public JPanel table_information() {
		JPanel managerment = new JPanel();
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã thuốc", "Tên thuốc ", "Đơn vị", "Số lượng", "Giá", "Thành tiền" };

		model = new DefaultTableModel(column, 0);
		table = new JTable(model);
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table);
		managerment.add(scoll, BorderLayout.CENTER);
		return managerment;
	}

	public JPanel inputSell() {
		sell = new JPanel(new GridLayout(1, 2));
//		Box boxx = Box.createVerticalBox();
//		sell.setLayout(new BoxLayout(sell, BoxLayout.Y_AXIS));
		sell.add(inf_custommer());
		sell.add(inf_sell());

//		sell.add(boxx);
		return sell;
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
		t.add(sampleModel2("Mã KH"), BorderLayout.WEST);
		t.add(jtextMaKH = new JTextField(15), BorderLayout.CENTER);
		btnTimKH = buttonInPageSell("Tìm", "");
		btnTimKH.setPreferredSize(new Dimension(80, 30));
		t.add(btnTimKH, BorderLayout.AFTER_LINE_ENDS);
		input.add(t);

		String[] gt = { "Nam", "Nữ" };
		Object[][] trage = { { "Tên KH", new JTextField() }, { "Tuổi", new JTextField() },
				{ "Giới tính", optionGender = new JComboBox(gt) }, { "SDT", new JTextField() },
				{ "Xếp hạng", new JTextField() } };
		object_custommer = trage;
		for (Object[] objects : object_custommer) {
			if (objects[1] instanceof JTextField) {

				input.add(createNameAndTextField2(((JTextField) objects[1]), objects[0].toString()));
			} else {
				input.add(createJcombobox2(objects[0].toString(), (JComboBox) objects[1]));

			}
		}
//		JPanel DTL = new JPanel();
//		DTL.setLayout(new BoxLayout(DTL, BoxLayout.X_AXIS));
//		cbDTL = new JCheckBox();
//		cbDTL.setBorder(new EmptyBorder(0, 0, 0, 10));
//		DTL.add(cbDTL);
//		DTL.setBorder(new EmptyBorder(0, 30, 0, 0));
//		DTL.add(sampleModel2("Điểm tích lũy"));
//		labelDTL.setBorder(new EmptyBorder(0, 40, 0, 0));
//		labelDTL.setFont(new Font("Arial", Font.ITALIC, 18));
//		labelDTL.setForeground(Color.RED);
//		DTL.add(labelDTL);

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
				{ "Khách cần trả", new JTextField(37) }, { "Khấu trừ", new JLabel() }, { "PTTT", new JComboBox(pttt) },
				{ "Khách đưa", new JTextField() }, };

		object_sell = trage;
		for (Object[] objects : object_sell) {
			if (objects[1] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel2(objects[0].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(5, 0, 5, 0));
				t.add((Component) objects[1], BorderLayout.CENTER);
				box.add(t);

			} else {
				box.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));

			}
		}

		sell.add(box);
		return sell;
	}

	public JPanel totalMoney() {
		JPanel money = new JPanel(new BorderLayout());
		labelTotal = sampleModel("SỐ TIỀN TRẢ LẠI : ");
		labelTotal.setPreferredSize(new Dimension(320, 30));
		labelTotal.setFont(new Font("Arial", Font.ITALIC, 18));
		labelTotal.setForeground(Color.RED);

		money.add(labelTotal, BorderLayout.EAST);

		return money;
	}

	public JPanel footer_sell() {

		JPanel footer = new JPanel();
//		footer.setBorder(new EmptyBorder(10, 0, 10, 10));
		JButton btn = null;
		String[][] object = { { "", "gift\\reset.png" }, { "Xóa Thuốc", "gift\\trash-bin.png" },
				{ "Lưu tạm", "gift\\trash-bin.png" }, { "Xử lí hóa đơn tạm", "gift\\excel-file.png" },
				{ "Thanh toán", "gift\\excel-file.png" } };
		for (String[] strings : object) {
			btn = buttonInPageSell(strings[0].toString(), strings[1].toString());
			footer.add(btn);
		}
		btn.setPreferredSize(new Dimension(180, 40));
		return footer;

	}

	private void enterListen() {

		ActionListener enter = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					timThuoc();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		};
		textMaThuocFind.addActionListener(enter);

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

	public void timThuoc() {
		if (regex()) {
			maThuoc = textMaThuocFind.getText();

			Thuoc th = th_DAO.getThuocByID(maThuoc);

			if (checkTrung(th.getMaThuoc()) == false) {
				if (th.getMaThuoc() != null) {
					// Kiểm tra nếu ô số lượng đã được điền
					if (isSoLuongFilled()) {

						String[] row = { th.getMaThuoc(), th.getTenThuoc(), th.getDonVi(), "", th.getGia() + "", "" };
						model.addRow(row);
						int soLuongColumnIndex = 3;

						int lastRowIndex = model.getRowCount() - 1;

						table.requestFocus();
						table.changeSelection(lastRowIndex, soLuongColumnIndex, false, false);
						table.editCellAt(lastRowIndex, soLuongColumnIndex);
						Component editor = table.getEditorComponent();
						testBug(editor);

					} else {
						JOptionPane.showMessageDialog(null,
								"Vui lòng nhập số lượng trước khi tìm kiếm sản phẩm tiếp theo");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy mã thuốc trong hệ thống");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Thuốc đã có trong danh sách");
			}
			textMaThuocFind.setText("");
		}
	}

	private void testBug(Component editor) {

		if (editor != null && editor instanceof JTextComponent) {
			((JTextComponent) editor).selectAll();
		}
		Document doc = ((JTextComponent) editor).getDocument();
		doc.addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!table.getValueAt(model.getRowCount() - 1, 3).toString().equals("")
						&& table.getValueAt(model.getRowCount() - 1, 3).toString().matches("\\d+"))
					updateTotalPrice();

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!table.getValueAt(model.getRowCount() - 1, 3).toString().equals(""))
					updateTotalPrice();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
					updateTotalPrice();
					double tong = tinhGia();

					((JTextField) object_sell[2][1]).setText(tong + "");
				}
			}
		});
	}

	private boolean isSoLuongFilled() {
		int rowCount = model.getRowCount();
		if (rowCount == 0) {
			return true; // Nếu bảng chưa có dòng nào, không cần kiểm tra ô số lượng
		}

		int soLuongColumnIndex = 3;
		for (int i = 0; i < rowCount; i++) {
			Object value = model.getValueAt(i, soLuongColumnIndex);
			if (value == null || value.toString().isEmpty()) {
				return false; // Nếu có ít nhất một ô số lượng trống, trả về false
			}
		}
		return true; // Nếu tất cả ô số lượng đã được điền, trả về true
	}

	private void updateTotalPrice() {
		int soLuongColumnIndex = 3;
		int lastRowIndex = model.getRowCount() - 1;
		int soLuong = 0;
		double gia = 0;

		try {
			String soLuongInput = table.getValueAt(lastRowIndex, soLuongColumnIndex).toString();
			String giaInput = table.getValueAt(lastRowIndex, 4).toString();

			soLuong = Integer.parseInt(soLuongInput);
			gia = Double.parseDouble(giaInput);

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}

		double thanhTien = soLuong * gia;
		table.setValueAt(thanhTien, lastRowIndex, 5);
	}

	private boolean regex() {
		if (textMaThuocFind.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Phải nhập mã thuôc trước khi tìm");
			textMaThuocFind.requestFocus();
			return false;
		}

		return true;
	}

	private boolean regexInHoaDon() {
		String tienDua = ((JTextField) object_sell[5][1]).getText();
		if (((JTextField) object_sell[2][1]).getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn thuốc để lập hoá đơn");
			textMaThuocFind.requestFocus();
			return false;
		} else if (!tienDua.matches("\\d+")) {
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

	private void handleActionCheckbox() {
		cb.addActionListener(e -> {

			if (cb.isSelected()) {
				hidden(true);
			} else {

				hidden(false);
			}
		});

	}

	public void setDefaultText(String ma) {

		((JLabel) object_sell[0][1]).setText(generateCustomerCode(ma));
		((JLabel) object_sell[0][1]).setFont(new Font("Arial", Font.BOLD, 15));
		((JTextField) object_sell[1][1]).setText(formatTime(LocalDate.now()));

		((JTextField) object_sell[1][1]).setFont(new Font("Arial", Font.BOLD, 15));

	}

	public void xoaTrang() {
		setDefaultText(maNhanVien);
		hidden(false);
		((JTextField) object_custommer[0][1]).setText("");
		((JTextField) object_custommer[1][1]).setText("");
		((JComboBox) object_custommer[2][1]).setSelectedIndex(0);
		((JTextField) object_custommer[3][1]).setText("");
		((JTextField) object_custommer[4][1]).setText("");
		jtextMaKH.setText("");

		cb.setSelected(false);

		((JTextField) object_sell[2][1]).setText("");
		((JLabel) object_sell[3][1]).setText("");
		((JComboBox) object_sell[4][1]).setSelectedIndex(0);
		((JTextField) object_sell[5][1]).setText("");

		labelTotal.setText("SỐ TIỀN TRẢ LẠI : ");

		textMaThuocFind.setText("");
		model.setRowCount(0);
		table.setModel(model);
	}

	public void xoaThuoc() {
		int index = table.getSelectedRow();

		if (index >= 0) {
			int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa thuốc ", "Lưu ý",
					JOptionPane.YES_NO_OPTION);
			if (recomment == JOptionPane.YES_OPTION) {

				model.removeRow(index);

			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn thuốc để xóa");
		}
	}

	public HoaDon getHoaDon(String fake) {
		ArrayList<ChiTietHoaDon> listCtHD = new ArrayList<ChiTietHoaDon>();

		for (int i = 0; i < table.getRowCount(); i++) {
			HoaDon hd = new HoaDon(getValueStringInJlabel(object_sell[0][1]));
			Thuoc th = new Thuoc(table.getValueAt(i, 0).toString());
			String tenThuoc = table.getValueAt(i, 1).toString();
			String donVi = table.getValueAt(i, 2).toString();
			int soLuong = Integer.parseInt(table.getValueAt(i, 3).toString());
			double gia = Double.parseDouble(table.getValueAt(i, 4).toString());
			double thanhTien = Double.parseDouble(table.getValueAt(i, 5).toString());
			ChiTietHoaDon ct = new ChiTietHoaDon(hd, th, tenThuoc, donVi, soLuong, gia, thanhTien);

			listCtHD.add(ct);
		}

		String maHD = getValueStringInJlabel(object_sell[0][1]);
		NhanVien nv = new NhanVien(maNhanVien);

		String tenKh = getValueStringInJTextField(object_custommer[0][1]);
		String hinhThucThanhToan = ((JComboBox) object_sell[4][1]).getSelectedItem().toString();
		Boolean loaiHoaDon;

		if (!fake.equals("")) {
			loaiHoaDon = cb.isSelected() ? true : false;
		} else {
			loaiHoaDon = null;
		}
		String tinhTrang;
		KhachHang kh = new KhachHang(jtextMaKH.getText());
		if (loaiHoaDon == null) {
			tinhTrang = "";
		} else {
			if (loaiHoaDon == true) {
				kh = new KhachHang(jtextMaKH.getText());
			} else {
				kh = new KhachHang("");
			}
			tinhTrang = "Bán ra";

		}

		double tongTien = Double.parseDouble(getValueStringInJTextField(object_sell[2][1]));
		HoaDon hd = new HoaDon(maHD, nv, kh, tenKh, hinhThucThanhToan, LocalDate.now(), loaiHoaDon, tinhTrang, tongTien,
				"", listCtHD);
		return hd;
	}

	public void luuTamHoaDON() {

		if (cb.isSelected()) {
			if (table.getRowCount() > 0) {
				HoaDon hd = getHoaDon("");

				int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn thêm hóa đơn vào hàng chờ ",
						"Lưu ý", JOptionPane.YES_NO_OPTION);
				if (recomment == JOptionPane.YES_OPTION) {

					if (hDao.themHoaDon(hd)) {
						JOptionPane.showMessageDialog(null, "Thêm hóa đơn vào hàng chờ thành công");
						xoaTrang();
					} else {
						JOptionPane.showMessageDialog(null, "Hóa đơn đã có sẵn trong hàng chờ");
					}
				}
			} else {
				textMaThuocFind.requestFocus();
				JOptionPane.showMessageDialog(null, "Bạn cần chọn thuốc để lập hóa đơn");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Cần lưu thông tin khách hàng để thêm hóa đơn vào hàng chờ", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public double tinhGia() {
		double gia = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			gia += Double.parseDouble(table.getValueAt(i, 5).toString());
		}
		return gia;
	}

	public boolean checkTrung(String ma) {
		if (table.getRowCount() < 0)
			return false;
		else {

			for (int i = 0; i < table.getRowCount(); i++) {
				if (table.getValueAt(i, 0).toString().equals(ma)) {
					return true;
				}
			}
		}
		return false;
	}

	public void thanhToan(double tongTien, double khachDua, String khuyenMai) {

		HoaDon hd = getHoaDon("1");
	

		if (!hDao.themHoaDon(hd)) {
			Boolean loaiHoaDon = cb.isSelected() ? true : false;
			if (hDao.themHoaDonVaoLoai(loaiHoaDon, "Bán ra", hd.getMaHD())) {
				JOptionPane.showMessageDialog(null, "Tạo hóa đơn thành công");
				if(hd.getMaKh().getMaKH().equals("")) {
					generateInvoiceBanLe(hd, tongTien, khachDua,result);
				}
				else {
					generateInvoice(hd, tongTien, khachDua, khuyenMai,result);
				}
				thayDoiDiemXepHang();
				thayDoiSoLuongThuoc();
				xoaTrang();

			} else {
				JOptionPane.showMessageDialog(null, "Thêm hóa đơn không thành công");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Tạo hóa đơn thành công");
			if(hd.getMaKh().getMaKH().equals("")) {
				generateInvoiceBanLe(hd, tongTien, khachDua,result);
			}
			else {
				generateInvoice(hd, tongTien, khachDua, khuyenMai,result);
			}
			thayDoiDiemXepHang();
			thayDoiSoLuongThuoc();
			xoaTrang();
		}

	}

	public void thayDoiSoLuongThuoc() {
		for (int i = 0; i < table.getRowCount(); i++) {
			String maThuoc = table.getValueAt(i, 0).toString();
			int soLuong = Integer.parseInt(table.getValueAt(i, 3).toString());
			Thuoc th = thuoc_DAO.getThuocByID(maThuoc);

			soLuong = th.getSoLuong() - soLuong;
			thuoc_DAO.suaThuoc(new Thuoc(), soLuong, maThuoc);
		}
	}

	public void thayDoiDiemXepHang() {
		String maKh = jtextMaKH.getText();
		int dtl = (int) tinhGia() / 1000;
		KhachHang kh = khachHang_DAO.getKhachHangByID(maKh, "");
		int tinhDiem = kh.getDiemThanhVien() + dtl;
		String xepHang;
		if (tinhDiem <= 100) {
			xepHang = "Đồng";
		} else if (tinhDiem > 100 && tinhDiem <= 500) {
			xepHang = "Bạc";
		} else if (tinhDiem > 500 && tinhDiem <= 1500) {
			xepHang = "Vàng";
		} else if (tinhDiem > 1500 && tinhDiem <= 4000) {
			xepHang = "Bạch kim";
		} else {
			xepHang = "Kim cương";
		}
		if (khachHang_DAO.updateKhachHangXepHang(tinhDiem, xepHang, maKh)) {

		} else {
			System.out.println("sai");
		}
	}

	public JButton buttonInPageSell(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(150, 40));
		btn.addActionListener(e -> {
			if (nameButton.equals("Tìm kiếm")) {

				timThuoc();

			} else if (nameButton.equals("")) {
				xoaTrang();
			} else if (nameButton.equals("Tìm")) {
				timKhach.getTimKhach(jtextMaKH, (JTextField) object_custommer[0][1],
						(JTextField) object_custommer[1][1], (JComboBox) object_custommer[2][1],
						(JTextField) object_custommer[3][1], (JTextField) object_custommer[4][1],
						(JLabel) object_sell[3][1]);

			} else if (nameButton.equals("Xóa Thuốc")) {
				xoaThuoc();
			} else if (nameButton.equals("Xử lí hóa đơn tạm")) {

//				String ma = ((JLabel) object_sell[0][1]).getText();
//				System.out.println("Ma Truoc : " + ma);
				hoaDonLuuTam.getHoaDonLuuTam(jtextMaKH, object_custommer, object_sell, table, cb);

//				String masau = ((JLabel) object_sell[0][1]).getText();
//				System.out.println("Ma Sau : " + masau);

			} else if (nameButton.equals("Lưu tạm")) {
				luuTamHoaDON();

			} else if (nameButton.equals("Thanh toán")) {
				if (regexInHoaDon()) {
					thanhToan(getValueDoubleỊntextField(object_sell[2][1]),
							getValueDoubleỊntextField(object_sell[5][1]), getValueStringInJlabel(object_sell[3][1]));
						

				}
			}
		});

		return btn;
	}
}
