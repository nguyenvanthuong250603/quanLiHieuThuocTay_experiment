package experiment_UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.Box;
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

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.toedter.calendar.JDateChooser;

import dao.HoaDon_DAO;
import entity.HoaDon;
import entity.NhanVien;
import experiment_UI.Generate_All.CustomTableCellRenderer;

import static experiment_UI.Generate_All.*;

public class ThongKe_UI {
	private Object[][] obj, obj2;
	private DefaultTableModel model;
	private JTable table;
	private HoaDon_DAO hoaDon_DAO = new HoaDon_DAO();

	public JPanel getThongKe() {
		JPanel thongKe = new JPanel(new BorderLayout());
		createTiTlePage(thongKe, "BÁO CÁO THỐNG KÊ NHÂN VIÊN");

		thongKe.add(manageMent(), BorderLayout.CENTER);

		return thongKe;

	}

	public JPanel manageMent() {
		JPanel north = new JPanel(new GridLayout(2, 1, 10, 10));
		north.add(getNorth());
		north.add(table_information());
		return north;
	}

	public JPanel getNorth() {
		JPanel north = new JPanel(new BorderLayout());
		createTitle(north, "Thống kê");

		Object[][] trage = { { "Mã nhân viên", new JTextField() }, { "Từ ngày", new JDateChooser() },
				{ "Đến ngày", new JDateChooser() } };
		obj = trage;
		JPanel dia = new JPanel();
		for (Object[] objects : trage) {
			if (objects[1] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel(objects[0].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(10, 5, 10, 5));
				((Component) objects[1]).setPreferredSize(new Dimension(130, 40));
				t.add((Component) objects[1], BorderLayout.CENTER);

				dia.add(t);

			} else {
				dia.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));

			}
			dia.add(Box.createHorizontalStrut(10));
		}
		dia.add(buttonThongKe("Thống kê", ""));
		dia.add(buttonThongKe("In thống kê", ""));
		dia.add(buttonThongKe("", "gift\\reset.png"));
		north.add(dia, BorderLayout.NORTH);

		JPanel cacMuc = new JPanel(new GridLayout(4, 2, 10, 10));
		Object[][] trage2 = { { "Tổng hóa đơn", new JLabel() }, { "Doanh thu", new JLabel() },
				{ "Hóa đơn bán hàng", new JLabel() }, { "Hóa đơn bán lẻ", new JLabel() },
				{ "Hóa đơn bán ra", new JLabel() }, { "Hóa đơn hoàn trả", new JLabel() },
				{ "Hóa đơn đổi thuốc", new JLabel() } };
		obj2 = trage2;
		for (Object[] objects : trage2) {
			if (objects[1] instanceof JLabel) {
				JPanel t = new JPanel(new BorderLayout());
				JLabel x;
				t.add(x = new JLabel(objects[0].toString()), BorderLayout.WEST);
				x.setFont(new Font("Arial", Font.BOLD, 18));

				t.add((JLabel) objects[1], BorderLayout.CENTER);
				((JLabel) objects[1]).setFont(new Font("Arial", Font.BOLD, 18));
				((JLabel) objects[1]).setBorder(new EmptyBorder(0, 60, 0, 0));
				((JLabel) objects[1]).setForeground(Color.red);
				t.setBorder(new EmptyBorder(0, 10, 0, 10));

				cacMuc.add(t);
			}
		}

		north.add(cacMuc, BorderLayout.CENTER);
		return north;
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

	public void thongKe() {

		// Tính số ngày giữa hai ngày
//		long daysBetween = ChronoUnit.DAYS.between(date1, date2);
		if (regex()) {
			String value = getValueStringInJTextField(obj[0][1]);
			String maGui = value.substring(6);

			model.setRowCount(0);
			ArrayList<HoaDon> lhd = hoaDon_DAO.getHoaDonThongKeByNhanVien(maGui);
			double doanhThu = 0;
			int hoaDonBanHang = 0;
			int hoaDonBanLe = 0;
			int hdbr = 0;
			int hddt = 0;
			int hdht = 0;

			for (HoaDon hoaDon : lhd) {
				if (((JDateChooser) obj[1][1]).getDate() == null && ((JDateChooser) obj[2][1]).getDate() == null) {
					LocalDate ngayDate = hoaDon.getNgayTaoHoaDon();
					if (ngayDate.isEqual(LocalDate.now())) {
						hienBang(hoaDon);
						doanhThu += hoaDon.getTongTien();
						if (hoaDon.getTinhTrang().equals("Bán ra"))
							hdbr += 1;
						else if (hoaDon.getTinhTrang().equals("Hoàn trả"))
							hdht += 1;
						else if (hoaDon.getTinhTrang().equals("Đổi thuốc"))
							hddt += 1;
						if (hoaDon.getLoaiHoaDon() == true)
							hoaDonBanHang += 1;
						else
							hoaDonBanLe += 1;
					}

				} else {
					LocalDate date1 = ((JDateChooser) obj[1][1]).getDate().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();
					LocalDate date2 = ((JDateChooser) obj[2][1]).getDate().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();
					LocalDate ngayDate = hoaDon.getNgayTaoHoaDon();
					if (date1.isBefore(ngayDate) && date2.isAfter(ngayDate)) {
						hienBang(hoaDon);
						doanhThu += hoaDon.getTongTien();
						if (hoaDon.getTinhTrang().equals("Bán ra"))
							hdbr += 1;
						else if (hoaDon.getTinhTrang().equals("Hoàn trả"))
							hdht += 1;
						else if (hoaDon.getTinhTrang().equals("Đổi thuốc"))
							hddt += 1;
						if (hoaDon.getLoaiHoaDon() == true)
							hoaDonBanHang += 1;
						else
							hoaDonBanLe += 1;
					}

				}
			}
			table.setModel(model);
			((JLabel) obj2[0][1]).setText(table.getRowCount() + "");

			((JLabel) obj2[1][1]).setText(doanhThu + "");
			((JLabel) obj2[2][1]).setText(hoaDonBanHang + "");
			((JLabel) obj2[3][1]).setText(hoaDonBanLe + "");
			((JLabel) obj2[4][1]).setText(hdbr + "");
			((JLabel) obj2[5][1]).setText(hdht + "");
			((JLabel) obj2[6][1]).setText(hddt + "");

		}
	}

	public void hienBang(HoaDon hoaDon) {
		NhanVien nv = getNV(hoaDon.getMaNV().getMaNV());
		Object[] row = { hoaDon.getMaHD(), nv.getHoTen(), hoaDon.getMaKh().getMaKH(),
				formatTime(hoaDon.getNgayTaoHoaDon()), hoaDon.getTongTien() };

		model.addRow(row);
	}

	public void xoaTrang() {
		model.setRowCount(0);
		for (Object[] objects : obj2) {
			if (objects[1] instanceof JLabel)
				((JLabel) objects[1]).setText("");
		}
		((JDateChooser) obj[1][1]).setDate(null);
		((JDateChooser) obj[2][1]).setDate(null);
	}

	public boolean regex() {
		String value = getValueStringInJTextField(obj[0][1]);
		JDateChooser date1 = ((JDateChooser) obj[1][1]);
		// Lấy ngày được chọn từ JDateChooser 2
		JDateChooser date2 = ((JDateChooser) obj[2][1]);

		if (value.equals("") && (date1.getDate() != null || date2.getDate() != null)) {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập mã nhân viên để thống kê");
			((JTextField) obj[0][1]).requestFocus();
			return false;
		}
		if (((date1.getDate() == null || date2.getDate() == null)) && value.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn đủ mốc thống kê");
			return false;
		}
		return true;
	}

	public void xuatThongKe() {
		ArrayList<HoaDon> list = new ArrayList<HoaDon>();
		for (int i = 0; i < table.getRowCount(); i++) {
			String ma = table.getValueAt(i, 0).toString();
			HoaDon hd = hoaDon_DAO.getHoaDonByID(ma);
			list.add(hd);
		}
		String maNhanVien = getValueStringInJTextField(obj[0][1]);

		generateInvoiceBaoCao(list, maNhanVien);

	}

	public JButton buttonThongKe(String nameBtn, String pathFile) {
		JButton btn = createJbutton(nameBtn, pathFile);
		btn.setPreferredSize(new Dimension(120, 40));
		btn.addActionListener(e -> {
			if (nameBtn.equals("Thống kê")) {
				thongKe();
			} else if (nameBtn.equals("")) {
				xoaTrang();

			} else if (nameBtn.equals("In thống kê")) {
				xuatThongKe();

			} else  {
				System.out.println(nameBtn);
			}
		});
		return btn;

	}
}
