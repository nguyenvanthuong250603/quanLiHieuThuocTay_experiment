package experiment_UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import java.awt.Label;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.itextpdf.text.xml.XmlToTxt;

import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.Thuoc_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thuoc;
import experiment_UI.Generate_All.CustomTableCellRenderer;

import static experiment_UI.Generate_All.*;

public class DoiThuoc_UI {
	private Object[][] object_customer, object_sell, object_find;
	private Object[] object_status;
	private ButtonGroup group;
	private JLabel labelMoney;

	private JTextField jtetJTextAreReason;
	private DefaultTableModel model;
	private JTable table;
	private TimThuoc_UI timThuoc = new TimThuoc_UI();
	private DefaultTableModel model_product;
	private JTable table_product;

	private Object[] trageStatus;
	private JTextField textFind;
	private DefaultTableModel model_change;
	private JTable table_change;
	private ChiTietHoaDon_DAO ctDao = new ChiTietHoaDon_DAO();
	private Thuoc_DAO thuoc_DAO = new Thuoc_DAO();
	private HoaDon_DAO hoaDon_DAO = new HoaDon_DAO();

	public JPanel getDoiThuoc() {
		JPanel doithuoc = new JPanel(new BorderLayout());
		createTiTlePage(doithuoc, "QUẢN LÝ ĐỔI TRẢ THUỐC");
		doithuoc.add(table_Exchange(), BorderLayout.CENTER);
		doithuoc.add(inputSell(), BorderLayout.EAST);
		((JTextField) object_sell[2][1]).setText(formatTime(LocalDate.now()));

		return doithuoc;
	}

//center
	public JPanel findID() {
		JPanel find = new JPanel();
		createTitle(find, "Tìm kiếm hóa đơn");
		find.setLayout(new GridLayout(2, 1, 5, 5));

		JPanel jpanelTim = new JPanel();
		jpanelTim.setLayout(new BoxLayout(jpanelTim, BoxLayout.X_AXIS));
		textFind = new JTextField(25);
		jpanelTim.add(textFind);
		jpanelTim.add(Box.createHorizontalStrut(5));
		JButton btnTim;
		jpanelTim.add(btnTim = buttonInPageExChange("Tìm", ""));

		JPanel jpanelLoc = new JPanel();
		jpanelLoc.setLayout(new BoxLayout(jpanelLoc, BoxLayout.X_AXIS));
		String[] optionHoaDon = { "", "Hóa đơn bán hàng", "Hóa đơn bán lẻ" };
		Object[][] trage = { { "Số điện thoại", new JTextField(10) },
				{ "Loại hóa đơn", new JComboBox<String>(optionHoaDon) } };
		object_find = trage;
		for (Object[] objects : trage) {
			if (objects[1] instanceof JTextField) {
				jpanelLoc.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));
			} else {
				jpanelLoc.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));
			}
			jpanelLoc.add(Box.createHorizontalStrut(5));
		}
		JButton btnLoc;
		jpanelLoc.add(btnLoc = buttonInPageExChange("Lọc", ""));

		find.add(jpanelTim);
		find.add(jpanelLoc);

		return find;
	}

	public JPanel table_Exchange() {
		JPanel Jpanel_table = new JPanel(new BorderLayout());
		Jpanel_table.add(findID(), BorderLayout.NORTH);
		Jpanel_table.add(table_information(), BorderLayout.CENTER);
		Jpanel_table.add(footer_Exchange(), BorderLayout.SOUTH);
		return Jpanel_table;
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
		String[] object = { "", "gift\\reset.png", "Xóa Thuốc", "gift\\trash-bin.png", "Thêm thuốc", "gift\\add.png" };
		for (int i = 0; i < object.length; i += 2) {
			btn = buttonInPageExChange(object[i], object[i + 1]);
			footer.add(btn);
		}

		return footer;

	}

