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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import dao.HoaDon_DAO;
import entity.HoaDon;
import entity.NhanVien;
import experiment_UI.Generate_All.CustomTableCellRenderer;

import static experiment_UI.Generate_All.*;

public class ThongKeDoanhThu_UI {
	private Object[][] obj, obj2;
	private DefaultTableModel model;
	private JTable table;
	private HoaDon_DAO hoaDon_DAO = new HoaDon_DAO();

	public JPanel getThongKeDoanhThu() {
		JPanel thongKe = new JPanel(new BorderLayout());
		createTiTlePage(thongKe, "BÁO CÁO THỐNG KÊ DOANH THU");

		thongKe.add(manageMent(), BorderLayout.CENTER);
		setValueInThongKe();
		thongKe(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
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
		JPanel dia = new JPanel();
		SpinnerModel model = new SpinnerNumberModel(0, 0, 31, 1) {
			@Override
			public Object getNextValue() {
				int currentValue = (Integer) getValue();
				return (currentValue >= 31) ? 0 : currentValue + 1;
			}

			@Override
			public Object getPreviousValue() {
				int currentValue = (Integer) getValue();
				return (currentValue <= 0) ? 31 : currentValue - 1;
			}
		};
		SpinnerModel model2 = new SpinnerNumberModel(0, 0, 12, 1) {
			@Override
			public Object getNextValue() {
				int currentValue = (Integer) getValue();
				return (currentValue >= 12) ? 0 : currentValue + 1;
			}

			@Override
			public Object getPreviousValue() {
				int currentValue = (Integer) getValue();
				return (currentValue <= 0) ? 12 : currentValue - 1;
			}
		};
		Object[][] trage = { { "Ngày", new JSpinner(model) }, { "Tháng", new JSpinner(model2) },
				{ "Năm", new JYearChooser() } };
		obj = trage;
		for (Object[] objects : trage) {
			if (objects[1] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel(objects[0].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(10, 5, 10, 5));
				((Component) objects[1]).setPreferredSize(new Dimension(130, 40));
				t.add((Component) objects[1], BorderLayout.CENTER);

				dia.add(t);

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
				JPanel tt = new JPanel();
				tt.setLayout(new BoxLayout(tt, BoxLayout.X_AXIS));

				JPanel t = new JPanel();
				t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
				JLabel x;
				t.add(x = new JLabel(objects[0].toString()));
				x.setFont(new Font("Arial", Font.BOLD, 18));

				JPanel m = new JPanel();
				m.setLayout(new BoxLayout(m, BoxLayout.Y_AXIS));
				m.add((JLabel) objects[1]);

				((JLabel) objects[1]).setFont(new Font("Arial", Font.BOLD, 18));
				((JLabel) objects[1]).setBorder(new EmptyBorder(0, 60, 0, 0));
				((JLabel) objects[1]).setForeground(Color.red);
				tt.setBorder(new EmptyBorder(0, 10, 0, 10));
				tt.add(t);
				tt.add(m);

				cacMuc.add(tt);
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

	public void thongKe(int ngay, int thang, int nam) {

		double doanhThu = 0;
		int hoaDonBanHang = 0;
		int hoaDonBanLe = 0;
		int hdbr = 0;
		int hddt = 0;
		int hdht = 0;
		ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();
		model.setRowCount(0);

		lhd = hoaDon_DAO.getHoaDonDanhSachDoanhThuByNgayThangNam(ngay, thang, nam);
		for (HoaDon hoaDon : lhd) {

			LocalDate ngayDate = hoaDon.getNgayTaoHoaDon();
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

		table.setModel(model);
		((JLabel) obj2[0][1]).setText(table.getRowCount() + "");

		((JLabel) obj2[1][1]).setText(doanhThu + "");
		((JLabel) obj2[2][1]).setText(hoaDonBanHang + "");
		((JLabel) obj2[3][1]).setText(hoaDonBanLe + "");
		((JLabel) obj2[4][1]).setText(hdbr + "");
		((JLabel) obj2[5][1]).setText(hdht + "");
		((JLabel) obj2[6][1]).setText(hddt + "");

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
		setValueInThongKe();
	}

	public void xuatThongKe() {
		ArrayList<HoaDon> list = new ArrayList<HoaDon>();
		for (int i = 0; i < table.getRowCount(); i++) {
			String ma = table.getValueAt(i, 0).toString();
			HoaDon hd = hoaDon_DAO.getHoaDonByID(ma);
			list.add(hd);
		}

		int ngay = Integer.parseInt(((JSpinner) obj[0][1]).getValue().toString());
		int thang = Integer.parseInt(((JSpinner) obj[1][1]).getValue().toString());
		int nam = ((JYearChooser) obj[2][1]).getYear();
		generateInvoiceBaoCaoDoanhThu(list, ngay, thang, nam);

	}

	public void setValueInThongKe() {
		((JSpinner) obj[0][1]).setValue(LocalDate.now().getDayOfMonth());
		((JSpinner) obj[1][1]).setValue(LocalDate.now().getMonthValue());
	}

	public JButton buttonThongKe(String nameBtn, String pathFile) {
		JButton btn = createJbutton(nameBtn, pathFile);
		btn.setPreferredSize(new Dimension(120, 40));
		btn.addActionListener(e -> {
			if (nameBtn.equals("Thống kê")) {
				int ngay = Integer.parseInt(((JSpinner) obj[0][1]).getValue().toString());
				int thang = Integer.parseInt(((JSpinner) obj[1][1]).getValue().toString());
				int nam = ((JYearChooser) obj[2][1]).getYear();
				thongKe(ngay, thang, nam);
			} else if (nameBtn.equals("")) {
				xoaTrang();

			} else if (nameBtn.equals("In thống kê")) {
				xuatThongKe();

			} else {
				System.out.println(nameBtn);
			}
		});
		return btn;

	}
}
