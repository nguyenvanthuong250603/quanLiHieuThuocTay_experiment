package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {
    public MyFrame() {
        // Thiết lập JFrame
        setTitle("JFrame với JSpinner và JFileChooser");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Tạo một SpinnerModel tùy chỉnh
        SpinnerModel model = new SpinnerNumberModel(0, 0, 3, 1) {
            @Override
            public Object getNextValue() {
                int currentValue = (Integer) getValue();
                return (currentValue >= 3) ? 0 : currentValue + 1;
            }

            @Override
            public Object getPreviousValue() {
                int currentValue = (Integer) getValue();
                return (currentValue <= 0) ?3 : currentValue - 1;
            }
        };

        // Tạo một JSpinner với model tùy chỉnh
        JSpinner spinner = new JSpinner(model);
        add(spinner);

        // Tạo một nút để mở JFileChooser
        JButton openFileButton = new JButton("Open File");
        add(openFileButton);

        // Thêm ActionListener cho nút để mở JFileChooser khi được nhấn
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // Lấy file được chọn
                    java.io.File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("File selected: " + selectedFile.getAbsolutePath());
                }
            }
        });
    }

    public static void main(String[] args) {
        // Tạo và hiển thị JFrame
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFrame().setVisible(true);
            }
        });
    }
}
