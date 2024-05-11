package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class openPDF {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Open PDF in Microsoft Edge");

        JButton openPdfButton = new JButton("Open PDF");
        openPdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Đường dẫn đến file PDF
                String filePath = "C:\\Users\\ADMIN\\Downloads\\Nguyễn Văn Thương_21121741.pdf";
                
                // Kiểm tra xem Desktop được hỗ trợ không
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.OPEN)) {
                        try {
                            File file = new File(filePath);
                            desktop.open(file); // Mở file PDF bằng Microsoft Edge
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        frame.getContentPane().add(openPdfButton, BorderLayout.CENTER);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

