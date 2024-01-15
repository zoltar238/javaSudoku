import javax.swing.*;
import java.io.File;

public class SelectorImagen extends JFrame {

    JButton botonDesdeArchivo = new JButton("Abrir foto");
    JButton botonCaptura = new JButton("Tomar captura");
    String selectedFilePath;
    PanelCapturaPantalla panelCapturaPantalla;
    GridSudoku gridSudoku;

    public SelectorImagen(GridSudoku gridSudoku) {
        this.gridSudoku = gridSudoku;
        panelCapturaPantalla = new PanelCapturaPantalla(gridSudoku);
        JPanel panel = new JPanel();
        panel.add(botonCaptura);
        panel.add(botonDesdeArchivo);
        this.add(panel);
        setTitle("Selección archivo");
        setSize(400, 150);
        setLocationRelativeTo(null);

        botonCaptura.addActionListener(e -> {
            panelCapturaPantalla.setVisible(true);
            this.setVisible(false);
        });
        botonDesdeArchivo.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(SelectorImagen.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFilePath = selectedFile.getAbsolutePath();
            }
            VisualizadorFoto visualizadorFoto = new VisualizadorFoto(selectedFilePath, gridSudoku);
            this.dispose();
        });
        this.setVisible(false);
    }
}

