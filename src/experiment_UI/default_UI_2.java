package experiment_UI;

import static experiment_UI.brief.T;
import static experiment_UI.brief.HD;
import static experiment_UI.brief.BT;
import static experiment_UI.brief.*;
import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

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

public class default_UI_2 {
	private JPanel sidebar;
	private JPanel container;
	private CardLayout cardLayout;
	private AbstractButton selectedButton = null;
	private JFrame jFrame;

	public default_UI_2(JFrame jFrame) {
		this.jFrame = jFrame;
		jFrame.setTitle("Hiệu Thuốc Ánh Dương");
		jFrame.setExtendedState(jFrame.MAXIMIZED_BOTH);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String iconPath = "gift\\store.png";
		ImageIcon icon = new ImageIcon(iconPath);
		Image iconImage = icon.getImage();
		jFrame.setIconImage(iconImage);
		jFrame.setLocationRelativeTo(null);

		JScrollPane sidebar = new JScrollPane(west());
		sidebar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sidebar.setPreferredSize(new Dimension((int) sidebar.getPreferredSize().getWidth(),
				(int) sidebar.getPreferredSize().getHeight()));
		sidebar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		container = new JPanel();
		cardLayout = new CardLayout();
		container.setLayout(cardLayout);

		container.add(createQuanLyThuoc(), T);
		container.add(createQuanLyNhapThuoc(),QLDNT);
		container.add(createBanThuoc(), BT);
		container.add(createDoiThuoc(), DT);
		container.add(createNhapThuoc(),NT);
		jFrame.add(sidebar, BorderLayout.WEST);
		jFrame.add(container, BorderLayout.CENTER);
//		jFrame.setResizable(false);
		jFrame.setVisible(true);
	}

	public JPanel createCompoment(String iconRight, String nameButton, String iconLeft, boolean type) {
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

		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (type) {
					if (selectedButton != null) {
						selectedButton.setBackground(new Color(0, 132, 255));
					}
					btn.setBackground(Color.red);
					selectedButton = btn;
				}
				switch (nameButton) {

				case T: {
					cardLayout.show(container, T);

					break;
				}
				case HD: {
					if (btn.isSelected()) {

						btn.setIcon(new ImageIcon("gift//upload.png"));

						JPanel r = new JPanel();
						r.setLayout(new BoxLayout(r, BoxLayout.Y_AXIS));
						JPanel v = createCompoment("gift\\upload.png", "Nhập thuốc", "gift\\hoadon.png", true);
						JPanel o = createCompoment("gift\\upload.png", "QL Đơn bán", "gift\\hoadon.png", true);
						JPanel z = createCompoment("gift\\upload.png", "QL Nhập thuốc", "gift\\hoadon.png", true);
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

					cardLayout.show(container, BT);

					break;
				}
				case DT: {
					cardLayout.show(container, DT);
					break;
				}
				case NT:{
					cardLayout.show(container, NT);
					break;
				}
				case QLDNT:{
					cardLayout.show(container, QLDNT);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + nameButton);
				}

				jFrame.repaint();
				jFrame.revalidate();

			}
		});

		compoment.add(btn);
		return compoment;
	}

	public JPanel west() {
		JPanel westt = new JPanel();
		Box boxx = Box.createVerticalBox();
		westt.setBackground(new Color(0, 132, 255));
		boxx.add(brand());

		boxx.add(createCompoment("gift\\thuoc.png", "Bán thuốc", "gift\\thuoc.png", true));
		boxx.add(createCompoment("gift\\thuoc.png", "Thuốc", "gift\\hoadon.png", true));
		boxx.add(createCompoment("gift\\upload.png", "Đổi Thuốc", "gift\\hoadon.png", true));
		boxx.add(createCompoment("gift\\down.png", "Hóa đơn", "gift\\hoadon.png", false));
		boxx.add(createCompoment("gift\\upload.png", "Khách hàng", "gift\\hoadon.png", true));
		boxx.add(createCompoment("gift\\thuoc.png", "Nhân viên", "gift\\hoadon.png", true));

		boxx.add(createCompoment("gift\\thuoc.png", "Thống kê", "gift\\drug.png", true));
		boxx.add(createCompoment("gift\\thuoc.png", "Kiểm toán", "gift\\hoadon.png", true));
		boxx.add(createCompoment("gift\\thuoc.png", "Đăng xuất", "gift\\hoadon.png", true));
		westt.add(boxx);
		return westt;
	}

	public JPanel brand() {
		JPanel compomet = new JPanel();
		ImageIcon iconn = new ImageIcon("gift\\storee.png");
		JLabel text = new JLabel(iconn);
		text.setText("Cửa Hàng");
		text.setFont(new Font("Arial", Font.BOLD, 30));
		text.setForeground(Color.WHITE);
		text.setIconTextGap(20);
		compomet.setBorder(new EmptyBorder(20, 20, 10, 20));
		compomet.setBackground(new Color(0, 132, 255));
		compomet.add(text);
		return compomet;
	}

	private JPanel createQuanLyThuoc() {
		quanLyThuoc_UI quanLyThuocUI = new quanLyThuoc_UI();
		return quanLyThuocUI.getQuanLiThuoc();
	}

	private JPanel createBanThuoc() {
		banThuoc_UI banThuocUI = new banThuoc_UI();
		return banThuocUI.getBanThuoc();
	}

	private JPanel createDoiThuoc() {
		doiThuoc_UI doiThuoc = new doiThuoc_UI();
		return doiThuoc.getDoiThuoc();
	}
	private JPanel createNhapThuoc() {
		nhapThuoc_UI nhapThuoc = new  nhapThuoc_UI();
		return nhapThuoc.getNhapThuoc();
	}
	private JPanel createQuanLyNhapThuoc() {
		quanLyDonNhapThuoc_UI QLDNT = new quanLyDonNhapThuoc_UI();
		return QLDNT.getQuanLyDonNhapThuoc();
	}
}