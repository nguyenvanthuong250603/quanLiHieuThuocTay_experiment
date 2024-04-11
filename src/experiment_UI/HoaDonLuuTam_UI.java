package experiment_UI;

import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.AsyncBoxView.ChildLocator;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.toedter.calendar.JDateChooser;

import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.NhanVien_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thuoc;
import experiment_UI.Generate_All.CustomTableCellRenderer;

public class HoaDonLuuTam_UI {

	private JTable table, table_trage;
	private Object[][] object_kh;
	private JFrame frame;

	private JTable table_product;

	private ArrayList<ChiTietHoaDon> cthdon = new ArrayList<ChiTietHoaDon>();

	private DefaultTableModel model_product;

	private DefaultTableModel model;
	private JTextField textMaKH;
	private Object[][] obj_customer, obj_sell;
	private JLabel dtl;
	private HoaDon_DAO hDao = new HoaDon_DAO();
	private JCheckBox cBox;
	private ChiTietHoaDon_DAO ctDao = new ChiTietHoaDon_DAO();
	private JTextField timSdt;

	public synchronized void getHoaDonLuuTam(JTextField textMaKhach, Object[][] customer, Object[][] sell, JLabel dtl,
			JTable tablee, JCheckBox cb) {
		frame = new JFrame();
		frame.setTitle("Tìm Kiếm Khách Hàng");

		frame.setSize(1100, 800);
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.add(north(), BorderLayout.NORTH);
		frame.add(cenTer(), BorderLayout.CENTER);
		frame.add(footer(), BorderLayout.SOUTH);
		hienTableTrongHoaDon(table, model, table_product, model_product, object_kh, "", 3);
		if (table.getRowCount() >= 1) {
			this.textMaKH = textMaKhach;
			this.obj_customer = customer;
			this.obj_sell = sell;
			this.dtl = dtl;
			this.cBox = cb;
			this.table_trage = tablee;
		}
		frame.setVisible(true);
		frame.setResizable(false);

	}

	private JPanel north() {
		JPanel north = new JPanel();
		createTitle(north, "Tìm kiếm hóa đơn");
		north.add(createNameAndTextField2(timSdt = new JTextField(20), "Số điện thoại"));
		north.add(createButtonInHoaDonLuuTam("Tìm", ""));
		north.add(createButtonInHoaDonLuuTam("", "gift\\reset.png"));
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

		return managerment;
	}

	public JPanel inf_product() {
		JPanel input = new JPanel(new BorderLayout());
		createTitle(input, "Danh sách sản phẩm");

		String[] column = { "Mã thuốc", "Tên thuốc ", "Đơn vị", "Số lượng", "Giá", "Thành tiền" };

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
		Object[][] btn = { { "Xóa đơn", "" }, { "Xóa tất cả", "" }, { "Chọn", "" }, { "Thoát", "exit.png" } };
		for (Object[] objects : btn) {
			footer.add(createButtonInHoaDonLuuTam(objects[0].toString(), objects[1].toString()));
		}
		return footer;

	}

	public void xoaDon() {
		int index = table.getSelectedRow();
		String maHD = table.getValueAt(index, 0).toString();

		if (index >= 0) {
			int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa đơn thuốc ", "Lưu ý",
					JOptionPane.YES_NO_OPTION);
			if (recomment == JOptionPane.YES_OPTION) {
				for (int i = 0; i < table_product.getRowCount(); i++) {
					String mathuoc = table_product.getValueAt(i, 0).toString();
					if (ctDao.xoaChiTiet(maHD,mathuoc)) {
						if (hDao.xoaHoaDonLuuTam(maHD)) {
							JOptionPane.showMessageDialog(null, "Xóa đơn thành công");
							model.setRowCount(0);
							model_product.setRowCount(0);
							hienTableTrongHoaDon(table, model, table_product, model_product, object_kh, "", 3);
							table.setModel(model);
						}
					}
				}

			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn hóa đơn để xóa");
		}

	}

