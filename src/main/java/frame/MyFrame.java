package frame;

import frame.drawEntities.IDraw;
import frame.panels.*;
import game.level.Level;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    public MyFrame() {
        super("Art");
        frameInit();

        int size = IDraw.TILE_SIZE * Level.size;
        setSize(size + 14, size + 36);

        setBackground(Color.BLACK);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MenuPanel());
        setVisible(true);
    }

    public static void switchPane(Container parent, JPanel oldP, JPanel newP) {
        parent.remove(oldP);
        parent.add(newP);
        newP.revalidate();
        newP.requestFocus();
    }
}
