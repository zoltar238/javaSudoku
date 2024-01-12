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
    PanelCapturaPantalla panelCapturaPantalla = new PanelCapturaPantalla();

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
                if (e.getSource() == botonera.tableroVacia) {
                    gridSudoku.vaciadoTablero();
                }

                if (e.getSource() == botonera.tableroOriginal) {
                    if (muestraOriginal) {
                        gridSudoku.gridToMatrizVacia();
                        muestraOriginal = false;
                    } else if (!muestraOriginal) {
                        gridSudoku.gridToMatriz();
                        muestraOriginal = true;
                    }
                }

                if (e.getSource() == botonera.tableroImagen) {
                    EventQueue.invokeLater(() -> panelCapturaPantalla.setVisible(true));
                    panelCapturaPantalla.detectorRaton.capturaTomada = false;
                }
            });
        }
        this.pack();
        this.setVisible(true);
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
            panelCapturaPantalla.repaint();
            if (panelCapturaPantalla.detectorRaton.capturaTomada) {
                panelCapturaPantalla.dispose();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < panelConSolucionList.size(); ++i) {
            if (e.getSource() == panelConSolucionList.get(i)) {
                gridSudoku.board = deepCopy(panelConSolucionList.get(i).boardSolucion);
                gridSudoku.boardVacia = deepCopy(panelConSolucionList.get(i).boardVacia);
            }
        }
        gridSudoku.gridToMatriz();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < panelConSolucionList.size(); ++i) {
            if (e.getSource() == panelConSolucionList.get(i)) {
                panelConSolucionList.get(i).setBackground(Color.GRAY);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (int i = 0; i < panelConSolucionList.size(); ++i) {
            if (e.getSource() == panelConSolucionList.get(i)) {
                panelConSolucionList.get(i).setBackground(Color.WHITE);
            }
        }
    }

    public static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < original.length; ++i) {
            for (int j = 0; j < original[i].length; ++j) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

}
