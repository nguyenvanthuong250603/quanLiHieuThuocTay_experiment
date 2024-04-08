package test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class InvoiceGenerator {
    public static void generateInvoice() {
        // Tạo một đối tượng Document
        Document document = new Document();

        try {
            // Sử dụng ByteArrayOutputStream để lưu PDF tạm thời trong bộ nhớ
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            // Tạo font chữ Unicode Tiếng Việt và nhúng vào tài liệu PDF
            BaseFont baseFont = BaseFont.createFont("library\\Arial Unicode Font.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont,22, Font.NORMAL);
            Font fontsdt = new Font(baseFont,18, Font.BOLDITALIC);
            Font fontHD = new Font(baseFont,24, Font.BOLD);
            // Mở Document
            document.open();

            // Thêm nội dung vào Document
            Paragraph title = new Paragraph("NHÀ THUỐC ÁNH DƯƠNG", font);                                                                         
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            Paragraph diaChi = new Paragraph("Đ.C: 123- NGUYỄN VĂN C - XYZ - HCM",font);
            diaChi.setAlignment(Element.ALIGN_CENTER);
            document.add(diaChi);
            
            
            
            Paragraph SDT = new Paragraph("SĐT: 0968xxxxxxx",fontsdt);
            SDT.setAlignment(Element.ALIGN_CENTER);
            document.add(SDT);
            document.add(new Paragraph(""));
            
            Paragraph tenHD = new Paragraph("HÓA ĐƠN BÁN LẺ",fontHD);
            tenHD.setAlignment(Element.ALIGN_CENTER);
            document.add(tenHD);
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            
            
            
            document.add(new Paragraph("Hóa đơn", font));
            document.add(new Paragraph("Ngày: " + java.time.LocalDate.now(), font));
            document.add(new Paragraph("Số hóa đơn: 123456", font));
            document.add(new Paragraph("Sản phẩm 1 - $50", font));
            document.add(new Paragraph("Sản phẩm 2 - $30", font));
            document.add(new Paragraph("-----------------------", font));
            document.add(new Paragraph("Tổng cộng: $80", font));
            
            Paragraph dateAndEmployeeId = new Paragraph();
            dateAndEmployeeId.add(new Phrase("Ngày mua: " + LocalDate.now()));
            dateAndEmployeeId.add(Chunk.TABBING);
            dateAndEmployeeId.add(new Phrase("\t\tMã nhân viên: NV001"));

            // Thêm đoạn văn bản vào Document
            document.add(dateAndEmployeeId);
            PdfPTable table = new PdfPTable(2); // 2 cột

            // Thêm tiêu đề cho từng cột
            table.addCell("Tên thuốc");
            table.addCell("Giá");

            // Thêm dữ liệu vào bảng
            
            table.addCell("Thuốc A");
            table.addCell("$10");
            table.addCell("Thuốc B");
            table.addCell("$20");
            table.addCell("Thuốc C");
            table.addCell("$15");

            // Thêm bảng vào Document
            document.add(table);
            // Đóng Document
            document.close();

            // Chuyển đổi ByteArrayOutputStream thành mảng byte
            byte[] pdfBytes = outputStream.toByteArray();

            // Tạo một tệp tạm thời
            File tempFile = File.createTempFile("invoice", ".pdf");
            tempFile.deleteOnExit(); // Xóa tệp tạm thời sau khi kết thúc chương trình

            // Ghi dữ liệu PDF vào tệp tạm thời
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(pdfBytes);
            }
            
         
            Desktop.getDesktop().open(tempFile);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	 LocalTime currentTime = LocalTime.now();

         // Định dạng thời gian để in ra
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

         // In thời gian hiện tại với định dạng giờ:phút
         String formattedTime = currentTime.format(formatter);
         System.out.println("Thời gian hiện tại là: " + formattedTime);
        generateInvoice();
    }
}
