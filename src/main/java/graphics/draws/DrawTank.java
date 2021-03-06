package graphics.draws;

import game.entities.mobiles.Tank;
import game.level.Level;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class DrawTank extends Tank implements Drawable {
    private ImageView image;
    private int z;

    public DrawTank(Tank tank) {
        super(tank);
        z = 2;
        image = new ImageView(enemy);
    }



    @Override
    public void draw(Pane pane) {
        double width = pane.getPrefWidth() / Level.SIZE;
        double height = pane.getPrefHeight() / Level.SIZE;

        image.setViewport(getRectangle(getDirection()));
        image.setFitWidth(width * 2);
        image.setFitHeight(height * 2);

        image.setX(getX() * width);
        image.setY(getY() * height);
        pane.getChildren().add(image);
    }

    @Override
    public ImageView getImage() {
        return image;
    }

    @Override
    public int getZ() {
        return z;
    }
}
