import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Ventana extends JFrame implements Runnable, MouseListener {

    PanelLateral panelLateral = new PanelLateral();
    GridSudoku gridSudoku = new GridSudoku();
    Botonera botonera = new Botonera();
    Thread threadControl;
    int conteo = 0;
    ArrayList<PanelConSolucion> panelConSolucionList = new ArrayList<>();
    ArrayList<int[][]> registrosVacios = new ArrayList<>();
    ArrayList<int[][]> registrosSolucionado = new ArrayList<>();
    boolean muestraOriginal = true;
    SelectorImagen selectorImagen = new SelectorImagen(gridSudoku);


    Ventana() {
        this.setTitle("Resuelve sudokus");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(gridSudoku, gbc);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(botonera, gbc);
        gbc.gridheight = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(panelLateral, gbc);
        startControlThread();
        for (int i = 0; i < botonera.arrayBotones.length; ++i) {
            botonera.arrayBotones[i].addActionListener((e) -> {
                if (e.getSource() == botonera.tableroResuelve) {
                    resuelveTablero();
                }
                if (e.getSource() == botonera.tableroVacia) {
                    gridSudoku.vaciadoTablero();
                }

                if (e.getSource() == botonera.tableroOriginal) {
                    if (muestraOriginal) {
                        gridSudoku.gridToMatrizVacia();
                        muestraOriginal = false;
                    } else {
                        gridSudoku.gridToMatriz();
                        muestraOriginal = true;
                    }
                }

                if (e.getSource() == botonera.tableroImagen) {
                    EventQueue.invokeLater(() -> selectorImagen.setVisible(true));
                }
            });
        }
        this.pack();
        this.setVisible(true);
    }

    public void resuelveTablero(){
        gridSudoku.matrizToGrid();
        registrosVacios.add(deepCopy(gridSudoku.board));
        gridSudoku.boardVacia = deepCopy(gridSudoku.board);
        SudokuSolver.solveBoard(gridSudoku.board);
        registrosSolucionado.add(deepCopy(gridSudoku.board));
        gridSudoku.gridToMatriz();
        panelConSolucionList.add(new PanelConSolucion(registrosVacios.get(conteo), registrosSolucionado.get(conteo)));
        panelLateral.frameSudokuHistorico.add(panelConSolucionList.get(conteo));
        panelConSolucionList.get(conteo).addMouseListener(this);
        panelLateral.revalidate();
        panelLateral.repaint();
        ++conteo;
    }

    public void startControlThread() {
        threadControl = new Thread(this);
        threadControl.start();
    }

    @Override
    public void run() {
        while (threadControl != null) {
            gridSudoku.compruebaEstadoTexto();
            gridSudoku.repaint();
            selectorImagen.panelCapturaPantalla.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (PanelConSolucion panelConSolucion : panelConSolucionList) {
            if (e.getSource() == panelConSolucion) {
                gridSudoku.board = deepCopy(panelConSolucion.boardSolucion);
                gridSudoku.boardVacia = deepCopy(panelConSolucion.boardVacia);
            }
        }
        EventQueue.invokeLater(() -> gridSudoku.gridToMatriz());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (PanelConSolucion panelConSolucion : panelConSolucionList) {
            if (e.getSource() == panelConSolucion) {
                panelConSolucion.setBackground(Color.GRAY);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (PanelConSolucion panelConSolucion : panelConSolucionList) {
            if (e.getSource() == panelConSolucion) {
                panelConSolucion.setBackground(Color.WHITE);
            }
        }
    }

    public static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < original.length; ++i) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

}