//exchanger
	public JPanel inputSell() {
		JPanel sell = new JPanel(new BorderLayout());
//		Box boxx = Box.createVerticalBox();
//		sell.setLayout(new BoxLayout(sell, BoxLayout.Y_AXIS));
		sell.add(inf_custommer(), BorderLayout.NORTH);
		sell.add(inf_sell(), BorderLayout.CENTER);
		sell.add(footer_sell(), BorderLayout.SOUTH);
//		sell.add(boxx);
		return sell;
	}

	public JPanel inf_custommer() {
		JPanel inf = new JPanel();
		createTitle(inf, "Thông tin khách hàng");
		inf.setLayout(new BoxLayout(inf, BoxLayout.Y_AXIS));
		Object[][] trage = { { "Mã KH", new JTextField(20) }, { "Tên KH", new JTextField() },
				{ "SDT", new JTextField() }, { "Giới Tính", new JTextField() } };
		object_customer = trage;
		for (Object[] objects : trage) {
			inf.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));

		}

		return inf;

	}

	public JPanel inf_sell() {
		JPanel sell = new JPanel(new BorderLayout());
		createTitle(sell, "Thông tin hóa đơn");
		Box box = Box.createVerticalBox();
//		sell.setLayout(new BoxLayout(sell, BoxLayout.Y_AXIS));
		Object[][] trage = { { "Mã HĐ", new JTextField(25) }, { "Ngày bán", new JTextField() },
				{ "Ngày đổi", new JTextField() } };
		object_sell = trage;
		for (Object[] objects : trage) {

			box.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));

		}
		JPanel status = new JPanel(new BorderLayout());
		status.add(sampleModel("Tình trạng"), BorderLayout.WEST);
		JPanel div = new JPanel(new GridLayout(1, 3));
		Object[] trages = { customRadio("Bán ra"), customRadio("Hoàn trả"), customRadio("Đổi thuốc"),
				customRadio("Kê lại đơn") };
		trageStatus = trages;
		group = new ButtonGroup();
		for (Object object : trageStatus) {
			div.add((JRadioButton) object);
			group.add((JRadioButton) object);
		}

		status.add(div, BorderLayout.CENTER);

		box.add(status);
		box.add(changeDrug());
		JPanel JpenlMoney = new JPanel(new BorderLayout());
		JpenlMoney.add(labelMoney = new JLabel("Tổng tiền : "), BorderLayout.WEST);
		labelMoney.setFont(new Font("Arial", Font.BOLD, 15));
		box.add(JpenlMoney);
		jtetJTextAreReason = new JTextField();

		box.add(createNameAndTextField(jtetJTextAreReason, "Lý do"));

		sell.add(box, BorderLayout.CENTER);

		return sell;
	}

	public JPanel footer_sell() {

		JPanel footer = new JPanel();

		JPanel topButton = new JPanel();
		Object[][] btns = { { "Đổi thuốc", "" }, { "Tạo hóa đơn", "" } };
		for (Object[] objects : btns) {
			JButton btn;
			footer.add(btn = buttonInPageExChange(objects[0].toString(), objects[1].toString()));
//			if (objects[0].toString().equals("Đổi thuốc")) {
//				btn.setEnabled(false);
//			}
		}

		return footer;

	}

	public JPanel changeDrug() {
		JPanel input = new JPanel(new BorderLayout());

		String[] column = { "Mã thuốc", "Tên thuốc ", "Đơn vị", "Số lượng", "Giá", "Thành tiền" };

		model_change = new DefaultTableModel(column, 0);
		table_change = new JTable(model_change);

		table_change.setShowGrid(false);
		table_change.setShowVerticalLines(false);
		table_change.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_change.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table_change, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		input.add(scoll, BorderLayout.CENTER);
		return input;

	}

	public void timHoaDonTheoSo() {
		if (regexLoc()) {

			String sdt = getValueStringInJTextField(object_find[0][1]);
			String optionHd = getValueInComboBox((JComboBox) object_find[1][1]);
			int lhd;
			if (!optionHd.equals("")) {
				
				lhd = optionHd.equals("Hóa đơn bán hàng") ? 1 : 0;
			
			}else {
				
				lhd = 4;
			}
			model.setRowCount(0);
			model_product.setRowCount(0);
		
			hienTableTrongHoaDon(table, model, table_product, model_product, object_customer, sdt, lhd);
			hienChungLocVaTim();

		}

	}

	public void xoaTrang() {
		((JTextField) object_sell[0][1]).setText("");
		((JTextField) object_sell[1][1]).setText("");
		((JTextField) object_customer[0][1]).setText("");
		((JTextField) object_customer[1][1]).setText("");
		((JTextField) object_customer[2][1]).setText("");
		((JTextField) object_customer[3][1]).setText("");
		((JTextField) object_find[0][1]).setText("");
		textFind.setText("");
		textFind.requestFocus();
		model.setRowCount(0);
		model_product.setRowCount(0);
		group.clearSelection();
		labelMoney.setText("Tổng tiền : ");
		jtetJTextAreReason.setText("");
		model_change.setRowCount(0);

	}

	public boolean regex() {

		if (textFind.equals("")) {
			JOptionPane.showConfirmDialog(null, "Bạn phải nhập mã hóa đơn để tìm");
			textFind.requestFocus();
			return false;
		}
		return true;

	}

	public boolean regexLoc() {
		String sdt = ((JTextField) object_find[0][1]).getText();
		String loaiHd = getValueInComboBox((JComboBox) object_find[1][1]);

		if (!sdt.equals("") && !loaiHd.equals("")) {
			if (!sdt.matches("0\\d{10}")) {
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

	public void timHoaDon() {
		String ma = textFind.getText();
		HoaDon hd = hoaDon_DAO.getHoaDonByID(ma);
		xoaTrang();
		if (hd != null) {
			NhanVien nv = getNV(hd.getMaNV().getMaNV());
			Object[] row = { hd.getMaHD(), nv.getHoTen(), hd.getMaKh().getMaKH(), formatTime(hd.getNgayTaoHoaDon()),
					hd.getTongTien() };
			model.addRow(row);
			table.setModel(model);
			String tinhTrang = hd.getTinhTrang();
			radioSelection(tinhTrang);
			ArrayList<ChiTietHoaDon> lChiTietHoaDons = hd.getListChiTietHoaDon();

			for (ChiTietHoaDon ct : lChiTietHoaDons) {
				Object[] row_product = { ct.getMaThuoc().getMaThuoc(), ct.getTenThuoc(), ct.getDonVi(), ct.getSoLuong(),
						ct.getDonGia(), ct.getThanhTien() };
				model_product.addRow(row_product);

			}
			table_product.setModel(model_product);
			table.setRowSelectionInterval(0, 0);
			hienChungLocVaTim();
			if (hd.getMaKh().getMaKH() != null) {
				KhachHang kh = getKH(hd.getMaKh().getMaKH(), "");
				((JTextField) object_customer[0][1]).setText(kh.getMaKH());
				((JTextField) object_customer[1][1]).setText(kh.getTenKH());
				((JTextField) object_customer[2][1]).setText(kh.getsDT());
				((JTextField) object_customer[3][1]).setText(transGender(kh.isGioiTinh()));

			}
		} else {
			JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn trong hệ thống");
		}

	}

	public void hienChungLocVaTim() {
		if (table.getRowCount() > 0) {
			((JTextField) object_sell[0][1]).setText(table.getValueAt(table.getSelectedRow(), 0).toString());
			((JTextField) object_sell[1][1]).setText(table.getValueAt(table.getSelectedRow(), 3).toString());
			((JTextField) object_sell[2][1]).setText(formatTime(LocalDate.now()));

			String mahd = table.getValueAt(table.getSelectedRow(), 0).toString();
			HoaDon hd = hoaDon_DAO.getHoaDonByID(mahd);
			String tinhtrang = hd.getTinhTrang();
			labelMoney.setText("Tổng tiền : " + table.getValueAt(table.getSelectedRow(), 4));

			jtetJTextAreReason.setText(hd.getLyDo());
			radioSelection(tinhtrang);

			table.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

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
					labelMoney.setText("Tổng tiền : " + table.getValueAt(table.getSelectedRow(), 4));
					HoaDon hdon = hoaDon_DAO.getHoaDonByID(table.getValueAt(table.getSelectedRow(), 0).toString());
					String tinhtrang = hdon.getTinhTrang();

					jtetJTextAreReason.setText(hdon.getLyDo());
					radioSelection(tinhtrang);

				}
			});

		}
	}

	public void radioSelection(String tinhtrang) {

		for (Object object : trageStatus) {
			JRadioButton radioButton = (JRadioButton) object;
			if (radioButton.getText().equals(tinhtrang)) {
				radioButton.setSelected(true);
				break;
			}
		}
	}

	public void taoHoaDonMoi() {
		if (((JRadioButton) trageStatus[2]).isSelected()) {
			if (taoHoaDonDoiTHuoc()) {
				thayDoiSoLuongThuoc();
				xoaTrang();
				JOptionPane.showMessageDialog(null, "Tạo hóa đơn đổi thuốc thành công");
			}
		}
		if (((JRadioButton) trageStatus[3]).isSelected()) {
			if (taoHoaDonKeLai()) {
				thayDoiSoLuongThuoc();

				JOptionPane.showMessageDialog(null, "Tạo hóa đơn đổi thuốc thành công");
			}
		}
	}

	public void thayDoiSoLuongThuoc() {

		for (int i = 0; i < table_change.getRowCount(); i++) {
			String maThuoc = table_change.getValueAt(i, 0).toString();
			int soLuong = Integer.parseInt(table_change.getValueAt(i, 3).toString());
			Thuoc th = thuoc_DAO.getThuocByID(maThuoc);
			soLuong = th.getSoLuong() - soLuong;
			thuoc_DAO.suaThuoc(new Thuoc(), soLuong, maThuoc);
		}
	}

	public boolean taoHoaDonDoiTHuoc() {
		if (!jtetJTextAreReason.getText().equals("")) {
			ArrayList<ChiTietHoaDon> listCtHD = new ArrayList<ChiTietHoaDon>();

			for (int i = 0; i < table_change.getRowCount(); i++) {
				HoaDon hd = new HoaDon(table.getValueAt(i, 0).toString());
				Thuoc th = new Thuoc(table_change.getValueAt(i, 0).toString());
				String tenThuoc = table_change.getValueAt(i, 1).toString();
				String donVi = table_change.getValueAt(i, 2).toString();
				int soLuong = Integer.parseInt(table_change.getValueAt(i, 3).toString());
				double gia = Double.parseDouble(table_change.getValueAt(i, 4).toString());
				double thanhTien = Double.parseDouble(table_change.getValueAt(i, 5).toString());
				ChiTietHoaDon ct = new ChiTietHoaDon(hd, th, tenThuoc, donVi, soLuong, gia, thanhTien);

				listCtHD.add(ct);
			}

			if (ctDao.upDateDoiTra(listCtHD)) {
				Boolean loaiHD = getValueInComboBox((JComboBox) object_find[1][1]).equals("Hóa đơn bán hàng") ? true
						: false;
				hoaDon_DAO.themHoaDonVaoLoai(loaiHD, "Đổi thuốc", table.getValueAt(0, 0).toString(),
						jtetJTextAreReason.getText());
			} else {
				System.out.println("loi");
			}
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập lí do đổi trả");
			return false;
		}
	}

	public void doiThuoc() {
		if (((JRadioButton) trageStatus[2]).isSelected()) {
			int index = table_product.getSelectedRow();
			if (index > -1) {
				Object[] row = { table_product.getValueAt(index, 0).toString(),
						table_product.getValueAt(index, 1).toString(), table_product.getValueAt(index, 2).toString(),
						table_product.getValueAt(index, 3).toString(), table_product.getValueAt(index, 4).toString(),
						table_product.getValueAt(index, 5).toString() };

				model_change.addRow(row);
				table_change.setModel(model_change);
				model_product.removeRow(index);

				int soLuongColumnIndex = 3;

				int lastRowIndex = model_change.getRowCount() - 1;
				table_change.requestFocus();
				table_change.changeSelection(lastRowIndex, soLuongColumnIndex, false, false);
				table_change.editCellAt(lastRowIndex, soLuongColumnIndex);
				Component editor = table_change.getEditorComponent();
				if (editor != null && editor instanceof JTextComponent) {
					((JTextComponent) editor).selectAll();
				}
				Document doc = ((JTextComponent) editor).getDocument();
				doc.addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						if (!table_change.getValueAt(model_change.getRowCount() - 1, 3).toString().equals("")
								&& table_change.getValueAt(model_change.getRowCount() - 1, 3).toString()
										.matches("\\d+"))
							updateTotalPrice();
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						if (!table_change.getValueAt(model_change.getRowCount() - 1, 3).toString().equals(""))
							updateTotalPrice();
					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});
				model_change.addTableModelListener(new TableModelListener() {
					@Override
					public void tableChanged(TableModelEvent e) {
						if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == soLuongColumnIndex) {
							updateTotalPrice();

						}
					}
				});

			} else {
				JOptionPane.showMessageDialog(null, "Bạn phải chọn thuốc cần được đổi trả để thực hiện đổi trả");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn chế độ đổi thuốc mới được đổi thuốc");
		}
	}

	private void updateTotalPrice() {

		int soLuongColumnIndex = 3;

		int lastRowIndex = model_change.getRowCount() - 1;

		int soLuong = 0;
		double gia = 0;

		try {

			soLuong = Integer.parseInt(table_change.getValueAt(lastRowIndex, soLuongColumnIndex).toString());
			gia = Double.parseDouble(table_change.getValueAt(lastRowIndex, 4).toString());

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		double thanhTien = soLuong * gia;
		table_change.setValueAt(thanhTien, lastRowIndex, 5);

	}

	public void xoaThuocKeLaiDon() {

		int index = table_product.getSelectedRow();
		String maHD = table.getValueAt(table.getSelectedRow(), 0).toString();
		String maThuoc = table_product.getValueAt(index, 0).toString();

		if (index > -1) {
			int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa  thuốc ", "Lưu ý",
					JOptionPane.YES_NO_OPTION);
			if (recomment == JOptionPane.YES_OPTION) {

				if (ctDao.xoaChiTiet(maHD, maThuoc)) {

					JOptionPane.showMessageDialog(null, "Xóa thuốc thành công");

					model_product.removeRow(index);
					;

				}

			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn thuốc để xóa");
		}

	}

	public boolean regexRadio() {
		if (table.getRowCount() < 1) {
			if (group.getSelection() != null) {
				JOptionPane.showMessageDialog(null, "Bạn phải lựa chọn một hóa đơn để lựa chọn thay đổi");
				return false;
			}
		}
		return true;
	}

	public boolean taoHoaDonKeLai() {
		if (!jtetJTextAreReason.getText().equals("")) {

			int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn tạo hóa đơn kê lại đơn thuốc ",
					"Lưu ý", JOptionPane.YES_NO_OPTION);
			if (recomment == JOptionPane.YES_OPTION) {

				for (int i = 0; i < table_product.getRowCount(); i++) {
					String ma = table.getValueAt(table.getSelectedRow(), 0).toString();

					HoaDon hd = new HoaDon(ma);
					Thuoc th = new Thuoc(table_product.getValueAt(i, 0).toString());
					String tenThuoc = table_product.getValueAt(i, 1).toString();
					String donVi = table_product.getValueAt(i, 2).toString();
					int soLuong = Integer.parseInt(table_product.getValueAt(i, 3).toString());
					double gia = Double.parseDouble(table_product.getValueAt(i, 4).toString());
					double thanhTien = Double.parseDouble(table_product.getValueAt(i, 5).toString());
					ChiTietHoaDon ct = new ChiTietHoaDon(hd, th, tenThuoc, donVi, soLuong, gia, thanhTien);
					if (ctDao.themChiTietHoaDon(ct)) {
						thayDoiSoLuongThuocThem(ct);

					} else {
						ArrayList<ChiTietHoaDon> lct = new ArrayList<ChiTietHoaDon>();
						ctDao.upDateDoiTra(lct);
						thayDoiSoLuongThuocUpdate(ct);
					}
				}
				Boolean loaiHD = getValueInComboBox((JComboBox) object_find[1][1]).equals("Hóa đơn bán hàng") ? true
						: false;
				hoaDon_DAO.themHoaDonVaoLoai(loaiHD, "Kê lại đơn",
						table.getValueAt(table.getSelectedRow(), 0).toString(), jtetJTextAreReason.getText());

			}
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập lí do đổi trả");
			return false;
		}
	}

	public void thayDoiSoLuongThuocUpdate(ChiTietHoaDon ct) {
		String maHD = table.getValueAt(table.getSelectedRow(), 0).toString();
		ArrayList<ChiTietHoaDon> lcthd = ctDao.getcChiTietHoaDons(maHD);
		for (ChiTietHoaDon chiTietHoaDon : lcthd) {
			if (chiTietHoaDon.getMaThuoc().getMaThuoc().equals(ct.getMaThuoc().getMaThuoc())) {
				int soLuong = ct.getSoLuong() - chiTietHoaDon.getSoLuong();
				Thuoc th = thuoc_DAO.getThuocByID(ct.getMaThuoc().getMaThuoc());
				soLuong = th.getSoLuong() - soLuong;
				thuoc_DAO.suaThuoc(new Thuoc(), soLuong, ct.getMaThuoc().getMaThuoc());
			}
		}

	}

	public void thayDoiSoLuongThuocThem(ChiTietHoaDon ct) {

		int soLuong = ct.getSoLuong();
		Thuoc th = thuoc_DAO.getThuocByID(ct.getMaThuoc().getMaThuoc());
		soLuong = th.getSoLuong() - soLuong;
		thuoc_DAO.suaThuoc(new Thuoc(), soLuong, ct.getMaThuoc().getMaThuoc());

	}

	public JButton buttonInPageExChange(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(180, 40));
		btn.addActionListener(e -> {

			if (nameButton.equals("Thêm thuốc")) {
				if (((JRadioButton) trageStatus[3]).isSelected()) {
					timThuoc.getTimThuoc(model_product, table_product);

				} else {
					JOptionPane.showMessageDialog(null, "Bạn phải chọn kê lại đơn để thực hiện chức năng");
				}
			} else if (nameButton.equals("Lọc")) {
				timHoaDonTheoSo();
			} else if (nameButton.equals("")) {

				if (((JRadioButton) trageStatus[3]).isSelected()) {
					xoaTrang();
				} else {
					JOptionPane.showMessageDialog(null, "Bạn đang trong chức năng xóa thuốc không thể xóa trắng");
				}
			} else if (nameButton.equals("Tìm")) {
				timHoaDon();
			} else if (nameButton.equals("Tạo hóa đơn")) {
				taoHoaDonMoi();
			} else if (nameButton.equals("Đổi thuốc")) {
				doiThuoc();
			} else if (nameButton.equals("Xóa Thuốc")) {
				if (((JRadioButton) trageStatus[3]).isSelected()) {
					xoaThuocKeLaiDon();
				} else {
					JOptionPane.showMessageDialog(null, "Bạn phải chọn kê lại đơn để thực hiện chức năng");
				}
			} else {
				System.out.println(nameButton);
			}

		});

		return btn;
	}

}
