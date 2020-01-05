package game.entities.statics;

import game.entities.Entity;
import game.entities.statics.blocks.*;

public enum BlockType {
    BASE() {
        public Block createBlock(int x, int y) {
            return new Base(x, y);
        }
    },
    BRICK() {
        public Block createBlock(int x, int y) {
            return new Brick(x, y);
        }
    },
    IRON() {
        public Block createBlock(int x, int y) {
            return new Iron(x, y);
        }
    },
    LEAF() {
        public Block createBlock(int x, int y) {
            return new Leaf(x, y);
        }
    },
    WATER() {
        public Block createBlock(int x, int y) {
            return new Water(x, y);
        }
    },
    ICE() {
        public Block createBlock(int x, int y) {
            return new Ice(x, y);
        }
    },
    AIR() {
        public Block createBlock(int x, int y) {
            return new Air(x, y);
        }
    };

    public abstract Block createBlock(int x, int y);
}
