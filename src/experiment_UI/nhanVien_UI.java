package experiment_UI;
import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.security.Provider.Service;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import experiment_UI.Generate_All.CustomTableCellRenderer;

public class NhanVien_UI {
	public Object inf_personnel_left,inf_personnel_right;
	public JComboBox optionGender;
	private DefaultTableModel model;
	private JTable table;
	private JTextField jtextMaKH;
	public JPanel getNhanVien() {
		Box box = Box.createVerticalBox();
		JPanel getNhanVien = new JPanel();
		box.add(getTitle());
		box.add(getNorth());
		box.add(service());
		box.add(search());
		box.add(table_personnel());
		getNhanVien.add(box);
		return getNhanVien;
	}

	public JPanel getNorth() {
		JPanel north = new JPanel();
		createTitle(north, "Thông tin nhân viên");
		north.setLayout(new GridLayout(1, 3,20,20));
		
		String[] gender = {"Nam","Nữ"};
		String[] gender2 = {"Nam","Nữ"};
		
		
		Object[][] trage = { { new JTextField(26), "Mã nhân viên" }, { new JTextField(), "Tên nhân viên" },
				{new JComboBox(gender2), "Giới tính" }, { new JDateChooser(), "Ngày sinh" }, { new JTextField(), "Tuổi" },
				};
		inf_personnel_left = trage;
		JPanel left = new JPanel();
		Box box = Box.createVerticalBox();
		for (Object[] objects : trage) {
			if (objects[0] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel(objects[1].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(5, 0, 5, 0));
				t.add((Component) objects[0], BorderLayout.CENTER);
				box.add(t);
				
			} else {
				box.add(createJcombobox(objects[1].toString(), (JComboBox) objects[0]));

			}
		}
		
		JPanel image = new JPanel(new BorderLayout());
		JPanel boxImage = new JPanel(new BorderLayout());

		JLabel labelImage = new JLabel();
		labelImage.setPreferredSize(new Dimension(230, 150));
		boxImage.add(labelImage, BorderLayout.CENTER);

		JButton inputImage = createButtonInPagePesonnel("Chọn hình ảnh", "");
		boxImage.add(inputImage, BorderLayout.SOUTH);

//	 right
		Object[][] trage2 = { { new JTextField(28), "SDT" }, { new JTextField(), "Số cccd" },
				{ new JTextField(), "Địa chỉ" }, { new JComboBox(gender), "Chức vụ" }, { new JDateChooser(), "Ngày vào làm" },
				 };
		inf_personnel_right = trage;
		Box box2 = Box.createVerticalBox();
			for (Object[] objects : trage2) {
				if (objects[0] instanceof Component) {
					JPanel t = new JPanel(new BorderLayout());
					t.add(sampleModel(objects[1].toString()), BorderLayout.WEST);
					t.setBorder(new EmptyBorder(5, 0, 5, 0));
					t.add((Component) objects[0], BorderLayout.CENTER);
					box2.add(t);
					
				} else {
					box2.add(createJcombobox(objects[1].toString(), (JComboBox) objects[0]));

				
			}
		}
		north.add(box);
 		north.add(boxImage);
 		north.add(box2);	
		return north;
	}
	public JPanel service() {
		JPanel footer = new JPanel();
		String[][] object = { { "Xóa trắng", "gift//brush.png" }, { "Thêm", "gift//update.png" },
				{ "Cập nhât", "gift//add.png" },{ "In danh sách", "gift//add.png" } };
		for (String[] strings : object) {
			JButton btn = createButtonInPagePesonnel(strings[0].toString(), strings[1].toString());
			
			footer.add(btn);
		}
		return footer;
	}
	public JPanel table_personnel() {
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
	public JPanel search() {
		JPanel se = new JPanel();
		createTitle(se, "Tìm kiếm nhân viên");
		se.add(createJcombobox("Trạng thái", new JComboBox()));
		se.add(createJcombobox("Chức vụ", new JComboBox()));
		se.add(createButtonInPagePesonnel("Lọc", ""));
		JPanel input = new JPanel();
		input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
		JPanel t = new JPanel(new BorderLayout());
		t.setBorder( new EmptyBorder(0,30,0,30));
		t.add(sampleModel2("Mã nhân viên"), BorderLayout.WEST);
		t.add(jtextMaKH = new JTextField(25), BorderLayout.CENTER);
		JButton btnTimKH = createButtonInPagePesonnel("Tìm", "");
		t.add(btnTimKH, BorderLayout.AFTER_LINE_ENDS);
		input.add(t);
		
		se.add(t);
		return se;
	}
	
	public JPanel getTitle() {
		JPanel titlee = new JPanel(new BorderLayout());
		createTiTlePage(titlee, "QUẢN LÍ NHÂN VIÊN");
		
		return titlee;
		
	}
	public static JButton createButtonInPagePesonnel(String nameButton,String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(140, 40));
		btn.addActionListener(e->{
			System.out.println(nameButton);
		});
		return btn;
	}
}
