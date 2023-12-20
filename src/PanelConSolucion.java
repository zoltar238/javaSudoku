import javax.swing.*;
import java.awt.*;

public class PanelConSolucion extends JPanel {

    int[][] boardVacia;
    int[][] boardSolucion;

    PanelConSolucion(int[][] boardVacia, int[][] boardSolucion) {
        this.boardVacia = boardVacia;
        this.boardSolucion = boardSolucion;
        this.setMaximumSize(new Dimension(300, 300));
        this.setPreferredSize(new Dimension(300, 300));
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        int distanciaX = this.getWidth()/18;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        for (int i = 0; i <= 3; ++i){
            g2.drawLine(distanciaX*(i*3), 0, distanciaX*(i*3), distanciaX*9);
        }
        for (int i = 0; i <= 3; ++i){
            g2.drawLine(0, distanciaX*(i*3), distanciaX*9, distanciaX*(i*3));
        }
        g2.setStroke(new BasicStroke(1));
        for (int i = 0; i <= 9; ++i){
            g2.drawLine(distanciaX*i, 0, distanciaX*i, distanciaX*9);
        }
        for (int i = 0; i <= 9; ++i){
            g2.drawLine(0, distanciaX*i, distanciaX*9, distanciaX*i);
        }

        g2.setStroke(new BasicStroke(2));
        for (int i = 0; i <= 3; ++i){
            g2.drawLine(distanciaX*(i*3)+distanciaX*9, distanciaX * 9, distanciaX*(i*3)+distanciaX*9, distanciaX*9+distanciaX*9);
        }
        for (int i = 0; i <= 3; ++i){
            g2.drawLine(distanciaX * 9, distanciaX*(i*3)+distanciaX*9, distanciaX*9+distanciaX*9, distanciaX*(i*3)+distanciaX*9);
        }
        g2.setStroke(new BasicStroke(1));
        for (int i = 0; i <= 9; ++i){
            g2.drawLine(distanciaX*i+distanciaX*9, distanciaX * 9, distanciaX*i+distanciaX*9, distanciaX*9+distanciaX*9);
        }
        for (int i = 0; i <= 9; ++i){
            g2.drawLine(distanciaX * 9, distanciaX*i+distanciaX*9, distanciaX*9+distanciaX*9, distanciaX*i+distanciaX*9);
        }

        //dibuja los numeros en el tablero de arriba
        g2.setStroke(new BasicStroke(1));
        for (int i = 0; i < boardVacia.length; ++i) {
            for (int j = 0; j < boardVacia[i].length; ++j) {
                g2.drawString(String.valueOf(boardVacia[j][i]), distanciaX*j + 2, distanciaX*i + distanciaX - 2);
            }
        }
        //dibuja los numeros en el tablero de abajo
        for (int i = 0; i < boardSolucion.length; ++i) {
            for (int j = 0; j < boardSolucion[i].length; ++j) {
                g2.drawString(String.valueOf(boardSolucion[j][i]), distanciaX*j + 2+distanciaX*9, distanciaX*i + distanciaX - 2+distanciaX*9);
            }
        }
    }
}
