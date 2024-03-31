package experiment_UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.KhachHang_DAO;
import entity.KhachHang;

public class Generate_All {
	private static KhachHang_DAO khang = new KhachHang_DAO();
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
		TitledBorder boderDecor = new TitledBorder(BorderFactory.createLineBorder(new Color(190, 195, 199), 4), title);

		boderDecor.setTitleColor(new Color(0, 132, 255));
		boderDecor.setTitleFont(new Font("Arial", Font.ITALIC, 20));
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
				component.setBackground(new Color(125, 124, 124));
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
	public static String getValueInComboBox(JComboBox combo) {
		JComboBox cb = combo;
		return cb.getSelectedItem().toString();
	}
	public static LocalDate getDateJDateChoor(Object object) {
		JDateChooser date = (JDateChooser) object;
		return date.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	public static String getString(String x) {
		return  x;
	}
	public static void hienTableKhachHang(JTable table,DefaultTableModel model,Object[][] objects) {
		
		ArrayList<KhachHang> lkh = khang.getListKhachHang();
		for (KhachHang khachHang : lkh) {
			String[] row = {khachHang.getMaKH(),khachHang.getTenKH(),khachHang.getsDT(),khachHang.getDiaCHi()};
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
					if(khachHang.getMaKH().equals(maKh)) {
						((JTextField) objects[0][1]).setText(khachHang.getMaKH());
						((JTextField) objects[1][1]).setText(khachHang.getTenKH());
						((JDateChooser) objects[2][1]).setDate(java.sql.Date.valueOf(khachHang.getNgaySinh()));
						((JTextField) objects[3][1]).setText(khachHang.getTuoi()+"");
						((JComboBox) objects[4][1]).setSelectedItem(gender);
						((JTextField) objects[5][1]).setText(khachHang.getsDT());
						((JTextField) objects[6][1]).setText(khachHang.getDiaCHi());
					}
				}
			}
		});
		
		
	}
	public  static String transGender(boolean gender) {
		return  gender?  "Nam" :"Nữ" ;  
		
	}
	public static void createTiTlePage(JPanel t,String label) {
		JLabel title = new JLabel(label);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 28));
		title.setBorder(new EmptyBorder(20, 0, 20, 0));
		t.add(title,BorderLayout.BEFORE_FIRST_LINE);
	}
	public static String formatTime(LocalDate time) {
		DateTimeFormatter x = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return x.format(time);	
	}
}
