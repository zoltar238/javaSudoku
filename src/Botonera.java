import javax.swing.*;
import java.awt.*;

public class Botonera extends JPanel {

    JButton tableroVacia = new JButton("VACIA TABLERO");
    JButton tableroResuelve = new JButton("RESUELVE TABLERO");
    JButton tableroOriginal = new JButton("ORIGINAL/RESUELTO");
    JButton tableroImagen = new JButton("INTRUDUCIR FOTO");
    JButton[] arrayBotones = {tableroVacia,tableroResuelve,tableroOriginal, tableroImagen};

    Botonera(){
        this.setPreferredSize(new Dimension(400, 100));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(tableroResuelve,gbc);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(tableroImagen,gbc);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(tableroVacia,gbc);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(tableroOriginal,gbc);
    }
}
