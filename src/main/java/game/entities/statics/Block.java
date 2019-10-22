package game.entities.statics;

import game.entities.Entity;
import game.level.Location;
import game.level.Tile;

public class Block extends Entity {
    private BlockType type;

    public Block(Tile tile, BlockType type) {
        super(new Location(new Tile[][]{{tile}}));
        this.type = type;
        if (type == BlockType.BRICK) setHealth(2);
        if (type == BlockType.IRON) setHealth(2);
    }

    public boolean isBreakable() {
        switch (type) {
            case AIR:
            case ICE:
            case LEAF:
            case WATER:
                return false;
        }
        return true;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }
}
