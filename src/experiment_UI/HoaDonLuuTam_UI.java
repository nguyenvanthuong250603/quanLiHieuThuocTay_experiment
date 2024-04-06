package experiment_UI;

import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.AsyncBoxView.ChildLocator;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import dao.HoaDon_DAO;
import dao.NhanVien_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.NhanVien;
import entity.Thuoc;
import experiment_UI.Generate_All.CustomTableCellRenderer;

public class HoaDonLuuTam_UI {


	private JTable table;
	private Object[][] object_kh;
	private JFrame frame;

	private JTable table_product;

	private ArrayList<ChiTietHoaDon> cthdon = new ArrayList<ChiTietHoaDon>();

	private DefaultTableModel model_product;

	private DefaultTableModel model;
	private JLabel textMaHD; 
	
	public void getHoaDonLuuTam(JLabel x,JTable tablee) {
		frame = new JFrame();
		frame.setTitle("Tìm Kiếm Khách Hàng");

		frame.setSize(1100, 800);
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.add(north(), BorderLayout.NORTH);
		frame.add(cenTer(), BorderLayout.CENTER);
		frame.add(footer(), BorderLayout.SOUTH);
		hienTableInHoaDon(table, model, table_product,model_product,object_kh);
		
		this.textMaHD = x;
		tablee.setModel(model_product);
		frame.setVisible(true);
		frame.setResizable(false);

	}

	private JPanel north() {
		JPanel north = new JPanel();
		createTitle(north, "Tìm kiếm và lọc hóa đơn");
		north.add(createNameAndTextField(new JTextField(20), "Số điện thoại"));
		north.add(createButtonInHoaDonLuuTam("Tìm", ""));
		north.add(createJcombobox("Loại hóa đơn", new JComboBox()));
		north.add(createButtonInHoaDonLuuTam("Lọc", ""));
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

//	public void luuHoaDonTamThoiVaoBang(ArrayList<HoaDon> hd) {
//		DefaultTableModel model = createModel();
//		for (int i = 0; i < hd.size(); i++) {
//			NhanVien nv = lnv.getNhanVienFindByID(hd.get(i).getMaNV().getMaNV().toString());
//
//			Object[] row = { hd.get(i).getMaHD(), nv.getHoTen(), hd.get(i).getMaKh().getMaKH(),
//					formatTime(hd.get(i).getNgayTaoHoaDon()), hd.get(i).getTongTien() };
//			model.addRow(row);
//			
////			model_product
//			ArrayList<ChiTietHoaDon> cthd = hd.get(i).getListChiTietHoaDon();
//			
//			for (int j = 0; j < cthd.size(); j++) {
//				HoaDon hdon = new HoaDon(cthd.get(i).getMaHD().getMaHD());
//				Thuoc th = new Thuoc(cthd.get(i).getMaThuoc().getMaThuoc());
//				String tenThuoc = cthd.get(i).getTenThuoc();
//				String donVi =cthd.get(i).getDonVi();
//				int soLuong = cthd.get(i).getSoLuong();
//				double gia = cthd.get(i).getDonGia();
//				double thanhTien = cthd.get(i).getThanhTien();
//	 			ChiTietHoaDon ct = new ChiTietHoaDon(hdon, th, tenThuoc, donVi, soLuong, gia, thanhTien);
//	 			cthdon.add(ct);
//			}
//			
//
//		}
//		table2 = new JTable(model);
//
//	}

	

	private JButton createButtonInHoaDonLuuTam(String nameBtn, String pathIcon) {
		JButton btn = createJbutton(nameBtn, pathIcon);
		btn.setPreferredSize(new Dimension(140, 35));
		btn.addActionListener(e -> {
			if (nameBtn.equals("Thoát")) {
			
				frame.dispose();
			}
			else if(nameBtn.equals("Chọn")) {
				
				textMaHD.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
				
				frame.dispose();
			}
		});
		return btn;
	}
}
