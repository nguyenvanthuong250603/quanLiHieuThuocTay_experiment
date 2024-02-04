package experiment_UI;

import static experiment_UI.Generate_All.createJbutton;
import static experiment_UI.Generate_All.createJcombobox;
import static experiment_UI.Generate_All.createNameAndTextField;
import static experiment_UI.Generate_All.createTextArea;
import static experiment_UI.Generate_All.createTitle;
import static experiment_UI.Generate_All.sampleModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import experiment_UI.Generate_All.CustomTableCellRenderer;

public class nhapThuoc_UI {
	private JComboBox cbLoaiThuoc;
	private JComboBox cbNSX;
	private JCheckBox cb;
	private JLabel labelTotal;
	private Object[][] objects;
	private JTextArea jtextNote;
 	public JPanel getNhapThuoc() {
		JPanel nhapThuoc = new JPanel(new BorderLayout());
		nhapThuoc.add(table_AddDrug());
		nhapThuoc.add(East(),BorderLayout.EAST);
		return nhapThuoc;
	}
	public JPanel table_AddDrug() {
		JPanel Jpanel_table = new JPanel(new BorderLayout());
		Jpanel_table.add(findID(), BorderLayout.NORTH);
		Jpanel_table.add(table_information(), BorderLayout.CENTER);
		Jpanel_table.add(totalMoney(), BorderLayout.SOUTH);
		return Jpanel_table;
	}

	public JPanel findID() {
		JPanel f = new JPanel();
		f.setLayout(new BorderLayout());
		JTextField text = new JTextField();
		f.add(text, BorderLayout.CENTER);
		f.setBorder(new EmptyBorder(5, 0, 5, 0));
		JButton timkiemMaThuocHoacTen = buttonInPageAdd("Tìm Kiếm", "");
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
	public JPanel totalMoney() {
		JPanel money = new JPanel(new BorderLayout());
		labelTotal = sampleModel("TỔNG TIỀN : ");
		labelTotal.setPreferredSize(new Dimension(250, 30));
		money.add(labelTotal, BorderLayout.EAST);

		return money;
	}
	public JPanel East() {
		JPanel east = new JPanel(new BorderLayout());
		east.add(input(),BorderLayout.NORTH);
		east.add(footer_table(),BorderLayout.SOUTH);
		return east;
	}
	public JPanel input() {
		JPanel input = new JPanel();
		createTitle(input, "Thông tin đơn nhập");
		Box box = Box.createVerticalBox();
		String [] nsx = {"nsx1","nsx2"};
		Object[][] trage = {{"Mã hóa đơn", new JTextField(25)},{"Nhà cung cấp ",new JComboBox(nsx)},{"Ngày lập HĐ", new JTextField()}};
		objects = trage;
		for (Object[] objects : trage) {
			 if (!(objects[1] instanceof JComboBox)) {
			        box.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));
			    } else  {
			        box.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));
			    }
		}
		input.add(box);
		jtextNote = new JTextArea();
		box.add(createTextArea("Lưu ý", jtextNote));
		return input;
		
	}
	
	public JPanel footer_table() {
		JPanel footer = new JPanel();

		String[] object = { "Hủy","gift\\exit.png" , "Tạo đơn nhập ", "gift\\check.png" };
	
		for (int i = 0; i < object.length; i += 2) {
			JButton btn = buttonInPageAdd(object[i], object[i + 1]);
			footer.add(btn);
		}

		return footer;
	}
	public JButton buttonInPageAdd(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize( new Dimension(120,40));
		btn.addActionListener(e -> {
		
		
			System.out.println(nameButton);
		});
		return btn;
	}
	
}
