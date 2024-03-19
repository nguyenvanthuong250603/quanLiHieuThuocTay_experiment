package experiment_UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import javax.swing.JFileChooser;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.Thuoc_DAO;
import entity.NhaSanXuat;
import entity.Thuoc;

import static experiment_UI.Generate_All.*;

public class QuanLyThuoc_UI {
	private JLabel labelImage;
	private JTextField jTextMaThuoc,  jTextGiaThuoc, jTextSoLuong, jTextNgaySx, jTextNgayHetHan,
			jTextDonVi;
	private JComboBox cbNSX, cbLoaiThuoc, cbTuoiSD,cbNSXTim, cbLoaiThuocTim;
	private JCheckBox cb;
	private JDateChooser JdateNgaySanXuat, JdateNgayHetHan;
	private Object[][] object_inf, object_detail;
	private JTextArea jTextTenThuoc,jTextAreaMoTa;
	private JTable table;
	private DefaultTableModel model;
	private Thuoc_DAO list_Thuoc = new Thuoc_DAO();

	String pathImageShow;

	public JPanel getQuanLiThuoc() {
		JPanel container = new JPanel(new BorderLayout());
		container.add(searchAndfilter(), BorderLayout.CENTER);
		container.add(inputProduct(), BorderLayout.EAST);
		hienBangTableThuoc();
		return container;
	}

	public JPanel table_information() {
		JPanel managerment = new JPanel();
		createTitle(managerment, "Danh sách sản phẩm");
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã thuốc", "Tên thuốc ", "Số lượng", "Giá", "Loại thuốc", "Nhà sản xuất", "Ngày sản xuất",
				"Ngày hết Hạn", };
		model = new DefaultTableModel(column, 0);
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setBackground(Color.YELLOW);
		table = new JTable(model);
		table.setDefaultRenderer(Object.class, cellRenderer);
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table);
		managerment.add(scoll, BorderLayout.CENTER);
		managerment.add(footer_table(), BorderLayout.SOUTH);
		return managerment;
	}

	public JPanel findID() {
		JPanel f = new JPanel();
		f.setLayout(new BorderLayout());
		JTextField text = new JTextField();
		f.add(text, BorderLayout.CENTER);
		f.setBorder(new EmptyBorder(5, 5, 5, 0));
		JButton timkiemMaThuoc = buttonInPage("Tìm kiếm", "");
		f.add(timkiemMaThuoc, BorderLayout.EAST);
		return f;
	}

	public JPanel searchAndfilter() {
		JPanel compoment = new JPanel();
		compoment.setLayout(new BorderLayout());
		compoment.add(rubi(), BorderLayout.NORTH);
		compoment.add(table_information(), BorderLayout.CENTER);
		return compoment;
	}

	public JPanel rubi() {
		JPanel compoment = new JPanel();
		compoment.setLayout(new BoxLayout(compoment, BoxLayout.Y_AXIS));

		createTitle(compoment, "Tìm kiếm và lọc");
		compoment.add(findID());
		JPanel box = new JPanel(new BorderLayout());

		JPanel box1 = new JPanel();
		box1.setLayout(new GridLayout(2, 2, 10, 10));

		JPanel t = new JPanel();
		t.setLayout(new BoxLayout(t, BoxLayout.X_AXIS));
		t.add(sampleModel("Tên thuốc"));
		JTextField tenThuoc = new JTextField();
		t.add(tenThuoc);

		JPanel t2 = new JPanel();
		t2.setLayout(new BoxLayout(t2, BoxLayout.X_AXIS));
		t2.add(cb = new JCheckBox());
		t2.add(sampleModel("sản phẩm hết hàng"));

		box1.add(t);
		box1.add(t2);

		String[] optionNsx = { "","CTY SX1", "CTY SX2" };
		String[] optionLoaiThuoc = { "","Thuốc giảm đau, hạ sốt ", "Thuốc đặc trị", "Kháng sinh", "Thuốc tiêu hóa",
				"Thuốc an thần", "Vitamin", "Thuốc sát khuẩn , khử trùng", "Thuốc chống dị ứng", "Thuốc chống viêm",
				"Thuốc tim mạch", "Dịch truyền", "Thực phẩm chức năng" };
		
		cbNSXTim= new JComboBox(optionNsx);
		box1.add(createJcombobox("Nhà sản xuất", cbNSXTim));

		cbLoaiThuocTim = new JComboBox(optionLoaiThuoc);
		box1.add(createJcombobox("Loại thuốc", cbLoaiThuocTim));

		box.add(box1, BorderLayout.CENTER);
		JButton loc = buttonInPage("Lọc", "");
		box.add(loc, BorderLayout.EAST);

		compoment.add(box);

		return compoment;
	}

	public JPanel footer_table() {
		JPanel footer = new JPanel();

		String[] object = { "", "gift\\reset.png", "Xóa", "gift\\trash-bin.png", "Excel", "gift\\excel-file.png" };
		for (int i = 0; i < object.length; i += 2) {
			JButton btn = buttonInPage(object[i], object[i + 1]);
			footer.add(btn);
		}

		return footer;
	}

	// bảng 2 tạo thông tin
	public JPanel inputProduct() {
		JPanel inputManager = new JPanel(new GridLayout(2, 1));

		inputManager.add(inputInformation());
		inputManager.add(detailInformation());
		return inputManager;
	}

