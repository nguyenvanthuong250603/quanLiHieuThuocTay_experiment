package experiment_UI;

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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import javax.swing.table.DefaultTableModel;

import javax.swing.text.JTextComponent;

import com.itextpdf.text.xml.simpleparser.NewLineHandler;
import com.toedter.calendar.JDateChooser;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.text.Document;

import dao.Thuoc_DAO;

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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class BanThuoc_UI {

	private JCheckBox cb, cbDTL;
	private Object[][] object_custommer, object_sell;
	private JComboBox optionGender, optionPTTT, optionDoTuoi;
	private JLabel labelTotal;
	private JButton btnTimKH;
	private JTextField jtextMaKH;
	private JTextArea jtextNote;
	private JTextField textMaThuocFind;
	private DefaultTableModel model;
	private JTable table;
	private Thuoc_DAO th_DAO = new Thuoc_DAO();
	private TimKhach_UI timKhach = new TimKhach_UI();
	private JLabel labelDTL = new JLabel();
	private int giatru = 0;
	private double tong = 0;

	public JPanel getBanThuoc(String maNV) {
		JPanel sellTotal = new JPanel(new BorderLayout());
		JPanel sellManagement = new JPanel(new BorderLayout());

		sellManagement.add(inputSell(), BorderLayout.NORTH);
		sellManagement.add(table_Drug(), BorderLayout.CENTER);
		sellManagement.add(footer_sell(), BorderLayout.SOUTH);

		enterListen();
		hidden(false);
		handleActionCheckbox();
		setDefaultText(maNV);
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
			} else if (object_custommer[i][1] instanceof JLabel) {
				((JLabel) object_custommer[i][1]).setEnabled(value);
			} else {
				((JComboBox) object_custommer[i][1]).setEnabled(value);
			}

		}
		cbDTL.setEnabled(value);
		if (value == false) {
			cbDTL.setSelected(false);
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
		JPanel sell = new JPanel(new GridLayout(1, 2));
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
				{ "Giới tính", optionGender = new JComboBox(gt) }, { "SDT", new JTextField() } };
		object_custommer = trage;
		for (Object[] objects : object_custommer) {
			if (objects[1] instanceof JTextField) {

				input.add(createNameAndTextField2(((JTextField) objects[1]), objects[0].toString()));
			} else {
				input.add(createJcombobox2(objects[0].toString(), (JComboBox) objects[1]));

			}
		}
		JPanel DTL = new JPanel();
		DTL.setLayout(new BoxLayout(DTL, BoxLayout.X_AXIS));
		cbDTL = new JCheckBox();
		cbDTL.setBorder(new EmptyBorder(0, 0, 0, 10));
		DTL.add(cbDTL);
		DTL.setBorder(new EmptyBorder(0, 30, 0, 0));
		DTL.add(sampleModel2("Điểm tích lũy"));
		labelDTL.setBorder(new EmptyBorder(0, 40, 0, 0));
		labelDTL.setFont(new Font("Arial", Font.ITALIC, 18));
		labelDTL.setForeground(Color.RED);
		DTL.add(labelDTL);

		inf.add(check, BorderLayout.NORTH);
		inf.add(input, BorderLayout.CENTER);
		inf.add(DTL, BorderLayout.SOUTH);
		return inf;

	}

	public JPanel inf_sell() {
		JPanel sell = new JPanel();
		createTitle(sell, "Thông tin hóa đơn");
		Box box = Box.createVerticalBox();

//		sell.setLayout(new BoxLayout(sell, BoxLayout.Y_AXIS));
		String[] pttt = { "Tiền mặt", "Chuyển khoản", "Quẹt thẻ" };

		Object[][] trage = { { "Mã HĐ", new JLabel() }, { "Ngày tạo", new JTextField() },
				{ "Khách cần trả", new JTextField(37) }, { "PTTT", new JComboBox(pttt) },
				{ "Thuế VAT", new JLabel("5%") }, { "Khách đưa", new JTextField() }, { "", new JLabel() } };
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
		String[][] object = { { "Lưu tạm", "gift\\trash-bin.png" }, { "Xử lí hóa đơn tạm", "gift\\excel-file.png" },
				{ "Xóa", "gift\\trash-bin.png" }, { "Thanh toán", "gift\\excel-file.png" } };
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
					caculateResult();
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		};
		((JTextField) object_sell[2][1]).addActionListener(enter);
		((JTextField) object_sell[5][1]).addActionListener(enter);
		((JTextField) object_sell[2][1]).requestFocusInWindow();
		((JTextField) object_sell[5][1]).requestFocusInWindow();
	}

