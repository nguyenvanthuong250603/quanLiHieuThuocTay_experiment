package test;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entity.Thuoc;

import javax.swing.*;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import static experiment_UI.Generate_All.*;

public class WriteToExcelWithFileChooser {
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

}
