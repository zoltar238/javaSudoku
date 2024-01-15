import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class VisualizadorFoto extends JFrame {
    BufferedImage img;
    JLabel label = new JLabel();
    JButton aceptar = new JButton("Aceptar");
    JButton reintentar = new JButton("Retomar");
    JButton cancelar = new JButton("Cancelar");
    PanelCapturaPantalla panel;
    DeteccionRatonCapturador detector;
    GridSudoku gridSudoku;
    String resultado = null;
    int [][] arrayResultado = new int[9][9];
    File file = new File("");
    String directoryName = file.getAbsoluteFile().toString();

    public VisualizadorFoto(String path, GridSudoku gridSudoku){
        System.out.println(directoryName);
        this.gridSudoku = gridSudoku;
        try {
            img = ImageIO.read(new File(path));
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
        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(cancelar, gbc);
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        aceptar.addActionListener((e) -> {
            try {
                realizaOcr(path);
            } catch (IOException | AWTException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(resultado);
            stringToArray(resultado);
            gridSudoku.board = arrayResultado;
            gridSudoku.gridToMatriz();
            this.dispose();
        });
        cancelar.addActionListener((e) -> this.dispose());
    }

    public VisualizadorFoto(int contadorFoto, PanelCapturaPantalla panel, DeteccionRatonCapturador detector, GridSudoku gridSudoku) {
        this.panel = panel;
        this.detector = detector;
        this.gridSudoku = gridSudoku;
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
            panel.dispose();
            try {
                realizaOcr("C:\\Users\\dabac\\Proton Drive\\Protoandrei\\My files\\SudokuSolver\\untitled\\" + (contadorFoto - 1) + "captura.jpeg");
            } catch (IOException | AWTException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(resultado);
            stringToArray(resultado);
            gridSudoku.board = arrayResultado;
            gridSudoku.gridToMatriz();
            this.dispose();
        });
        cancelar.addActionListener((e) -> {
            panel.dispose(); 
            this.dispose();});
        reintentar.addActionListener((e) -> {
            EventQueue.invokeLater(() -> panel.setVisible(true));
            this.dispose();
        });
    }


    public void realizaOcr(String pathFoto) throws IOException, AWTException {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "C:\\Users\\dabac\\Proton Drive\\Protoandrei\\My files\\SudokuSolver\\untitled\\src\\SudokuOCR.py", pathFoto);
            pb.redirectErrorStream(true);
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("[")){
                    resultado = line;
                }
            }

            if (resultado == null){
                System.out.println("Error en la captura, tablero no detectado");
            }

            in.close();
            p.waitFor();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void stringToArray(String resultado){
        int posicion = 1;
        for (int i = 0; i < arrayResultado.length; ++i){
            for (int j = 0; j < arrayResultado[i].length; ++j){
                System.out.println(resultado.charAt(posicion));
                arrayResultado[j][i] = resultado.charAt(posicion) - 48;
                posicion += 3;
            }
        }
    }
}
