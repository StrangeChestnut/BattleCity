package game.entities.statics.blocks;

import game.entities.statics.*;
import game.entities.statics.Block;

public class Brick extends Block {
    public Brick(int x, int y) {
        super(x, y, BlockType.BRICK, true, false);
        setHealth(2);
    }
}
