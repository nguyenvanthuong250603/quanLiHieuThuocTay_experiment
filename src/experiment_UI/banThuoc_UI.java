package experiment_UI;

import javax.print.attribute.TextSyntax;
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
import javax.swing.plaf.BorderUIResource.BevelBorderUIResource;
import javax.swing.table.DefaultTableModel;

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
	private JComboBox optionGender;
	private JLabel labelTotal,ll;
	private JButton btnTimKH;
	private JTextField jtextMaKH;
	private JTextArea jtextNote;
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
			((JTextField) object_custommer[i][1]).setEditable(value);
			optionGender.setEnabled(value);

		}
	}
	public JPanel table_Drug() {
		JPanel Jpanel_table = new JPanel(new BorderLayout());
		Jpanel_table.add(findID(), BorderLayout.NORTH);
		Jpanel_table.add(table_information(), BorderLayout.CENTER);
		Jpanel_table.add(totalMoney(),BorderLayout.SOUTH);
		return Jpanel_table;
	}

	public JPanel findID() {
		JPanel f = new JPanel();
		f.setLayout(new BorderLayout());
		JTextField text = new JTextField();
		f.add(text, BorderLayout.CENTER);
		f.setBorder(new EmptyBorder(5, 0, 5, 0));
		JButton timkiemMaThuocHoacTen = buttonInPageSell("Tìm kiếm", "");
		f.add(timkiemMaThuocHoacTen, BorderLayout.EAST);
		return f;
	}

	public JPanel table_information() {
		JPanel managerment = new JPanel();
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã thuốc", "Tên thuốc ", "Số lượng", "Giá", "Loại thuốc", "Nhà sản xuất", "Ngày sản xuất",
				"Ngày hết Hạn", };

		String[][] row = {
				{ "SP01", "Bảo thanh", "" + 20, "" + 10000, "Thực phẩm chức năng", "Công Ty Nam Cao", "31/01/2024",
						"30/04/2024" },

				{ "", "", "", "", "", "", "", "" } };
		DefaultTableModel model = new DefaultTableModel(row, column);
		JTable table = new JTable(model);
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
		sell.add(inf_custommer(),BorderLayout.NORTH);
		sell.add(inf_sell(),BorderLayout.CENTER);
		sell.add(footer_sell(),BorderLayout.SOUTH);
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
		t.add(jtextMaKH = new JTextField(15),BorderLayout.CENTER);
		btnTimKH = buttonInPageSell("Tìm", "");
		btnTimKH.setPreferredSize(new Dimension(80, 30));
		t.add(btnTimKH,BorderLayout.AFTER_LINE_ENDS);
		input.add(t);
		Object[][] trage = { { "Tên KH", new JTextField() },
				{ "SDT", new JTextField() } };
		object_custommer = trage;
		for (Object[] objects : object_custommer) {
			
			input.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));
		}

		String[] gt = { "Nam", "Nữ" };
		optionGender = new JComboBox(gt);
		input.add(createJcombobox("Giới tính", optionGender));

		inf.add(check, BorderLayout.NORTH);
		inf.add(input, BorderLayout.CENTER);
		return inf;

	}

	public JPanel inf_sell() {
		JPanel sell = new JPanel();
		createTitle(sell, "Thông tin hóa đơn");
		Box box = Box.createVerticalBox();
//		sell.setLayout(new BoxLayout(sell, BoxLayout.Y_AXIS));
		Object[][] trage = { { "Mã HĐ", new JTextField(25) }, { "Ngày tạo", new JTextField() },
				{ "Khách cần trả", new JTextField() }, { "Khách đưa", new JTextField() } };
		object_sell = trage;
		for (Object[] objects : trage) {

			box.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));

		}
		jtextNote = new JTextArea();
		box.add(createTextArea("Lưu ý", jtextNote));
		sell.add(box);
 		return sell;
	}
	public JPanel totalMoney() {
		JPanel money = new JPanel(new BorderLayout());
		labelTotal  = sampleModel("SỐ TIỀN TRẢ LẠI : ");
		labelTotal.setPreferredSize(new Dimension(250, 30));
		money.add(labelTotal,BorderLayout.EAST);
		
		return money;
	}
	

	public JButton buttonInPageSell(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(120, 40));
		btn.addActionListener(e -> {
			System.err.println(nameButton);
		});

		return btn;
	}
	public void forcusListen() {
		FocusListener calculation = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
			try {
				double value1 = Double.parseDouble(((JTextField)object_sell[2][1]).getText());
				double value2 = Double.parseDouble(((JTextField)object_sell[3][1]).getText());
				double result = value2 - value1;
				
				labelTotal.setText("SỐ TIỀN TRẢ LẠI : "+result);
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		((JTextField)object_sell[2][1]).addFocusListener(calculation);
		((JTextField)object_sell[3][1]).addFocusListener(calculation);
		
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

}
