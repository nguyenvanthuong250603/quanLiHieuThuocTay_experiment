package experiment_UI;

import com.itextpdf.text.*;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Random;

import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.toedter.calendar.JDateChooser;

import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thuoc;

public class Generate_All {
	private static KhachHang_DAO khang = new KhachHang_DAO();
	private static HoaDon_DAO hDon_DAO = new HoaDon_DAO();
	private static NhanVien_DAO nvDao = new NhanVien_DAO();
	private static ChiTietHoaDon_DAO cTietHoaDon_DAO = new ChiTietHoaDon_DAO();
	private static KhachHang_DAO khDao = new KhachHang_DAO();

	public static void generateInvoice(HoaDon hd, double tongTien, double khachDua, String khuyenMai) {
		// Tạo một đối tượng Document
		Document document = new Document();

		try {

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			BaseFont baseFont = BaseFont.createFont("library\\Arial Unicode Font.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			com.itextpdf.text.Font font = new com.itextpdf.text.Font(baseFont, 22, com.itextpdf.text.Font.NORMAL);
			com.itextpdf.text.Font fontsdt = new com.itextpdf.text.Font(baseFont, 18,
					com.itextpdf.text.Font.BOLDITALIC);
			com.itextpdf.text.Font fontHD = new com.itextpdf.text.Font(baseFont, 24, com.itextpdf.text.Font.BOLD);
			com.itextpdf.text.Font fontWord = new com.itextpdf.text.Font(baseFont, 16, com.itextpdf.text.Font.BOLD);
			com.itextpdf.text.Font fontWord2 = new com.itextpdf.text.Font(baseFont, 16, com.itextpdf.text.Font.NORMAL);
			// Mở Document
			document.open();

			Paragraph title = new Paragraph("NHÀ THUỐC ÁNH DƯƠNG", font);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
			Paragraph diaChi = new Paragraph("Đ.C: 123- NGUYỄN VĂN C - XYZ - HCM", font);
			diaChi.setAlignment(Element.ALIGN_CENTER);
			document.add(diaChi);

			Paragraph SDT = new Paragraph("SĐT: 0968xxxxxxx", fontsdt);
			SDT.setAlignment(Element.ALIGN_CENTER);
			document.add(SDT);
			document.add(new Paragraph(""));

			Paragraph tenHD = new Paragraph("HÓA ĐƠN BÁN LẺ", fontHD);
			tenHD.setAlignment(Element.ALIGN_CENTER);
			document.add(tenHD);
			document.add(new Paragraph(" "));
			document.add(new Paragraph("  "));
			Paragraph ngayMua = new Paragraph();
			ngayMua.add(new Phrase("Ngày mua : ", fontWord));
			ngayMua.add(new Phrase("" + formatTime(hd.getNgayTaoHoaDon()), fontWord2));
			ngayMua.add(Chunk.TABBING);
			ngayMua.add(new Phrase("Mã hóa đơn :", fontWord));
			ngayMua.add(new Phrase(hd.getMaHD(), fontWord2));
			document.add(ngayMua);

			document.add(new Paragraph("  "));
			Paragraph nhanVien = new Paragraph();
			nhanVien.add(new Phrase("Nhân viên : ", fontWord));
			nhanVien.add(new Phrase(hd.getMaNV().getMaNV(), fontWord2));
			document.add(nhanVien);

			document.add(new Paragraph("  "));
			Paragraph ten = new Paragraph();
			ten.add(new Phrase("Tên khách hàng : ", fontWord));
			KhachHang kh = getKH(hd.getMaKh().getMaKH(), "");
			ten.add(new Phrase(kh.getTenKH(), fontWord2));
			ten.add(Chunk.TABBING);
			ten.add(new Phrase("Mã KH:", fontWord));
			ten.add(new Phrase(hd.getMaKh().getMaKH(), fontWord2));

			document.add(ten);

			document.add(new Paragraph("  "));
			LocalTime currentTime = LocalTime.now();

			// Định dạng thời gian để in ra
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

			// In thời gian hiện tại với định dạng giờ:phút
			String formattedTime = currentTime.format(formatter);
			Paragraph thoiGian = new Paragraph(
					"Thời gian in : " + formatTime(LocalDate.now()) + "      " + formattedTime, fontWord2);
			thoiGian.setAlignment(Element.ALIGN_CENTER);
			document.add(thoiGian);
			document.add(new Paragraph("  "));

			ArrayList<ChiTietHoaDon> lcthd = hd.getListChiTietHoaDon();
			PdfPTable table = new PdfPTable(7);
			addCell(table, "STT", fontWord);
			addCell(table, "Mã thuốc", fontWord);
			addCell(table, "Tên thuốc", fontWord);
			addCell(table, "Đơn vị", fontWord);
			addCell(table, "Số lượng", fontWord);
			addCell(table, "Giá", fontWord);
			addCell(table, "Thành tiền", fontWord);
			int i = 0;
			for (ChiTietHoaDon chiTietHoaDon : lcthd) {
				i += 1;
				addCell(table, i + "", fontWord2);
				addCell(table, chiTietHoaDon.getMaThuoc().getMaThuoc(), fontWord2); // Không sử dụng font cho các cell
																					// khác
				addCell(table, chiTietHoaDon.getTenThuoc(), fontWord2);
				addCell(table, chiTietHoaDon.getDonVi(), fontWord2);
				addCell(table, String.valueOf(chiTietHoaDon.getSoLuong()), fontWord2);
				addCell(table, String.valueOf(chiTietHoaDon.getDonGia()), fontWord2);
				addCell(table, String.valueOf(chiTietHoaDon.getThanhTien()), fontWord2);// Chuyển thành tiền thành chuỗi
			}
			document.add(table);
			document.add(new Paragraph("   "));

			Paragraph khachCanTra = new Paragraph("Tiền hàng : " + tongTien, fontWord);
			khachCanTra.setAlignment(Element.ALIGN_RIGHT);
			document.add(khachCanTra);

			Paragraph thanhtoan = new Paragraph();

			// Tạo Phrase cho điểm thành viên và thêm vào Paragraph
			Phrase km = new Phrase("Điểm thành viên : " + khuyenMai, fontWord);
			thanhtoan.add(km);

			// Tạo một khoảng cách trắng giữa hai Phrase
			thanhtoan.add(Chunk.TABBING);

			// Thêm khoảng trắng vào Paragraph

			// Tạo Phrase cho tổng tiền và thêm vào Paragraph
			Phrase km2 = new Phrase("Tổng tiền :" + hd.getTongTien(), fontWord);
			thanhtoan.setAlignment(Element.ALIGN_RIGHT);
			thanhtoan.add(km2);

			document.add(thanhtoan);

			document.close();

			byte[] pdfBytes = outputStream.toByteArray();

			File tempFile = File.createTempFile("invoice", ".pdf");
			tempFile.deleteOnExit();

			try (FileOutputStream fos = new FileOutputStream(tempFile)) {
				fos.write(pdfBytes);
			}

			Desktop.getDesktop().open(tempFile);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToExcelWithFileChooser(ArrayList<Thuoc> thuocList) {
		JFileChooser fileChooser = new JFileChooser() {
			@Override
			protected JDialog createDialog(Component parent) throws HeadlessException {
				JDialog dialog = super.createDialog(parent);
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				return dialog;
			}
		};
		fileChooser.setDialogTitle("Nhập tên file");
		int userSelection = fileChooser.showSaveDialog(null);
		// Nếu người dùng chọn nơi lưu trữ và nhấn OK
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			try {
				// Lấy đường dẫn đã chọn
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				// Kiểm tra xem có danh sách thuốc không
				if (thuocList.isEmpty()) {
					System.out.println("Không có dữ liệu để ghi vào file Excel.");
					return;
				}

				// Tạo workbook và sheet
				try (Workbook workbook = new XSSFWorkbook()) {
					Sheet sheet = workbook.createSheet("Danh sách thuốc");

					// Tạo hàng tiêu đề
					Row headerRow = sheet.createRow(0);
					String[] headers = { "Mã thuốc", "Tên thuốc", "Số lượng", "Giá", "Loại thuốc", "Nhà sản xuất",
							"Ngày sản xuất", "Ngày hết hạn" };
					for (int i = 0; i < headers.length; i++) {
						Cell cell = headerRow.createCell(i);
						cell.setCellValue(headers[i]);
					}

					// Ghi thông tin từ danh sách thuốc vào file Excel
					int rowNum = 1;
					for (Thuoc thuoc : thuocList) {
						Row row = sheet.createRow(rowNum++);

						row.createCell(0).setCellValue(thuoc.getMaThuoc());
						row.createCell(1).setCellValue(thuoc.getTenThuoc());
						row.createCell(2).setCellValue(thuoc.getSoLuong() + "");
						row.createCell(3).setCellValue(thuoc.getGia() + "");
						row.createCell(4).setCellValue(thuoc.getLoaiThuoc());
						row.createCell(5).setCellValue(thuoc.getTenNhaSanXuat().getTenNSX());
						row.createCell(6).setCellValue(formatTime(thuoc.getNgaySanXuat()));
						row.createCell(7).setCellValue(formatTime(thuoc.getNgayHetHan()));
					}

					// Ghi workbook vào file
					String excelFilePath = filePath + ".xlsx";
					try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
						workbook.write(fileOut);
						JOptionPane.showMessageDialog(null, "xuất file thành công");
						Desktop.getDesktop().open(new File(excelFilePath));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void addCell(PdfPTable table, String text, com.itextpdf.text.Font font) {
		PdfPCell cell;
		if (font != null) {
			cell = new PdfPCell(new Phrase(text, font));

		} else {
			cell = new PdfPCell(new Phrase(text));
		}
		table.addCell(cell);
	}

	public static JButton createJbutton(String nameButton, String pathIcon) {
		JButton btn = new JButton(nameButton);
		ImageIcon icon = new ImageIcon(pathIcon);
		btn.setIcon(icon);
		btn.setIconTextGap(10);
		btn.setBackground(new Color(89, 168, 104));
		btn.setFocusPainted(false);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Arial", Font.BOLD, 15));
		return btn;
	}

	public static void createTitle(JPanel t, String title) {
		TitledBorder boderDecor = new TitledBorder(BorderFactory.createLineBorder(new Color(89, 168, 104), 3), title);

		boderDecor.setTitleColor(new Color(89, 168, 104));
		boderDecor.setTitleFont(new Font("Arial", Font.BOLD, 20));
		t.setBorder(boderDecor);

	}

	public static JLabel sampleModel(String string) {

		JLabel lb = new JLabel(string);
		lb.setFont(new Font("Arial", Font.BOLD, 15));

		lb.setHorizontalTextPosition(JLabel.LEFT);
		lb.setPreferredSize(new Dimension(100, 30));
//		lb.setBorder(new EmptyBorder(5, 0, 5, 0));
		return lb;

	}

	public static JLabel sampleModel2(String string) {

		JLabel lb = new JLabel(string);
		lb.setFont(new Font("Arial", Font.BOLD, 15));

		lb.setHorizontalTextPosition(JLabel.LEFT);
		lb.setPreferredSize(new Dimension(150, 30));
//		lb.setBorder(new EmptyBorder(5, 0, 5, 0));
		return lb;

	}

	public static JLabel sampleModel3(String string) {

		JLabel lb = new JLabel(string);
		lb.setFont(new Font("Arial", Font.BOLD, 15));

		lb.setHorizontalTextPosition(JLabel.LEFT);
		lb.setPreferredSize(new Dimension(120, 30));
//		lb.setBorder(new EmptyBorder(5, 0, 5, 0));
		return lb;

	}

	public static JPanel createNameAndTextField(JTextField jtext, String nameLabel) {

		JPanel div = new JPanel(new BorderLayout());
		div.add(sampleModel(nameLabel), BorderLayout.WEST);
		div.add(jtext, BorderLayout.CENTER);

		div.setBorder(new EmptyBorder(5, 0, 5, 0));
		return div;
	}

	public static JPanel createNameAndTextField2(JTextField jtext, String nameLabel) {

		JPanel div = new JPanel(new BorderLayout());
		div.add(sampleModel2(nameLabel), BorderLayout.WEST);
		div.add(jtext, BorderLayout.CENTER);
		div.setBorder(new EmptyBorder(5, 30, 5, 30));
		return div;
	}

	public static class CustomTableCellRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (isSelected) {
				component.setBackground(new Color(89, 168, 104, 150));
				component.setForeground(Color.white);

			} else {
				component.setBackground(table.getBackground());
				component.setForeground(table.getForeground());
			}
			table.setRowHeight(30);
			table.setBackground(Color.white);

			return component;
		}

	}

	public static JPanel createJcombobox(String labelString, JComboBox cb) {
		JPanel t3 = new JPanel(new BorderLayout());
		t3.add(sampleModel(labelString), BorderLayout.WEST);
		t3.add(cb, BorderLayout.CENTER);
		cb.setPreferredSize(new Dimension(100, 30));
		t3.setBorder(new EmptyBorder(5, 0, 5, 0));
		return t3;

	}

	public static JPanel createJcombobox2(String labelString, JComboBox cb) {
		JPanel t3 = new JPanel(new BorderLayout());
		t3.add(sampleModel2(labelString), BorderLayout.WEST);
		t3.add(cb, BorderLayout.CENTER);
		cb.setPreferredSize(new Dimension(100, 30));
		t3.setBorder(new EmptyBorder(5, 30, 5, 30));
		return t3;

	}

	public static JPanel createJcombobox3(String labelString, JComboBox cb) {
		JPanel t3 = new JPanel(new BorderLayout());
		t3.add(sampleModel3(labelString), BorderLayout.WEST);
		t3.add(cb, BorderLayout.CENTER);
		cb.setPreferredSize(new Dimension(100, 30));
		t3.setBorder(new EmptyBorder(5, 30, 5, 30));
		return t3;

	}

	public static JPanel createTextArea(String label, JTextArea jtextNote) {

		JPanel note = new JPanel(new BorderLayout());
		note.add(sampleModel(label), BorderLayout.NORTH);
		note.add(jtextNote, BorderLayout.CENTER);
		jtextNote.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		note.setPreferredSize(new Dimension(100, 200));

		return note;
	}

	public static JRadioButton customRadio(String name) {
		JRadioButton rd = new JRadioButton(name);

		rd.setContentAreaFilled(false);
		rd.setFocusPainted(false);
		rd.setBorderPainted(false);

		rd.setBorderPainted(false);
		if (rd.isSelected()) {

			rd.setBorderPainted(false);
		} else {

			rd.setBorderPainted(true);
		}

		return rd;
	}

	public static String getValueStringInJTextField(Object object) {
		JTextField textField = (JTextField) object;
		return textField.getText();
	}

	public static double getValueDoubleỊntextField(Object object) {
		JTextField textField = (JTextField) object;
		return Double.parseDouble(textField.getText());

	}

	public static int getValueIntỊntextField(Object object) {
		JTextField textField = (JTextField) object;
		return Integer.parseInt(textField.getText());
	}

	public static String getValueInComboBox(JComboBox<String> combo) {

		return combo.getSelectedItem().toString();
	}

	public static LocalDate getDateJDateChoor(Object object) {
		JDateChooser date = (JDateChooser) object;
		return date.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static int getValueInlabel(Object object) {
		JLabel x = (JLabel) object;
		return Integer.parseInt(x.getText());
	}

	public static String getValueStringInJlabel(Object object) {
		JLabel x = (JLabel) object;
		return x.getText();
	}

	public static void hienTableKhachHang(JTable table, DefaultTableModel model, Object[][] objects) {

		ArrayList<KhachHang> lkh = khang.getListKhachHang();
		for (KhachHang khachHang : lkh) {
			String[] row = { khachHang.getMaKH(), khachHang.getTenKH(), khachHang.getDiemThanhVien() + "",
					khachHang.getsDT(), khachHang.getDiaCHi() };
			model.addRow(row);

		}
		table.setModel(model);
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
				String maKh = table.getValueAt(index, 0).toString();
				for (KhachHang khachHang : lkh) {
					String gender = transGender(khachHang.isGioiTinh());
					if (khachHang.getMaKH().equals(maKh)) {
						
						((JLabel) objects[0][1]).setText(khachHang.getMaKH());
						
						((JTextField) objects[1][1]).setText(khachHang.getTenKH());
						((JDateChooser) objects[2][1]).setDate(java.sql.Date.valueOf(khachHang.getNgaySinh()));
						((JTextField) objects[3][1]).setText(khachHang.getTuoi() + "");
						((JComboBox) objects[4][1]).setSelectedItem(gender);
						((JTextField) objects[5][1]).setText(khachHang.getsDT());
						((JTextField) objects[6][1]).setText(khachHang.getDiaCHi());
						((JTextField) objects[7][1]).setText(khachHang.getXepHang());
						((JLabel) objects[8][1]).setText(khachHang.getDiemThanhVien() + "");

					}
				}
			}
		});

	}

	public static String transGender(boolean gender) {
		return gender ? "Nam" : "Nữ";

	}

	public static void createTiTlePage(JPanel t, String label) {
		JLabel title = new JLabel(label);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 28));
		title.setBorder(new EmptyBorder(20, 0, 20, 0));
		t.add(title, BorderLayout.BEFORE_FIRST_LINE);
	}

