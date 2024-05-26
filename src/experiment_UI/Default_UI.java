package experiment_UI;

import static experiment_UI.Brief.*;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import dao.HoaDon_DAO;
import dao.NhanVien_DAO;
import entity.HoaDon;
import entity.NhanVien;

public class Default_UI {
	
	private JPanel container;
	private CardLayout cardLayout;
	private AbstractButton selectedButton = null;
	private JFrame jFrame;
	private NhanVien_DAO nv_Dao = new NhanVien_DAO();
	private HoaDon_DAO hoaDon_DAO = new HoaDon_DAO();
	private NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
	private String maNhanVien;
	public Default_UI(JFrame jFrame, String MaNV) {
		this.jFrame = jFrame;
		jFrame.setTitle("Hiệu Thuốc Ánh Dương");
		jFrame.setExtendedState(jFrame.MAXIMIZED_BOTH);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String iconPath = "gift\\logo1.jpg";
		ImageIcon icon = new ImageIcon(iconPath);
		Image iconImage = icon.getImage();
		jFrame.setIconImage(iconImage);
		jFrame.setLocationRelativeTo(null);

		JScrollPane sidebar = new JScrollPane(west(MaNV));
		sidebar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sidebar.setPreferredSize(new Dimension((int) sidebar.getPreferredSize().getWidth(),
				(int) sidebar.getPreferredSize().getHeight()));
		sidebar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		container = new JPanel();
		cardLayout = new CardLayout();
		container.setLayout(cardLayout);
		container.add(createThongKeDoanhThuTheoThuoc(),TKDTT);
		container.add(createThongKeDoanhThu(),TKDT);
		container.add(createHoaDon(hoaDon_DAO), HĐBT);
		container.add(createBanThuocKeLaiDon(MaNV), KLD);
		container.add(createBanThuocKeDonMoi(MaNV), HDM);
		container.add(createKhachHang(), KH);
		container.add(createQuanLyThuoc(), T);
		container.add(createThongKe(), TKNV);
		container.add(createNhanVien(), NV);
		container.add(createDoiThuoc(MaNV), DT);
		container.add(createKhachHang(), KH);

		
		jFrame.add(sidebar, BorderLayout.WEST);
		jFrame.add(container, BorderLayout.CENTER);
		this.maNhanVien = MaNV;
		jFrame.setResizable(false);
		jFrame.setVisible(true);
	}

