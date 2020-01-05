package game.entities.statics.blocks;

import game.entities.statics.*;
import game.entities.statics.Block;

public class Water extends Block {
    public Water(int x, int y) {
        super(x, y, BlockType.WATER, false, false);
    }
}
