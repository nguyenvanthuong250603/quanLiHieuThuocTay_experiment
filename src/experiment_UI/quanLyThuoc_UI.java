package experiment_UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.NhaSanXuat_DAO;
import dao.Thuoc_DAO;
import entity.NhaSanXuat;
import entity.Thuoc;

import static experiment_UI.Generate_All.*;

public class QuanLyThuoc_UI {
	private JLabel labelImage;
	private JTextField jTextGiaThuoc, jTextSoLuong, jTextNgaySx, jTextNgayHetHan, jTextDonVi;
	private JComboBox<String> cbNSX, cbLoaiThuoc, cbTuoiSD, cbNSXTim, cbLoaiThuocTim, cbTinhTrang;
	private JCheckBox cb;
	private JDateChooser JdateNgaySanXuat, JdateNgayHetHan;
	private Object[][] object_inf, object_detail;
	private JTextArea jTextTenThuoc, jTextAreaMoTa;
	private JTable table;
	private DefaultTableModel model;
	private JTextField textFind;
	private JTextField jTextMaThuoc;
	private Thuoc_DAO list_Thuoc = new Thuoc_DAO();
	private NhaSanXuat_DAO nhaSanXuat_DAO = new NhaSanXuat_DAO();
	String pathImageShow;
	private JTextField tenThuoc;

	public JPanel getQuanLiThuoc() {
		JPanel container = new JPanel(new BorderLayout());
		createTiTlePage(container, "QUẢN LÝ THUỐC");

		container.add(searchAndfilter(), BorderLayout.CENTER);
		container.add(inputProduct(), BorderLayout.EAST);
		hienBangTableThuoc();
		enterListen();
		jTextMaThuoc.setText(generateCode("TH"));
		return container;
	}

	public JPanel table_information() {
		JPanel managerment = new JPanel();
		createTitle(managerment, "Danh sách sản phẩm");
		managerment.setLayout(new BorderLayout());
		JPanel north = new JPanel(new GridLayout(1, 5));
		Object[][] note = { { "Còn hạn", Color.white }, { "Sắp hết hạn", Color.yellow }, { "Hết hạn", Color.red },
				{ "Sắp hết hàng", Color.orange }, { "Hết hàng", Color.BLUE } };

		for (Object[] objects : note) {
			JPanel t = new JPanel(new FlowLayout(FlowLayout.LEFT));
//			t.setLayout(new BoxLayout(t, BoxLayout.X_AXIS));

			JButton btn = new JButton("");
			btn.setBackground(((Color) objects[1]));
			btn.setPreferredSize(new Dimension(35, 20));
			t.add(btn);
			t.add(Box.createHorizontalStrut(5));

			t.add(new JLabel(objects[0].toString()));
			btn.setEnabled(false);
			north.add(t);
		}
		String[] column = { "Mã thuốc", "Tên thuốc ", "Số lượng", "Giá", "Loại thuốc", "Nhà sản xuất", "Ngày sản xuất",
				"Ngày hết Hạn", };
		model = new DefaultTableModel(column, 0);
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setBackground(Color.YELLOW);
		table = new JTable(model);
		table.setDefaultRenderer(Object.class, cellRenderer);
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		table.setDefaultRenderer(Object.class, new CustomTableCellRendererThuoc());
		JScrollPane scoll = new JScrollPane(table);
		managerment.add(north, BorderLayout.NORTH);
		managerment.add(scoll, BorderLayout.CENTER);
		managerment.add(footer_table(), BorderLayout.SOUTH);
		return managerment;
	}

