package experiment_UI;

import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.security.Provider.Service;
import java.sql.NClob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.xmlbeans.impl.xb.xsdschema.impl.PublicImpl;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.toedter.calendar.JDateChooser;

import dao.NhanVien_DAO;
import entity.KhachHang;
import entity.NhanVien;
import experiment_UI.Generate_All.CustomTableCellRenderer;

public class NhanVien_UI {
	public Object[][] inf_personnel_left, inf_personnel_right;
	public JComboBox optionGender;
	private DefaultTableModel model;
	private JTable table;
	private JTextField jtextMaKH;
	private String pathImageShow;
	private JLabel labelImage;
	private NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
	private JComboBox trangThaiJcombo;
	private JComboBox chucVuJcombo;

	public JPanel getNhanVien() {
		Box box = Box.createVerticalBox();
		JPanel getNhanVien = new JPanel();
		box.add(getTitle());
		box.add(getNorth());
		box.add(service());
		box.add(search());
		box.add(table_personnel());
		getNhanVien.add(box);
		((JLabel) inf_personnel_left[0][0]).setText(generateCode("NV"));
		((JTextField) inf_personnel_right[4][0]).setText(formatTime(LocalDate.now()));
		hienBangNhanVien();
		renderTuoi();
		return getNhanVien;
	}

	public JPanel getNorth() {
		JPanel north = new JPanel();
		createTitle(north, "Thông tin nhân viên");
		north.setLayout(new GridLayout(1, 3, 20, 20));

		String[] gender = { "Nam", "Nữ" };

		Object[][] trage = { { new JLabel(), "Mã nhân viên" }, { new JTextField(), "Tên nhân viên" },
				{ new JComboBox(gender), "Giới tính" }, { new JDateChooser(), "Ngày sinh" },
				{ new JTextField(), "Tuổi" }, { new JTextField(), "SĐT" } };
		inf_personnel_left = trage;
		JPanel left = new JPanel();
		Box box = Box.createVerticalBox();
		for (Object[] objects : trage) {
			if (objects[0] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel3(objects[1].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(5, 0, 5, 0));
				t.add((Component) objects[0], BorderLayout.CENTER);
				box.add(t);

			} else {
				box.add(createJcombobox3(objects[1].toString(), (JComboBox) objects[0]));

			}
		}

		JPanel image = new JPanel(new BorderLayout());
		JPanel boxImage = new JPanel(new BorderLayout());

		labelImage = new JLabel();
		labelImage.setPreferredSize(new Dimension(230, 150));
		labelImage.setBorder(new EmptyBorder(0, 85, 0, 0));
		boxImage.add(labelImage, BorderLayout.CENTER);

		JButton inputImage = createButtonInPagePesonnel("Chọn hình ảnh", "");
		boxImage.add(inputImage, BorderLayout.SOUTH);

//	 right
		String[] option = { "Nhân viên", "Quản lý" };
		String[] option2 = { "Đang làm", "Đã nghỉ", "Nghỉ phép" };
		Object[][] trage2 = { { new JTextField(), "Số cccd" }, { new JTextField(), "Địa chỉ" },
				{ new JComboBox(option), "Chức vụ" }, { new JComboBox(option2), "Trạng thái" },
				{ new JTextField(), "Ngày vào làm" } };
		inf_personnel_right = trage2;
		Box box2 = Box.createVerticalBox();
		for (Object[] objects : trage2) {
			if (objects[0] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel3(objects[1].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(5, 0, 5, 0));
				t.add((Component) objects[0], BorderLayout.CENTER);
				box2.add(t);

			} else {
				box2.add(createJcombobox3(objects[1].toString(), (JComboBox) objects[0]));

			}
		}
		north.add(box);
		north.add(boxImage);
		north.add(box2);
		return north;
	}

	public JPanel service() {
		JPanel footer = new JPanel();
		String[][] object = { { "Xóa trắng", "gift//brush.png" }, { "Thêm", "gift//them.png" },
				{ "Xóa", "gift//trash-bin.png" }, { "Cập nhật", "gift//update.png" },
				{ "In danh sách", "gift//excel-file.png" } };
		for (String[] strings : object) {
			JButton btn = createButtonInPagePesonnel(strings[0].toString(), strings[1].toString());

			footer.add(btn);
		}
		return footer;
	}

