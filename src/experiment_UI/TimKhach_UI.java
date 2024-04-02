package experiment_UI;

import static experiment_UI.Generate_All.createJbutton;
import static experiment_UI.Generate_All.createJcombobox;
import static experiment_UI.Generate_All.createNameAndTextField;
import static experiment_UI.Generate_All.createTitle;
import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.KhachHang_DAO;
import entity.KhachHang;
import experiment_UI.Generate_All.CustomTableCellRenderer;

public class TimKhach_UI {
	private JFrame frame;
	private JTextField Jtext_maKH, Jtext_tenKH, jText_sdtKH,jText_tuoiKH;
	private JComboBox  cbGioiTinhKH;
	private Object[][] objects_North, objects_custommer;
	private JComboBox cbTuoi,cbGioiTinh;
	private JLabel labelDiemTichLuy;
	private DefaultTableModel model;
	private JTable table;
	private KhachHang_DAO lKhachHang_DAO = new KhachHang_DAO();
	public void getTimKhach(JTextField maKH, JTextField tenKH, JTextField tuoiKH, JComboBox gioiTinhKH,
			JTextField sdtKH,JLabel labelDiem) {
		frame = new JFrame();
		frame.setTitle("Tìm Kiếm Khách Hàng");
		
		frame.setSize(1100, 800);
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.add(layOut());
		hienTableKhachHang(table, model,objects_custommer);
		frame.setVisible(true);
		frame.setResizable(false);
		this.Jtext_maKH = maKH;
		this.Jtext_tenKH = tenKH;
		this.jText_tuoiKH = tuoiKH;
		this.cbGioiTinhKH = gioiTinhKH;
		this.jText_sdtKH = sdtKH;
		this.labelDiemTichLuy = labelDiem; 
	}

	public JPanel layOut() {
		JPanel management = new JPanel(new BorderLayout());
		JLabel title = new JLabel("TÌM KIẾM THUỐC");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arail", Font.BOLD, 30));
		title.setForeground(Color.BLUE);
		management.add(title, BorderLayout.NORTH);
		management.add(getKhachHang(), BorderLayout.CENTER);
		return management;
	}

	public JPanel getKhachHang() {
		JPanel getKH = new JPanel(new BorderLayout());
		getKH.add(getNorth(), BorderLayout.NORTH);
		getKH.add(getContent(), BorderLayout.CENTER);
		return getKH;
	}

	public JPanel getNorth() {
		JPanel north = new JPanel();
		north.setBorder(new EmptyBorder(30, 50, 30, 80));

		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		String[] doTuoi = { "Từ 1-23 tháng tuổi", "Từ 2-11 tuổi", "Từ 12-18 tuổi", "Từ 18 tuổi trở lên" };
		String[] gioiTinh = { "Nam", "Nữ" };
		Object[][] trage = { { "Số điện thoại", new JTextField(15) }, { "Độ tuổi", cbTuoi = new JComboBox(doTuoi) },
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
		JButton btnReset = buttonInPageCustommer("", "gift\\reset.png");
		north.add(btnReset);

		return north;
	}

	public JPanel getContent() {
		JPanel content = new JPanel(new BorderLayout());
		content.add(getCenter(), BorderLayout.CENTER);
		content.add(getEast(), BorderLayout.EAST);
		content.add(fotter_South(), BorderLayout.SOUTH);
		return content;
	}

	public JPanel getCenter() {
		
		JPanel managerment = new JPanel();
		createTitle(managerment, "Danh sách khách hàng");
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã khách hàng", "Tên khách hàng ", "Điểm tích lũy","Số điện thoại", "Địa chỉ" };
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

		east.add(inf_custommer(), BorderLayout.NORTH);

		return east;
	}

	public JPanel inf_custommer() {
		JPanel inf = new JPanel();
		createTitle(inf, "Thông tin khách hàng");
		inf.setLayout(new BoxLayout(inf, BoxLayout.Y_AXIS));

		String[] gioiTinh = { "Nam", "Nữ" };
		Object[][] trage = { { "Mã khách hàng", new JTextField(20) }, { "Tên khách hàng", new JTextField() },{"Ngày sinh",new JDateChooser()},
				{ "Tuổi", new JTextField() }, { "Giới tính", cbGioiTinh = new JComboBox(gioiTinh), },
				{ "Số điện thoại", new JTextField() }, { "Địa chỉ", new JTextField() },{"Điểm tích lũy",new JLabel()} };
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

	public JPanel fotter_South() {
		JPanel footer = new JPanel();
		
		JButton btn = null;
		String[] object = { "Chọn", "gift\\trash-bin.png", "Thoát", "gift\\excel-file.png" };
		for (int i = 0; i < object.length; i += 2) {
			btn = buttonInPageCustommer(object[i], object[i + 1]);
			footer.add(btn);
		}
	
		return footer;
	}

	public JButton buttonInPageCustommer(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(180, 40));
		btn.addActionListener(e -> {

			if (nameButton.equals("Thoát")) {
				frame.dispose();
			} else if (nameButton.equals("Chọn")) {
				serviceTimKhach();
				
			}
		});
		return btn;
	}

	private void serviceTimKhach() {
		int index = table.getSelectedRow();
		String ma = table.getValueAt(index, 0).toString();
		KhachHang kh = lKhachHang_DAO.getKhachHangByID(ma);
		Jtext_maKH.setText(kh.getMaKH());
		Jtext_tenKH.setText(kh.getTenKH());
		jText_tuoiKH.setText(kh.getTuoi()+"");	
		cbGioiTinhKH.setSelectedItem(transGender(kh.isGioiTinh()));
		jText_sdtKH.setText(kh.getsDT());
		labelDiemTichLuy.setText(kh.getDiemThanhVien()+"");
		frame.dispose();
	}
	

}