	public JPanel createCompoment(String iconRight, String nameButton, String iconLeft, boolean type) {
		JPanel compoment = new JPanel(new BorderLayout());

		AbstractButton btn;
		
		ImageIcon icon = new ImageIcon(iconLeft);
		Image image = icon.getImage(); // Lấy hình ảnh từ ImageIcon
		Image newImage = image.getScaledInstance(48, 48, Image.SCALE_DEFAULT); // Thay đổi kích thước hình ảnh
		ImageIcon newIcon = new ImageIcon(newImage); // Tạo mới ImageIcon với hình ảnh đã thay đổi kích thước
		
		JLabel lableicon = new JLabel(newIcon); 
		lableicon.setText(nameButton);
		lableicon.setBackground(Color.white);
		lableicon.setIconTextGap(20);
		lableicon.setFont(new Font("Arial", Font.BOLD, 20));
		lableicon.setForeground(Color.WHITE);
		
		if (type) {
			
			btn = new JButton();
			btn.setPreferredSize(
					new Dimension(compoment.getPreferredSize().width + 260, btn.getPreferredSize().height + 60));
			if(nameButton.equals("Kê lại đơn")|| nameButton.equals("Tạo đơn mới")||nameButton.equals("nhân viên")||nameButton.equals("doanh thu")||nameButton.equals("thuốc")) {
				lableicon.setBorder( new EmptyBorder(0, 20, 0, 0));
			}
			
		} else {
			
			ImageIcon iconn = new ImageIcon(iconRight);
			btn = new JToggleButton();
			btn.setIcon(iconn);
			btn.setIcon(new ImageIcon("gift//upload.png"));
			btn.setPreferredSize(
					new Dimension(compoment.getPreferredSize().width + 260, btn.getPreferredSize().height + 40));
		}

		btn.setBackground(new Color(89, 168, 104));
		btn.setFocusPainted(false);
		btn.setBorder(new EmptyBorder(0, 20, 0, 20));

		
		

		btn.add(lableicon);

		btn.setHorizontalAlignment(SwingConstants.RIGHT);

		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (type) {
					if (selectedButton != null) {
						selectedButton.setBackground(new Color(89, 168, 104));
					}
					btn.setBackground(Color.GREEN);
					selectedButton = btn;
				}
				
				switch (nameButton) {

				case T: {
					cardLayout.show(container, T);

					break;
				}
				case HD: {
				
						cardLayout.show(container, HĐBT);		
					
					break;
				}
				case BT: {
					if (btn.isSelected()) {
						btn.setIcon(new ImageIcon("gift//down.png"));
									
						JPanel r = new JPanel();
						r.setLayout(new BoxLayout(r, BoxLayout.Y_AXIS));
						JPanel v = createCompoment("gift\\upload.png", "Tạo đơn mới", "gift\\hoadonmoi.png", true);
						JPanel o = createCompoment("gift\\upload.png", "Kê lại đơn", "gift\\kelaidon.png", true);
			
						Object[] xx = { v, o};
						for (Object s : xx) {
							r.add((Component) s);
						}
						compoment.add(r, BorderLayout.AFTER_LAST_LINE);

					} else {

						btn.setIcon(new ImageIcon("gift//upload.png"));
						for (int i = 1; i < compoment.getComponentCount(); i++) {
							compoment.remove(i);
						}
					}
//					cardLayout.show(container, BT);

					break;
				}
				case DT: {
					cardLayout.show(container, DT);
					break;
				}
			
				case KH: {
					cardLayout.show(container, KH);
					break;
				}
				case NV: {
					cardLayout.show(container, NV);
					break;
				}
				case HĐBT: {
					cardLayout.show(container, HĐBT);
					break;
				}
				case TK: {
					if (btn.isSelected()) {
						btn.setIcon(new ImageIcon("gift//down.png"));
									
						JPanel r = new JPanel();
						r.setLayout(new BoxLayout(r, BoxLayout.Y_AXIS));
						JPanel v = createCompoment("gift\\upload.png", "nhân viên", "gift\\tknv.png", true);
						JPanel t = createCompoment("gift\\upload.png", "thuốc", "gift\\tktt.png", true);
						JPanel o = createCompoment("gift\\upload.png", "doanh thu", "gift\\tkdt.png", true);
			
						Object[] xx = { v,t, o};
						for (Object s : xx) {
							r.add((Component) s);
						}
						compoment.add(r, BorderLayout.AFTER_LAST_LINE);

					} else {

						btn.setIcon(new ImageIcon("gift//upload.png"));
						for (int i = 1; i < compoment.getComponentCount(); i++) {
							compoment.remove(i);
						}
					}
					

					break;
				}
				case HDM :{
					cardLayout.show(container, HDM);
					break;
				}
				case KLD :{
					cardLayout.show(container, KLD);
					break;
				}
				case ĐX: {
					int recomment= JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn đăng xuất chứ ?" , "Lưu ý", JOptionPane.YES_NO_OPTION);
					if(recomment==JOptionPane.YES_OPTION) {
						jFrame.dispose();
						new TaiKhoan_UI();
					}
					break;
				}
				case TKNV : {
					cardLayout.show(container, TKNV);
					break;
				}
				case TKDT : {
					cardLayout.show(container, TKDT);
					break;
				}
				case TKDTT : {
					cardLayout.show(container, TKDTT);
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

	public JPanel west(String MaNV) {
		JPanel westt = new JPanel();
		Box boxx = Box.createVerticalBox();
		westt.setBackground(new Color(89, 168, 104));
		boxx.add(brand(MaNV));

		boxx.add(createCompoment("gift\\thuoc.png", "Bán Thuốc", "gift\\banthuoc.png", false));
		boxx.add(createCompoment("gift\\thuoc.png", "Thuốc", "gift\\quanlythuoc.png", true));
		boxx.add(createCompoment("gift\\upload.png", "Đổi Thuốc", "gift\\doitra.png", true));
		boxx.add(createCompoment("gift\\down.png", "Hóa Đơn", "gift\\bill.png", true));
		boxx.add(createCompoment("gift\\upload.png", "Khách Hàng", "gift\\khachhang.png", true));
		boxx.add(createCompoment("gift\\thuoc.png", "Nhân Viên", "gift\\nhanvien.png", true));
		boxx.add(createCompoment("gift\\thuoc.png", "Thống Kê", "gift\\thongke.png", false));
		boxx.add(createCompoment("gift\\thuoc.png", "Đăng Xuất", "gift\\logout.png", true));
		westt.add(boxx);
		return westt;
	}

	public JPanel brand(String MaNV) {
		JPanel compomet = new JPanel(new BorderLayout());
		ImageIcon iconn = new ImageIcon("gift\\logo2.png");
		JLabel text = new JLabel(iconn);
		text.setIconTextGap(20);
		compomet.setBorder(new EmptyBorder(20, 0, 10, 20));
		compomet.setBackground(new Color(89, 168, 104));
		compomet.add(text, BorderLayout.NORTH);

		NhanVien nv = nv_Dao.getNhanVienFindByID(MaNV);
		JLabel x = new JLabel(nv.getChucVu() + " :  " + nv.getHoTen());
		x.setFont(new Font("Arial", Font.BOLD, 18));
		x.setBorder(new EmptyBorder(15, 10, 0, 0));
		x.setForeground(Color.WHITE);

		compomet.add(x, BorderLayout.AFTER_LAST_LINE);
		return compomet;
	}

	private JPanel createQuanLyThuoc() {
		QuanLyThuoc_UI quanLyThuocUI = new QuanLyThuoc_UI();
		return quanLyThuocUI.getQuanLiThuoc();
	}

	private JPanel createBanThuocKeDonMoi(String maNV) {
		BanThuocKeDonMoi_UI banThuocUI = new BanThuocKeDonMoi_UI();
		return banThuocUI.getBanThuoc(maNV);
	}

	private JPanel createDoiThuoc(String maNV) {
		DoiThuoc_UI doiThuoc = new DoiThuoc_UI();
		return doiThuoc.getDoiThuoc(maNV);
	}



	private JPanel createKhachHang() {
		KhachHang_UI KH = new KhachHang_UI();
		return KH.getKhachHang();
	}

	private JPanel createNhanVien() {
		NhanVien_UI NV = new NhanVien_UI();
		return NV.getNhanVien();
	}

	private JPanel createHoaDon(HoaDon_DAO hoaDon_Dao) {
		HoaDonBanThuoc_UI hd = new HoaDonBanThuoc_UI();
		return hd.getHoaDon(hoaDon_DAO);
	}
	private JPanel createThongKe() {
		ThongKe_UI thongKe_UI = new ThongKe_UI();
		return thongKe_UI.getThongKe();
	}
	private JPanel createBanThuocKeLaiDon(String maNV) {
		BanThuocKeLaiDon_UI banThuoc =new  BanThuocKeLaiDon_UI();
		return banThuoc.getBanThuocKeLaiDon(maNV);
		
	}
	private JPanel createThongKeDoanhThu() {
		ThongKeDoanhThu_UI tKDT = new ThongKeDoanhThu_UI();
		return tKDT.getThongKeDoanhThu();
	}
	private JPanel createThongKeDoanhThuTheoThuoc() {
		ThongKeDoanhThuTheoThuoc_UI tKDTT = new ThongKeDoanhThuTheoThuoc_UI();
		return tKDTT.getThongKeDoanhThuTheoThuoc();
	}
}