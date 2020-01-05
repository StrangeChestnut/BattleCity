package graphics.draws;

import game.entities.mobiles.Bullet;
import game.level.Level;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class DrawBullet extends Bullet implements Drawable {
    private ImageView image;
    private double x, y;
    private int z;

    public DrawBullet(Bullet bull) {
        setDirection(bull.getDirection());
        setX(bull.getX());
        setY(bull.getY());
        setEnemy(bull.isEnemy());
        setSpeed(bull.getSpeed());
        setHealth(bull.getHealth());

        x = (getX());
        y = (getY());
        z = 1;

        image = new ImageView(bullet);
    }

    @Override
    public void draw(Pane pane) {
        double width = pane.getPrefWidth() / Level.SIZE;
        double height = pane.getPrefHeight() / Level.SIZE;
        image.setFitWidth(width * 2);
        image.setFitHeight(height * 2);

        if (!isDead()) {
            image.setX(x * width);
            image.setY(y * height);
            image.setViewport(getRectangle(getDirection()));
            pane.getChildren().add(image);
            //moveAnimation(image, width, height);

        } else {
            boom(pane, width, height);
        }
    }

    private void boom(Pane pane, double width, double height) {
        ImageView view = new ImageView(boom);
        view.setX(x * width);
        view.setY(y * height);
        view.setFitWidth(width * 2);
        view.setFitHeight(height * 2);

        AnimationTimer t = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (pane.getChildren().contains(view)) {
                    pane.getChildren().remove(view);
                    stop();
                } else {
                    pane.getChildren().add(view);
                }
            }
        };
        t.start();
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
