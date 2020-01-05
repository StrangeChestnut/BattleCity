package game.entities.statics.blocks;

import game.entities.statics.*;
import game.entities.statics.Block;

public class Base extends Block {
    public Base(int x, int y) {
        super(x, y, BlockType.BASE, true, false);
    }
}
