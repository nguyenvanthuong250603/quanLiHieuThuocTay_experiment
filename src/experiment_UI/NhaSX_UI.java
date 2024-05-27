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

import org.apache.commons.collections4.functors.IfClosure;
import org.apache.commons.compress.harmony.pack200.NewAttribute;

import com.toedter.calendar.JDateChooser;

import dao.KhachHang_DAO;
import dao.NhaSanXuat_DAO;
import dao.NhanVien_DAO;
import entity.*;
import experiment_UI.Generate_All.CustomTableCellRenderer;

import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.channels.NonWritableChannelException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class NhaSX_UI {
	private Object[][] objects_North, objects_custommer;
	private JComboBox cbDoTuoi, cbGioiTinh;
	private DefaultTableModel model;
	private JTable table;
	private JTextField jtextTuoi;
	private KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
	private JComboBox cbXepHang;
	private NhaSanXuat_DAO NhaSanXuat_DAO = new NhaSanXuat_DAO();

	public JPanel getNSX() {
		JPanel getKH = new JPanel(new BorderLayout());
		createTiTlePage(getKH, "QUẢN LÍ NHÀ SẢN XUẤT");
		getKH.add(getContent(), BorderLayout.CENTER);
		
		((JLabel) objects_custommer[0][1]).setText(generateCode("NSX"));
		getTableNhaSanXuat();
		return getKH;

	}

	public JPanel getSearch() {
		JPanel north = new JPanel();
//		north.setBorder(new EmptyBorder(30, 80, 30, 80));

		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		createTitle(north, "Tìm kiếm nhà sản xuất");

		Object[][] trage = { { "Mã nhà sản xuất", new JTextField(15) } };
		objects_North = trage;
		for (Object[] objects : trage) {
			if (objects[1] instanceof JTextField) {
				north.add(createNameAndTextField6((JTextField) objects[1], objects[0].toString()));
			} else {
				north.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));

			}
			north.add(Box.createHorizontalStrut(20));

		}

		north.add(buttonInPageCustommer("Tìm", "gift\\timkhach.png"));
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
		createTitle(managerment, "Danh sách nhà sản xuất");
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã nhà sản xuât", "Tên nhà sản xuất ", "Địa chỉ" };
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
		createTitle(inf, "Thông tin nhà sản xuất");
		inf.setLayout(new BoxLayout(inf, BoxLayout.Y_AXIS));

		Object[][] trage = { { "Mã nhà sản xuất", new JLabel() }, { "Tên nhà sản xuất", new JTextField() },
				{ "Địa chỉ", new JTextField(20) } };
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

		String[] object = { "Thêm", "gift\\add.png", "Xóa", "gift\\trash-bin.png", "Cập nhật", "gift\\update.png",
				"Xuất Execl", "gift\\excel-file.png" };
		for (int i = 0; i < object.length; i += 2) {
			footer.add(buttonInPageCustommer(object[i], object[i + 1]));

		}

		return footer;
	}

	public void xoaTrang() {
		model.setRowCount(0);
		getTableNhaSanXuat();
		((JLabel) objects_custommer[0][1]).setText(generateCode("NSX"));

		((JTextField) objects_custommer[1][1]).setText("");

		((JTextField) objects_custommer[2][1]).setText("");
		((JTextField) objects_North[0][1]).setText("");

	}

	private NhaSanXuat layKhachHangUI() {
		String maKh = ((JLabel) objects_custommer[0][1]).getText();
		String tenKh = getValueStringInJTextField(objects_custommer[1][1]);
		String diaChi = getValueStringInJTextField(objects_custommer[2][1]);
		NhaSanXuat kh = new NhaSanXuat(maKh, tenKh, diaChi);
		return kh;
	}

	private void themKhachHang() {
		if (regexThem()) {
			NhaSanXuat kh = layKhachHangUI();
			if (NhaSanXuat_DAO.themKhachHang(kh)) {
				JOptionPane.showMessageDialog(null, "Thêm nhà sản xuất thành công");
				Object[] row = { kh.getMaNSX(), kh.getTenNSX(), kh.getDiaChiNSX() };
				model.addRow(row);
				table.setRowSelectionInterval(model.getRowCount() - 1, model.getRowCount() - 1);
				xoaTrang();
			} else {
				JOptionPane.showMessageDialog(null, "Số điện thoại này đã có người sử dụng");
			}
		}
	}

	public boolean regexThem() {
		for (Object[] objects : objects_custommer) {
			if (objects[1] instanceof JTextField) {
				if (((JTextField) objects[1]).getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Bạn chưa nhập thông tin vào " + objects[0].toString());
					((JTextField) objects[1]).requestFocus();
					return false;
				}
				if (objects[0].toString().equals("Tuổi")) {
					if (!((JTextField) objects[1]).getText().matches("\\d+")) {
						JOptionPane.showMessageDialog(null,
								"Thông tin của " + objects[0].toString() + " phải là chữ số");
						((JTextField) objects[1]).requestFocus();
						return false;
					}
					int check = Integer.parseInt(((JTextField) objects[1]).getText());
					if (check < 0) {
						JOptionPane.showMessageDialog(null, objects[0].toString() + "tuổi của khách phải >0");
						((JTextField) objects[1]).requestFocus();
						return false;
					}
				}
			} else if (objects[1] instanceof JDateChooser) {
				if (((JDateChooser) objects[1]).getDate() == null) {
					JOptionPane.showMessageDialog(null, "Ngày của " + objects[0].toString() + " chưa được nhập");

					return false;
				}
				if (((JDateChooser) objects[1]).getDate().after(new java.util.Date())) {
					JOptionPane.showMessageDialog(null,
							"Ngày của " + objects[0].toString() + " phải là ngày sau hôm nay");

					return false;
				}

			}

		}
		return true;

	}

