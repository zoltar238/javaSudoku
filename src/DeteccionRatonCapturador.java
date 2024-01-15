import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DeteccionRatonCapturador  implements MouseListener, MouseMotionListener{

    int mousex1,mousey1,mousex2,mousey2,contadorFoto = 0;
    PanelCapturaPantalla panelCaptura;
    GridSudoku gridSudoku;

    DeteccionRatonCapturador(PanelCapturaPantalla panelCaptura, GridSudoku gridSudoku){
        this.panelCaptura = panelCaptura;
        this.gridSudoku = gridSudoku;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousex1 = (int) MouseInfo.getPointerInfo().getLocation().getX();
        mousey1 = (int) MouseInfo.getPointerInfo().getLocation().getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (Math.abs(mousex2- mousex1) > 20 && Math.abs(mousey2 - mousey1) > 20){
            try {
                capturaPantalla();
            } catch (AWTException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousex2 = (int) MouseInfo.getPointerInfo().getLocation().getX();
        mousey2 = (int) MouseInfo.getPointerInfo().getLocation().getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    static boolean isImage(String image_path){
        try{
            File f = new File(image_path);
            return  f.exists();

        }catch(Exception ex){
            return  false;
        }
    }

    public void capturaPantalla() throws AWTException, IOException {
        Robot robot = new Robot();
        int x = mousex1;
        int y = mousey1;
        int width = Math.abs(mousex2 - mousex1);
        int height = Math.abs(mousey2 - mousey1);
        if (mousex1 > mousex2){
            x = mousex2;
        }
        if (mousey1 > mousey2){
            y = mousey2;
        }
        BufferedImage image= robot.createScreenCapture(new Rectangle(x,y, width, height));
        while (true){
            if(isImage(contadorFoto + "captura.jpeg")){
                ++contadorFoto;
            }
            else break;
        }
        ImageIO.write(image, "jpeg", new File(contadorFoto + "captura.jpeg"));
        ++contadorFoto;
        mousey2 = mousey1 = mousex1 = mousex2 = 0;
        new VisualizadorFoto(contadorFoto, panelCaptura, this, gridSudoku);
    }
}
