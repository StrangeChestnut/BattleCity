package frame.panels;

import javax.swing.*;
import java.awt.*;

public class PicturePanel extends JPanel {
    private Image image = null;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            int x = (getWidth() - image.getWidth(null))/2;
            int y = (getHeight() - image.getHeight(null))/2;

            g.drawImage(image,x,y,null);
        }
    }
}
