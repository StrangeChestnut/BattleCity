package game.entities.statics.blocks;

import game.entities.statics.*;
import game.entities.statics.Block;

public class Ice extends Block {
    public Ice(int x, int y) {
        super(x, y, BlockType.ICE, false, true);
    }

    @Override
    public void doEvent() {

    }
}
