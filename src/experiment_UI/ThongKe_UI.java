package experiment_UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
		createTiTlePage(thongKe, "BÁO CÁO THỐNG KÊ");

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
		String[] op = { "Hôm nay", "7 ngày gần nhất", "Tháng này" };
		Object[][] trage = { { "Theo", new JComboBox(op) }, { "Từ ngày", new JDateChooser() },
				{ "Đến ngày", new JDateChooser() } };
		obj = trage;
		JPanel dia = new JPanel();
		for (Object[] objects : trage) {
			if (objects[1] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel(objects[0].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(10, 10, 10, 10));
				((Component) objects[1]).setPreferredSize(new Dimension(150, 40));
				t.add((Component) objects[1], BorderLayout.CENTER);

				dia.add(t);

			} else {
				dia.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));

			}
			dia.add(Box.createHorizontalStrut(10));
		}
		dia.add(buttonThongKe("Thống kê", ""));
		dia.add(buttonThongKe("", ""));
		north.add(dia, BorderLayout.NORTH);

		JPanel cacMuc = new JPanel(new GridLayout(4, 2, 10, 10));
		Object[][] trage2 = { { "Tổng hóa đơn", new JTextField() }, { "Doanh thu", new JTextField() },
				{ "Hóa đơn bán hàng", new JTextField() }, { "Hóa đơn bán lẻ", new JTextField() },
				{ "Hóa đơn bán ra", new JTextField() }, { "Hóa đơn hoàn trả", new JTextField() },
				{ "Hóa đơn đổi thuốc", new JTextField() }, { "Hóa đơn kê lại đơn", new JTextField() } };
		obj2 = trage2;
		for (Object[] objects : trage2) {
			if (objects[1] instanceof JTextField) {
				cacMuc.add(createNameAndTextField2((JTextField) objects[1], objects[0].toString()));
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
		String value = getValueInComboBox((JComboBox<String>) obj[0][1]);
		if(((JDateChooser)obj[1][1]).getDate()!=null) {
		LocalDate date1 = ((JDateChooser)obj[1][1]).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		// Lấy ngày được chọn từ JDateChooser 2
		LocalDate date2 = ((JDateChooser)obj[2][1]).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		// Tính số ngày giữa hai ngày
//		long daysBetween = ChronoUnit.DAYS.between(date1, date2);
		model.setRowCount(0);
		ArrayList<HoaDon> lhd = hoaDon_DAO.getHoaDons();
		double doanhThu = 0;
		for (HoaDon hoaDon : lhd) {
			LocalDate date = hoaDon.getNgayTaoHoaDon();
			if (value.equals("Hôm nay")) {
				if (date.isEqual(LocalDate.now())) {
					hienBang(hoaDon);
					doanhThu+=hoaDon.getTongTien();
				}
			}else if (value.equals("7 ngày gần nhất")) {
				LocalDate currentDate = LocalDate.now();
				LocalDate sevenDaysAgo = currentDate.minusDays(7);
				if(date.isBefore(currentDate)&&date.isAfter(sevenDaysAgo)) {
					hienBang(hoaDon);
					doanhThu+=hoaDon.getTongTien();
				}
			}else if(value.equals("Tháng này")) {
				
			}
			
		}
		table.setModel(model);
		((JTextField)obj2[0][1]).setText(table.getRowCount()+"");
		int hdbr = hoaDon_DAO.thongKeHoaDon("Bán ra",LocalDate.now());
		((JTextField)obj2[1][1]).setText(doanhThu+"");
		((JTextField)obj2[2][1]).setText(hdbr+"");
		
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
			if(objects[1] instanceof JTextField)
				((JTextField) objects[1]).setText("");
		}
		((JDateChooser)obj[1][1]).setDate(null);
		((JDateChooser)obj[2][1]).setDate(null);
	}
	public JButton buttonThongKe(String nameBtn, String pathFile) {
		JButton btn = createJbutton(nameBtn, pathFile);
		btn.setPreferredSize(new Dimension(150, 40));
		btn.addActionListener(e -> {
			if (nameBtn.equals("Thống kê")) {
				thongKe();
			}
			else if(nameBtn.equals("")) {
				xoaTrang();
			}
		});
		return btn;

	}
}
