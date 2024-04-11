package test;


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
public class TestPieChart {

    private static JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "CƠ CẤU DÂN SỐ THEO NHÓM TUỔI NĂM 2010", dataset, true, true, true);
        return chart;
    }

    private static PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Nhóm 0 - 14", new Double(25.0));
        dataset.setValue("Nhóm 15 - 59", new Double(66.0));
        dataset.setValue("Nhóm trên 60", new Double(9.0));
        return dataset;
    }

    public static void main(String[] args) {
        JFreeChart pieChart = createChart(createDataset());
        ChartPanel chartPanel = new ChartPanel(pieChart);
        JFrame frame = new JFrame();
        frame.add(chartPanel);
        frame.setTitle("Biểu đồ JFreeChart trong Java Swing");
        JButton btn = new JButton("nhan");
        btn.addActionListener(e->{
        	
        });
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}