import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class VisualizadorFoto extends JFrame {
    BufferedImage img = null;
    JLabel label = new JLabel();
    JButton aceptar = new JButton("Aceptar");
    JButton reintentar = new JButton("Retomar");
    JButton cancelar = new JButton("Cancelar");
    PanelCapturaPantalla panel;
    DeteccionRatonCapturador detector;

    public VisualizadorFoto(int contadorFoto, PanelCapturaPantalla panel, DeteccionRatonCapturador detector) {
        this.panel = panel;
        this.detector = detector;
        try {
            img = ImageIO.read(new File((contadorFoto - 1) + "captura.jpeg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageIcon icon = new ImageIcon(img);
        label.setIcon(icon);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(label, gbc);
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(aceptar, gbc);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(reintentar, gbc);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(cancelar, gbc);
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        aceptar.addActionListener((e) -> {
            try {
                realizaOcr("C:\\Users\\dabac\\Proton Drive\\Protoandrei\\My files\\SudokuSolver\\untitled\\" + (contadorFoto - 1) + "captura.jpeg");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
        });
        cancelar.addActionListener((e) -> {
            this.dispose();
        });
        reintentar.addActionListener((e) -> {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    panel.setVisible(true);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            detector.capturaTomada = false;
            this.dispose();
        });
    }

    public void realizaOcr(String pathFoto) throws IOException, AWTException {
        try {
            String pathScript = "C:\\Users\\dabac\\PycharmProjects\\pythonProject\\SudokuOCR.py";
            ProcessBuilder pb = new ProcessBuilder("python", pathScript, pathFoto);
            pb.redirectErrorStream(true);
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            in.close();
            p.waitFor();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
