package test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
            Font font = new Font(baseFont,24, Font.BOLD);

            // Mở Document
            document.open();

            // Thêm nội dung vào Document
            Paragraph title = new Paragraph("NHÀ THUỐC ÁNH DƯƠNG", font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("Hóa đơn", font));
            document.add(new Paragraph("Ngày: " + java.time.LocalDate.now(), font));
            document.add(new Paragraph("Số hóa đơn: 123456", font));
            document.add(new Paragraph("Sản phẩm 1 - $50", font));
            document.add(new Paragraph("Sản phẩm 2 - $30", font));
            document.add(new Paragraph("-----------------------", font));
            document.add(new Paragraph("Tổng cộng: $80", font));

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

            // Mở tệp PDF bằng trình duyệt mặc định
            Desktop.getDesktop().open(tempFile);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        generateInvoice();
    }
}
