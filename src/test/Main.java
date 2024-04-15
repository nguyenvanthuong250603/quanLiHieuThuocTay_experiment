package test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author TVD
 */


	public class Main {
	    public static void main(String[] args) {
	        String dateString = "05/01/2025"; // Chuỗi đại diện cho ngày tháng
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Định dạng của chuỗi
	        LocalDate date = LocalDate.parse(dateString, formatter); // Chuyển đổi chuỗi thành LocalDate
	        Long diffInYears = ChronoUnit.DAYS.between(LocalDate.now(), date);	
	        System.out.println(diffInYears);
	    }
	


}