	public static String formatTime(LocalDate time) {
		DateTimeFormatter x = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return x.format(time);
	}

	public static String formatValueDouble(double result) {
		DecimalFormat y = new DecimalFormat("#,000");
		return y.format(result);
	}

	public static boolean transGenderToSQL(String gender) {
		String x = gender;
		boolean gt = x.equals("Nam") ? true : false;
		return gt;
	}

	public static String generateCustomerCode(String ma) {
		Calendar now = Calendar.getInstance();

		int currentDay = now.get(Calendar.DAY_OF_MONTH);
		int currentMonth = now.get(Calendar.MONTH) + 1; // Adding 1 to get the correct month

		Random random = new Random();
		int randomDigits = 1000 + random.nextInt(9000); // Generate 4 random digits
		String customerCode = "HĐ" + String.format("%02d", currentDay) + String.format("%02d", currentMonth)
				+ ma.substring(2) + randomDigits;
		return customerCode;
	}

	public static String generateCode(String kh) {
		Calendar now = Calendar.getInstance();

		int currentDay = now.get(Calendar.DAY_OF_MONTH);
		int currentMonth = now.get(Calendar.MONTH) + 1; // Adding 1 to get the correct month

		Random random = new Random();
		int randomDigits = 1000 + random.nextInt(9000); // Generate 4 random digits
		String customerCode = kh + String.format("%02d", currentDay) + String.format("%02d", currentMonth)
				+ randomDigits;
		return customerCode;
	}

