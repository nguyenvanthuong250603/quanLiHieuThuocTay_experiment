package experiment_UI;

import static experiment_UI.brief.T;
import static experiment_UI.brief.HD;
import static experiment_UI.brief.BT;
import static experiment_UI.brief.DT;
import java.awt.Adjustable;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

public class default_UI extends JFrame {
	private quanLyThuoc_UI quanLyThuoc = new quanLyThuoc_UI();
	private AbstractButton selectedButton = null;
	private banThuoc_UI banThuoc = new banThuoc_UI();
	private doiThuoc_UI doiThuoc = new doiThuoc_UI();

	public default_UI() {

		setTitle("NHÀ THUỐC ÁNH DƯƠNG");
		String iconPath = "gift\\store.png";

		ImageIcon icon = new ImageIcon(iconPath);
		Image iconImage = icon.getImage();
		setIconImage(iconImage);
		setLocationRelativeTo(null);
		JScrollPane scollWest = new JScrollPane(west());
		scollWest.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scollWest.setPreferredSize(new Dimension((int) scollWest.getPreferredSize().getWidth(),
				(int) scollWest.getPreferredSize().getHeight()));
		scollWest.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scollWest, BorderLayout.WEST);

		setExtendedState(MAXIMIZED_BOTH);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new default_UI();
	}

	public JPanel west() {
		JPanel westt = new JPanel();
		Box boxx = Box.createVerticalBox();
		westt.setBackground(new Color(0, 132, 255));
		boxx.add(brand());
		String[][] t = {};
		boxx.add(createCompoment("gift\\thuoc.png", "Bán thuốc", t, "gift\\thuoc.png", true));
		boxx.add(createCompoment("gift\\thuoc.png", "Thuốc", t, "gift\\hoadon.png", true));
		boxx.add(createCompoment("gift\\upload.png", "Đổi Thuốc", t, "gift\\hoadon.png", true));
		boxx.add(createCompoment("gift\\down.png", "Hóa đơn", t, "gift\\hoadon.png", false));
		boxx.add(createCompoment("gift\\upload.png", "Khách hàng", t, "gift\\hoadon.png", true));
		boxx.add(createCompoment("gift\\thuoc.png", "Nhân viên", t, "gift\\hoadon.png", true));

		boxx.add(createCompoment("gift\\thuoc.png", "Thống kê", t, "gift\\drug.png", true));
		boxx.add(createCompoment("gift\\thuoc.png", "Kiểm toán", t, "gift\\hoadon.png", true));
		boxx.add(createCompoment("gift\\thuoc.png", "Đăng xuất", t, "gift\\hoadon.png", true));
		westt.add(boxx);
		return westt;
	}

	public JPanel brand() {
		JPanel compomet = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ImageIcon iconn = new ImageIcon("gift\\storee.png");
		JLabel text = new JLabel(iconn);
		text.setText("Cửa Hàng");
		text.setFont(new Font("Arial", Font.BOLD, 30));
		text.setForeground(Color.WHITE);
		text.setIconTextGap(20);
		compomet.setBorder(new EmptyBorder(40, 20, 10, 20));
		compomet.setBackground(new Color(0, 132, 255));
		compomet.add(text);
		return compomet;
	}

	public JButton createJbutton(JPanel compoment) {
		JButton btn = new JButton();
		btn.setBackground(new Color(0, 132, 255));
		btn.setFocusPainted(false);
		btn.setBorder(new EmptyBorder(0, 20, 0, 10));
		btn.setPreferredSize(
				new Dimension(compoment.getPreferredSize().width + 260, btn.getPreferredSize().height + 60));
		return btn;
	}

	public JButton createButton(String name, String icon1) {
		JButton btn = new JButton();
		btn.setBackground(new Color(0, 132, 255));
		btn.setFocusPainted(false);
		btn.setBorder(new EmptyBorder(0, 20, 0, 10));

		JLabel lableicon = new JLabel(new ImageIcon(icon1));
		lableicon.setText(name);
		lableicon.setBackground(Color.white);
		lableicon.setIconTextGap(20);
		lableicon.setFont(new Font("Arial", Font.BOLD, 20));
		lableicon.setForeground(Color.WHITE);

		btn.add(lableicon);
		btn.setHorizontalAlignment(SwingConstants.RIGHT);

		return btn;
	}

	public JPanel createCompoment(String iconRight, String nameButton, String[][] obj, String iconLeft, boolean type) {
		JPanel compoment = new JPanel(new BorderLayout());
		compoment.setBackground(new Color(0, 132, 255));
		AbstractButton btn;

		if (type) {

			btn = new JButton();
			btn.setPreferredSize(
					new Dimension(compoment.getPreferredSize().width + 260, btn.getPreferredSize().height + 60));
		} else {
			ImageIcon iconn = new ImageIcon(iconRight);
			btn = new JToggleButton();
			btn.setIcon(iconn);
			btn.setPreferredSize(
					new Dimension(compoment.getPreferredSize().width + 260, btn.getPreferredSize().height + 40));
		}

		btn.setBackground(new Color(0, 132, 255));
		btn.setFocusPainted(false);
		btn.setBorder(new EmptyBorder(0, 20, 0, 10));

		JLabel lableicon = new JLabel(new ImageIcon(iconLeft));
		lableicon.setText(nameButton);
		lableicon.setBackground(Color.white);
		lableicon.setIconTextGap(20);
		lableicon.setFont(new Font("Arial", Font.BOLD, 20));
		lableicon.setForeground(Color.WHITE);

		btn.add(lableicon);

		btn.setHorizontalAlignment(SwingConstants.RIGHT);

		if (obj.length <= 0) {
			btn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					if (selectedButton != null) {
						selectedButton.setBackground(new Color(0, 132, 255));
					}
					btn.setBackground(Color.red);
					selectedButton = btn;

					switch (nameButton) {

					case T: {
						add(quanLyThuoc.getQuanLiThuoc(), BorderLayout.CENTER);
						
						break;
					}
					case HD: {
						if (btn.isSelected()) {

							btn.setIcon(new ImageIcon("gift//upload.png"));
							String[][] objj = {};
							JPanel r = new JPanel();
							r.setLayout(new BoxLayout(r, BoxLayout.Y_AXIS));
							JPanel v = createCompoment("gift\\upload.png", "new", objj, "gift\\hoadon.png", true);
							JPanel o = createCompoment("gift\\upload.png", "kdfsksa", objj, "gift\\hoadon.png", true);
							JPanel z = createCompoment("gift\\upload.png", "kdfsksa", objj, "gift\\hoadon.png", true);
							Object[] xx = { v, o, z };
							for (Object s : xx) {
								r.add((Component) s);
							}

							compoment.add(r, BorderLayout.AFTER_LAST_LINE);

						} else {

							btn.setIcon(new ImageIcon("gift//down.png"));
							for (int i = 1; i < compoment.getComponentCount(); i++) {
								compoment.remove(i);
							}
						}
						break;
					}
					case BT: {

						add(banThuoc.getBanThuoc(), BorderLayout.CENTER);

						break;
					}
					case DT:{
						add(doiThuoc.getDoiThuoc(),BorderLayout.CENTER);
						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + nameButton);
					}
					
					repaint();
					revalidate();

				}
			});
		} else {

		}
		compoment.add(btn);
		return compoment;
	}
}