//bảng 2 tạo thông tin _ nửa trên
	public JPanel inputInformation() {
		JPanel total = new JPanel(new BorderLayout());
		JPanel inf = new JPanel(new BorderLayout());
		createTitle(total, "Thông tin thuốc");
//		top chứa phần hình ảnh và phần kế bên
		JPanel top = new JPanel(new GridLayout(1, 2, 5, 5));

		JPanel boxImage = new JPanel(new BorderLayout());

		labelImage = new JLabel();
		labelImage.setPreferredSize(new Dimension(200, 150));
		boxImage.add(labelImage, BorderLayout.CENTER);

		JButton inputImage = buttonInPage("Chọn hình ảnh", "");
		boxImage.add(inputImage, BorderLayout.SOUTH);

		top.add(boxImage);
//	bên phải ảnh	
		JPanel infRight_top = new JPanel(new BorderLayout());

//		ma sp
		jTextMaThuoc = new JTextField();
		infRight_top.add(createNameAndTextField(jTextMaThuoc, "Mã thuốc"), BorderLayout.NORTH);
//Ten thuoc
		JPanel box2 = new JPanel(new BorderLayout());
	
		jTextTenThuoc = new JTextArea();
		box2.add(createTextArea("Tên thuốc", jTextTenThuoc));

		infRight_top.add(box2, BorderLayout.CENTER);

		top.add(infRight_top);
//		
		inf.add(top, BorderLayout.CENTER);
//tao gia , so luong
		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		String[] optionNsx = { "CTY SX1", "CTY SX2" };
		String[] optionLoaiThuoc = { "Thuốc giảm đau, hạ sốt ", "Thuốc đặc trị", "Kháng sinh", "Thuốc tiêu hóa",
				"Thuốc an thần", "Vitamin", "Thuốc sát khuẩn , khử trùng", "Thuốc chống dị ứng", "Thuốc chống viêm",
				"Thuốc tim mạch", "Dịch truyền", "Thực phẩm chức năng" };
		String[] optionDoTuoi = { "Mọi lứa tuổi", "Từ 1-23 tháng", "Từ 2-11 tuổi", "Từ 12 tuổi trở lên" };
		Object[][] trage = { { "Số lượng", new JTextField() }, { "Giá", new JTextField() },
				{ "Đơn vị", new JTextField() }, { "Loại thuốc", cbLoaiThuoc = new JComboBox(optionLoaiThuoc) },
				{ "Độ tuổi", cbTuoiSD = new JComboBox(optionDoTuoi) }, { "NSX", cbNSX = new JComboBox(optionNsx) },
				{ "Ngày SX", new JDateChooser() }, { "Ngày Hết Hạn", new JDateChooser() } };
		object_inf = trage;
		for (Object[] objects : object_inf) {
			if (objects[1] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel(objects[0].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(5, 0, 5, 0));
				t.add((Component) objects[1], BorderLayout.CENTER);
				bottom.add(t);

			} else {
				bottom.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));
			}

		}
		inf.add(bottom, BorderLayout.SOUTH);
		JScrollPane scroll = new JScrollPane(inf);
		total.add(scroll);

		return total;
	}

