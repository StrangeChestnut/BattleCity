package game.entities.statics.blocks;

import game.entities.statics.*;
import game.entities.statics.Block;

public class Air extends Block {
    public Air(int x, int y) {
        super(x, y, BlockType.AIR, false, true);
    }
}