	public JPanel findID() {
		JPanel f = new JPanel();
		f.setLayout(new BorderLayout());
		textFind = new JTextField();
		f.add(textFind, BorderLayout.CENTER);
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
		box1.setBorder(new EmptyBorder(0, 0, 0, 10));
		box1.setLayout(new GridLayout(2, 2, 10, 10));

		JPanel t = new JPanel();
		t.setLayout(new BoxLayout(t, BoxLayout.X_AXIS));
		t.add(sampleModel("Tên thuốc"));
		tenThuoc = new JTextField();
		t.add(tenThuoc);

		JPanel t2 = new JPanel();
		t2.setLayout(new BoxLayout(t2, BoxLayout.X_AXIS));
		String[] optionTinhTrang = { "", "sản phẩm hết hạn", "sản phẩm sắp hết hạn", "sản phẩm hết hàng",
				"sản phẩm sắp hết", "sản phẩm còn hạn" };

		t2.add(createJcombobox("Tình trạng", cbTinhTrang = new JComboBox(optionTinhTrang)));

		box1.add(t);
		box1.add(t2);

		String[] optionLoaiThuoc = { "", "Thuốc giảm đau, hạ sốt ", "Thuốc đặc trị", "Kháng sinh", "Thuốc tiêu hóa",
				"Thuốc an thần", "Vitamin", "Thuốc sát khuẩn , khử trùng", "Thuốc chống dị ứng", "Thuốc chống viêm",
				"Thuốc tim mạch", "Thuốc dịch truyền", "Thực phẩm chức năng" };

		cbNSXTim = new JComboBox<String>();

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

		String[] object = { "", "gift\\reset.png", "Xuất Excel",
				"gift\\excel-file.png" };
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

		labelImage.setBorder(new EmptyBorder(10, 10, 0, 0));
		labelImage.setPreferredSize(new Dimension(250, 153));
		labelImage.setMaximumSize(new Dimension(250, 153));

		boxImage.add(labelImage, BorderLayout.CENTER);

		JButton inputImage = buttonInPage("Chọn hình ảnh", "");
		boxImage.add(inputImage, BorderLayout.SOUTH);

		top.add(boxImage);
//	bên phải ảnh	
		JPanel infRight_top = new JPanel(new BorderLayout());

//		ma sp
		jTextMaThuoc = new JTextField();
		jTextMaThuoc.setBorder(null);
		jTextMaThuoc.setBackground(null);
		jTextMaThuoc.setFont(new Font("Arial", Font.BOLD, 15));
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

		String[] optionLoaiThuoc = { "Thuốc giảm đau, hạ sốt ", "Thuốc đặc trị", "Kháng sinh", "Thuốc tiêu hóa",
				"Thuốc an thần", "Vitamin", "Thuốc sát khuẩn , khử trùng", "Thuốc chống dị ứng", "Thuốc chống viêm",
				"Thuốc tim mạch", "Dịch truyền", "Thực phẩm chức năng" };

		Object[][] trage = { { "Số lượng", new JTextField() }, { "Giá", new JTextField() },
				{ "Loại thuốc", cbLoaiThuoc = new JComboBox(optionLoaiThuoc) }, { "NSX", cbNSX = new JComboBox() },
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
		String[] optionDoTuoi = { "Mọi lứa tuổi", "Từ 1-23 tháng", "Từ 2-11 tuổi", "Từ 12-17 tuổi",
				"Từ 18 tuổi trở lên" };
		Object[][] trage = { { "Đơn vị ", new JTextField() }, { "Dạng bào chế ", new JTextField() },
				{ "Độ tuổi", cbTuoiSD = new JComboBox(optionDoTuoi) }, { "Thành phần", new JTextField() },
				{ "Chỉ định", new JTextField() }, { "Liều dùng", new JTextField() }, { "Bảo quản", new JTextField() } };
		object_detail = trage;
		for (Object[] objects : trage) {
			if (objects[1] instanceof JTextField) {
				detail_compoment.add(createNameAndTextField((JTextField) objects[1], objects[0].toString()));
			} else {
				detail_compoment.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));
			}
		}
		;
		jTextAreaMoTa = new JTextArea();
		detail_compoment.add(createTextArea("Mô tả", jTextAreaMoTa));
		JScrollPane scroll = new JScrollPane(detail_compoment);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		detail_big.add(scroll);
		detail_big.add(footer_inf(), BorderLayout.SOUTH);
		return detail_big;
	}

