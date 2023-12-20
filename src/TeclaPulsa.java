import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TeclaPulsa implements KeyListener {
    boolean num_0 = false;
    boolean num_1 = false;
    boolean num_2 = false;
    boolean num_3 = false;
    boolean num_4 = false;
    boolean num_5 = false;
    boolean num_6 = false;
    boolean num_7 = false;
    boolean num_8 = false;
    boolean num_9 = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_0) {
            num_0 = true;
        }
        if (code == KeyEvent.VK_1) {
            num_1 = true;
        }
        if (code == KeyEvent.VK_2) {
            num_2 = true;
        }
        if (code == KeyEvent.VK_3) {
            num_3 = true;
        }
        if (code == KeyEvent.VK_4) {
            num_4 = true;
        }
        if (code == KeyEvent.VK_5) {
            num_5 = true;
        }
        if (code == KeyEvent.VK_6) {
            num_6 = true;
        }
        if (code == KeyEvent.VK_7) {
            num_7 = true;
        }
        if (code == KeyEvent.VK_8) {
            num_8 = true;
        }
        if (code == KeyEvent.VK_9) {
            num_9 = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_0) {
            num_0 = false;
        }
        if (code == KeyEvent.VK_1) {
            num_1 = false;
        }
        if (code == KeyEvent.VK_2) {
            num_2 = false;
        }
        if (code == KeyEvent.VK_3) {
            num_3 = false;
        }
        if (code == KeyEvent.VK_4) {
            num_4 = false;
        }
        if (code == KeyEvent.VK_5) {
            num_5 = false;
        }
        if (code == KeyEvent.VK_6) {
            num_6 = false;
        }
        if (code == KeyEvent.VK_7) {
            num_7 = false;
        }
        if (code == KeyEvent.VK_8) {
            num_8 = false;
        }
        if (code == KeyEvent.VK_9) {
            num_9 = false;
        }
    }
}