//	private void capNhatKhachHang() {
//		int index = table.getSelectedColumn();
//		if (index > -1) {
//			KhachHang kh = layKhachHangUI();
//			int question = JOptionPane.showConfirmDialog(null,
//					"Bạn có chắc muốn sửa thông tin khách hàng này hay không ?", "Chú ý", JOptionPane.YES_NO_OPTION);
//			if (question == JOptionPane.YES_OPTION) {
//				if (khachHang_DAO.xoaKhachHang(kh, "Cập nhật")) {
//					JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công");
//
//					xoaTrang();
//				}
//			}
//		} else {
//			JOptionPane.showMessageDialog(null, "Bạn phải chọn khách hàng để cập nhật");
//		}
//	}

	private void xoaKhachHang() {
		int index = table.getSelectedRow();
		if (index > -1) {
			int question = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhà sản xuất đã chọn hay không ?",
					"Chú ý", JOptionPane.YES_NO_OPTION);

			if (question == JOptionPane.YES_OPTION
					&& NhaSanXuat_DAO.xoaNhanVien(table.getValueAt(index, 0).toString())) {

				xoaTrang();
				JOptionPane.showMessageDialog(null, "Xóa nhà sản xuất thành công");

			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn nhà sản xuất để xóa");
		}
	}

	public void xuatFileExcel() {

		if (table.getRowCount() > 0) {
			ArrayList<NhaSanXuat> list_Xuat = new ArrayList<NhaSanXuat>();
			for (int i = 0; i < table.getRowCount(); i++) {
				NhaSanXuat kh = NhaSanXuat_DAO.getNhanVienFindByID(table.getValueAt(i, 0).toString());
				list_Xuat.add(kh);
			}
			writeToExcelNhaSanXuat(list_Xuat);
		} else {
			JOptionPane.showMessageDialog(null, "Bạn cần có ít nhất 1 khách hàng để in danh sách");
		}

	}

	public void getTableNhaSanXuat() {
		ArrayList<NhaSanXuat> list_NSX = NhaSanXuat_DAO.getNhaSanXuatDataBase();
		for (NhaSanXuat nhaSanXuat : list_NSX) {
			Object[] row = { nhaSanXuat.getMaNSX(), nhaSanXuat.getTenNSX(), nhaSanXuat.getDiaChiNSX() };
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
				((JLabel)objects_custommer[0][1]).setText(table.getValueAt(index, 0).toString());
				((JTextField)objects_custommer[1][1]).setText(table.getValueAt(index, 1).toString());
				((JTextField)objects_custommer[2][1]).setText(table.getValueAt(index, 2).toString());
				
			}
		});
	}
	public void timNSX() {
		String maNV = getValueStringInJTextField(objects_North[0][1]);
		if (maNV.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn phải nhập mã nhà sản xuất  để tìm");
		} else {
			NhaSanXuat nhanVien = NhaSanXuat_DAO.getNhanVienFindByID(maNV);

			if (nhanVien.getMaNSX() != null) {
				model.setRowCount(0);
				Object[] row = { nhanVien.getMaNSX(),nhanVien.getTenNSX(),nhanVien.getDiaChiNSX()};
				model.addRow(row);
				table.setModel(model);

			} else {
				JOptionPane.showMessageDialog(null,
						"Không tìm thấy nhà sản xuất trong hệ thống vui lòng kiểm tra thông tin tìm");
			}
		}
	}
	public void capNhatNSX() {
		String ma = getValueStringInJlabel(objects_custommer[0][1]);
		String ten  = getValueStringInJTextField(objects_custommer[1][1]);
		String diaChi =getValueStringInJTextField(objects_custommer[2][1]);
		if(table.getSelectedRow()>=0) {
			int question = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhà sản xuất đã chọn hay không ?",
					"Chú ý", JOptionPane.YES_NO_OPTION);
			if(question==JOptionPane.YES_OPTION) {
				if(NhaSanXuat_DAO.updateNSX(ten, diaChi, ma)) {
					JOptionPane.showMessageDialog(null, "Cập nhật thành công");
					xoaTrang();
					
				}else {
					System.out.println("loi");
				}
					
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn nhà sản xuất để cập nhật");
		}
	}
	public JButton buttonInPageCustommer(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(150, 50));
		btn.addActionListener(e -> {
			if (nameButton.equals("")) {
				xoaTrang();
			} else if (nameButton.equals("Thêm")) {
				themKhachHang();
			} else if (nameButton.equals("Tìm")) {
				timNSX();
			} else if (nameButton.equals("Xóa")) {
				xoaKhachHang();
			} else if (nameButton.equals("Xuất Execl")) {
				xuatFileExcel();
			}
			else if(nameButton.equals("Cập nhật")) {
				capNhatNSX();
			}

			else {

				System.out.println(nameButton);
			}
		});
		return btn;
	}

}