//	public void forcusListen() {
//		FocusListener calculation = new FocusListener() {
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				try {
//					double x = table.getValueAt(giatru, giatru);
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
//
//			@Override
//			public void focusGained(FocusEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//		};
//
//		table.addFocusListener(calculation);
//		
//
//	}

	private void caculateResult() {
		String getValue1 = ((JTextField) object_sell[2][1]).getText();
		String getValue2 = ((JTextField) object_sell[5][1]).getText();
		if (!getValue1.isEmpty() && !getValue2.isEmpty()) {
			double value1 = Double.parseDouble(getValue1);
			double value2 = Double.parseDouble(getValue2);
			double result = value2 - value1 - giatru;
			
			labelTotal.setText("SỐ TIỀN TRẢ LẠI : " + formatValueDouble(result) +"VND" );
		}
	}

	public void timThuoc() {
		String maThuoc = textMaThuocFind.getText();

		if (regex()) {
			Thuoc th = th_DAO.getThuocByID(maThuoc);

			if (th.getMaThuoc() != null) {

				String[] row = { th.getMaThuoc(), th.getTenThuoc(), th.getDonVi(), "",th.getGia()+"", "" };
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
				Document doc = ((JTextComponent) editor).getDocument();
				doc.addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						updateTotalPrice();
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						updateTotalPrice();
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						// Plain text components do not fire these events
					}

				});
				model.addTableModelListener(new TableModelListener() {
					@Override
					public void tableChanged(TableModelEvent e) {
						if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == soLuongColumnIndex) {
							updateTotalPrice();
							tong +=Double.parseDouble(table.getValueAt( lastRowIndex, 5).toString()); 
							
							((JTextField)object_sell[2][1]).setText(tong+"");
						}
					}
				});

			} else {
				JOptionPane.showMessageDialog(findID(), "không tìm thấy mã thuốc trong hệ thống");
			}

		} else {
			JOptionPane.showConfirmDialog(table, "Mã thuốc nhập sai");
		}
	}

	private void updateTotalPrice() {
		int soLuongColumnIndex = 3;

		int lastRowIndex = table.getSelectedRow();

		int soLuong = 0;
		double gia = 0;
		
		try {
			soLuong = Integer.parseInt(table.getValueAt(lastRowIndex, soLuongColumnIndex).toString());
			gia = Double.parseDouble(table.getValueAt(lastRowIndex, 4).toString());
			
		} catch (NumberFormatException ex) {

		}
		double thanhTien = soLuong * gia;
		table.setValueAt(thanhTien, lastRowIndex, 5);
		

	}

	private boolean regex() {
		if (textMaThuocFind.getText().equals("")) {
			JOptionPane.showMessageDialog(table, "Mã thuốc không được để rỗng");
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
		cbDTL.addActionListener(e -> {
			if (cbDTL.isSelected()) {
				if (!labelDTL.getText().isEmpty()) {
					giatru = Integer.parseInt(labelDTL.getText()) * 10;
					((JLabel) object_sell[6][1]).setText("-" + giatru + "VND");
				}
			} else {
				((JLabel) object_sell[6][1]).setText("");
			}
		});
	}

	public JButton buttonInPageSell(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(120, 40));
		btn.addActionListener(e -> {
			if (nameButton.equals("Tìm kiếm")) {

				timThuoc();

			}
			if (nameButton.equals("Xóa")) {

			}
			if (nameButton.equals("Tìm")) {
				timKhach.getTimKhach(jtextMaKH, (JTextField) object_custommer[0][1],
						(JTextField) object_custommer[1][1], (JComboBox) object_custommer[2][1],
						(JTextField) object_custommer[3][1], labelDTL);

			}
		});

		return btn;
	}

	public String generateCustomerCode(String ma) {
		Calendar now = Calendar.getInstance();

		int currentDay = now.get(Calendar.DAY_OF_MONTH);
		int currentMonth = now.get(Calendar.MONTH) + 1; // Adding 1 to get the correct month

		Random random = new Random();
		int randomDigits = 1000 + random.nextInt(9000); // Generate 4 random digits
		String customerCode = "HĐ" + String.format("%02d", currentDay) + String.format("%02d", currentMonth)
				+ ma.substring(2) + randomDigits;
		return customerCode;
	}

	public void setDefaultText(String ma) {

		((JLabel) object_sell[0][1]).setText(generateCustomerCode(ma));
		((JLabel) object_sell[0][1]).setFont(new Font("Arial", Font.BOLD, 15));
		((JTextField) object_sell[1][1]).setText(formatTime(LocalDate.now()));

		((JTextField) object_sell[1][1]).setFont(new Font("Arial", Font.BOLD, 15));
		((JLabel) object_sell[6][1]).setFont(new Font("Arial", Font.ITALIC, 18));
		((JLabel) object_sell[6][1]).setForeground(Color.RED);

	}
}