// phần 2 nữa dưới
	public JPanel detailInformation() {
		JPanel detail_big = new JPanel(new BorderLayout());
		createTitle(detail_big, "Chi tiết thông tin");
		JPanel detail_compoment = new JPanel();
		detail_compoment.setLayout(new BoxLayout(detail_compoment, BoxLayout.Y_AXIS));

		Object[][] trage = { { "Thành phần", new JTextField() }, { "Chỉ định", new JTextField() },
				{ "Liều dùng", new JTextField() }, { "Bảo quản", new JTextField() } };
		object_detail = trage;
		for (Object[] objects : trage) {
			detail_compoment.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));
		}
		;
		jTextAreaMoTa = new JTextArea();
		detail_compoment.add(createTextArea("Mô tả", jTextAreaMoTa));
		JScrollPane scroll = new JScrollPane(detail_compoment);
		detail_big.add(scroll);
		detail_big.add(footer_inf(), BorderLayout.SOUTH);
		return detail_big;
	}

//	phần 2 footer
	public JPanel footer_inf() {
		JPanel footer = new JPanel();
		String[][] object = { { "Xóa trắng", "gift//brush.png" }, { "Cập nhật", "gift//update.png" },
				{ "Thêm", "gift//add.png" } };
		for (String[] strings : object) {
			JButton btn = buttonInPage(strings[0].toString(), strings[1].toString());
			btn.setPreferredSize(new Dimension(140, 40));
			footer.add(btn);
		}
		return footer;
	}

