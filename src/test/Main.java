package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Suggestion List");
        JTextField textField = new JTextField(20);
        DefaultComboBoxModel<String> suggestionModel = new DefaultComboBoxModel<>();
        JComboBox<String> suggestionBox = new JComboBox<>(suggestionModel);

        // Thêm danh sách gợi ý mẫu
        suggestionModel.addElement("Apple");
        suggestionModel.addElement("Banana");
        suggestionModel.addElement("Cherry");
        suggestionModel.addElement("Grapes");

        // Sử dụng key listener để lắng nghe sự kiện nhập ký tự
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isLetter(c)) {
                    String prefix = textField.getText() + c;
                    filterSuggestions(prefix);
                }
            }
        });

        // Phương thức để lọc và cập nhật danh sách gợi ý
//       public void filterSuggestions(String prefix) {
//            suggestionModel.removeAllElements();
//            for (int i = 0; i < suggestionModel.getSize(); i++) {
//                String suggestion = suggestionModel.getElementAt(i);
//                if (suggestion.toLowerCase().startsWith(prefix.toLowerCase())) {
//                    suggestionModel.addElement(suggestion);
//                }
//            }
//        }

        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(suggestionBox);

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

	protected static void filterSuggestions(String prefix) {
		// TODO Auto-generated method stub
		
	}
}
