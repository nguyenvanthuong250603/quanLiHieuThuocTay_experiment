package experiment_UI;

import static experiment_UI.Generate_All.createJbutton;
import static experiment_UI.Generate_All.createJcombobox;
import static experiment_UI.Generate_All.createNameAndTextField2;
import static experiment_UI.Generate_All.createTitle;
import static experiment_UI.Generate_All.generateCode;
import static experiment_UI.Generate_All.hienTableKhachHang;
import static experiment_UI.Generate_All.sampleModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

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

import experiment_UI.Generate_All.CustomTableCellRenderer;

public class HoaDonLuuTam_UI {
	
	private Object[][] object_search;
	private DefaultTableModel model;
	private JTable table;
	
	public void getTimKhach(JTextField maKH, JTextField tenKH, JTextField tuoiKH, JComboBox gioiTinhKH,
			JTextField sdtKH, JLabel labelDiem) {
		frame = new JFrame();
		frame.setTitle("Tìm Kiếm Khách Hàng");

		frame.setSize(1100, 800);
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.add(layOut());
		hienTableKhachHang(table, model, objects_custommer);
		renderTuoi();
		((JLabel)objects_custommer[0][1]).setText(generateCode("KH"));

		frame.setVisible(true);
		frame.setResizable(false);
		this.Jtext_maKH = maKH;
		this.Jtext_tenKH = tenKH;
		this.jText_tuoiKH = tuoiKH;
		this.cbGioiTinhKH = gioiTinhKH;
		this.jText_sdtKH = sdtKH;
		this.labelDiemTichLuy = labelDiem;
	}
	private JPanel north() {
		JPanel north = new JPanel(new BorderLayout());
		createTitle(north, "Tìm kiếm và lọc hóa đơn");

		JPanel north_center = new JPanel(new GridLayout(2, 3, 10, 10));
		Object[][] trage = { { "Mã hóa đơn", new JTextField() }, { "Từ ngày", new JDateChooser() },
				{ "Đến ngày", new JDateChooser() }, { "Mã KH", new JTextField() }, { "Doanh thu", new JComboBox() },
				{ "Trạng thái", new JComboBox() }, };

		object_search = trage;
		for (Object[] objects : object_search) {
			if (objects[1] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel(objects[0].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(5, 10, 5, 10));
				t.add((Component) objects[1], BorderLayout.CENTER);

				north_center.add(t);

			} else {

				north_center.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));

			}
		}
		north.add(north_center, BorderLayout.CENTER);

		JPanel north_btn = new JPanel(new GridLayout(2, 1, 10, 10));

		north_btn.add(createButtonInHoaDonBanHang("Tìm kiếm", ""));
		north_btn.add(createButtonInHoaDonBanHang("", "gift\\reset.png"));

		north.add(north_btn, BorderLayout.EAST);
		return north;
	}

	private JPanel cenTer() {
		JPanel center = new JPanel(new GridLayout(1, 2));
		center.add(table_information());
		center.add(inf_product());
		return center;
	}

	public JPanel table_information() {
		JPanel managerment = new JPanel();
		createTitle(managerment, "Danh sách hóa đơn");
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã Hóa Đơn", "Nhân viên", "Mã khách hàng", "Ngày mua", "Tổng tiền" };
		model = new DefaultTableModel(column, 0);
		table = new JTable(model);
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table);
		managerment.add(scoll, BorderLayout.CENTER);
		managerment.add(footer(), BorderLayout.SOUTH);
		return managerment;
	}

	public JPanel inf_product() {
		JPanel input = new JPanel(new BorderLayout());
		createTitle(input, "Danh sách sản phẩm");
		String[] column = { "Mã thuốc", "Tên thuốc ", "Số lượng", "Số lượng", "Giá", "Thành tiền" };

		String[][] row = { { "SP01", "Bảo thanh", "" + 20, "" + 10000, "" },

				{ "", "", "", "", "", "" } };
		DefaultTableModel model = new DefaultTableModel(row, column);
		JTable table = new JTable(model);

		table.setShowGrid(false);
		table.setShowVerticalLines(false);

		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		input.add(scoll, BorderLayout.CENTER);
		input.add(getThongTin(), BorderLayout.SOUTH);
		return input;

	}

	private JPanel getThongTin() {
		JPanel thongTin = new JPanel();
		thongTin.setLayout(new BoxLayout(thongTin, BoxLayout.Y_AXIS));
		Object[][] trage = { { "Tên khách hàng", new JTextField() }, { "Số điện thoại", new JTextField() },
				{ "Tổng tiền", new JTextField() } };
		object_kh = trage;
		for (Object[] objects : object_kh) {

			thongTin.add(createNameAndTextField2((JTextField) objects[1], objects[0].toString()));
		}
		return thongTin;
	}

	public JPanel footer() {
		JPanel footer = new JPanel();
		Object[][] btn = { { "Xuất danh sách", "" }, { "In hóa đơn" ,""} };
		for (Object[] objects : btn) {
			footer.add(createButtonInHoaDonBanHang(objects[0].toString(), objects[1].toString()));
		}
		return footer;

	}

	private JButton createButtonInHoaDonBanHang(String nameBtn, String pathIcon) {
		JButton btn = createJbutton(nameBtn, pathIcon);
		btn.setPreferredSize(new Dimension(180, 35));
		btn.addActionListener(e -> {
			System.out.println(nameBtn);
		});
		return btn;
	}
}
