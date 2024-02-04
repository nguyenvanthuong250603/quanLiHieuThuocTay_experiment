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

public class quanLyDonNhapThuoc_UI {
	private JComboBox cbLoaiThuoc;
	private JComboBox cbNSX;
	private JCheckBox cb;
	private JLabel labelTotal;
	private Object[][] objects;
	private JTextArea jtextNote;
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
		return east;
	}
	public JPanel input() {
		JPanel 	input = new JPanel(new BorderLayout());
		
		String[] column = { "Mã thuốc", "Tên thuốc ", "Số lượng", "Giá", "Loại thuốc", "Nhà sản xuất", "Ngày sản xuất",
				"Ngày hết Hạn", };

		String[][] row = {
				{ "SP01", "Bảo thanh", "" + 20, "" + 10000, "Thực phẩm chức năng", "Công Ty Nam Cao", "31/01/2024",
						"30/04/2024" },

				{ "", "", "", "", "", "", "", "" } };
		DefaultTableModel model = new DefaultTableModel(row, column);
		JTable table = new JTable(model);
		
			
			table.getColumnModel().getColumn(0).setPreferredWidth(800);
			table.getColumnModel().getColumn(1).setPreferredWidth(800);
			table.getColumnModel().getColumn(2).setPreferredWidth(800);
			table.getColumnModel().getColumn(3).setPreferredWidth(800);
			table.getColumnModel().getColumn(4).setPreferredWidth(800);
			table.getColumnModel().getColumn(5).setPreferredWidth(800);
			table.getColumnModel().getColumn(6).setPreferredWidth(800);
		
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
//		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		input.add(scoll, BorderLayout.CENTER);
		return input;
		
	}
	
	public JPanel footer_table() {
		JPanel footer = new JPanel();

		String[] object = { "Thay đổi","gift\\exit.png" , "Xuất hóa đơn", "gift\\check.png" };
	
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