//	phần 2 footer
	public JPanel footer_inf() {
		JPanel footer = new JPanel();
		String[][] object = { { "Xóa thuốc", "gift\\trash-bin.png"}, { "Cập nhật", "gift//update.png" },
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
			final ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
			Image image = imageIcon.getImage().getScaledInstance(250, 153, Image.SCALE_SMOOTH);
			imageIcon.setImage(image);

			SwingUtilities.invokeLater(() -> {
				labelImage.setIcon(imageIcon);
				pathImageShow = file.getAbsolutePath();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hienBangTableThuoc() {

		ArrayList<Thuoc> list_thuoc = list_Thuoc.getThuocDataBase();

		for (Thuoc thuoc : list_thuoc) {
			NhaSanXuat nsx = thuoc.getTenNhaSanXuat();

			String[] row = { thuoc.getMaThuoc(), thuoc.getTenThuoc(), thuoc.getSoLuong() + "", thuoc.getGia() + "",
					thuoc.getLoaiThuoc(), nsx.getTenNSX(), formatTime(thuoc.getNgaySanXuat()),
					formatTime(thuoc.getNgayHetHan()) };
			model.addRow(row);
		}
		ArrayList<NhaSanXuat> lnsx = nhaSanXuat_DAO.getNhaSanXuatDataBase();
		cbNSX.removeAllItems();
		cbNSXTim.removeAllItems();
		cbNSXTim.addItem("");
		for (NhaSanXuat nhaSanXuat : lnsx) {
			cbNSX.addItem(nhaSanXuat.getTenNSX());
			cbNSXTim.addItem(nhaSanXuat.getTenNSX());
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
						hienThongTin(thuoc);
					}
				}

			}
		});
		table.setModel(model);
	}

	public void hienThongTin(Thuoc thuoc) {
		jTextMaThuoc.setText(thuoc.getMaThuoc());
		jTextTenThuoc.setText(thuoc.getTenThuoc());
		((JTextField) object_inf[0][1]).setText(thuoc.getSoLuong() + "");
		((JTextField) object_inf[1][1]).setText(thuoc.getGia() + "");

		cbLoaiThuoc.setSelectedItem(thuoc.getLoaiThuoc());

		cbNSX.setSelectedItem(thuoc.getTenNhaSanXuat().getTenNSX());
		((JDateChooser) object_inf[4][1]).setDate(java.sql.Date.valueOf(thuoc.getNgaySanXuat()));
		((JDateChooser) object_inf[5][1]).setDate(java.sql.Date.valueOf(thuoc.getNgayHetHan()));
		String pathFile = thuoc.getHinhAnh();
		File file = new File(pathFile);

		displayImage(file);
		((JTextField) object_detail[0][1]).setText(thuoc.getDonVi());
		((JTextField) object_detail[1][1]).setText(thuoc.getDangBaoChe());
		cbTuoiSD.setSelectedItem(thuoc.getDoTuoi());

		((JTextField) object_detail[3][1]).setText(thuoc.getThanhPhan());
		((JTextField) object_detail[4][1]).setText(thuoc.getChiDinh());
		((JTextField) object_detail[5][1]).setText(thuoc.getLieuDung());
		((JTextField) object_detail[6][1]).setText(thuoc.getBaoQuan());
		jTextAreaMoTa.setText(thuoc.getMoTa());
	}

	public void themThuoc() {
		if (regexThem()) {
			String ma = jTextMaThuoc.getText();
			String ten = jTextTenThuoc.getText();
			int soLuong = getValueIntỊntextField(object_inf[0][1]);
			double gia = getValueDoubleỊntextField(object_inf[1][1]);
			String loaiThuoc = getValueInComboBox(cbLoaiThuoc);
			String nsx = getValueInComboBox(cbNSX);
			LocalDate ngaySanXuat = getDateJDateChoor(object_inf[4][1]);
			LocalDate ngayHetHan = getDateJDateChoor(object_inf[5][1]);
//		chitiet
			String donVi = getValueStringInJTextField(object_detail[0][1]);
			String dangBaoChe = getValueStringInJTextField(object_detail[1][1]);
			String doTuoi = getValueInComboBox(cbLoaiThuoc);
			String thanhPhan = getValueStringInJTextField(object_detail[3][1]);
			String chiDinh = getValueStringInJTextField(object_detail[4][1]);
			String lieuDung = getValueStringInJTextField(object_detail[5][1]);
			String baoQuan = getValueStringInJTextField(object_detail[6][1]);
			String moTa = jTextAreaMoTa.getText();

			NhaSanXuat nhaSanXuat = new NhaSanXuat(nsx);
			Thuoc thuoc = new Thuoc(ma, ten, soLuong, gia, loaiThuoc, nhaSanXuat, ngaySanXuat, ngayHetHan,
					pathImageShow, donVi, dangBaoChe, doTuoi, thanhPhan, chiDinh, lieuDung, baoQuan, moTa);

			if (list_Thuoc.themThuoc(thuoc)) {
				String[] row = { ma, ten, soLuong + "", gia + "", donVi, loaiThuoc, nhaSanXuat.getTenNSX(),
						ngaySanXuat + "", ngayHetHan + "" };
				model.addRow(row);
			} else {
				JOptionPane.showMessageDialog(null, "Mã thuốc bị trùng");
			}
		}
	}

	public boolean regexThem() {
		for (Object[] objects : object_inf) {
			if (objects[1] instanceof JTextField) {
				if (((JTextField) objects[1]).getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Bạn chưa nhập thông tin vào " + objects[0].toString());
					((JTextField) objects[1]).requestFocus();
					return false;
				}
				if (objects[0].toString().equals("Số lượng") || objects[0].toString().equals("Giá")) {
					if (!((JTextField) objects[1]).getText().matches("\\d+")) {
						JOptionPane.showMessageDialog(null,
								"Thông tin của " + objects[0].toString() + " phải là chữ số");
						((JTextField) objects[1]).requestFocus();
						return false;
					}
					double check = Double.parseDouble(((JTextField) objects[1]).getText());
					if (check < 0) {
						JOptionPane.showMessageDialog(null, objects[0].toString() + " phải là số có giá trị lớn hơn 0");
						((JTextField) objects[1]).requestFocus();
						return false;
					}
				}
			}
			if (objects[1] instanceof JDateChooser) {
				if (((JDateChooser) objects[1]).getDate() == null) {
					JOptionPane.showMessageDialog(null, "Ngày của " + objects[0].toString() + " chưa được nhập");

					return false;
				}
				if (((JDateChooser) objects[1]).getDate().before(new java.util.Date())) {
					JOptionPane.showMessageDialog(null,
							"Ngày của " + objects[0].toString() + " phải là ngày sau hôm nay");

					return false;
				}
				JDateChooser nsx = null;
				if (objects[0].toString().equals("NSX")) {
					nsx = (JDateChooser) objects[1];
				}
				if (objects[0].toString().equals("Ngày Hết Hạn")) {
					if (((JDateChooser) objects[1]).getDate().before(nsx.getDate())) {
						JOptionPane.showMessageDialog(null, "Ngày của hết hạn phải lớn hơn ngày sản xuất");
						((JTextField) objects[1]).requestFocus();
						return false;
					}
				}
			}

		}
		return true;

	}

	public void suaThuoc() {
		if(table.getSelectedRow()<0) {
		String ma = jTextMaThuoc.getText();
		String ten = jTextTenThuoc.getText();
		int soLuong = getValueIntỊntextField(object_inf[0][1]);
		System.out.println();
		double gia = getValueDoubleỊntextField(object_inf[1][1]);
		String loaiThuoc = getValueInComboBox(cbLoaiThuoc);
		String nsx = getValueInComboBox(cbNSX);
		LocalDate ngaySanXuat = getDateJDateChoor(object_inf[4][1]);
		LocalDate ngayHetHan = getDateJDateChoor(object_inf[5][1]);
//		chitiet
		String donVi = getValueStringInJTextField(object_detail[0][1]);
		String dangBaoChe = getValueStringInJTextField(object_detail[1][1]);
		String doTuoi = getValueInComboBox(cbTuoiSD);
		String thanhPhan = getValueStringInJTextField(object_detail[3][1]);
		String chiDinh = getValueStringInJTextField(object_detail[4][1]);
		String lieuDung = getValueStringInJTextField(object_detail[5][1]);
		String baoQuan = getValueStringInJTextField(object_detail[6][1]);
		String moTa = jTextAreaMoTa.getText();

		NhaSanXuat nhaSanXuat = new NhaSanXuat(nsx);
		Thuoc thuoc = new Thuoc(ma, ten, soLuong, gia, loaiThuoc, nhaSanXuat, ngaySanXuat, ngayHetHan, pathImageShow,
				donVi, dangBaoChe, doTuoi, thanhPhan, chiDinh, lieuDung, baoQuan, moTa);

		int index = table.getSelectedRow();
		String maThuoc = table.getValueAt(index, 0).toString();

		if (!ma.equals(maThuoc)) {
			JOptionPane.showMessageDialog(table, "Không được sửa mã thuốc");
		} else {
			int question = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn sửa thông tin thuốc này hay không ?",
					"Chú ý", JOptionPane.YES_NO_OPTION);
			if (question == JOptionPane.YES_OPTION) {
				if (list_Thuoc.suaThuoc(thuoc, 0, "")) {
					JOptionPane.showMessageDialog(null, "Sửa thành công");
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
		}else {
			JOptionPane.showMessageDialog(table, "Bạn chưa chọn thuốc để sửa");
		}
	}

	public void xoaTrang() {
		model.setRowCount(0);
		textFind.setText("");
		jTextMaThuoc.setText(generateCode("TH"));
		jTextTenThuoc.setText("");
		((JTextField) object_inf[0][1]).setText("");
		((JTextField) object_inf[1][1]).setText("");
		tenThuoc.setText("");
		cbLoaiThuoc.setSelectedIndex(0);

		((JDateChooser) object_inf[4][1]).setDate(null);
		((JDateChooser) object_inf[5][1]).setDate(null);
		String pathFile = "";
		File file = new File(pathFile);
		displayImage(file);
		((JTextField) object_detail[0][1]).setText("");
		((JTextField) object_detail[1][1]).setText("");
		cbTuoiSD.setSelectedIndex(0);
		((JTextField) object_detail[3][1]).setText("");
		((JTextField) object_detail[4][1]).setText("");
		((JTextField) object_detail[5][1]).setText("");
		((JTextField) object_detail[6][1]).setText("");
		cbTinhTrang.setSelectedIndex(0);
		cbLoaiThuocTim.setSelectedIndex(0);
		
		jTextAreaMoTa.setText("");
		hienBangTableThuoc();
	}

	public void timNangCao() {
		if (regexThuocLoc()) {
			String tinhTrangThuoc = getValueInComboBox(cbTinhTrang);

			String tenNSX = getValueInComboBox(cbNSXTim);
			String loaithuoc = getValueInComboBox(cbLoaiThuocTim);
			String tenThuocc = tenThuoc.getText();
			ArrayList<Thuoc> list_ThuocTim = list_Thuoc.timThuoc(tenNSX, loaithuoc, tenThuocc);

			model.setRowCount(0);
			for (Thuoc thuoc : list_ThuocTim) {
				if (!tinhTrangThuoc.equals("")) {
					if (tinhTrangThuoc.equals("sản phẩm còn hạn")) {
						LocalDate date = thuoc.getNgayHetHan();
						Long dayDifference = ChronoUnit.DAYS.between(LocalDate.now(), date);
						if (date.isAfter(LocalDate.now()) && dayDifference > 8)
							hienBangLocThuoc(thuoc);

					} else if (tinhTrangThuoc.equals("sản phẩm hết hạn")) {
						LocalDate date = thuoc.getNgayHetHan();
						if (date.isBefore(LocalDate.now()))
							hienBangLocThuoc(thuoc);

					} else if (tinhTrangThuoc.equals("sản phẩm sắp hết hạn")) {
						LocalDate date = thuoc.getNgayHetHan();
						Long daysDifference = ChronoUnit.DAYS.between(LocalDate.now(), date);
						if (daysDifference > 0 && daysDifference <= 7)
							hienBangLocThuoc(thuoc);

					} else if (tinhTrangThuoc.equals("sản phẩm hết hàng")) {
						if (thuoc.getSoLuong() == 0)
							hienBangLocThuoc(thuoc);
					} else if (tinhTrangThuoc.equals("sản phẩm sắp hết")) {
						if (thuoc.getSoLuong() > 0 && thuoc.getSoLuong() <= 5)
							hienBangLocThuoc(thuoc);
					}

				} else {
					hienBangLocThuoc(thuoc);
				}

			}
			table.setModel(model);

		}
	}

	public void hienBangLocThuoc(Thuoc thuoc) {
		String[] row = { thuoc.getMaThuoc(), thuoc.getTenThuoc(), thuoc.getSoLuong() + "", thuoc.getGia() + "",
				thuoc.getLoaiThuoc(), thuoc.getTenNhaSanXuat().getTenNSX(), formatTime(thuoc.getNgaySanXuat()) + "",
				formatTime(thuoc.getNgayHetHan()) + "" };

		model.addRow(row);
	}

	private boolean regex() {
		if (textFind.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Phải nhập mã thuôc trước khi tìm");
			textFind.requestFocus();
			return false;
		}
//		if (table.getRowCount() > 0) {
//			if (!table.getValueAt(model.getRowCount() - 1, 3).toString().matches("\\d+")) {
//				JOptionPane.showMessageDialog(null, "Số lượng thuốc phải là chữ số");
//				textMaThuocFind.requestFocus();
//				return false;
//			}
//		}
		return true;
	}

	public void timThuoc() {
		if (regex()) {
			String maThuoc = textFind.getText();

			Thuoc th = list_Thuoc.getThuocByID(maThuoc);
			if (th.getMaThuoc() != null) {
				model.setRowCount(0);
				System.out.println( th.getTenNhaSanXuat());
				Object[] row = { th.getMaThuoc(), th.getTenThuoc(), th.getSoLuong() + "", th.getGia() + "",
						th.getLoaiThuoc(), th.getTenNhaSanXuat().getTenNSX(), formatTime(th.getNgaySanXuat()),
						formatTime(th.getNgayHetHan()) };
				model.addRow(row);
				table.setModel(model);
				table.setRowSelectionInterval(0, 0);
				hienThongTin(th);

			} else {
				JOptionPane.showMessageDialog(null, "Không tìm thấy mã thuốc trong hệ thống");
			}
		}
	}

	private void enterListen() {

		ActionListener enter = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					timThuoc();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		};
		textFind.addActionListener(enter);
	}

	public class CustomTableCellRendererThuoc extends CustomTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			Object cellValueNHH = table.getValueAt(row, 7);
			if (cellValueNHH != null) {

				try {

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate date = LocalDate.parse(cellValueNHH.toString(), formatter);
					Long diffInDays = ChronoUnit.DAYS.between(LocalDate.now(), date);

					if (diffInDays <= 0 && column == 0) {
						component.setBackground(Color.RED);

					}
					if (diffInDays > 0 && diffInDays <= 7 && column == 0) {

						component.setBackground(Color.yellow);

					}
					Object cellValueSoLuong = table.getValueAt(row, 2);
					int sl = Integer.parseInt(cellValueSoLuong.toString());
					if (sl == 0 && column == 0) {
						component.setBackground(Color.blue);
					}
					if (sl > 0 && sl <= 5 && column == 0) {
						component.setBackground(Color.orange);
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (isSelected) {
						component.setBackground(new Color(89, 168, 104, 150));
					}
				}
			}

			return component;
		}
	}

	public boolean regexThuocLoc() {
		String tinhTrangThuoc = getValueInComboBox(cbTinhTrang);

		String tenNSX = getValueInComboBox(cbNSXTim);
		String loaithuoc = getValueInComboBox(cbLoaiThuocTim);
		String tenThuocc = tenThuoc.getText();
		if (tinhTrangThuoc.equals("") && tenNSX.equals("") && loaithuoc.equals("") && tenThuocc.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần lựa chọn ít nhất 1 tiêu chí để lọc thuốc");
			return false;
		}
		return true;
	}

	public void xoaThuoc() {
		String maThuoc = jTextMaThuoc.getText();
		if (jTextTenThuoc.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn thuốc để xóa");
			textFind.requestFocus();
		} else {
			int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa thuốc hay không ?", "Lưu ý",
					JOptionPane.YES_NO_OPTION);
			if (recomment == JOptionPane.YES_OPTION) {
				if (list_Thuoc.xoaThuoc(maThuoc)) {
					JOptionPane.showMessageDialog(null, "Xóa thuốc thành công");
					xoaTrang();
				}

			}
		}

	}
	public void xuatFileExcel() {
		
		if(table.getRowCount()>0) {
			ArrayList<Thuoc> list_Xuat = new ArrayList<Thuoc>();
			for(int i= 0;i<table.getRowCount();i++) {
				Thuoc th = list_Thuoc.getThuocByID(table.getValueAt(i, 0).toString());
				list_Xuat.add(th);
			}
			int recomment = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn in danh sách thuốc hay không ?","Lưu ý",JOptionPane.YES_NO_OPTION);
			if (recomment==JOptionPane.YES_OPTION) {
				
				
				writeToExcelWithFileChooser(list_Xuat);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Bạn cần có ít nhất 1 loại thuốc để in danh sách");
		}
		
	}
	public JButton buttonInPage(String nameBtn, String pathIcon) {
		JButton btn = createJbutton(nameBtn, pathIcon);
		btn.setPreferredSize(new Dimension(140, 40));

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameBtn.equals("Chọn hình ảnh")) {
					chooseImage();

				} else if (nameBtn.equals("Thêm")) {
//						String gia = ((JTextField)object_inf[1][1]).getText();

					themThuoc();

				} else if (nameBtn.equals("Cập nhật")) {

					suaThuoc();
				}  else if (nameBtn.equals("")) {

					xoaTrang();

				} else if (nameBtn.equals("Lọc")) {

					timNangCao();
				} else if (nameBtn.equals("Tìm kiếm")) {
					timThuoc();
				} else if (nameBtn.equals("Xuất Excel")) {
					xuatFileExcel();
				} else if (nameBtn.equals("Xóa thuốc")) {
					xoaThuoc();
				} else {
					System.out.println(nameBtn);
				}
			}
		});

		return btn;
	}

}
