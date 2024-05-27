package test;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class QRCodeScanner extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeScanner.class);
    private JTextArea resultArea;
    private Webcam webcam;

    public QRCodeScanner() {
        setTitle("QR Code Scanner");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        panel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setPreferredSize(WebcamResolution.VGA.getSize());
        webcamPanel.setFPSDisplayed(true);

        panel.add(webcamPanel, BorderLayout.CENTER);
        add(panel);

        new Thread(this::scanQRCode).start();
    }

    private void scanQRCode() {
        while (true) {
            BufferedImage image = null;
            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                Result result = new MultiFormatReader().decode(bitmap);
                resultArea.setText(result.getText());
                logger.info("QR Code found: {}", result.getText());
            } catch (NotFoundException e) {
                // No QR code found in image
                resultArea.setText("No QR code found");
                logger.debug("No QR code found in current frame.");
            }

            try {
                Thread.sleep(100);  // Adjust the sleep time if needed
            } catch (InterruptedException e) {
                logger.error("Error in QR code scanning thread", e);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QRCodeScanner scanner = new QRCodeScanner();
            scanner.setVisible(true);
        });
    }
}
