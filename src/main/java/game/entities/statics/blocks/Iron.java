package game.entities.statics.blocks;

import game.entities.statics.*;
import game.entities.statics.Block;

public class Iron extends Block {
    public Iron(int x, int y) {
        super(x, y, BlockType.IRON, true, false);
        setHealth(2);
    }
}