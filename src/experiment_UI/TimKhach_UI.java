package experiment_UI;

import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.FormSubmitEvent;

import com.itextpdf.text.pdf.fonts.cmaps.CidLocationFromByte;
import com.itextpdf.text.pdf.security.CertificateInfo.X500Name;
import com.toedter.calendar.JDateChooser;

import dao.KhachHang_DAO;
import entity.KhachHang;
import experiment_UI.Generate_All.CustomTableCellRenderer;

public class TimKhach_UI {
	private JFrame frame;
	private JTextField Jtext_maKH, Jtext_tenKH, jText_sdtKH, jText_tuoiKH, jtextXepHang;
	private JComboBox cbGioiTinhKH;
	private Object[][] objects_North, objects_custommer;
	private JComboBox cbTuoi, cbGioiTinh;

	private DefaultTableModel model;
	private JTable table;
	private KhachHang_DAO lKhachHang_DAO = new KhachHang_DAO();
	private JLabel giam;
	private JComboBox cbXepHang;
	
	public void getTimKhach(JTextField maKH, JTextField tenKH, JTextField tuoiKH, JTextField sdtKH,
			JComboBox gioiTinhKH, JTextField xepHang, JLabel giamgia, JTextField sdtVao) {
		frame = new JFrame();
		frame.setTitle("Tìm Kiếm Khách Hàng");

		frame.setSize(1100, 800);
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.add(layOut());

		hienTableKhachHang(table, model, objects_custommer);
		renderTuoi();
		((JLabel) objects_custommer[0][1]).setText(generateCode("KH"));

		frame.setVisible(true);
		frame.setResizable(false);
		this.Jtext_maKH = maKH;

		this.Jtext_tenKH = tenKH;
		this.jText_tuoiKH = tuoiKH;
		this.cbGioiTinhKH = gioiTinhKH;
		this.jText_sdtKH = sdtKH;
		this.jtextXepHang = xepHang;
		this.giam = giamgia;

		((JTextField) objects_custommer[5][1]).setText(sdtVao.getText());
		enterAction();

	}

