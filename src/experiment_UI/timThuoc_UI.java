package experiment_UI;

import static experiment_UI.Generate_All.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.sql.rowset.RowSetWarning;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import dao.NhaSanXuat_DAO;
import dao.Thuoc_DAO;
import entity.NhaSanXuat;
import entity.Thuoc;
import experiment_UI.Generate_All.CustomTableCellRenderer;

import static experiment_UI.Generate_All.*;

public class TimThuoc_UI {
	private JComboBox cbLoaiThuoc;
	private JComboBox cbTinhTrang;
	private JFrame frame;
	private JCheckBox cb;
	private DefaultTableModel model, modelGetThuoc;
	private JTable table, tableGetThuoc;
	private Thuoc_DAO list_Thuoc = new Thuoc_DAO();
	private JComboBox cbNSXTim;
	private JComboBox cbLoaiThuocTim;
	private JTextField textFind;
	private NhaSanXuat_DAO nhaSanXuat_DAO = new NhaSanXuat_DAO();
	private JTextField tenThuoc;

	public void getTimThuoc(DefaultTableModel modell, JTable tablee) {
		frame = new JFrame();
		frame.setTitle("Tìm kiếm thuốc");
		frame.add(diaLog());
		frame.setSize(1100, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		hienBangTableThuoc();
		frame.setResizable(false);
		this.modelGetThuoc = modell;
		this.tableGetThuoc = tablee;
	}

	public JPanel diaLog() {
		JPanel control = new JPanel(new BorderLayout());
		JLabel title = new JLabel("TÌM KIẾM THUỐC");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arail", Font.BOLD, 30));
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
		box1.setBorder(new EmptyBorder(0, 0, 0, 10));
		box1.setLayout(new GridLayout(1, 3, 10, 10));

		JPanel t = new JPanel();
		t.setLayout(new BoxLayout(t, BoxLayout.X_AXIS));
		t.add(sampleModel("Tên thuốc"));
		tenThuoc = new JTextField();
		t.add(tenThuoc);

		box1.add(t);

		String[] optionLoaiThuoc = { "", "Thuốc giảm đau, hạ sốt ", "Thuốc đặc trị", "Kháng sinh", "Thuốc tiêu hóa",
				"Thuốc an thần", "Vitamin", "Thuốc sát khuẩn , khử trùng", "Thuốc chống dị ứng", "Thuốc chống viêm",
				"Thuốc tim mạch", "Thuốc dịch truyền", "Thực phẩm chức năng" };

		cbNSXTim = new JComboBox();
		box1.add(createJcombobox("Nhà sản xuất", cbNSXTim));

		cbLoaiThuocTim = new JComboBox(optionLoaiThuoc);
		box1.add(createJcombobox("Loại thuốc", cbLoaiThuocTim));

		box.add(box1, BorderLayout.CENTER);
		JButton loc = buttonInPageSearch("Lọc", "");
		box.add(loc, BorderLayout.EAST);

		compoment.add(box);

		return compoment;
	}

	public JPanel findID() {
		JPanel f = new JPanel();
		f.setLayout(new BorderLayout());
		textFind = new JTextField();
		f.add(textFind, BorderLayout.CENTER);
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

		model = new DefaultTableModel(column, 0);
		table = new JTable(model);
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		table.setDefaultRenderer(Object.class, new CustomTableCellRendererThuoc());
		JScrollPane scoll = new JScrollPane(table);
		managerment.add(scoll, BorderLayout.CENTER);
		managerment.add(footer_table(), BorderLayout.SOUTH);
		return managerment;
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

	public void hienBangTableThuoc() {

		ArrayList<Thuoc> list_thuoc = list_Thuoc.getThuocDataBase();

		ArrayList<NhaSanXuat> lnsx = nhaSanXuat_DAO.getNhaSanXuatDataBase();

		cbNSXTim.removeAllItems();
		cbNSXTim.addItem("");
		for (NhaSanXuat nhaSanXuat : lnsx) {

			cbNSXTim.addItem(nhaSanXuat.getTenNSX());
		}

		for (Thuoc thuoc : list_thuoc) {
			NhaSanXuat nsx = thuoc.getTenNhaSanXuat();
			String[] row = { thuoc.getMaThuoc(), thuoc.getTenThuoc(), thuoc.getSoLuong() + "", thuoc.getGia() + "",
					thuoc.getLoaiThuoc(), nsx.getTenNSX(), formatTime(thuoc.getNgaySanXuat()) + "",
					"" + formatTime(thuoc.getNgayHetHan()) };
			model.addRow(row);
		}
		table.setModel(model);
	}

	public void serviceGetThuoc() {
		String maThuoc = table.getValueAt(table.getSelectedRow(), 0).toString();

		Thuoc th = list_Thuoc.getThuocByID(maThuoc);

		Object[] row = { th.getMaThuoc(), th.getTenThuoc(), th.getDonVi(), 0, th.getGia(), "" };
		modelGetThuoc.addRow(row);
		tableGetThuoc.setModel(modelGetThuoc);

		int soLuongColumnIndex = 3;

		int lastRowIndex = modelGetThuoc.getRowCount() - 1;
		tableGetThuoc.requestFocus();
		tableGetThuoc.changeSelection(lastRowIndex, soLuongColumnIndex, false, false);
		tableGetThuoc.editCellAt(lastRowIndex, soLuongColumnIndex);
		Component editor = tableGetThuoc.getEditorComponent();
		if (editor != null && editor instanceof JTextComponent) {
			((JTextComponent) editor).selectAll();
		}
		Document doc = ((JTextComponent) editor).getDocument();
		doc.addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!tableGetThuoc.getValueAt(modelGetThuoc.getRowCount() - 1, 3).toString().equals("")
						&& tableGetThuoc.getValueAt(modelGetThuoc.getRowCount() - 1, 3).toString().matches("\\d+"))
					updateTotalPrice2();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!tableGetThuoc.getValueAt(modelGetThuoc.getRowCount() - 1, 3).toString().equals(""))
					updateTotalPrice2();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
		modelGetThuoc.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == soLuongColumnIndex) {
					updateTotalPrice2();

				}
			}
		});

		frame.dispose();
	}

	private void updateTotalPrice2() {

		int soLuongColumnIndex = 3;

		int lastRowIndex = modelGetThuoc.getRowCount() - 1;

		int soLuong = 0;
		double gia = 0;

		try {

			soLuong = Integer.parseInt(tableGetThuoc.getValueAt(lastRowIndex, soLuongColumnIndex).toString());
			gia = Double.parseDouble(tableGetThuoc.getValueAt(lastRowIndex, 4).toString());

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		double thanhTien = soLuong * gia;
		tableGetThuoc.setValueAt(thanhTien, lastRowIndex, 5);

	}

	private boolean regex() {
		if (textFind.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Phải nhập mã thuôc trước khi tìm");
			textFind.requestFocus();
			return false;
		}
//		if (table.getRowCount() > 0) {
//			if (!table.getValueAt(model.getRowCount() - 1, 3).toString().matches("\\d+")) {
//				JOptionPane.showMessageDialog(null, "Số lượng thuốc phải là chữ số");
//				textMaThuocFind.requestFocus();
//				return false;
//			}
//		}
		return true;
	}

	public void timThuoc() {
		if (regex()) {
			String maThuoc = textFind.getText();

			Thuoc th = list_Thuoc.getThuocByID(maThuoc);
			if (th.getMaThuoc() != null) {
				model.setRowCount(0);

				Object[] row = { th.getMaThuoc(), th.getTenThuoc(), th.getSoLuong() + "", th.getGia() + "",
						th.getLoaiThuoc(), th.getTenNhaSanXuat().getTenNSX(), formatTime(th.getNgaySanXuat()),
						formatTime(th.getNgayHetHan()) };
				model.addRow(row);
				table.setModel(model);
				table.setRowSelectionInterval(0, 0);

			} else {
				JOptionPane.showMessageDialog(null, "Không tìm thấy mã thuốc trong hệ thống");
			}
		}
	}

	public boolean regexThuocLoc() {

		String tenNSX = getValueInComboBox(cbNSXTim);
		String loaithuoc = getValueInComboBox(cbLoaiThuocTim);
		String tenThuocc = tenThuoc.getText();
		if (tenNSX.equals("") && loaithuoc.equals("") && tenThuocc.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần lựa chọn ít nhất 1 tiêu chí để lọc thuốc");
			return false;
		}
		return true;
	}

	public void timNangCao() {

		if (regexThuocLoc()) {
			String tenNSX = getValueInComboBox(cbNSXTim);
			String loaithuoc = getValueInComboBox(cbLoaiThuocTim);
			String tenThuocc = tenThuoc.getText();
			ArrayList<Thuoc> list_ThuocTim = list_Thuoc.timThuoc(tenNSX, loaithuoc, tenThuocc);

			model.setRowCount(0);
			for (Thuoc thuoc : list_ThuocTim) {

				hienBangLocThuoc(thuoc);

			}
			table.setModel(model);

		}
	}

	public void hienBangLocThuoc(Thuoc thuoc) {
		String[] row = { thuoc.getMaThuoc(), thuoc.getTenThuoc(), thuoc.getSoLuong() + "", thuoc.getGia() + "",
				thuoc.getLoaiThuoc(), thuoc.getTenNhaSanXuat().getTenNSX(), formatTime(thuoc.getNgaySanXuat()) + "",
				formatTime(thuoc.getNgayHetHan()) + "" };

		model.addRow(row);
	}

	public class CustomTableCellRendererThuoc extends CustomTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			;

			if (isSelected) {
				component.setBackground(new Color(89, 168, 104, 150));

			}

			return component;
		}
	}

	public void xoaTrang() {
		textFind.setText("");
		tenThuoc.setText("");
		cbNSXTim.setSelectedIndex(0);
		cbLoaiThuoc.setSelectedIndex(0);
		hienBangTableThuoc();
	}

	public JButton buttonInPageSearch(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(120, 40));
		btn.addActionListener(e -> {
			if (nameButton.equals("Thoát")) {
				frame.dispose();
			} else if (nameButton.equals("Chọn")) {
				serviceGetThuoc();
			} else if (nameButton.equals("Tìm kiếm")) {
				timThuoc();
			} else if (nameButton.equals("Lọc")) {
				timNangCao();
			} else if (nameButton.equals("")) {
				xoaTrang();
			}

		});
		return btn;
	}

}
