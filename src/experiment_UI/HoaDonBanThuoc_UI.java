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
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

import com.toedter.calendar.JDateChooser;

import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
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
	public KhachHang_DAO khachHang_DAO = new KhachHang_DAO();

	public JPanel getHoaDon(HoaDon_DAO hoaDon_DAO) {
		JPanel hd = new JPanel(new BorderLayout());
		createTiTlePage(hd, "Hóa đơn bán hàng");
		JPanel center = new JPanel(new BorderLayout());

		center.add(north(), BorderLayout.NORTH);
		center.add(cenTer(), BorderLayout.CENTER);
		hd.add(center, BorderLayout.CENTER);
		this.hoaDon_DAO = hoaDon_DAO;
		ArrayList<HoaDon> hDons = hoaDon_DAO.getHoaDons();
		hienBangDuLieu(hDons);
		enterAction();
		return hd;
	}

	private JPanel north() {
		JPanel north = new JPanel(new BorderLayout());
		createTitle(north, "Tìm kiếm và lọc hóa đơn");
		String[] optionDoanhThu = { "", "Dưới 100k", "Từ 100 - 500k", "500k-1 triệu", "1 triệu trở lên" };
		String[] optionLoaiHoaDon = { "", "Hóa đơn bán hàng", "Hóa đơn bán lẻ" };
		JPanel north_center = new JPanel(new GridLayout(2, 3, 10, 10));
		Object[][] trage = { { "Mã hóa đơn", new JTextField() }, { "Từ ngày", new JDateChooser() },
				{ "Đến ngày", new JDateChooser() }, { "Số điện thoại", new JTextField() },
				{ "Doanh thu", new JComboBox(optionDoanhThu) }, { "Loại hóa đơn", new JComboBox(optionLoaiHoaDon) } };

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

		north_btn.add(createButtonInHoaDonBanHang("Lọc", ""));
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

	public void hienBangDuLieu(ArrayList<HoaDon> hDons) {

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
		((JTextField) object_search[0][1]).setText("");
		((JDateChooser) object_search[1][1]).setDate(null);
		((JDateChooser) object_search[2][1]).setDate(null);
		((JTextField) object_search[3][1]).setText("");
		((JComboBox) object_search[4][1]).setSelectedIndex(0);
		((JComboBox) object_search[5][1]).setSelectedIndex(0);
		model.setRowCount(0);
		model_product.setRowCount(0);
		ArrayList<HoaDon> hDons = hoaDon_DAO.getHoaDons();
		hienBangDuLieu(hDons);
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

	public void inLaiHoaDon() {
		String maHD = table.getValueAt(table.getSelectedRow(), 0).toString();
		HoaDon hd = hoaDon_DAO.getHoaDonByID(maHD);
		if (hd.getMaKh().getMaKH() == null) {
			int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn in lại hóa đơn này không ?", "Lưu ý",
					JOptionPane.YES_NO_OPTION);
			if (recomment == JOptionPane.YES_OPTION) {

				generateInvoiceBanLe(hd, hd.getTongTien(), 0, 0);
			}
		} else {
			KhachHang kh = khachHang_DAO.getKhachHangByID(table.getValueAt(table.getSelectedRow(), 2).toString(), "");
			String xh = kh.getXepHang();
			String kmString = "";
			if (xh.equals("Đồng")) {
				kmString = "0%";
			} else if (xh.equals("Bạc")) {
				kmString = "1%";
			} else if (xh.equals("Vàng")) {
				kmString = "2%";
			} else if (xh.equals("Bạch kim")) {
				kmString = "3%";
			} else if (xh.equals("Kim cương")) {
				kmString = "4.5%";
			}
			int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn in lại hóa đơn này không ?", "Lưu ý",
					JOptionPane.YES_NO_OPTION);
			if (recomment == JOptionPane.YES_OPTION) {

				generateInvoice(hd, hd.getTongTien(), 0, kmString, 0);
			}
		}
	}

	public void locNangCao() {
		String sdt = getValueStringInJTextField(object_search[3][1]);
		String doanhThu = getValueInComboBox((JComboBox) object_search[4][1]);
		String loaiHoaDon = getValueInComboBox((JComboBox) object_search[5][1]);
		ArrayList<HoaDon> lHoaDons = new ArrayList<HoaDon>();
		if (doanhThu.equals("Dưới 100k")) {
			lHoaDons = hoaDon_DAO.getHoaDonDanhSachThongKe(0, 100000);
		} else if (doanhThu.equals("Từ 100 - 500k")) {
			lHoaDons = hoaDon_DAO.getHoaDonDanhSachThongKe(100000, 500000);
		} else if (doanhThu.equals("500k-1 triệu")) {
			lHoaDons = hoaDon_DAO.getHoaDonDanhSachThongKe(500000, 1000000);
		} else if (doanhThu.equals("1 triệu trở lên")) {
			lHoaDons = hoaDon_DAO.getHoaDonDanhSachThongKe(1000000, 10000000);
		}

		if (((JDateChooser) object_search[1][1]).getDate() == null
				&& ((JDateChooser) object_search[1][1]).getDate() == null) {
			if (loaiHoaDon.equals("")) {
				if (sdt.equals("") && !doanhThu.equals("")) {
					hienBangHoaDonByLoc(lHoaDons);
				} else if (!sdt.equals("") && !doanhThu.equals("")) {
					ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();

					if (doanhThu.equals("Dưới 100k")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachSDT(sdt, 0, 100000);
					} else if (doanhThu.equals("Từ 100 - 500k")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachSDT(sdt, 100000, 500000);
					} else if (doanhThu.equals("500k-1 triệu")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachSDT(sdt, 500000, 1000000);
					} else if (doanhThu.equals("1 triệu trở lên")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachSDT(sdt, 1000000, 10000000);
					}

					hienBangHoaDonByLoc(lhd);
				} else if (!sdt.equals("") && doanhThu.equals("")) {
					ArrayList<HoaDon> lhd = hoaDon_DAO.getHoaDonDanhSachSDT(sdt, 0, 1000000000);
					if (lhd.size() > 0)
						hienBangHoaDonByLoc(lhd);
					else {
						JOptionPane.showConfirmDialog(null, "Không tìm thấy hóa đơn theo số điện thoại");
					}

				}

			} else if (loaiHoaDon.equals("Hóa đơn bán lẻ") && !sdt.equals("")) {
				JOptionPane.showMessageDialog(null, "Hóa đơn có số điện thoại phải là hóa đơn bán hàng");
			} else if (sdt.equals("")) {
				if (!doanhThu.equals("") && !loaiHoaDon.equals("")) {
					ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();
					int loai = loaiHoaDon.equals("Hóa đơn bán hàng") ? 1 : 0;
					if (doanhThu.equals("Dưới 100k")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachDoanhThuAndLoaiHd(loai, 0, 100000);
					} else if (doanhThu.equals("Từ 100 - 500k")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachDoanhThuAndLoaiHd(loai, 100000, 500000);
					} else if (doanhThu.equals("500k-1 triệu")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachDoanhThuAndLoaiHd(loai, 500000, 1000000);
					} else if (doanhThu.equals("1 triệu trở lên")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachDoanhThuAndLoaiHd(loai, 1000000, 10000000);
					}
					hienBangHoaDonByLoc(lhd);
				}
				if (doanhThu.equals("") && !loaiHoaDon.equals("")) {
					ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();
					int loai = loaiHoaDon.equals("Hóa đơn bán hàng") ? 1 : 0;
					lhd = hoaDon_DAO.getHoaDonDanhSachByLoaiHd(loai);
					System.err.println("oj");
					hienBangHoaDonByLoc(lhd);
				}
			}

		} else if (((JDateChooser) object_search[1][1]).getDate() != null
				&& ((JDateChooser) object_search[1][1]).getDate() != null) {
			LocalDate ngayBD = getDateJDateChoor(object_search[1][1]);
			LocalDate ngayKT = getDateJDateChoor(object_search[2][1]);
			if (ngayKT.isBefore(ngayBD)) {
				JOptionPane.showMessageDialog(null, "Ngày kết thúc phải sau ngày bắt đầu");
			}
			if (sdt.equals("") && doanhThu.equals("") && loaiHoaDon.equals("")) {
				ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();
				lhd = hoaDon_DAO.getHoaDonDanhSachByNgay(ngayBD, ngayKT);
				hienBangHoaDonByLoc(lhd);
			} else if (sdt.equals("")) {
				if (!doanhThu.equals("") && !loaiHoaDon.equals("")) {
					ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();
					int loai = loaiHoaDon.equals("Hóa đơn bán hàng") ? 1 : 0;
					if (doanhThu.equals("Dưới 100k")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAnddoanhThuAndLoaiHD(loai, ngayBD, ngayKT, 0, 100000);
					} else if (doanhThu.equals("Từ 100 - 500k")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAnddoanhThuAndLoaiHD(loai, ngayBD, ngayKT, 100000,
								500000);
					} else if (doanhThu.equals("500k-1 triệu")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAnddoanhThuAndLoaiHD(loai, ngayBD, ngayKT, 500000,
								1000000);
					} else if (doanhThu.equals("1 triệu trở lên")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAnddoanhThuAndLoaiHD(loai, ngayBD, ngayKT, 1000000,
								10000000);
					}
					hienBangHoaDonByLoc(lhd);

				} else if (!doanhThu.equals("") && loaiHoaDon.equals("")) {
					ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();

					if (doanhThu.equals("Dưới 100k")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAnddoanhThu(ngayBD, ngayKT, 0, 100000);
					} else if (doanhThu.equals("Từ 100 - 500k")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAnddoanhThu(ngayBD, ngayKT, 100000, 500000);
					} else if (doanhThu.equals("500k-1 triệu")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAnddoanhThu(ngayBD, ngayKT, 500000, 1000000);
					} else if (doanhThu.equals("1 triệu trở lên")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAnddoanhThu(ngayBD, ngayKT, 1000000, 10000000);
					}
					hienBangHoaDonByLoc(lhd);
				} else if (doanhThu.equals("") && !loaiHoaDon.equals("")) {
					ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();
					int loai = loaiHoaDon.equals("Hóa đơn bán hàng") ? 1 : 0;
					lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAndLoaiHD(loai, ngayBD, ngayKT);
					hienBangHoaDonByLoc(lhd);
				}
			} else if (loaiHoaDon.equals("")) {
				if (!sdt.equals("") && !doanhThu.equals("")) {
					ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();
					if (doanhThu.equals("Dưới 100k")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAndSDTandDoanhThu(sdt, ngayBD, ngayKT, 0, 100000);
					} else if (doanhThu.equals("Từ 100 - 500k")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAndSDTandDoanhThu(sdt, ngayBD, ngayKT, 100000, 500000);
					} else if (doanhThu.equals("500k-1 triệu")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAndSDTandDoanhThu(sdt, ngayBD, ngayKT, 500000, 1000000);
					} else if (doanhThu.equals("1 triệu trở lên")) {
						lhd = hoaDon_DAO.getHoaDonDanhSachByNgayAndSDTandDoanhThu(sdt, ngayBD, ngayKT, 1000000,
								10000000);
					}
					hienBangHoaDonByLoc(lhd);
				}
			}
		}

	}

	public void hienBangHoaDonByLoc(ArrayList<HoaDon> lhd) {
		model.setRowCount(0);
		model_product.setRowCount(0);
		hienBangDuLieu(lhd);
	}

	public void enterAcction() {
		ActionListener enter3 = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					timHoaDon();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		};
		((JTextField) object_search[0][0]).addActionListener(enter3);
	}

	public void timHoaDon() {
		String ma = ((JTextField) object_search[0][0]).getText();
		HoaDon hd = hoaDon_DAO.getHoaDonByID(ma);
		model.setRowCount(0);
		model_product.setRowCount(0);
		if (hd != null) {
			NhanVien nv = getNV(hd.getMaNV().getMaNV());
			Object[] row = { hd.getMaHD(), nv.getHoTen(), hd.getMaKh().getMaKH(), formatTime(hd.getNgayTaoHoaDon()),
					hd.getTongTien() };
			model.addRow(row);
			table.setModel(model);

			ArrayList<ChiTietHoaDon> lChiTietHoaDons = hd.getListChiTietHoaDon();
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
				((JTextField) object_kh[0][0]).setText(kh.getTenKH());
				((JTextField) object_kh[1][0]).setText(kh.getTenKH());
				((JTextField) object_kh[2][0]).setText(hd.getTongTien() + "");

			}
		} else {
			JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn trong hệ thống");
			ArrayList<HoaDon> hDons = hoaDon_DAO.getHoaDons();
			hienBangDuLieu(hDons);
		}

	}

	public void xuatDanhSach() {
		ArrayList<HoaDon> listHoaDon = new ArrayList<HoaDon>();
		for (int i = 0; i < table.getRowCount(); i++) {
			String maHD = table.getValueAt(i, 0).toString();
			HoaDon hd = hoaDon_DAO.getHoaDonByID(maHD);
			listHoaDon.add(hd);
		}
		int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn in danh sách hóa đơn hay không ?",
				"Lưu ý", JOptionPane.YES_NO_OPTION);
		if (recomment == JOptionPane.YES_OPTION) {

			writeToExcelHoaDon(listHoaDon);
		}
	}

	private JButton createButtonInHoaDonBanHang(String nameBtn, String pathIcon) {
		JButton btn = createJbutton(nameBtn, pathIcon);
		btn.setPreferredSize(new Dimension(180, 35));
		btn.addActionListener(e -> {
			if (nameBtn.equals("")) {
				xoaTrang();
			} else if (nameBtn.equals("In hóa đơn")) {
				inLaiHoaDon();
			} else if (nameBtn.equals("Lọc")) {
				locNangCao();
			} else if (nameBtn.equals("Xuất danh sách")) {
				xuatDanhSach();
			}

			else {
				System.out.println(nameBtn);
			}
		});
		return btn;
	}
}