	public JPanel table_personnel() {
		JPanel managerment = new JPanel();
		createTitle(managerment, "Danh sách nhân viên");
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã nhân viên", "Tên nhân viên", "Số điện thoại", "Chức vụ", "Trạng thái", "Địa chỉ" };
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
		String[] option = { "Nhân viên", "Quản lý" };
		String[] option2 = { "Đang làm", "Đã nghỉ", "Nghỉ phép" };
		se.add(createJcombobox3("Trạng thái", trangThaiJcombo = new JComboBox(option2)));
		se.add(createJcombobox3("Chức vụ", chucVuJcombo = new JComboBox(option)));
		se.add(createButtonInPagePesonnel("Lọc", ""));
		JPanel input = new JPanel();
		input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
		JPanel t = new JPanel(new BorderLayout());
		t.setBorder(new EmptyBorder(0, 10, 0, 10));
		t.add(sampleModel2("Mã nhân viên"), BorderLayout.WEST);
		t.add(jtextMaKH = new JTextField(15), BorderLayout.CENTER);
		JButton btnTimKH = createButtonInPagePesonnel("Tìm", "");
		t.add(btnTimKH, BorderLayout.AFTER_LINE_ENDS);
		input.add(t);

		se.add(input);
		return se;
	}

	private void chooseImage() {

		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
		fileChooser.setFileFilter(filter);

		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			displayImage(selectedFile);
		}

	}

	private void displayImage(File file) {

		try {
			ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
			Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(image);
			labelImage.setIcon(imageIcon);
			pathImageShow = file.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public JPanel getTitle() {
		JPanel titlee = new JPanel(new BorderLayout());
		createTiTlePage(titlee, "QUẢN LÍ NHÂN VIÊN");

		return titlee;

	}

	private void renderTuoi() {
		if (inf_personnel_left[3][0] instanceof JDateChooser) {
			((JDateChooser) inf_personnel_left[3][0]).getDateEditor()
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
								((JTextField) inf_personnel_left[4][0]).setText(age + "");
							}
						}
					});
		}

	}

	private NhanVien getNhanVienUI() {

		String ma = ((JLabel) inf_personnel_left[0][0]).getText();
		String ten = getValueStringInJTextField(inf_personnel_left[1][0]);
		LocalDate ngaySinh = getDateJDateChoor(inf_personnel_left[3][0]);
		int tuoi = getValueIntỊntextField(inf_personnel_left[4][0]);
		String gt = getValueInComboBox((JComboBox) inf_personnel_left[2][0]);

		String sdt = getValueStringInJTextField(inf_personnel_left[5][0]);

		String cccd = getValueStringInJTextField(inf_personnel_right[0][0]);
		String diaChi = getValueStringInJTextField(inf_personnel_right[1][0]);
		String chucVu = getValueInComboBox((JComboBox) inf_personnel_right[2][0]);
		String trangThai = getValueInComboBox((JComboBox) inf_personnel_right[3][0]);

		LocalDate ngayVaoLam = LocalDate.now();
		String hinhAnh = pathImageShow;
		NhanVien nv = new NhanVien(ma, ten, transGenderToSQL(gt), ngaySinh, tuoi, sdt, cccd, diaChi, chucVu, trangThai,
				ngayVaoLam, hinhAnh);
		return nv;

	}

	public void themNhanVien() {

		NhanVien nv = getNhanVienUI();
		if (nhanVien_DAO.themNhanVien(nv)) {
			JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công");
			String[] row = { nv.getMaNV(), nv.getHoTen(), nv.getSdt(), nv.getChucVu(), nv.getTinhTrang(),
					nv.getDiaChi() };
			model.addRow(row);
			xoaTrang();

		}
	}

	public void updateNhanVien() {
		int index = table.getSelectedColumn();
		if (index > -1) {
			NhanVien nv = getNhanVienUI();
			int question = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn sửa thông nhân viên hay không ?",
					"Chú ý", JOptionPane.YES_NO_OPTION);
			if (question == JOptionPane.YES_OPTION) {
				if (nhanVien_DAO.updateNhanVien(nv)) {
					JOptionPane.showMessageDialog(null, "Cập nhật nhân viên thành công");

					xoaTrang();

				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn nhân viên để cập nhật");
		}
	}

	public void xoaTrang() {
		model.setRowCount(0);
		((JLabel) inf_personnel_left[0][0]).setText("");
		((JTextField) inf_personnel_left[1][0]).setText("");
		((JComboBox) inf_personnel_left[2][0]).setSelectedIndex(0);
		((JDateChooser) inf_personnel_left[3][0]).setDate(null);
		;
		((JTextField) inf_personnel_left[4][0]).setText("");
		((JTextField) inf_personnel_left[5][0]).setText("");
		((JTextField) inf_personnel_right[0][0]).setText("");
		((JTextField) inf_personnel_right[1][0]).setText("");
		((JComboBox) inf_personnel_right[2][0]).setSelectedIndex(0);
		((JComboBox) inf_personnel_right[3][0]).setSelectedIndex(0);

		((JLabel) inf_personnel_left[0][0]).setText(generateCode("NV"));
		((JTextField) inf_personnel_right[4][0]).setText(formatTime(LocalDate.now()));
		hienBangNhanVien();
		labelImage.setText("");

	}

	public void hienBangNhanVien() {
		ArrayList<NhanVien> nv = nhanVien_DAO.getNhanVien();
		for (NhanVien nhanVien : nv) {
			Object[] row = { nhanVien.getMaNV(), nhanVien.getHoTen(), nhanVien.getSdt(), nhanVien.getChucVu(),
					nhanVien.getTinhTrang(), nhanVien.getDiaChi() };
			model.addRow(row);
		}
		table.setModel(model);
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				NhanVien nvVien = nhanVien_DAO.getNhanVienFindByID(table.getValueAt(index, 0).toString());
				((JLabel) inf_personnel_left[0][0]).setText(nvVien.getMaNV());
				((JTextField) inf_personnel_left[1][0]).setText(nvVien.getHoTen());
				((JDateChooser) inf_personnel_left[3][0]).setDate(java.sql.Date.valueOf(nvVien.getNgaySinh()));
				((JComboBox) inf_personnel_left[2][0]).setSelectedItem(transGender(nvVien.isGioiTinh()));

				((JTextField) inf_personnel_left[4][0]).setText(nvVien.getTuoi() + "");
				((JTextField) inf_personnel_left[5][0]).setText(nvVien.getSdt());
				((JTextField) inf_personnel_right[0][0]).setText(nvVien.getCccd());
				((JTextField) inf_personnel_right[1][0]).setText(nvVien.getDiaChi());
				((JComboBox) inf_personnel_right[2][0]).setSelectedItem(nvVien.getChucVu());
				((JComboBox) inf_personnel_right[3][0]).setSelectedItem(nvVien.getTinhTrang());
				((JTextField) inf_personnel_right[4][0]).setText(formatTime(nvVien.getNgayVaoLam()));
				if (nvVien.getHinhAnh() != null) {

					String pathFile = nvVien.getHinhAnh();
					File file = new File(pathFile);
					displayImage(file);
				} else {
					displayImage(new File(""));
				}

			}
		});
	}

	public void locNhanVien() {
		String trangThai = getValueInComboBox(trangThaiJcombo);
		String chucVu = getValueInComboBox(chucVuJcombo);
	
		ArrayList<NhanVien> lnv = nhanVien_DAO.locNhanVien(trangThai, chucVu);
		model.setRowCount(0);
		((JLabel) inf_personnel_left[0][0]).setText("");
		((JTextField) inf_personnel_left[1][0]).setText("");
		((JComboBox) inf_personnel_left[2][0]).setSelectedIndex(0);
		((JDateChooser) inf_personnel_left[3][0]).setDate(null);
		;
		((JTextField) inf_personnel_left[4][0]).setText("");
		((JTextField) inf_personnel_left[5][0]).setText("");
		((JTextField) inf_personnel_right[0][0]).setText("");
		((JTextField) inf_personnel_right[1][0]).setText("");
		((JComboBox) inf_personnel_right[2][0]).setSelectedIndex(0);
		((JComboBox) inf_personnel_right[3][0]).setSelectedIndex(0);

		((JLabel) inf_personnel_left[0][0]).setText(generateCode("NV"));
		((JTextField) inf_personnel_right[4][0]).setText(formatTime(LocalDate.now()));
			
			for (NhanVien nhanVien : lnv) {
				Object[] row = { nhanVien.getMaNV(), nhanVien.getHoTen(), nhanVien.getSdt(), nhanVien.getChucVu(),
						nhanVien.getTinhTrang(), nhanVien.getDiaChi() };
				model.addRow(row);
			}
		
		table.setModel(model);
	}

	public void xoaNhanVien() {
		int index = table.getSelectedRow();
		if (index > -1) {
			int question = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên hay không ?", "Chú ý",
					JOptionPane.YES_NO_OPTION);
			if (question == JOptionPane.YES_OPTION ) {
				if(nhanVien_DAO.xoaNhanVien(table.getValueAt(index, 0).toString())) {
				JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công");
				model.removeRow(index);
				}else {
					System.out.println("sai");
				}

			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn nhân viên để xóa");
		}
	}

	public void timNhanVien() {
		String maNV = jtextMaKH.getText();
		if (maNV.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn phải nhập mã nhân viên để tìm");
		} else {
			NhanVien nhanVien = nhanVien_DAO.getNhanVienFindByID(maNV);

			if (nhanVien.getMaNV() != null) {
				model.setRowCount(0);
				Object[] row = { nhanVien.getMaNV(), nhanVien.getHoTen(), nhanVien.getSdt(), nhanVien.getChucVu(),
						nhanVien.getTinhTrang(), nhanVien.getDiaChi() };
				model.addRow(row);
				table.setModel(model);

			} else {
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy nhân viên trong hệ thống vui lòng kiểm tra thông tin tìm");
			}
		}
	}

	public boolean regexthemKhachHang() {

		for (Object[] objects : inf_personnel_left) {
			if (objects[0] instanceof JTextField) {
				if (((JTextField) objects[0]).getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Bạn chưa nhập thông tin vào " + objects[1].toString());
					((JTextField) objects[0]).requestFocus();
					return false;
				}
				if (objects[1].toString().equals("Tuổi")) {
					if (!((JTextField) objects[0]).getText().matches("\\d+")) {
						JOptionPane.showMessageDialog(null,
								"Thông tin của " + objects[1].toString() + " phải là chữ số");
						((JTextField) objects[1]).requestFocus();
						return false;
					}
					int check = Integer.parseInt(((JTextField) objects[0]).getText());
					if (check < 18) {
						JOptionPane.showMessageDialog(null, objects[1].toString() + "tuổi của nhân viên phải >18");
						((JTextField) objects[0]).requestFocus();
						return false;
					}
				}
			} else if (objects[0] instanceof JDateChooser) {
				if (((JDateChooser) objects[0]).getDate() == null) {
					JOptionPane.showMessageDialog(null, "Ngày của " + objects[1].toString() + " chưa được nhập");

					return false;
				}
				if (((JDateChooser) objects[0]).getDate().after(new java.util.Date())) {
					JOptionPane.showMessageDialog(null,
							"Ngày của " + objects[1].toString() + " phải là ngày sau hôm nay");

					return false;
				}

			}

		}
		for (Object[] object : inf_personnel_right) {
			if (object[1] instanceof JTextField) {
				if (((JTextField) object[1]).getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Bạn chưa nhập thông tin vào " + object[0].toString());
					((JTextField) object[1]).requestFocus();
					return false;
				}
			}

		}

		return true;
	}

	public void xuatExcel() {
		if (table.getRowCount() > 0) {
			ArrayList<NhanVien> list_Xuat = new ArrayList<NhanVien>();
			for (int i = 0; i < table.getRowCount(); i++) {
				NhanVien nv = nhanVien_DAO.getNhanVienFindByID(table.getValueAt(i, 0).toString());
				list_Xuat.add(nv);
			}
			writeToExcelNhanVien(list_Xuat);
		} else {
			JOptionPane.showMessageDialog(null, "Bạn cần có ít nhất 1 khách hàng để in danh sách");
		}
	}

	public JButton createButtonInPagePesonnel(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(130, 40));
		btn.addActionListener(e -> {
			if (nameButton.equals("Chọn hình ảnh")) {
				chooseImage();

			} else if (nameButton.equals("Thêm")) {
				if (regexthemKhachHang()) {
					themNhanVien();
				}

			} else if (nameButton.equals("Cập nhật")) {
				if (regexthemKhachHang()) {
					updateNhanVien();
				}

			} else if (nameButton.equals("Xóa trắng")) {
				xoaTrang();
			} else if (nameButton.equals("Lọc")) {
				
				locNhanVien();
			} else if (nameButton.equals("Xóa")) {
				xoaNhanVien();
			} else if (nameButton.equals("Tìm")) {
				timNhanVien();
			} else if (nameButton.equals("In danh sách")) {
				xuatExcel();
			} else {
				System.out.println(nameButton);
			}
		});
		return btn;
	}
}
