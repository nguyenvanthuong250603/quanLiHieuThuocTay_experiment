package experiment_UI;

import javax.print.attribute.TextSyntax;
import javax.sql.rowset.RowSetWarning;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.BorderUIResource.BevelBorderUIResource;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import dao.NhanVien_DAO;
import dao.Thuoc_DAO;
import entity.NhanVien;
import entity.Thuoc;
import experiment_UI.Generate_All.CustomTableCellRenderer;

import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Iterator;

public class banThuoc_UI {

	private JCheckBox cb;
	private Object[][] object_custommer, object_sell;
	private JComboBox optionGender, optionPTTT, optionDoTuoi;
	private JLabel labelTotal, ll;
	private JButton btnTimKH;
	private JTextField jtextMaKH;
	private JTextArea jtextNote;
	private JTextField textMaThuocFind;
	private DefaultTableModel model;
	private JTable table;
	private Thuoc_DAO th_DAO = new Thuoc_DAO();

	public JPanel getBanThuoc() {
		JPanel sellManagement = new JPanel(new BorderLayout());
		sellManagement.add(table_Drug(), BorderLayout.CENTER);
		sellManagement.add(inputSell(), BorderLayout.EAST);
		forcusListen();

		hidden(false);
		cb.addActionListener(e -> {

			if (cb.isSelected()) {
				hidden(true);
			} else {

				hidden(false);
			}
		});
		return sellManagement;
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
		Jpanel_table.add(findID(), BorderLayout.NORTH);
		Jpanel_table.add(table_information(), BorderLayout.CENTER);
		Jpanel_table.add(totalMoney(), BorderLayout.SOUTH);
		return Jpanel_table;
	}

	public JPanel findID() {
		JPanel f = new JPanel();
		f.setLayout(new BorderLayout());
		textMaThuocFind = new JTextField();
		f.add(textMaThuocFind, BorderLayout.CENTER);
		f.setBorder(new EmptyBorder(5, 5, 5, 0));
		JButton timkiemMaThuoc = buttonInPageSell("Tìm kiếm", "");
		f.add(timkiemMaThuoc, BorderLayout.EAST);
		return f;
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
		JPanel inf = new JPanel(new BorderLayout());
//	west
		createTitle(inf, "Thông tin khách hàng ");
		JPanel check = new JPanel();
		check.setLayout(new BoxLayout(check, BoxLayout.X_AXIS));
		cb = new JCheckBox();
		check.add(cb);
		check.add(sampleModel("Lưu thông tin khách hàng"));
//	center		
		JPanel input = new JPanel();
		input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
		JPanel t = new JPanel(new BorderLayout());

		t.add(sampleModel("Mã KH"), BorderLayout.WEST);
		t.add(jtextMaKH = new JTextField(15), BorderLayout.CENTER);
		btnTimKH = buttonInPageSell("Tìm", "");
		btnTimKH.setPreferredSize(new Dimension(80, 30));
		t.add(btnTimKH, BorderLayout.AFTER_LINE_ENDS);
		input.add(t);
		String[] doTuoi = { "Từ 1-23 tháng tuổi", "Từ 2-11 tuổi", "Từ 12-18 tuổi", "Từ 18 tuổi trở lên" };
		String[] gt = { "Nam", "Nữ" };
		Object[][] trage = { { "Tên KH", new JTextField() }, { "Tuổi", optionDoTuoi = new JComboBox(doTuoi) },
				{ "Giới tính", optionGender = new JComboBox(gt) }, { "SDT", new JTextField() } };
		object_custommer = trage;
		for (Object[] objects : object_custommer) {
			if (!(objects[1] instanceof JComboBox)) {
				input.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));
			} else {
				input.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));
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

		Object[][] trage = { { "Mã HĐ", new JTextField(25) }, { "Ngày tạo", new JTextField() },
				{ "Khách cần trả", new JTextField() }, { "PTTT", new JComboBox(pttt) },
				{ "Khách đưa", new JTextField() } };
		object_sell = trage;
		for (Object[] objects : object_sell) {
			if (!(objects[1] instanceof JComboBox)) {
				box.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));
			} else {
				box.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));
			}
		}
		jtextNote = new JTextArea();
		box.add(createTextArea("Lưu ý", jtextNote));
		sell.add(box);
		return sell;
	}

	public JPanel totalMoney() {
		JPanel money = new JPanel(new BorderLayout());
		labelTotal = sampleModel("SỐ TIỀN TRẢ LẠI : ");
		labelTotal.setPreferredSize(new Dimension(250, 30));
		money.add(labelTotal, BorderLayout.EAST);

		return money;
	}

	public JButton buttonInPageSell(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(120, 40));
		btn.addActionListener(e -> {
			if (nameButton.equals("Tìm kiếm")) {

				timThuoc();

			}
		});

		return btn;
	}

	public void forcusListen() {
		FocusListener calculation = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				try {
					double value1 = Double.parseDouble(((JTextField) object_sell[2][1]).getText());
					double value2 = Double.parseDouble(((JTextField) object_sell[4][1]).getText());
					double result = value2 - value1;

					labelTotal.setText("SỐ TIỀN TRẢ LẠI : " + result);

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
		((JTextField) object_sell[4][1]).addFocusListener(calculation);

	}

	public JPanel footer_sell() {

		JPanel footer = new JPanel();
		JButton btn = null;
		String[] object = { "Xóa", "gift\\trash-bin.png", "Thanh toán", "gift\\excel-file.png" };
		for (int i = 0; i < object.length; i += 2) {
			btn = buttonInPageSell(object[i], object[i + 1]);
			footer.add(btn);
		}
		btn.setPreferredSize(new Dimension(180, 40));
		return footer;

	}

	public void timThuoc() {
		String maThuoc = textMaThuocFind.getText();
		Thuoc th = th_DAO.getThuocByID(maThuoc);

		double thanhTien = th.getSoLuong() * th.getGia();
		String[] row = { th.getMaThuoc(), th.getTenThuoc(), th.getDonVi(), "", th.getGia() + "", "" + thanhTien };
		model.addRow(row);
		int soLuongColumnIndex = 3;

		int lastRowIndex = model.getRowCount() - 1;
		table.requestFocus();
		table.changeSelection(lastRowIndex, soLuongColumnIndex, false, false);
		table.editCellAt(lastRowIndex, soLuongColumnIndex);
		Component editor = table.getEditorComponent();
		if (editor != null && editor instanceof JTextComponent) {
			((JTextComponent) editor).selectAll();
		}
	}
	
	public void forcusListenTable() {
		FocusListener calculation = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				try {
					
					Integer	getSoLuong	=  Integer.parseInt(table.getValueAt(0, 3).toString());
					Double getGia =Double.parseDouble(table.getValueAt(0, 4).toString());
					double tinhTien = getSoLuong * getGia;
					table.setValueAt(tinhTien, 0, 5);
					table.
					

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		};
		table.addFocusListener(calculation);

	}
	
	

}
