package experiment_UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.chrono.JapaneseDate;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import javax.swing.JFileChooser;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.table.DefaultTableModel;
import javax.xml.stream.events.Comment;

import com.toedter.calendar.JDateChooser;

import static experiment_UI.Generate_All.*;

public class quanLyThuoc_UI {
	private JLabel labelImage;
	private JTextField jTextMaThuoc, jTextTenThuoc, jTextGiaThuoc, jTextSoLuong, jTextNgaySx, jTextNgayHetHan,
			jTextDonVi;
	private JComboBox cbNSX,cbLoaiThuoc,cbTuoiSD;
	private JCheckBox cb;
	private JDateChooser JdateNgaySanXuat, JdateNgayHetHan;
	private Object[][] object_inf, object_detail;
	private JTextArea jTextAreaMoTa;
	public JPanel getQuanLiThuoc() {
		JPanel container = new JPanel(new BorderLayout());
		container.add(searchAndfilter(), BorderLayout.CENTER);
		container.add(inputProduct(), BorderLayout.EAST);
		return container;
	}

	public JPanel table_information() {
		JPanel managerment = new JPanel();
		createTitle(managerment, "Danh sách sản phẩm");
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã thuốc", "Tên thuốc ", "Số lượng", "Giá","Loại thuốc","Nhà sản xuất","Ngày sản xuất", "Ngày hết Hạn", 
				 };

		String[][] row = {
				{ "SP01", "Bảo thanh", ""+20, ""+10000,
						"Thực phẩm chức năng", "Công Ty Nam Cao", "31/01/2024", "30/04/2024" },

				{ "", "", "", "", "", "", "", "" } };
		DefaultTableModel model = new DefaultTableModel(row, column);
		JTable table = new JTable(model);
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		JScrollPane scoll = new JScrollPane(table);
		managerment.add(scoll, BorderLayout.CENTER);
		managerment.add(footer_table(), BorderLayout.SOUTH);
		return managerment;
	}

	public JPanel findID() {
		JPanel f = new JPanel();
		f.setLayout(new BorderLayout());
		JTextField text = new JTextField();
		f.add(text, BorderLayout.CENTER);
		f.setBorder(new EmptyBorder(5, 5, 5, 0));
		JButton timkiemMaThuoc = buttonInPage("Tìm kiếm", "");
		f.add(timkiemMaThuoc, BorderLayout.EAST);
		return f;
	}

	public JPanel searchAndfilter() {
		JPanel compoment = new JPanel();
		compoment.setLayout(new BorderLayout());
		compoment.add(rubi(), BorderLayout.NORTH);
		compoment.add(table_information(), BorderLayout.CENTER);
		return compoment;
	}

	public JPanel rubi() {
		JPanel compoment = new JPanel();
		compoment.setLayout(new BoxLayout(compoment, BoxLayout.Y_AXIS));

		createTitle(compoment, "Tìm kiếm và lọc");
		compoment.add(findID());
		JPanel box = new JPanel(new BorderLayout());

		JPanel box1 = new JPanel();
		box1.setLayout(new GridLayout(2, 2, 10, 10));

		JPanel t = new JPanel();
		t.setLayout(new BoxLayout(t, BoxLayout.X_AXIS));
		t.add(sampleModel("Tên thuốc"));
		JTextField tenThuoc = new JTextField();
		t.add(tenThuoc);

		JPanel t2 = new JPanel();
		t2.setLayout(new BoxLayout(t2, BoxLayout.X_AXIS));
		t2.add(cb = new JCheckBox());
		t2.add(sampleModel("sản phẩm hết hàng"));

		

		box1.add(t);
		box1.add(t2);
		String[] optionNsx = { "NSX1", "NSX2", "NSX3" };
		cbNSX = new JComboBox(optionNsx);
		box1.add(createJcombobox("Nhà sản xuất",cbNSX));
		
		cbLoaiThuoc = new JComboBox(optionNsx);
		box1.add(createJcombobox("Loại thuốc",cbLoaiThuoc));

		
		box.add(box1, BorderLayout.CENTER);
		JButton loc = buttonInPage("Lọc", "");
		box.add(loc, BorderLayout.EAST);

		compoment.add(box);

		return compoment;
	}



	public JPanel footer_table() {
		JPanel footer = new JPanel();

		String[] object = { "", "gift\\reset.png", "Xóa", "gift\\trash-bin.png", "Excel", "gift\\excel-file.png" };
		for (int i = 0; i < object.length; i += 2) {
			JButton btn = buttonInPage(object[i], object[i + 1]);
			footer.add(btn);
		}

		return footer;
	}
	//bảng 2 tạo thông tin
	public JPanel inputProduct() {
		JPanel inputManager = new JPanel(new GridLayout(2, 1));

		inputManager.add(inputInformation());
		inputManager.add(detailInformation());
		return inputManager;
	}
