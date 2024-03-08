package experiment_UI;



import javax.swing.JFrame;
import static connectDataBase.connectionData.*;
public class commadRun extends JFrame{
	public static void main(String[] args) {
		if (accessDataBase()!=null) {
			JFrame frame  = new JFrame();
			default_UI_2 run = new default_UI_2(frame);
		}
		
	}
}
