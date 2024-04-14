package experiment_UI;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.compress.harmony.pack200.NewAttribute;

import com.toedter.calendar.JDateChooser;

import dao.KhachHang_DAO;
import entity.KhachHang;
import entity.NhanVien;
import experiment_UI.Generate_All.CustomTableCellRenderer;

import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.channels.NonWritableChannelException;
import java.time.LocalDate;
import java.util.Calendar;

public class KhachHang_UI {
	private Object[][] objects_North, objects_custommer;
	private JComboBox cbDoTuoi, cbGioiTinh;
	private DefaultTableModel model;
	private JTable table;
	private JTextField jtextTuoi;
	private KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
	public JPanel getKhachHang() {
		JPanel getKH = new JPanel(new BorderLayout());
		createTiTlePage(getKH, "QUẢN LÍ KHÁCH HÀNG");
		getKH.add(getContent(), BorderLayout.CENTER);

		hienTableKhachHang(table, model, objects_custommer);
		renderTuoi();
		((JLabel) objects_custommer[0][1]).setText(generateCode("KH"));
		return getKH;

	}

	public JPanel getSearch() {
		JPanel north = new JPanel();
//		north.setBorder(new EmptyBorder(30, 80, 30, 80));

		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		createTitle(north, "Tìm kiếm và lọc");
		String[] gioiTinh = { "", "Nam", "Nữ" };
		Object[][] trage = { { "Số điện thoại", new JTextField(15) },
				{ "Giới tính ", cbGioiTinh = new JComboBox(gioiTinh) }, { "Điểm tích lũy", new JComboBox(gioiTinh) } };
		objects_North = trage;
		for (Object[] objects : trage) {
			if (objects[1] instanceof JTextField) {
				north.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));
			} else {
				north.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));

			}
			north.add(Box.createHorizontalStrut(20));

		}

		north.add(buttonInPageCustommer("Lọc", "gift\\\\reset.png"));
		north.add(Box.createHorizontalStrut(10));

		north.add(buttonInPageCustommer("", "gift\\reset.png"));

		return north;
	}

	public JPanel getContent() {
		JPanel content = new JPanel(new BorderLayout());
		content.add(getSearch(), BorderLayout.NORTH);
		content.add(getCenter(), BorderLayout.CENTER);
		content.add(fotter_East(), BorderLayout.SOUTH);
		content.add(getEast(), BorderLayout.EAST);
		return content;
	}

	public JPanel getCenter() {
		JPanel managerment = new JPanel();
		createTitle(managerment, "Danh sách khách hàng");
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã khách hàng", "Tên khách hàng ", "Điểm tích lũy", "Số điện thoại", "Địa chỉ" };
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
		Object[][] trage = { { "Mã khách hàng", new JLabel() }, { "Tên khách hàng", new JTextField() },
				{ "Ngày sinh", new JDateChooser() }, { "Tuổi", new JTextField(20) },
				{ "Giới tính", cbDoTuoi = new JComboBox(gioiTinh), }, { "Số điện thoại", new JTextField() },
				{ "Địa chỉ", new JTextField() }, {"Xếp hạng", new JTextField()},{ "Điểm tích lũy", new JLabel() } };
		objects_custommer = trage;
		for (Object[] objects : objects_custommer) {
			if (objects[1] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel2(objects[0].toString()), BorderLayout.WEST);
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

	
		String[] object = { "Thêm", "gift\\trash-bin.png", "Xóa", "gift\\excel-file.png", "Cập nhật", "gift\\trash-bin.png",
				"Xuất file", "gift\\excel-file.png" };
		for (int i = 0; i < object.length; i += 2) {
			footer.add(buttonInPageCustommer(object[i], object[i + 1]));
			
		}

		return footer;
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
		((JTextField) objects_custommer[7][1]).setText("");
		((JLabel) objects_custommer[8][1]).setText("");

	}
	private KhachHang layKhachHangUI() {
		String maKh = ((JLabel) objects_custommer[0][1]).getText();
		String tenKh = getValueStringInJTextField(objects_custommer[1][1]);
		LocalDate ngaySing = getDateJDateChoor(objects_custommer[2][1]);
		int tuoi = getValueIntỊntextField(objects_custommer[3][1]);
		String gt = getValueInComboBox((JComboBox) objects_custommer[4][1]);
		String sdt = getValueStringInJTextField(objects_custommer[5][1]);
		String diaChi = getValueStringInJTextField(objects_custommer[6][1]);
		String xepHang = getValueStringInJTextField(objects_custommer[7][1]);
		KhachHang kh = new KhachHang(maKh, tenKh, ngaySing, tuoi, transGenderToSQL(gt), sdt, diaChi, 0,xepHang);
		return kh;
	}
	private void themKhachHang() {
		KhachHang kh = layKhachHangUI();
		if (khachHang_DAO.themKhachHang(kh)) {
			JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công");
			Object[] row = { kh.getMaKH(), kh.getTenKH(), kh.getDiemThanhVien(), kh.getsDT(), kh.getDiaCHi()};
			model.addRow(row);
			table.setRowSelectionInterval(model.getRowCount() - 1, model.getRowCount() - 1);
			xoaTrang();
		} else {
			JOptionPane.showMessageDialog(null, "Số điện thoại này đã có người sử dụng");
		}
	}
	private void capNhatKhachHang() {
		int index = table.getSelectedColumn();
		if (index > -1) {
			KhachHang kh = layKhachHangUI();
			int question = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn sửa thông tin khách hàng này hay không ?",
					"Chú ý", JOptionPane.YES_NO_OPTION);
			if (question == JOptionPane.YES_OPTION) {
				if (khachHang_DAO.xoaKhachHang(kh, "Cập nhật")) {
					JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công");
				
					xoaTrang();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn khách hàng để cập nhật");
		}
	}
	private void xoaKhachHang() {
		int index = table.getSelectedColumn();
		if (index > -1) {
			int question = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa khách hàng đã chọn hay không ?",
					"Chú ý", JOptionPane.YES_NO_OPTION);
			KhachHang kh = new KhachHang(table.getValueAt(index, 0).toString());
			if (question == JOptionPane.YES_OPTION && khachHang_DAO.xoaKhachHang(kh, "")){

				JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công");
				
				xoaTrang();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn khách hàng để xóa");
		}
	}
	public JButton buttonInPageCustommer(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(150, 50));
		btn.addActionListener(e -> {
			if (nameButton.equals("")) {
				xoaTrang();
			}else if(nameButton.equals("Thêm")) {
				themKhachHang();
			}
			else if(nameButton.equals("Cập nhật")) {
				capNhatKhachHang();
			}
			else if(nameButton.equals("Xóa")) {
				xoaKhachHang();
			}
			else {

				System.out.println(nameButton);
			}
		});
		return btn;
	}

}
