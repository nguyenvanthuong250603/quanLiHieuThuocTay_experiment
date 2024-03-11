package experiment_UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Image;
import java.awt.Insets;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.taiKhoan;
import static experiment_UI.brief.*;
import static experiment_UI.Generate_All.*;
public class taiKhoan_UI  extends JFrame{
	private JTextField maNhanVienJtextField;
	private JPasswordField passwordField;
	private JLabel titleJLabel;
	private JCheckBox hienMatKhau;
	private boolean isHovering = false;
	JFrame framee = new JFrame();
	private TaiKhoan_DAO dstk = new TaiKhoan_DAO();

	public taiKhoan_UI() {
		
		framee.setTitle("Test Gui ");
		framee.setExtendedState(JFrame.MAXIMIZED_BOTH);
		framee.add(backGround());
		forcusListen();
		checkJcheckbox();
		framee.setDefaultCloseOperation(EXIT_ON_CLOSE);
		framee.setLocationRelativeTo(null);
		framee.setVisible(true);
	}
	public static void main(String[] args) {
		new taiKhoan_UI();
	}
	public JPanel backGround() {
		JPanel panel = new JPanel(new GridBagLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon imageIcon = new ImageIcon("gift\\ohh.jpg");
				Image image = imageIcon.getImage();
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};

		JPanel t = new JPanel(new BorderLayout());
		t.setBackground(new Color(0, 0,0, 128));
		t.setPreferredSize(new Dimension(500, 250));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 180, 0); 
		
		
		
		
		inputAcc(t);
		
		panel.add(t, gbc);

		return panel;
	}
	public void inputAcc(JPanel t) {
		
		JPanel title = new JPanel();
		title.add(titleJLabel = new JLabel("Đăng nhập"));
		titleJLabel.setFont(new Font("Arial", Font.BOLD, 32));
		titleJLabel.setForeground(Color.BLUE);
		title.setBackground(new Color(0, 0,0, 0));
		titleJLabel.setBorder(new EmptyBorder(10,0,10,0));
		t.add(title,BorderLayout.NORTH);
		
		JPanel center = new JPanel();
	
		center.setLayout(new BoxLayout(center,BoxLayout.Y_AXIS));
		JPanel box1 = new JPanel();
		box1.setBackground(new Color(0, 0,0, 0));
		
		box1.add(maNhanVienJtextField = new JTextField());
		maNhanVienJtextField.setPreferredSize(new Dimension(300, 40));
		center.setBackground(new Color(0, 0,0, 0));
		maNhanVienJtextField.setText("NV001");
		
		JPanel box2 = new JPanel();
		box2.setBackground(new Color(0, 0,0, 0));
		box2.add(passwordField = new JPasswordField());
		passwordField.setPreferredSize(new Dimension(300, 40));
		passwordField.setText("123");
		passwordField.setEchoChar('*');
		
		
		JPanel hiden = new JPanel();
		hiden.add(hienMatKhau = new JCheckBox("Hiện mật khẩu"));
		hiden.setBackground(new Color(0, 0,0, 0));
		hiden.setBorder(new EmptyBorder(0, 198, 0, 0));
		
		center.add(box1);
		center.add(box2);
		center.add(hiden);
		t.add(center,BorderLayout.CENTER);
		
		JPanel south = new JPanel();
		south.add(createButtonInAcc("Đăng nhập",""));
		
		south.setBackground(new Color(0, 0,0, 0));
		t.add(south,BorderLayout.SOUTH);
		
	
	}
	public JButton createButtonInAcc(String nameButton,String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(150, 40));
		btn.addActionListener((e)->{
			if (nameButton.equals("Đăng nhập")) {
				
//				if(maNhanVienJtextField.getText().equals("admin")&&passwordField.getText().equals("123")) {
//					framee.dispose();
//					
//					
//					JFrame frame  = new JFrame();
//					default_UI_2 run = new default_UI_2(frame);
//				
//				}
				ArrayList<taiKhoan> ds = dstk.getTaiKhoan();
				for (taiKhoan tk : ds) {
					System.out.println(tk.getMaNV().getMaNV());
					if(tk.getMaNV().getMaNV().equals(maNhanVienJtextField.getText())&&tk.getMatKhau().equals(passwordField.getText())) {
						framee.dispose();
						JFrame frame  = new JFrame();
						default_UI_2 run = new default_UI_2(frame,tk.getMaNV().getMaNV());
						
						
					}
					else {
						JOptionPane.showMessageDialog(this, "tai khoan sai");
					}
				}
			
			}
			
		});
		return btn;
	}
	public void forcusListen() {
		

			 maNhanVienJtextField.addMouseListener(new MouseAdapter() {
		            @Override
		            public void mouseEntered(MouseEvent e) {
		            	 isHovering = true ;
		                // Xóa văn bản khi hover vào JTextField
		            	if(maNhanVienJtextField.getText().equals("admin"))
		            		maNhanVienJtextField.setText("");
		            	
		            }

		            @Override
		            public void mouseExited(MouseEvent e) {
		            	
		                
		                if (maNhanVienJtextField.getText().isEmpty()) {
		                    maNhanVienJtextField.setText("admin");
		                }
		            }
		        });
		

	}
	public void checkJcheckbox() {
		hienMatKhau.addActionListener(e->{
			if(hienMatKhau.isSelected()) {
				 passwordField.setEchoChar((char) 0);
			}else {
				 passwordField.setEchoChar('*');
			}
		});
	}
}
