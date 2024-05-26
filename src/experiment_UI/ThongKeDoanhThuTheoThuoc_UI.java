package experiment_UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.toedter.calendar.JYearChooser;

import dao.HoaDon_DAO;
import dao.Thuoc_DAO;
import entity.HoaDon;
import entity.Thuoc;
import experiment_UI.Generate_All.CustomTableCellRenderer;

import static experiment_UI.Generate_All.*;

public class ThongKeDoanhThuTheoThuoc_UI {
	private Object[][] obj, obj2;
	private DefaultTableModel model;
	private JTable table;
	private HoaDon_DAO hoaDon_DAO = new HoaDon_DAO();
	private Thuoc_DAO thuoc_DAO = new Thuoc_DAO();
	ChartPanel chartPanel;

	public JPanel getThongKeDoanhThuTheoThuoc() {
		JPanel thongKe = new JPanel(new BorderLayout());
		createTiTlePage(thongKe, "BÁO CÁO THỐNG KÊ DOANH THU THEO THUỐC");

		thongKe.add(manageMent(), BorderLayout.CENTER);
		setValueInThongKe();
		thongKe("", LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
		return thongKe;
	}

	public JPanel manageMent() {
		JPanel north = new JPanel(new GridLayout(2, 1, 10, 10));
		north.add(getNorth());
		north.add(getBieuDo());
		return north;
	}

	public JPanel getNorth() {
		JPanel north = new JPanel(new BorderLayout());
		createTitle(north, "Thống kê");
		JPanel dia = new JPanel();
		SpinnerModel modell = new SpinnerNumberModel(0, 0, 31, 1) {
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
		Object[][] trage = { { "Mã thuốc ", new JTextField() }, { "Ngày", new JSpinner(modell) },
				{ "Tháng", new JSpinner(model2) }, { "Năm", new JYearChooser() } };
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

		north.add(dia, BorderLayout.NORTH);

		JPanel cacMuc = new JPanel(new GridLayout(1, 2, 10, 10));

		JPanel tuongTac = new JPanel(new BorderLayout());
		JPanel tuongTac2 = new JPanel();

		Object[][] trage2 = { { "Tổng hóa đơn", new JLabel() }, { "Doanh thu", new JLabel() } };
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
				((JLabel) objects[1]).setBorder(new EmptyBorder(0, 10, 0, 0));
				((JLabel) objects[1]).setPreferredSize(new Dimension(120, 40));
				((JLabel) objects[1]).setForeground(Color.red);
				tt.setBorder(new EmptyBorder(0, 10, 0, 10));
				tt.add(t);
				tt.add(m);

				tuongTac2.add(tt);
			}
		}
		tuongTac.add(tuongTac2, BorderLayout.NORTH);
		JPanel tuongTac3 = new JPanel();
		tuongTac3.add(buttonThongKe("In thống kê", ""));
		tuongTac3.add(buttonThongKe("", "gift\\reset.png"));

		tuongTac.add(tuongTac3, BorderLayout.SOUTH);

		cacMuc.add(tuongTac);
		JPanel managerment = new JPanel();
		createTitle(managerment, "Danh sách thuốc");
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã Thuốc", "Số lượng ", "Tổng tiền" };
		model = new DefaultTableModel(column, 0);
		table = new JTable(model);
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table);
		managerment.add(scoll, BorderLayout.CENTER);
		cacMuc.add(managerment);
		north.add(cacMuc, BorderLayout.CENTER);
		return north;
	}

	public void thongKe(String tenThuoc, int ngay, int thang, int nam) {
		double doanhThu = 0;
		int size;

		model.setRowCount(0);

		ArrayList<Object[]> mangHD = hoaDon_DAO.getHoaDonDoanhThuTheoThuoc(tenThuoc, ngay, thang, nam);
		size = mangHD.size();
		model.setRowCount(0);
		for (Object[] objects : mangHD) {
			Object[] row = { objects[0].toString(), objects[1], objects[2] };
			model.addRow(row);
			doanhThu += Double.parseDouble(objects[2].toString());
		}
		table.setModel(model);
		((JLabel) obj2[0][1]).setText(size + "");
		((JLabel) obj2[1][1]).setText("" + doanhThu);

		updateChart(mangHD);
	}

	public void updateChart(ArrayList<Object[]> mangHD) {
		chartPanel.setChart(createChart(mangHD));
	}

	public void hienBang(HoaDon hoaDon) {
		Object[] row = { hoaDon.getMaHD(), hoaDon.getMaNV().getHoTen(), hoaDon.getMaKh().getMaKH(),
				formatTime(hoaDon.getNgayTaoHoaDon()), hoaDon.getTongTien() };
		model.addRow(row);
	}

	public void xoaTrang() {
		model.setRowCount(0);
		for (Object[] objects : obj2) {
			if (objects[1] instanceof JLabel) {
				((JLabel) objects[1]).setText("");
			}
		}
		((JSpinner) obj[1][1]).setValue(LocalDate.now().getDayOfMonth());
		((JSpinner) obj[2][1]).setValue(LocalDate.now().getMonthValue());
		((JYearChooser) obj[3][1]).setValue(LocalDate.now().getYear());
		updateChart(new ArrayList<>());
	}

	public void xuatThongKe() {
		ArrayList<Object[]> list = new ArrayList<>();
		for (int i = 0; i < table.getRowCount(); i++) {
			Object[] row = { table.getValueAt(i, 0) != null ? table.getValueAt(i, 0).toString() : "",
					table.getValueAt(i, 1) != null ? table.getValueAt(i, 1).toString() : "",
					table.getValueAt(i, 2) != null ? table.getValueAt(i, 2).toString() : "" };
			list.add(row);
		}

		int ngay = Integer.parseInt(((JSpinner) obj[1][1]).getValue().toString());
		int thang = Integer.parseInt(((JSpinner) obj[2][1]).getValue().toString());
		int nam = ((JYearChooser) obj[3][1]).getYear();
		generateInvoiceBaoCaoDoanhThuTheoThuoc(list, ngay, thang, nam);
	}

	public JFreeChart createChart(ArrayList<Object[]> mangHD) {
		JFreeChart barChart = ChartFactory.createBarChart("", "Tên thuốc ", "Doanh thu", createDataset(mangHD),
				PlotOrientation.VERTICAL, false, false, false);

		CategoryPlot plot = barChart.getCategoryPlot();
		BarRenderer renderer = new BarRenderer();
		renderer.setSeriesPaint(0, new Color(79, 129, 189));
		renderer.setItemMargin(0.2);

		renderer.setMaximumBarWidth(0.1);
		plot.setRenderer(renderer);

		return barChart;
	}

	private CategoryDataset createDataset(ArrayList<Object[]> mangHD) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Object[] objects : mangHD) {
			Thuoc th = thuoc_DAO.getThuocByID(objects[0].toString());
			dataset.addValue(Double.parseDouble(objects[2].toString()), "Doanh thu", th.getTenThuoc());
		}
		return dataset;
	}

	public JPanel getBieuDo() {
		JPanel bieuDoJPanel = new JPanel();
		chartPanel = new ChartPanel(createChart(new ArrayList<>()));
		chartPanel.setPreferredSize(new java.awt.Dimension(1200, 367));

		bieuDoJPanel.add(chartPanel);
		return bieuDoJPanel;
	}

	public void setValueInThongKe() {
		((JSpinner) obj[1][1]).setValue(LocalDate.now().getDayOfMonth());
		((JSpinner) obj[2][1]).setValue(LocalDate.now().getMonthValue());
	}

	public JButton buttonThongKe(String nameBtn, String pathFile) {
		JButton btn = createJbutton(nameBtn, pathFile);
		btn.setPreferredSize(new Dimension(140, 40));
		btn.addActionListener(e -> {
			if (nameBtn.equals("Thống kê")) {

				int ngay = Integer.parseInt(((JSpinner) obj[1][1]).getValue().toString());
				int thang = Integer.parseInt(((JSpinner) obj[2][1]).getValue().toString());
				int nam = ((JYearChooser) obj[3][1]).getYear();
				String tenThuoc = getValueStringInJTextField(obj[0][1]);
				if (!tenThuoc.equals("")) {
					Thuoc th = thuoc_DAO.getThuocByID(tenThuoc);

					if (th.getMaThuoc() != null) {

						thongKe(tenThuoc, ngay, thang, nam);
					} else {
						JOptionPane.showMessageDialog(null, "Thuốc không có trong danh sách");
					}
				}else {
					thongKe(tenThuoc, ngay, thang, nam);
				}

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
