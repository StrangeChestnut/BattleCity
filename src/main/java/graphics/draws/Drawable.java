package graphics.draws;

import game.entities.Direction;
import game.entities.mobiles.Tank;
import game.entities.statics.BlockType;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Comparator;

public interface Drawable {
    Image brick = new Image("sprites\\brick.png");
    Image iron = new Image("sprites\\iron.png");
    Image leaf = new Image("sprites\\leaf.png");
    Image water = new Image("sprites\\water.png");
    Image base = new Image("sprites\\base.png");
    Image ice = new Image("sprites\\ice.png");
    Image air = new Image("sprites\\air.png");

    Image player = new Image("sprites\\player.png");
    Image enemy = new Image("sprites\\enemy.png");

    Image bullet = new Image("sprites\\bullet.png");
    Image boom = new Image("sprites\\boom.png");

    Comparator<? super Drawable> COMPARATOR = Comparator.comparingInt(Drawable::getZ);

    void draw(Pane pane);
    ImageView getImage();
    int getZ();

    default Image getImage(BlockType type) {
        Image view;
        switch (type) {
            case BRICK:
                view = brick;
                break;
            case WATER:
                view = water;
                break;
            case ICE:
                view = ice;
                break;
            case IRON:
                view = iron;
                break;
            case LEAF:
                view = leaf;
                break;
            case BASE:
                view = base;
                break;
            default:
                view = air;
        }
        return view;
    }

    default Rectangle2D getRectangle(Direction direction) {
        Rectangle2D rectangle2D = null;
        switch (direction) {
            case NORTH:
                rectangle2D =  new Rectangle2D(0.0, 0.0, 160.0, 160.0);
                break;
            case SOUTH:
                rectangle2D =  new Rectangle2D(0.0, 160.0, 160.0, 160.0);
                break;
            case EAST:
                rectangle2D =  new Rectangle2D(160.0, 0.0, 160.0, 160.0);
                break;
            case WEST:
                rectangle2D =  new Rectangle2D(160.0, 160.0, 160.0, 160.0);
        }
        return rectangle2D;
    }
}