	public void chonHoaDon() {
		if (table.getRowCount() > 0) {
			int index = table.getSelectedRow();
			((JLabel) obj_sell[0][1]).setText(table.getValueAt(index, 0).toString());

			HoaDon hd = hDao.getHoaDonByID(((JLabel) obj_sell[0][1]).getText());

			textMaKH.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
			KhachHang kh = getKH(hd.getMaKh().getMaKH(), "");
			textMaKH.setText(hd.getMaKh().getMaKH());
			((JTextField) obj_customer[0][1]).setText(kh.getTenKH());
			((JTextField) obj_customer[1][1]).setText(kh.getTuoi() + "");
			((JComboBox) obj_customer[2][1]).setSelectedItem(transGender(kh.isGioiTinh()));
			((JTextField) obj_customer[3][1]).setText(kh.getsDT());
			dtl.setText(kh.getDiemThanhVien() + "");
			cBox.setSelected(true);

			((JTextField) obj_sell[1][1]).setText(formatTime(hd.getNgayTaoHoaDon()));
			double tong = 0;
			for (int i = 0; i < table_product.getRowCount(); i++) {
				tong += Double.parseDouble(table_product.getValueAt(i, 5).toString());
			}
			((JTextField) obj_sell[2][1]).setText(tong + "");

			((JComboBox) obj_sell[3][1]).setSelectedItem(hd.getHinhThucThanhToan());
			table_trage.setModel(model_product);
			frame.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Không có hóa đơn nào để xử lí");
		}

	}

	public void timTheoSdt() {
		String sdt = timSdt.getText();
		ArrayList<HoaDon> hd = hDao.getHoaDonToLuuTam(sdt, 3);
		model.setRowCount(0);
		model_product.setRowCount(0);
		for (HoaDon hoaDon : hd) {
			NhanVien nv = getNV(hoaDon.getMaNV().getMaNV());
			Object[] row = { hoaDon.getMaHD(), nv.getHoTen(), hoaDon.getMaKh().getMaKH(),
					formatTime(hoaDon.getNgayTaoHoaDon()), hoaDon.getTongTien() };
			model.addRow(row);
			((JTextField) object_kh[2][1]).setText(hoaDon.getTongTien()+"");
		}
		table.setModel(model);
		ArrayList<ChiTietHoaDon> lChiTietHoaDons = ctDao.getcChiTietHoaDons(table.getValueAt(0, 0).toString());
		for (ChiTietHoaDon ct : lChiTietHoaDons) {
			Object[] row_product = { ct.getMaThuoc().getMaThuoc(), ct.getTenThuoc(), ct.getDonVi(), ct.getSoLuong(),
					ct.getDonGia(), ct.getThanhTien() };
			model_product.addRow(row_product);
			
		}
		table_product.setModel(model_product);
		KhachHang kh = getKH(table.getValueAt(0, 2).toString(), "");
		((JTextField) object_kh[0][1]).setText(kh.getTenKH());

		((JTextField) object_kh[1][1]).setText(kh.getsDT());
		

	}
	public void xoaTatCa() {
		
		int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa tất cả đơn lưu tạm ", "Lưu ý",
				JOptionPane.YES_NO_OPTION);
		if (recomment == JOptionPane.YES_OPTION) {

	
			for(int i= 0; i<table.getRowCount();i++) {
				String ma = table.getValueAt(i, 0).toString();
				ctDao.xoaChiTiet("","");		
			}
			if(hDao.xoaHoaDonLuuTam("")) {
			JOptionPane.showMessageDialog(null, "Xóa thành công");	
			}
			
			xoaTrang();
			

		}
		
		
	}
	public void xoaTrang() {
		((JTextField) obj_customer[0][1]).setText("");
		((JTextField) obj_customer[1][1]).setText("");
		((JComboBox) obj_customer[2][1]).setSelectedItem(0);
		((JTextField) obj_customer[3][1]).setText("");
		model.setRowCount(0);
		model_product.setRowCount(0);
		hienTableTrongHoaDon(table, model, table_product, model_product, object_kh, "", 3);
		table.setModel(model);
	}
	private JButton createButtonInHoaDonLuuTam(String nameBtn, String pathIcon) {
		JButton btn = createJbutton(nameBtn, pathIcon);
		btn.setPreferredSize(new Dimension(140, 35));
		btn.addActionListener(e -> {
			if (nameBtn.equals("Thoát")) {

				frame.dispose();
			} else if (nameBtn.equals("Chọn")) {
				chonHoaDon();
			} else if (nameBtn.equals("Xóa đơn")) {
				xoaDon();
			} else if (nameBtn.equals("")) {
			xoaTrang();
			} else if (nameBtn.equals("Tìm")) {
				timTheoSdt();
			}
			else if (nameBtn.equals("Xóa tất cả")) {
				xoaTatCa();
			}
		});
		return btn;
	}
}