	public JPanel layOut() {
		JPanel management = new JPanel(new BorderLayout());
		JLabel title = new JLabel("TÌM KIẾM KHÁCH HÀNG");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arail", Font.BOLD, 30));
		title.setForeground(Color.BLACK);
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
		
		String[] gioiTinh = { "","Nam", "Nữ" };
		
		String[] xepHang = { "", "Đồng", "Bạc", "Vàng", "Bạch kim", "Kim cương" };
		Object[][] trage = { { "Số điện thoại", new JTextField(15) },
				{ "Giới tính ", cbGioiTinh = new JComboBox(gioiTinh) },
				{ "Xếp hạng", cbXepHang = new JComboBox(xepHang) }, };
		objects_North = trage;
		for (Object[] objects : trage) {
			if (objects[1] instanceof JTextField) {
				north.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));
			} else {
				north.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));

			}
			north.add(Box.createHorizontalStrut(20));

		}
		JButton btnLoc = buttonInPageCustommer("Lọc", "gift\\timkhach.png");

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
		String[] column = { "Mã khách hàng", "Tên khách hàng ", "Giới tính", "Điểm tích lũy", "Xếp hạng",
				"Số điện thoại", "Địa chỉ" };
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
		String[] xepHang = { "Đồng", "Bạc", "Vàng", "Bạch kim", "Kim cương" };
		Object[][] trage = { { "Mã khách hàng", new JLabel() }, { "Tên khách hàng", new JTextField(20) },
				{ "Ngày sinh", new JDateChooser() }, { "Tuổi", new JTextField() },
				{ "Giới tính", cbGioiTinh = new JComboBox(gioiTinh), }, { "Số điện thoại", new JTextField() },
				{ "Địa chỉ", new JTextField() }, { "Xếp hạng", new JComboBox(xepHang) }, { "Điểm tích lũy", new JLabel() } };
		objects_custommer = trage;
		for (Object[] objects : trage) {
			if (objects[1] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel2(objects[0].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(5, 0, 5, 0));
				t.add((Component) objects[1], BorderLayout.CENTER);
				inf.add(t);

			} else {
				inf.add(createJcombobox2(objects[0].toString(), (JComboBox) objects[1]));

			}
		}
		return inf;
	}

	public JPanel fotter_South() {
		JPanel footer = new JPanel();

		JButton btn = null;
		String[] object = { "Thêm khách hàng", "gift\\add.png", "Chọn", "gift\\check.png", "Thoát", "gift\\exit.png" };
		for (int i = 0; i < object.length; i += 2) {
			btn = buttonInPageCustommer(object[i], object[i + 1]);
			footer.add(btn);
		}

		return footer;
	}

	private void serviceTimKhach() {
		int index = table.getSelectedRow();
		String ma = table.getValueAt(index, 0).toString();
		KhachHang kh = lKhachHang_DAO.getKhachHangByID(ma, "");
		Jtext_maKH.setText(kh.getsDT());
		Jtext_tenKH.setText(kh.getMaKH());
		jText_tuoiKH.setText(kh.getTenKH());
		cbGioiTinhKH.setSelectedItem(transGender(kh.isGioiTinh()));
		jText_sdtKH.setText(kh.getTuoi() + "");
		jtextXepHang.setText(kh.getXepHang());
		String xh = kh.getXepHang();
		if (xh.equals("Đồng")) {
			giam.setText("0%");
		} else if (xh.equals("Bạc")) {
			giam.setText("1%");
		} else if (xh.equals("Vàng")) {
			giam.setText("2%");
		} else if (xh.equals("Bạch kim")) {
			giam.setText("3%");
		} else if (xh.equals("Kim cương")) {
			giam.setText("4.5%");
		}
		frame.dispose();
	}

	private void renderTuoi() {
		if (objects_custommer[2][1] instanceof JDateChooser) {
			((JDateChooser) objects_custommer[2][1]).getDateEditor()
					.addPropertyChangeListener(new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {
							if ("date".equals(e.getPropertyName()) && e.getNewValue() != null) {
								// Lấy ngày được chọn
								Calendar dob = Calendar.getInstance();
								dob.setTime((java.util.Date) e.getNewValue());

								// Tính tuổi
								Calendar today = Calendar.getInstance();
								int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
								if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
									age--;
								}
								((JTextField) objects_custommer[3][1]).setText(age + "");
							}
						}
					});
		}

	}

	private void themKhachHang() {
		String maKh = ((JLabel) objects_custommer[0][1]).getText();
		String tenKh = getValueStringInJTextField(objects_custommer[1][1]);
		LocalDate ngaySing = getDateJDateChoor(objects_custommer[2][1]);
		int tuoi = getValueIntỊntextField(objects_custommer[3][1]);
		String gt = getValueInComboBox((JComboBox) objects_custommer[4][1]);
		String sdt = getValueStringInJTextField(objects_custommer[5][1]);
		String diaChi = getValueStringInJTextField(objects_custommer[6][1]);
		String xepHang = getValueInComboBox(cbXepHang);

		KhachHang kh = new KhachHang(maKh, tenKh, ngaySing, tuoi, transGenderToSQL(gt), sdt, diaChi, 0, xepHang);
		if (lKhachHang_DAO.themKhachHang(kh)) {
			JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công");
			String[] row = { maKh, tenKh, 0 + "", sdt, diaChi };
			model.addRow(row);
			xoaTrang();
			table.setRowSelectionInterval(model.getRowCount() - 1, model.getRowCount() - 1);
		} else {
			JOptionPane.showMessageDialog(null, "Số điện thoại này đã có người sử dụng");
		}
	}

	public int getIndex(String sdt) {
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 3).toString().equals(sdt)) {
				return i;
			}
		}
		return -1;
	}

	public void xoaTrang() {
		model.setRowCount(0);
		hienTableKhachHang(table, model, objects_custommer);
		((JLabel) objects_custommer[0][1]).setText(generateCode("KH"));

		((JTextField) objects_custommer[1][1]).setText("");
		((JDateChooser) objects_custommer[2][1]).setDate(null);
		((JTextField) objects_custommer[3][1]).setText("");
		((JComboBox) objects_custommer[4][1]).setSelectedIndex(0);
		((JTextField) objects_custommer[5][1]).setText("");
		((JTextField) objects_custommer[6][1]).setText("");
		((JComboBox) objects_custommer[7][1]).setSelectedIndex(0);
		((JLabel) objects_custommer[8][1]).setText("");

	}
	public void enterAction() {
		ActionListener enter3 = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					timTheoSdt();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		};
		((JTextField) objects_North[0][1]).addActionListener(enter3);

	}
	public void timTheoSdt() {

		String sdt = getValueStringInJTextField(objects_North[0][1]);

		if (sdt.matches("^(0|\\+84)\\d{9}$")) {
			KhachHang kh = lKhachHang_DAO.getKhachHangByID("", sdt);

			if (kh.getMaKH() != null) {
				model.setRowCount(0);
				hienKhachHang(kh);
				table.setModel(model);
				((JLabel) objects_custommer[0][1]).setText(kh.getMaKH());
				((JTextField) objects_custommer[1][1]).setText(kh.getTenKH());
				((JDateChooser) objects_custommer[2][1]).setDate(java.sql.Date.valueOf(kh.getNgaySinh()));
				((JTextField) objects_custommer[3][1]).setText(kh.getTuoi() + "");
				((JComboBox) objects_custommer[4][1]).setSelectedItem(transGender(kh.isGioiTinh()));
				((JTextField) objects_custommer[5][1]).setText(kh.getsDT());
				((JTextField) objects_custommer[6][1]).setText(kh.getDiaCHi());
				((JComboBox) objects_custommer[7][1]).setSelectedItem(kh.getXepHang());
				((JLabel) objects_custommer[8][1]).setText(kh.getDiemThanhVien() + "");

			} else {
				JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng trong hệ thống");
			}

		} else

		{
			xoaTrang();
			JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ , số phải có 10 số");
		}
	}
	public void hienKhachHang(KhachHang kh) {
		String gender = kh.isGioiTinh()==true ? "Nam" :"Nữ";
		Object[] row = { kh.getMaKH(), kh.getTenKH(), gender,kh.getDiemThanhVien(), kh.getXepHang(), kh.getsDT(),
				kh.getDiaCHi() };
		model.addRow(row);
	}

	public void timNangCao() {

		String gt = getValueInComboBox(cbGioiTinh);
		String xepHang = getValueInComboBox(cbXepHang);
	
		if (gt.equals("") && xepHang.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn ít nhất 1 lựa chọn để lọc khách");
		
		} else {
			ArrayList<KhachHang> list_khach = lKhachHang_DAO.getListKhachHang();
			model.setRowCount(0);
			for (KhachHang khachHang : list_khach) {
				boolean gender= gt.equals("Nam")? true :false;
				if (gt.equals("")&& khachHang.getXepHang()!=null && khachHang.getXepHang().equals(xepHang)) {
					hienKhachHang(khachHang);
				} else if (!gt.equals("") && xepHang.equals("") && khachHang.isGioiTinh() == gender) {
					hienKhachHang(khachHang);
				} else if (khachHang.isGioiTinh() == gender && khachHang.getXepHang()!=null&& khachHang.getXepHang().equals(xepHang)) {
					hienKhachHang(khachHang);
				}
			}
			table.setModel(model);

		}
	}
	public JButton buttonInPageCustommer(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(150, 40));
		btn.addActionListener(e -> {
			if (nameButton.equals("Thoát")) {

				frame.dispose();
			} else if (nameButton.equals("Chọn")) {
				serviceTimKhach();
			} else if (nameButton.equals("Thêm khách hàng")) {
				themKhachHang();
			} else if (nameButton.equals("Lọc")) {
				timNangCao();
			} else if (nameButton.equals("")) {

				xoaTrang();
			} else {
				System.out.println(nameButton);
			}
		});
		return btn;
	}

}
