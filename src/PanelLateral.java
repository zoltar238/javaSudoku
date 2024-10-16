import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static javax.swing.ScrollPaneConstants.*;

public class PanelLateral extends JPanel{

    JPanel frameSudokuHistorico = new JPanel();
    JScrollPane scrollPane = new JScrollPane(frameSudokuHistorico);

    PanelLateral() {
        scrollPane.setPreferredSize(new Dimension(350, 500));
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        frameSudokuHistorico.setLayout(new BoxLayout(frameSudokuHistorico, BoxLayout.Y_AXIS));
        this.add(scrollPane);
    }
}
