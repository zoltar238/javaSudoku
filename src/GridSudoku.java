import javax.swing.*;
import java.awt.*;

public class GridSudoku extends JPanel{
    JTextField[][] casillasSudoku = new JTextField[9][9];
    int[][] board = new int[9][9];
    int[][] boardVacia = new int[9][9];

    GridSudoku() {
        this.setPreferredSize(new Dimension(400, 400));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < casillasSudoku.length; ++i) {
            for (int z = 0; z < casillasSudoku[i].length; ++z) {
                casillasSudoku[i][z] = new JTextField();
                casillasSudoku[i][z].setFocusable(true);
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.5;
                gbc.weighty = 0.5;
                gbc.gridx = i;
                gbc.gridy = z + 1;
                this.add(casillasSudoku[i][z], gbc);
            }
        }
    }

    public void compruebaEstadoTexto() {
        String valoresValidos = "1234567890";
        for (int i = 0; i < casillasSudoku.length; ++i) {
            for (int z = 0; z < casillasSudoku[i].length; ++z) {
                if (casillasSudoku[i][z].getText().length() > 1) {
                    casillasSudoku[i][z].setText(casillasSudoku[i][z].getText().substring(0, casillasSudoku[i][z].getText().length() - 1));
                }
                if (!valoresValidos.contains(casillasSudoku[i][z].getText())) {
                    casillasSudoku[i][z].setText("0");
                }
            }
        }
    }

    public void matrizToGrid(){
        for (int i = 0; i < casillasSudoku.length; ++i) {
            for (int z = 0; z < casillasSudoku[i].length; ++z) {
                if (casillasSudoku[i][z].getText().isEmpty()){
                    board[i][z] = 0;
                }
                else{
                    board[i][z] = Integer.parseInt(casillasSudoku[i][z].getText());
                }
            }
        }
    }

    public void gridToMatriz(){
        for (int i = 0; i < board.length; ++i) {
            for (int z = 0; z < board[i].length; ++z) {
                casillasSudoku[i][z].setText(String.valueOf(board[i][z]));
            }
        }
    }

    public void gridToMatrizVacia(){
        for (int i = 0; i < board.length; ++i) {
            for (int z = 0; z < board[i].length; ++z) {
                if (boardVacia[i][z] == 0) {
                    casillasSudoku[i][z].setText(null);
                }
                else {
                    casillasSudoku[i][z].setText(String.valueOf(boardVacia[i][z]));
                }
            }
        }
    }

    public void vaciadoTablero(){
        for (int i = 0; i < casillasSudoku.length; ++i) {
            for (int z = 0; z < casillasSudoku[i].length; ++z) {
                casillasSudoku[i][z].setText(null);
            }
        }
        for (int i = 0; i < board.length;++i){
            for (int z = 0; z < board[i].length;++z){
                board[i][z] = 0;
            }
        }
    }
}