//bảng 2 tạo thông tin _ nửa trên
	public JPanel inputInformation() {
		JPanel total = new JPanel(new BorderLayout());
		JPanel inf = new JPanel(new BorderLayout());
		createTitle(total, "Thông tin thuốc");
//		top chứa phần hình ảnh và phần kế bên
		JPanel top = new JPanel(new GridLayout(1, 2, 5, 5));

		JPanel boxImage = new JPanel(new BorderLayout());

		labelImage = new JLabel();
		labelImage.setPreferredSize(new Dimension(200, 150));
		boxImage.add(labelImage, BorderLayout.CENTER);

		JButton inputImage = buttonInPage("Chọn hình ảnh", "");
		boxImage.add(inputImage, BorderLayout.SOUTH);

		top.add(boxImage);
//	bên phải ảnh	
		JPanel infRight_top = new JPanel(new BorderLayout());

//		ma sp
		jTextMaThuoc = new JTextField();
		JLabel labelMaThuoc= new JLabel("Mã thuốc");
		infRight_top.add(createNameAndTextField(jTextMaThuoc, "Mã thuốc"), BorderLayout.NORTH);
//Ten thuoc
		JPanel box2 = new JPanel(new BorderLayout());
		JLabel areText = sampleModel("Tên thuốc");
		box2.add(areText, BorderLayout.NORTH);
		jTextTenThuoc = new JTextField(10);
		
		box2.add(jTextTenThuoc, BorderLayout.CENTER);

		infRight_top.add(box2, BorderLayout.CENTER);

		top.add(infRight_top);
//		
		inf.add(top, BorderLayout.CENTER);
//tao gia , so luong
		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		String[] optionNsx = { "NSX1", "NSX2", "NSX3" };
		Object[][] trage = { { "Số lượng", new JTextField() }, { "Giá", new JTextField() },
				{ "Đơn vị", new JTextField() }, { "Loại thuốc",new JComboBox(optionNsx)},{"Độ tuổi",new JComboBox(optionNsx)},{ "NSX", new JComboBox(optionNsx) },
				{ "Ngày SX", new JDateChooser() }, { "Ngày Hết Hạn", new JDateChooser() } };
		object_inf = trage;
		for (Object[] objects : object_inf) {
			if (objects[1] instanceof Component) {
				JPanel t = new JPanel(new BorderLayout());
				t.add(sampleModel(objects[0].toString()), BorderLayout.WEST);
				t.setBorder(new EmptyBorder(5, 0, 5, 0));
				t.add((Component) objects[1], BorderLayout.CENTER);
				bottom.add(t);
				
			} else {
				bottom.add(createJcombobox(objects[0].toString(), (JComboBox) objects[1]));
			}

		}
		inf.add(bottom, BorderLayout.SOUTH);
		JScrollPane scroll = new JScrollPane(inf);
		total.add(scroll);

		return total;
	}
// phần 2 nữa dưới
	public JPanel detailInformation() {
		JPanel detail_big = new JPanel(new BorderLayout());
		createTitle(detail_big, "Chi tiết thông tin");
		JPanel detail_compoment = new JPanel();
		detail_compoment.setLayout(new BoxLayout(detail_compoment, BoxLayout.Y_AXIS));
		
		
 		
		Object[][] trage = { { "Thành phần", new JTextField() }, { "Chỉ định", new JTextField() },
				 { "Liều dùng", new JTextField() }, { "Chống chỉ đinh", new JTextField() },
				{ "Bảo quản", new JTextField() },{"Địa chỉ NSX", new JTextField()}};
		object_detail = trage;
		for (Object[] objects : trage) {
			detail_compoment.add(createNameAndTextField((JTextField)objects[1], objects[0].toString()));
		};
		jTextAreaMoTa  = new JTextArea();
		detail_compoment.add(createTextArea("Mô tả", jTextAreaMoTa));
		JScrollPane scroll = new JScrollPane(detail_compoment);
		detail_big.add(scroll);
		detail_big.add(footer_inf(),BorderLayout.SOUTH);
		return detail_big;
	}
//	phần 2 footer
	public JPanel footer_inf() {
		JPanel footer = new JPanel();
		String [][] object = {{"Xóa trắng","gift//brush.png"},{"Cập nhât","gift//update.png"},{"Thêm","gift//add.png"}};
		for (String[] strings : object) {
			JButton btn =buttonInPage(strings[0].toString(), strings[1].toString());
			btn.setPreferredSize(new Dimension(140, 40));
			footer.add(btn);
		}
		return footer;
	}

//hien anh
	private void chooseImage() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
		fileChooser.setFileFilter(filter);

		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			displayImage(selectedFile);
		}
	}

	private void displayImage(File file) {
		try {
			ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
			Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(image);
			labelImage.setIcon(imageIcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

// tạo nút và bắt sự kiện
	public JButton buttonInPage(String nameBtn, String pathIcon) {
		JButton btn = createJbutton(nameBtn, pathIcon);
		btn.setPreferredSize(new Dimension(120, 40));

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameBtn.equals("Chọn hình ảnh")) {
					chooseImage();

						System.out.println(((JTextField) object_inf[0][1])
								.getText());
						System.out.println(((JTextField) object_inf[1][1]).getText());


				} else {
					System.out.println(nameBtn);
				}

			}
		});

		return btn;
	}

}
