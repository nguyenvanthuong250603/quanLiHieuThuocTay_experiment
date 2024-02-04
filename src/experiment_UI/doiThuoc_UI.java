package experiment_UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;

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

import static experiment_UI.Generate_All.*;

public class doiThuoc_UI {
	private Object[][] object_customer,object_sell;
	private Object[] object_status;
	private ButtonGroup group;
	private JLabel labelMoney;
	private JTextArea jTextAreaNote , jtetJTextAreReason;
	private timThuoc_UI timThuoc = new timThuoc_UI();
	public JPanel getDoiThuoc() {
		JPanel doithuoc = new JPanel(new BorderLayout());
		doithuoc.add(table_Exchange(), BorderLayout.CENTER);
		doithuoc.add(inputSell(),BorderLayout.EAST);
		return doithuoc;
	}
//center
	public JPanel findID() {
		JPanel f = new JPanel();
		f.setLayout(new BorderLayout());
		JTextField text = new JTextField();
		f.add(text, BorderLayout.CENTER);
		f.setBorder(new EmptyBorder(5, 5, 5, 0));
		JButton timkiemMaThuoc = buttonInPageExChange("Tìm", "");
		timkiemMaThuoc.setPreferredSize(new Dimension(120, 40));
		f.add(timkiemMaThuoc, BorderLayout.EAST);
		return f;
	}

	public JPanel table_Exchange() {
		JPanel Jpanel_table = new JPanel(new BorderLayout());
		Jpanel_table.add(findID(), BorderLayout.NORTH);
		Jpanel_table.add(table_information(), BorderLayout.CENTER);

		return Jpanel_table;
	}

	public JPanel table_information() {
		JPanel managerment = new JPanel();
		createTitle(managerment, "Danh sách sản phẩm");
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã Hóa Đơn", "Mã Thuốc ", "Tên Thuốc", "Số lượng", "Giá", "Tổng tiền" };

		String[][] row = {
				{"HD01", "SP01", "Bảo thanh", "" + 20, "" + 10000, ""+200000 },

				{ "", "", "", "", "", "" } };
		DefaultTableModel model = new DefaultTableModel(row, column);
		JTable table = new JTable(model);
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table);
		managerment.add(scoll, BorderLayout.CENTER);
		managerment.add(footer_Exchange(), BorderLayout.SOUTH);
		return managerment;
	}

	public JPanel footer_Exchange() {

		JPanel footer = new JPanel();
		JButton btn = null;
		String[] object = { "", "gift\\reset.png", "Xóa Thuốc", "gift\\trash-bin.png","Thêm thuốc" ,"gift\\add.png"};
		for (int i = 0; i < object.length; i += 2) {
			btn = buttonInPageExChange(object[i], object[i + 1]);
			footer.add(btn);
		}
		btn.setPreferredSize(new Dimension(180, 40));
		return footer;

	}
//exchanger
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
		JPanel inf = new JPanel();
		createTitle(inf, "Thông tin khách hàng");
		inf.setLayout(new BoxLayout(inf, BoxLayout.Y_AXIS));
		Object[][] trage ={ {"Mã KH", new JTextField(20)},{ "Tên KH", new JTextField() },
				{ "SDT", new JTextField() } ,{"Giới Tính", new JTextField()}};
		object_customer = trage;
		for (Object[] objects : trage) {
			inf.add(createNameAndTextField((JTextField)objects[1], objects[0].toString()));
		}
			
		return inf;

	}
	public JPanel inf_sell() {
		JPanel sell = new JPanel();
		createTitle(sell, "Thông tin hóa đơn");
		Box box = Box.createVerticalBox();
//		sell.setLayout(new BoxLayout(sell, BoxLayout.Y_AXIS));
		Object[][] trage = { { "Mã HĐ", new JTextField(25) }, { "Ngày bán", new JTextField() },{ "Ngày đổi", new JTextField() }
				 };
		object_sell = trage;
		for (Object[] objects : trage) {

			box.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));

		}
		JPanel status = new JPanel(new BorderLayout());
		status.add(sampleModel("Tình trạng"),BorderLayout.WEST);
		JPanel div = new JPanel(new GridLayout(1, 3)); 
		Object[] trageStatus = {customRadio("Đã bán"),customRadio("Hoàn trả"),customRadio("Đổi mới")};
		group = new ButtonGroup();
		for (Object object : trageStatus) {
			div.add((JRadioButton)object);
			group.add((JRadioButton)object);
		}
		
		status.add(div,BorderLayout.CENTER);
	
		box.add(status);
		JPanel JpenlMoney = new JPanel(new BorderLayout());
		JpenlMoney.add(labelMoney = sampleModel("Tổng tiền : "),BorderLayout.WEST);
		box.add(JpenlMoney);
		jtetJTextAreReason = new JTextArea();

		box.add(createTextArea("Lý do", jtetJTextAreReason));
		
		
		sell.add(box);
 		return sell;
	}
	public JPanel footer_sell() {
		
		JPanel footer = new JPanel();
		JButton btn = null;
		String[] object = { "Xóa", "gift\\trash-bin.png", "Thanh toán", "gift\\excel-file.png" };
		for (int i = 0; i < object.length; i += 2) {
			 btn = buttonInPageExChange(object[i], object[i + 1]);
			footer.add(btn);
		}
		btn.setPreferredSize(new Dimension(180, 40));
		return footer;
	
}
	
	
	public JButton buttonInPageExChange(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);

		btn.addActionListener(e -> {
			if(nameButton.equals("Thêm thuốc")) {
				timThuoc.getTimThuoc();
			}
		});
		return btn;
	}
}
