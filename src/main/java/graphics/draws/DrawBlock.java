package graphics.draws;

import game.entities.statics.BlockType;
import game.entities.statics.Block;
import game.level.Level;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class DrawBlock extends Block implements Drawable {
    private ImageView image;
    private int z;

    public DrawBlock(Block block) {
        super(block);
        z = getType() == BlockType.LEAF ? 4 : 0;
        image = new ImageView(getImage(getType()));
    }


    @Override
    public void draw(Pane pane) {
        double width = pane.getPrefWidth() / Level.SIZE;
        double height = pane.getPrefHeight() / Level.SIZE;

        image.setFitWidth(width);
        image.setFitHeight(height);
        image.setX(getX() * width);
        image.setY(getY() * width);
        image.setPreserveRatio(true);

        pane.getChildren().add(image);
    }

    public void repaint(BlockType brush, Pane pane) {
        pane.getChildren().remove(image);
        setType(brush);
        image.setImage(getImage(brush));
        draw(pane);
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
