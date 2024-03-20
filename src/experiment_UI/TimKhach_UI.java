package experiment_UI;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class TimKhach_UI {
	private JFrame frame;
	private JTextField Jtext_maKH,Jtext_tenKH,jText_sdtKH;
	private JComboBox cbTuoiKH ,cbGioiTinhKH;
	public void getTimKhach(JTextField maKH,JTextField tenKH, JComboBox tuoiKH,JComboBox gioiTinhKH,JTextField sdtKH) {
		frame = new JFrame();
		frame.setTitle("Tìm kiếm thuốc");
		
		frame.setSize(1100, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
//		this.Jtext_maKH = maKH;
//		this.Jtext_tenKH = tenKH;
//		this.cbTuoiKH = tuoiKH;
//		this.cbGioiTinhKH = gioiTinhKH;
//		this.jText_sdtKH = sdtKH;
	
	}
}
