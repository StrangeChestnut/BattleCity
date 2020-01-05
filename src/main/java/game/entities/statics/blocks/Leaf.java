package game.entities.statics.blocks;

import game.entities.statics.*;
import game.entities.statics.Block;

public class Leaf extends Block {
    public Leaf(int x, int y) {
        super(x, y, BlockType.LEAF, false, true);
    }
}
