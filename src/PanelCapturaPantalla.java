import javax.swing.*;
import java.awt.*;

public class PanelCapturaPantalla extends JFrame{
    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    GridSudoku gridSudoku;
    DeteccionRatonCapturador detectorRaton;
    PanelCapturaPantalla(GridSudoku gridSudoku){
        this.gridSudoku = gridSudoku;
        this.setTitle("Capturador de pantalla");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setSize(screensize.width, screensize.height);
        this.setBackground(Color.black);
        this.setOpacity(0.1f);
        this.setLayout(new GridBagLayout());
        this.setVisible(false);
        detectorRaton = new DeteccionRatonCapturador(this, gridSudoku);
        this.addMouseListener(detectorRaton);
        this.addMouseMotionListener(detectorRaton);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.setStroke(new BasicStroke(2));
        int[] x = {detectorRaton.mousex1, detectorRaton.mousex1, detectorRaton.mousex2, detectorRaton.mousex2};
        int[] y = {detectorRaton.mousey1, detectorRaton.mousey2, detectorRaton.mousey2, detectorRaton.mousey1};
        g2.drawPolygon(x,y,4);
    }
}
