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
import java.util.ArrayList;

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

import dao.Thuoc_DAO;
import entity.NhaSanXuat;
import entity.Thuoc;

import static experiment_UI.Generate_All.*;

public class TimThuoc_UI {
	private JComboBox cbLoaiThuoc;
	private JComboBox cbNSX;
	private JFrame frame;
	private JCheckBox cb;
	private DefaultTableModel model,modelGetThuoc;
	private JTable table,tableGetThuoc;
	private Thuoc_DAO list_Thuoc= new Thuoc_DAO();
	private JComboBox cbNSXTim;
	private JComboBox cbLoaiThuocTim;
	private JTextField textFind;
	
	public void getTimThuoc(DefaultTableModel modell,JTable tablee) {
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
		box1.setLayout(new GridLayout(2, 2, 10, 10));

		JPanel t = new JPanel();
		t.setLayout(new BoxLayout(t, BoxLayout.X_AXIS));
		t.add(sampleModel("Tên thuốc"));
		JTextField tenThuoc = new JTextField();
		t.add(tenThuoc);

		JPanel t2 = new JPanel();
		t2.setLayout(new BoxLayout(t2, BoxLayout.X_AXIS));
		String[] optionTinhTrang = { "", "sản phẩm hết hạn", "sản phẩm sắp hết hạn", "sản phẩm hết hàng",
				"sản phẩm sắp hết", "sản phẩm còn hạn" };

		t2.add(createJcombobox("Tình trạng", new JComboBox(optionTinhTrang)));

		box1.add(t);
		box1.add(t2);

		String[] optionNsx = { "", "CTY SX1", "CTY SX2" };
		String[] optionLoaiThuoc = { "", "Thuốc giảm đau, hạ sốt ", "Thuốc đặc trị", "Kháng sinh", "Thuốc tiêu hóa",
				"Thuốc an thần", "Vitamin", "Thuốc sát khuẩn , khử trùng", "Thuốc chống dị ứng", "Thuốc chống viêm",
				"Thuốc tim mạch", "Dịch truyền", "Thực phẩm chức năng" };

		cbNSXTim = new JComboBox(optionNsx);
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
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
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

		for (Thuoc thuoc : list_thuoc) {
			NhaSanXuat nsx = thuoc.getTenNhaSanXuat();
			String[] row = { thuoc.getMaThuoc(), thuoc.getTenThuoc(), thuoc.getSoLuong() + "", thuoc.getGia() + "",
					thuoc.getLoaiThuoc(), nsx.getTenNSX(), thuoc.getNgaySanXuat() + "", "" + thuoc.getNgaySanXuat() };
			model.addRow(row);
		}
		table.setModel(model);
		}
	public void serviceGetThuoc() {
		String maThuoc = table.getValueAt(table.getSelectedRow(), 0).toString();

		Thuoc th = list_Thuoc.getThuocByID(maThuoc);
	
			

			Object[] row = { th.getMaThuoc(), th.getTenThuoc(),th.getDonVi(),0,th.getGia(),""};
			modelGetThuoc.addRow(row);
			tableGetThuoc.setModel(modelGetThuoc);
			
			int soLuongColumnIndex = 3;

			int lastRowIndex = modelGetThuoc.getRowCount() - 1;
			tableGetThuoc.requestFocus();
			tableGetThuoc.changeSelection(lastRowIndex, soLuongColumnIndex, false, false);
			tableGetThuoc.editCellAt(lastRowIndex, soLuongColumnIndex);
			Component editor =tableGetThuoc.getEditorComponent();
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
	public JButton buttonInPageSearch(String nameButton, String pathIcon) {
		JButton btn = createJbutton(nameButton, pathIcon);
		btn.setPreferredSize(new Dimension(120, 40));
		btn.addActionListener(e -> {
			if (nameButton.equals("Thoát")) {
				frame.dispose();
			} else if (nameButton.equals("Chọn")) {
				serviceGetThuoc();
			}
			else if(nameButton.equals("Tìm kiếm" )){
				timThuoc();
			}

		});
		return btn;
	}

}
