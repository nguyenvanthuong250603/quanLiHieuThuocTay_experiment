package experiment_UI;

import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import experiment_UI.Generate_All.CustomTableCellRenderer;

public class quanLyDonNhapThuoc_UI {
	private JComboBox cbLoaiThuoc;
	private JComboBox cbNSX;
	private JCheckBox cb;
	private JLabel labelTotal;
	private Object[][] objects;
	private JTextArea jtextNote;
	private ButtonGroup group;
 	public JPanel getQuanLyDonNhapThuoc() {
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
		JPanel east = new JPanel(new GridLayout(2, 1));
		east.add(input());
		east.add(inf());
		return east;
	}
	public JPanel input() {
		JPanel 	input = new JPanel(new BorderLayout());
		createTitle(input, "Chi tiết đơn nhập hàng");
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
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		input.add(scoll, BorderLayout.CENTER);
		return input;
		
	}
	public JPanel inf() {
		JPanel inf = new JPanel(new BorderLayout());
		Box box = Box.createVerticalBox();
		createTitle(inf, "Thông tin");
		Object[] trageStatus = {customRadio("Chưa nhận "),customRadio("Đã nhận"),customRadio("Đã hủy")};
		JPanel status = new JPanel(new BorderLayout());
		status.add(sampleModel("Tình trạng"),BorderLayout.WEST);
		JPanel div = new JPanel(new GridLayout(1, 3));
		group = new ButtonGroup();
		for (Object object : trageStatus) {
			div.add((JRadioButton)object);
			group.add((JRadioButton)object);
		}
		status.add(div,BorderLayout.CENTER);		
		box.add(status);
		
		Object[][] trage = {{"Ngày nhập hàng",new JTextField()},{"Nhà cung cấp", new JTextField()}};
		objects = trage;
		for (Object[] objects : trage) {
			box.add(createNameAndTextField((JTextField)objects[1], objects[0].toString()));
		}
		jtextNote = new JTextArea();
		box.add(createTextArea("Lưu ý", jtextNote));
		inf.add(box,BorderLayout.CENTER);
		inf.add(footer_table(),BorderLayout.SOUTH);
		
		return inf;
	}
	
	
	public JPanel footer_table() {
		JPanel footer = new JPanel();

		String[] object = { "Xác nhận","gift\\exit.png" , "Xuất hóa đơn", "gift\\check.png" };
	
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
