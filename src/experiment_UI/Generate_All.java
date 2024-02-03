package experiment_UI;

import static experiment_UI.Generate_All.sampleModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.toedter.calendar.JDateChooser;

public class Generate_All {
	public static JButton createJbutton(String nameButton, String pathIcon) {
		JButton btn = new JButton(nameButton);
		ImageIcon icon = new ImageIcon(pathIcon);
		btn.setIcon(icon);
		btn.setIconTextGap(10);
		btn.setBackground(new Color(69, 173, 255));
		btn.setFocusPainted(false);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Arial", Font.BOLD, 15));
		return btn;
	}

	public static void createTitle(JPanel t, String title) {
		TitledBorder boderDecor = new TitledBorder(BorderFactory.createLineBorder(new Color(190,195,199), 4), title);

		boderDecor.setTitleColor(new Color(0, 132, 255));
		boderDecor.setTitleFont(new Font("Arial", Font.ITALIC, 20));
		t.setBorder(boderDecor);
	}

	public static JLabel sampleModel(String string) {

		JLabel lb = new JLabel(string);
		lb.setFont(new Font("Arial", Font.BOLD, 15));
		lb.setHorizontalTextPosition(JLabel.LEFT);
		lb.setPreferredSize(new Dimension(113,30));
		lb.setBorder(new EmptyBorder(5, 0, 5, 0));
		return lb;

	}

	public static JPanel createNameAndTextField(JTextField jtext, String nameLabel) {

		JPanel div = new JPanel(new BorderLayout());
		div.add(sampleModel(nameLabel), BorderLayout.WEST);
		div.add(jtext, BorderLayout.CENTER);
		div.setBorder(new EmptyBorder(5,5,5,5));
		return div;
	}

	public static class CustomTableCellRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (isSelected) {
				component.setBackground(new Color(125, 124, 124));
				component.setForeground(Color.white);

			} else {
				component.setBackground(table.getBackground());
				component.setForeground(table.getForeground());
			}
			((JComponent) component).setBorder(null);
			if (row % 2 == 0) {
				component.setBackground(new Color(0, 143, 255));

			}
			return component;
		}

	}

	public static JPanel createJcombobox(String labelString, JComboBox cb) {
		JPanel t3 = new JPanel(new BorderLayout());
		t3.add(sampleModel(labelString), BorderLayout.WEST);
		t3.add(cb, BorderLayout.CENTER);
		t3.setBorder(new EmptyBorder(5, 0, 5, 0));
		return t3;

	}
	public static JPanel createTextArea(String label,JTextArea jtextNote) {
		
		JPanel note = new JPanel(new BorderLayout());
		note.add(sampleModel(label),BorderLayout.NORTH);
		note.add(jtextNote ,BorderLayout.CENTER);
		jtextNote.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
		note.setPreferredSize(new Dimension(100, 200));
		note.setBorder(new EmptyBorder(0, 5, 5, 5));
		return note;
	}
}