//hien anh
	private void chooseImage() {

		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
		fileChooser.setFileFilter(filter);

		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			displayImage(selectedFile);
		}

	}

	private void displayImage(File file) {

		try {
			ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
			Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(image);
			labelImage.setIcon(imageIcon);
			pathImageShow = file.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

// tạo nút và bắt sự kiện
	public JButton buttonInPage(String nameBtn, String pathIcon) {
		JButton btn = createJbutton(nameBtn, pathIcon);
		btn.setPreferredSize(new Dimension(120, 40));

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameBtn.equals("Chọn hình ảnh")) {
					chooseImage();

				} else if (nameBtn.equals("Thêm")) {
//					String gia = ((JTextField)object_inf[1][1]).getText();

					themThuoc();

				} else if (nameBtn.equals("Cập nhật")) {

					suaThuoc();
				} else if (nameBtn.equals("Xóa trắng")) {

					xoaTrang();
				}
				else if (nameBtn.equals("")) {
//					String gia = ((JTextField)object_inf[1][1]).getText();
					model.setRowCount(0);
					hienBangTableThuoc();

				}
				else if (nameBtn.equals("Lọc")) {

					timNangCao();
				}
				else {

					System.out.println(nameBtn);
				}

			}
		});

		return btn;
	}

	public void hienBangTableThuoc() {

		ArrayList<Thuoc> list_thuoc = list_Thuoc.getThuocDataBase();

		for (Thuoc thuoc : list_thuoc) {
			NhaSanXuat nsx = thuoc.getTenNhaSanXuat();
			String[] row = { thuoc.getMaThuoc(), thuoc.getTenThuoc(), thuoc.getSoLuong() + "", thuoc.getGia() + "",
					thuoc.getLoaiThuoc(), nsx.getTenNSX(), thuoc.getNgaySanXuat() + "", "" + thuoc.getNgaySanXuat() };
			model.addRow(row);
		}

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
				String maThuocTable = table.getValueAt(index, 0).toString();
				for (Thuoc thuoc : list_thuoc) {
					if (thuoc.getMaThuoc().equals(maThuocTable)) {
						jTextMaThuoc.setText(thuoc.getMaThuoc());
						jTextTenThuoc.setText(thuoc.getTenThuoc());
						((JTextField) object_inf[0][1]).setText(thuoc.getSoLuong() + "");
						((JTextField) object_inf[1][1]).setText(thuoc.getGia() + "");
						((JTextField) object_inf[2][1]).setText(thuoc.getDonVi());
						cbLoaiThuoc.setSelectedItem(thuoc.getLoaiThuoc());
						cbTuoiSD.setSelectedItem(thuoc.getDoTuoi());
						cbNSX.setSelectedItem(thuoc.getTenNhaSanXuat().getTenNSX());
						((JDateChooser) object_inf[6][1]).setDate(java.sql.Date.valueOf(thuoc.getNgaySanXuat()));
						((JDateChooser) object_inf[7][1]).setDate(java.sql.Date.valueOf(thuoc.getNgayHetHan()));
						String pathFile = thuoc.getHinhAnh();
						File file = new File(pathFile);
						displayImage(file);
						((JTextField) object_detail[0][1]).setText(thuoc.getThanhPhan());
						((JTextField) object_detail[1][1]).setText(thuoc.getChiDinh());
						((JTextField) object_detail[2][1]).setText(thuoc.getLieuDung());
						((JTextField) object_detail[3][1]).setText(thuoc.getBaoQuan());
						jTextAreaMoTa.setText(thuoc.getMoTa());
					}
				}

			}
		});
		table.setModel(model);
	}

	public void themThuoc() {

		String ma = jTextMaThuoc.getText();
		String ten = jTextTenThuoc.getText();
		int soLuong = getValueIntỊntextField(object_inf[0][1]);
		double gia = getValueDoubleỊntextField(object_inf[1][1]);
		String donVi = getValueStringInJTextField(object_inf[2][1]);
		String loaiThuoc = getValueInComboBox(cbLoaiThuoc);
		String doTuoi = getValueInComboBox(cbTuoiSD);
		String nsx = getValueInComboBox(cbNSX);
		LocalDate ngaySanXuat = getDateJDateChoor(object_inf[6][1]);
		LocalDate ngayHetHan = getDateJDateChoor(object_inf[7][1]);
//		chitiet
		String thanhPhan = getValueStringInJTextField(object_detail[0][1]);
		String chiDinh = getValueStringInJTextField(object_detail[1][1]);
		String lieuDung = getValueStringInJTextField(object_detail[2][1]);
		String baoQuan = getValueStringInJTextField(object_detail[3][1]);
		String moTa = jTextAreaMoTa.getText();

		NhaSanXuat nhaSanXuat = new NhaSanXuat(nsx);
		Thuoc thuoc = new Thuoc(ma, ten, soLuong, gia, donVi, loaiThuoc, doTuoi, nhaSanXuat, ngaySanXuat, ngayHetHan,
				pathImageShow, thanhPhan, chiDinh, lieuDung, baoQuan, moTa);

		if (list_Thuoc.themThuoc(thuoc)) {
			String[] row = { ma, ten, soLuong + "", gia + "", donVi, loaiThuoc, nhaSanXuat.getTenNSX(),
					ngaySanXuat + "", ngayHetHan + "" };
			model.addRow(row);
		} else {
			JOptionPane.showMessageDialog(table, "Mã thuốc bị trùng");
		}

	}

	public void suaThuoc() {
		String ma = jTextMaThuoc.getText();
		String ten = jTextTenThuoc.getText();
		int soLuong = getValueIntỊntextField(object_inf[0][1]);
		double gia = getValueDoubleỊntextField(object_inf[1][1]);
		String donVi = getValueStringInJTextField(object_inf[2][1]);
		String loaiThuoc = getValueInComboBox(cbLoaiThuoc);
		String doTuoi = getValueInComboBox(cbTuoiSD);
		String nsx = getValueInComboBox(cbNSX);
		LocalDate ngaySanXuat = getDateJDateChoor(object_inf[6][1]);
		LocalDate ngayHetHan = getDateJDateChoor(object_inf[7][1]);
//		chitiet
		String thanhPhan = getValueStringInJTextField(object_detail[0][1]);
		String chiDinh = getValueStringInJTextField(object_detail[1][1]);
		String lieuDung = getValueStringInJTextField(object_detail[2][1]);
		String baoQuan = getValueStringInJTextField(object_detail[3][1]);
		String moTa = jTextAreaMoTa.getText();

		NhaSanXuat nhaSanXuat = new NhaSanXuat(nsx);
		Thuoc thuoc = new Thuoc(ma, ten, soLuong, gia, donVi, loaiThuoc, doTuoi, nhaSanXuat, ngaySanXuat, ngayHetHan,
				pathImageShow, thanhPhan, chiDinh, lieuDung, baoQuan, moTa);

		int index = table.getSelectedRow();
		String maThuoc = table.getValueAt(index, 0).toString();

		if (!ma.equals(maThuoc)) {
			JOptionPane.showMessageDialog(table, "Không được sửa mã thuốc");
		} else {
			int question = JOptionPane.showConfirmDialog(table, "Bạn có chắc muốn sửa thông tin thuốc này hay không ?",
					"Chú ý", JOptionPane.YES_NO_OPTION);
			if (question == JOptionPane.YES_OPTION) {
				if (list_Thuoc.suaThuoc(thuoc)) {
					JOptionPane.showMessageDialog(table, "Sửa thành công");
					table.setValueAt(maThuoc, index, 0);
					table.setValueAt(ten, index, 1);
					table.setValueAt(soLuong, index, 2);
					table.setValueAt(gia, index, 3);
					table.setValueAt(loaiThuoc, index, 4);
					table.setValueAt(nhaSanXuat, index, 5);
					table.setValueAt(ngaySanXuat, index, 6);
					table.setValueAt(ngayHetHan, index, 7);
				}
			}

		}

	}

	public void xoaTrang() {
		jTextMaThuoc.setText("");
		jTextTenThuoc.setText("");
		((JTextField) object_inf[0][1]).setText("");
		((JTextField) object_inf[1][1]).setText("");
		((JTextField) object_inf[2][1]).setText("");
		cbLoaiThuoc.setSelectedIndex(0);
		cbTuoiSD.setSelectedIndex(0);
		cbNSX.setSelectedIndex(0);
		((JDateChooser) object_inf[6][1]).setDate(null);
		((JDateChooser) object_inf[7][1]).setDate(null);
		String pathFile = "";
		File file = new File(pathFile);
		displayImage(file);
		((JTextField) object_detail[0][1]).setText("");
		((JTextField) object_detail[1][1]).setText("");
		((JTextField) object_detail[2][1]).setText("");
		((JTextField) object_detail[3][1]).setText("");
		jTextAreaMoTa.setText("");
	}
	public void timNangCao() {

		String tenNSX = getValueInComboBox(cbNSXTim);
		String loaithuoc = getValueInComboBox(cbLoaiThuocTim);
		if(!tenNSX.equals("")||!loaithuoc.equals("")) {

		ArrayList<Thuoc> list_ThuocTim = list_Thuoc.timThuoc(tenNSX, loaithuoc);
		
		model.setRowCount(0);
		for (Thuoc thuoc : list_ThuocTim) {
			
			String[] row = { thuoc.getMaThuoc(), thuoc.getTenThuoc(), thuoc.getSoLuong() + "", thuoc.getGia() + "",  thuoc.getLoaiThuoc(), thuoc.getTenNhaSanXuat().getTenNSX(),
					thuoc.getNgaySanXuat() + "", thuoc.getNgayHetHan() + "" };
				
			model.addRow(row);
		}
		table.setModel(model);
		
	}
	}

}