	public static void hienTableTrongHoaDon(JTable table, DefaultTableModel model, JTable table_product,
			DefaultTableModel model_product, Object[][] object, String sdt, int loai) {
		System.out.println(sdt);
		ArrayList<HoaDon> hDons = hDon_DAO.getHoaDonToLuuTam(sdt, loai);
		for (HoaDon hoaDon : hDons) {
			NhanVien nv = getNV(hoaDon.getMaNV().getMaNV());
			Object[] row = { hoaDon.getMaHD(), nv.getHoTen(), hoaDon.getMaKh().getMaKH(),
					formatTime(hoaDon.getNgayTaoHoaDon()), hoaDon.getTongTien() };
			model.addRow(row);

		}

		if (table.getRowCount() >= 1) {
			table.setRowSelectionInterval(0, 0);

			String maKH = table.getValueAt(table.getSelectedRow(), 2) == null ? "" : table.getValueAt(table.getSelectedRow(), 2).toString();
			defaultMouse(model_product, table_product, table.getValueAt(0, 0).toString(), maKH, object, table);
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
				String maKH = table.getValueAt(table.getSelectedRow(), 2) == null ? "" : table.getValueAt(table.getSelectedRow(), 2).toString();
				model_product.setRowCount(0);
				int index = table.getSelectedRow();
				String maHD = table.getValueAt(index, 0).toString();
				defaultMouse(model_product, table_product, maHD, maKH, object, table);

			}
		});

	}

	private static void defaultMouse(DefaultTableModel model_product, JTable table_product, String maHD, String maKH,
			Object[][] obj, JTable table) {

		HoaDon hd = hDon_DAO.getHoaDonByID(maHD);

		ArrayList<ChiTietHoaDon> lChiTietHoaDons = cTietHoaDon_DAO.getcChiTietHoaDons(maHD);

		for (ChiTietHoaDon ct : lChiTietHoaDons) {
			Object[] row_product = { ct.getMaThuoc().getMaThuoc(), ct.getTenThuoc(), ct.getDonVi(), ct.getSoLuong(),
					ct.getDonGia(), ct.getThanhTien() };
			model_product.addRow(row_product);

		}
		table_product.setModel(model_product);
		if (!maKH.equals("")) {
			KhachHang kh = getKH(maKH, "");
			((JTextField) obj[0][1]).setText(kh.getTenKH());

			((JTextField) obj[1][1]).setText(kh.getsDT());
			((JTextField) obj[2][1]).setText(table.getValueAt(table.getSelectedRow(), 4).toString());
			if (obj.length > 3) {
				((JTextField) obj[0][1]).setText(kh.getMaKH());

				((JTextField) obj[1][1]).setText(kh.getTenKH());
				((JTextField) obj[2][1]).setText(kh.getsDT());
				((JTextField) obj[3][1]).setText(transGender(kh.isGioiTinh()));

			}
		}
	}

	public static NhanVien getNV(String maNv) {
		NhanVien nv = nvDao.getNhanVienFindByID(maNv);
		return nv;
	}

	public static KhachHang getKH(String maKH, String sdt) {
		KhachHang kh = khang.getKhachHangByID(maKH, sdt);
		return kh;
	}

}
