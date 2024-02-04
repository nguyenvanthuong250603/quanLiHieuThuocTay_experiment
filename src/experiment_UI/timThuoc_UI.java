package experiment_UI;

import static experiment_UI.Generate_All.createJcombobox;
import static experiment_UI.Generate_All.createTitle;
import static experiment_UI.Generate_All.sampleModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import static experiment_UI.Generate_All.*;

public class timThuoc_UI {
	private JComboBox cbLoaiThuoc;
	private JComboBox cbNSX;
	private JFrame frame;
	private JCheckBox cb;

	public void getTimThuoc() {
		frame = new JFrame();
		frame.setTitle("Tìm kiếm thuốc");
		frame.add(diaLog());
		frame.setSize(1100, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
	}

	public JPanel diaLog() {
		JPanel control = new JPanel(new BorderLayout());
		JLabel title = new JLabel("TÌM KIẾM THUỐC");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arail",Font.BOLD,30) );
		title.setForeground(Color.BLUE);
		control.add(title, BorderLayout.NORTH);
		control.add(searchAndfilter(), BorderLayout.CENTER);
		return control;
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
		box1.add(createJcombobox("Nhà sản xuất", cbNSX));

		cbLoaiThuoc = new JComboBox(optionNsx);
		box1.add(createJcombobox("Loại thuốc", cbLoaiThuoc));

		box.add(box1, BorderLayout.CENTER);
		JButton loc = buttonInPageSearch("Lọc", "");
		box.add(loc, BorderLayout.EAST);

		compoment.add(box);

		return compoment;
	}

	public JPanel findID() {
		JPanel f = new JPanel();
		f.setLayout(new BorderLayout());
		JTextField text = new JTextField();
		f.add(text, BorderLayout.CENTER);
		f.setBorder(new EmptyBorder(5, 5, 5, 0));
		JButton timkiemMaThuoc = buttonInPageSearch("Tìm kiếm", "");
		f.add(timkiemMaThuoc, BorderLayout.EAST);
		return f;
	}

	public JPanel table_information() {
		JPanel managerment = new JPanel();
		createTitle(managerment, "Danh sách sản phẩm");
		managerment.setLayout(new BorderLayout());
		String[] column = { "Mã thuốc", "Tên thuốc ", "Số lượng", "Giá", "Loại thuốc", "Nhà sản xuất", "Ngày sản xuất",
				"Ngày hết Hạn", };

		String[][] row = {
				{ "SP01", "Bảo thanh", "" + 20, "" + 10000, "Thực phẩm chức năng", "Công Ty Nam Cao", "31/01/2024",
						"30/04/2024" },

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

	public JButton buttonInPageSearch(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize( new Dimension(120,40));
		btn.addActionListener(e -> {
		
		
			System.out.println(nameButton);
		});
		return btn;
	}

	public JPanel footer_table() {
		JPanel footer = new JPanel();

		String[] object = { "", "gift\\reset.png", "Chọn", "gift\\check.png", "Thoát", "gift\\exit.png" };
	
		for (int i = 0; i < object.length; i += 2) {
			JButton btn = buttonInPageSearch(object[i], object[i + 1]);
			footer.add(btn);
		}

		return footer;
	}
}
