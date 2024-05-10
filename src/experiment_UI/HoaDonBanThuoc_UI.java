package experiment_UI;

import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.Thuoc_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thuoc;
import experiment_UI.Generate_All.CustomTableCellRenderer;

public class HoaDonBanThuoc_UI {
	public Object[][] object_search, object_kh;
	private DefaultTableModel model;
	private JTable table;
	private DefaultTableModel model_product;
	private JTable table_product;
	private HoaDon_DAO hoaDon_DAO;
	private ChiTietHoaDon_DAO chiTietHoaDon_DAO = new ChiTietHoaDon_DAO();
	private Thuoc_DAO thuoc_DAO = new Thuoc_DAO();

	public JPanel getHoaDon(HoaDon_DAO hoaDon_DAO) {
		JPanel hd = new JPanel(new BorderLayout());
		hd.add(north(), BorderLayout.NORTH);
		hd.add(cenTer(), BorderLayout.CENTER);
		this.hoaDon_DAO = hoaDon_DAO;
		hienBangDuLieu();
		enterAction();
		return hd;
	}

	private JPanel north() {
		JPanel north = new JPanel(new BorderLayout());
		createTitle(north, "Tìm kiếm và lọc hóa đơn");

		JPanel north_center = new JPanel(new GridLayout(2, 4, 10, 10));
		Object[][] trage = { { "Mã hóa đơn", new JTextField() }, { "Từ ngày", new JDateChooser() },
				{ "Đến ngày", new JDateChooser() }, { "Doanh thu", new JComboBox() }, { "Mã KH", new JTextField() },
				{ "Số điện thoại", new JTextField() }, { "Loại hóa đơn", new JComboBox() },
				{ "Trạng thái", new JComboBox() } };

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

		model_product = new DefaultTableModel(column, 0);
		table_product = new JTable(model_product);

		table_product.setShowGrid(false);
		table_product.setShowVerticalLines(false);

		table_product.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table_product, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
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
		Object[][] btn = { { "Xuất danh sách", "gift\\list.png" }, { "In hóa đơn", "gift\\excel-file.png" } };
		for (Object[] objects : btn) {
			footer.add(createButtonInHoaDonBanHang(objects[0].toString(), objects[1].toString()));
		}
		return footer;

	}

	public void hienBangDuLieu() {

		ArrayList<HoaDon> hDons = hoaDon_DAO.getHoaDons();
		for (HoaDon hoaDon : hDons) {
			if (!hoaDon.getTinhTrang().equals("")) {
				NhanVien nv = getNV(hoaDon.getMaNV().getMaNV());
				Object[] row = { hoaDon.getMaHD(), nv.getHoTen(), hoaDon.getMaKh().getMaKH(),
						formatTime(hoaDon.getNgayTaoHoaDon()), hoaDon.getTongTien() };
				model.addRow(row);
			}
		}

		if (table.getRowCount() >= 1) {
			table.setRowSelectionInterval(0, 0);

			String maKH = table.getValueAt(table.getSelectedRow(), 2) == null ? ""
					: table.getValueAt(table.getSelectedRow(), 2).toString();
			defaultMouse(model_product, table_product, table.getValueAt(0, 0).toString(), maKH, object_kh, table);
		}
		table.setModel(model);
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

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
				model_product.setRowCount(0);
				int index = table.getSelectedRow();
				String maKH = table.getValueAt(index, 2) == null ? "" : table.getValueAt(index, 2).toString();
				String maHD = table.getValueAt(index, 0).toString();
				defaultMouse(model_product, table_product, maHD, maKH, object_kh, table);

			}
		});

	}

	private void defaultMouse(DefaultTableModel model_product, JTable table_product, String maHD, String maKH,
			Object[][] obj, JTable table) {

		ArrayList<ChiTietHoaDon> lChiTietHoaDons = chiTietHoaDon_DAO.getcChiTietHoaDons(maHD);

		for (ChiTietHoaDon ct : lChiTietHoaDons) {
			Thuoc th = thuoc_DAO.getThuocByID(ct.getMaThuoc().getMaThuoc());
			Object[] row_product = { ct.getMaThuoc().getMaThuoc(), th.getTenThuoc(), th.getDonVi(),
					ct.getSoLuongThuoc(), th.getGia(), ct.getThanhTien() };
			model_product.addRow(row_product);

		}
		table_product.setModel(model_product);
		if (!maKH.equals("")) {

			KhachHang kh = getKH(maKH, "");

			((JTextField) obj[0][1]).setText(kh.getTenKH());

			((JTextField) obj[1][1]).setText(kh.getsDT());
			((JTextField) obj[2][1]).setText(table.getValueAt(table.getSelectedRow(), 4).toString());
		} else {

			((JTextField) obj[0][1]).setText("");

			((JTextField) obj[1][1]).setText("");
			((JTextField) obj[2][1]).setText(table.getValueAt(table.getSelectedRow(), 4).toString());
		}
	}

	public void xoaTrang() {
		((JTextField) object_kh[0][1]).setText("");
		((JTextField) object_kh[1][1]).setText("");
		((JTextField) object_kh[2][1]).setText("");
		model.setRowCount(0);
		model_product.setRowCount(0);
		hienBangDuLieu();
		table.setModel(model);
	}

	public void timHoaDonTheoMa() {
		String ma = ((JTextField) object_search[0][1]).getText();
		HoaDon hd = hoaDon_DAO.getHoaDonByID(ma);
		xoaTrang();
		if (hd.getMaHD() != null) {
			model.setRowCount(0);
			NhanVien nv = getNV(hd.getMaNV().getMaNV());
			Object[] row = { hd.getMaHD(), nv.getHoTen(), hd.getMaKh().getMaKH(), formatTime(hd.getNgayTaoHoaDon()),
					hd.getTongTien() };
			model.addRow(row);
			table.setModel(model);

			ArrayList<ChiTietHoaDon> lChiTietHoaDons = hd.getListChiTietHoaDon();
			model_product.setRowCount(0);
			for (ChiTietHoaDon ct : lChiTietHoaDons) {

				Thuoc th = thuoc_DAO.getThuocByID(ct.getMaThuoc().getMaThuoc());
				Object[] row_product = { ct.getMaThuoc().getMaThuoc(), th.getTenThuoc(), th.getDonVi(),
						ct.getSoLuongThuoc(), th.getGia(), ct.getThanhTien() };
				model_product.addRow(row_product);

			}
			table_product.setModel(model_product);
			table.setRowSelectionInterval(0, 0);

			if (hd.getMaKh().getMaKH() != null) {
				KhachHang kh = getKH(hd.getMaKh().getMaKH(), "");
				((JTextField) object_kh[0][1]).setText(kh.getTenKH());
				((JTextField) object_kh[1][1]).setText(kh.getsDT());
				((JTextField) object_kh[2][1]).setText(table.getValueAt(table.getSelectedRow(), 4).toString());

			}
		} else {
			JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn trong hệ thống");
		}

	}

	public void enterAction() {

		ActionListener enter = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					timHoaDonTheoMa();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		};
		((JTextField) object_search[0][1]).addActionListener(enter);

	}

	private JButton createButtonInHoaDonBanHang(String nameBtn, String pathIcon) {
		JButton btn = createJbutton(nameBtn, pathIcon);
		btn.setPreferredSize(new Dimension(180, 35));
		btn.addActionListener(e -> {
			if (nameBtn.equals("")) {
				xoaTrang();
			} else if (nameBtn.equals("")) {

			} else if (nameBtn.equals("")) {

			} else {
				System.out.println(nameBtn);
			}
		});
		return btn;
	}
}
