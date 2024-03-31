package experiment_UI;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;


import experiment_UI.Generate_All.CustomTableCellRenderer;

import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.nio.channels.NonWritableChannelException;

public class KhachHang_UI {
	private Object[][] objects_North, objects_custommer;
	private JComboBox cbDoTuoi, cbGioiTinh;
	private DefaultTableModel model;
	private JTable table;
	private JTextField jtextTuoi;

	public JPanel getKhachHang() {
		JPanel getKH = new JPanel(new BorderLayout());
		createTiTlePage(getKH, "QUẢN LÍ KHÁCH HÀNG");
		getKH.add(getContent(),BorderLayout.CENTER);
		getKH.add(getEast(),BorderLayout.EAST);
		hienTableKhachHang(table, model,objects_custommer);
		return getKH;
		
	}
	
	public JPanel getSearch() {
		JPanel north = new JPanel();
//		north.setBorder(new EmptyBorder(30, 80, 30, 80));

		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		
		String[] gioiTinh = { "Nam", "Nữ" };
		Object[][] trage = { { "Số điện thoại", new JTextField() }, { "Ngày sinh", jtextTuoi = new JTextField() },
				{ "Giới tính ", cbGioiTinh = new JComboBox(gioiTinh) } };
		objects_North = trage;
		for (Object[] objects : trage) {
			if (objects[1] instanceof JTextField) {
				north.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));
			} else {
				north.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));

			}
			north.add(Box.createHorizontalStrut(20));

		}
		JButton btnLoc = buttonInPageCustommer("Lọc", "gift\\\\reset.png");
		
		north.add(btnLoc);
		north.add(Box.createHorizontalStrut(10));
		JButton btnReset = buttonInPageCustommer("","gift\\reset.png" );
		north.add(btnReset);
		
		return north;
	}

	public JPanel getContent() {
		JPanel content = new JPanel(new BorderLayout());
		content.add(getSearch(), BorderLayout.NORTH);
		content.add(getCenter(), BorderLayout.CENTER);
		return content;
	}

	public JPanel getCenter() {
		JPanel managerment = new JPanel();
		createTitle(managerment, "Danh sách khách hàng");
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã khách hàng", "Tên khách hàng ", "Số điện thoại", "Địa chỉ" };
		model = new DefaultTableModel(column, 0);
		table = new JTable(model);
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table);
		managerment.add(scoll, BorderLayout.CENTER);
		return managerment;

	}

	public JPanel getEast() {
		JPanel east = new JPanel(new BorderLayout());
		
		east.add(inf_custommer(),BorderLayout.NORTH);
		east.add(fotter_East(),BorderLayout.SOUTH);
		return east;
	}

	public JPanel inf_custommer() {
		JPanel inf = new JPanel();
		createTitle(inf, "Thông tin khách hàng");
		inf.setLayout(new BoxLayout(inf, BoxLayout.Y_AXIS));

		String[] gioiTinh = { "Nam", "Nữ" };
		Object[][] trage = { { "Mã khách hàng", new JTextField() }, { "Tên khách hàng", new JTextField() },{"Ngày sinh",new JDateChooser()},
				{ "Tuổi", new JTextField() }, { "Giới tính", cbDoTuoi = new JComboBox(gioiTinh), },
				{ "Số điện thoại", new JTextField() }, { "Địa chỉ", new JTextField() } };
		objects_custommer = trage;
		for (Object[] objects : trage) {
			if (objects[1] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel(objects[0].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(5, 0, 5, 0));
				t.add((Component) objects[1], BorderLayout.CENTER);
				inf.add(t);
				
			} else {
				inf.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));

			}
		}
		return inf;
	}

	public JPanel fotter_East() {
		JPanel footer = new JPanel();
		footer.setLayout(new GridLayout(2, 2, 10, 10));
		JButton btn = null;
		String[] object = { "Thêm", "gift\\trash-bin.png", "Xóa", "gift\\excel-file.png", "Sửa",
				"gift\\trash-bin.png", "Xuất file", "gift\\excel-file.png" };
		for (int i = 0; i < object.length; i += 2) {
			btn = buttonInPageCustommer(object[i], object[i + 1]);
			footer.add(btn);
		}
		btn.setPreferredSize(new Dimension(180, 40));
		return footer;
	}

	public JButton buttonInPageCustommer(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(100, 40));
		btn.addActionListener(e -> {

			System.out.println(nameButton);
		});
		return btn;
	}
	
}
