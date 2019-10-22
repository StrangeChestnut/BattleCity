package game.level;

import game.entities.MobileEntity;
import game.entities.mobiles.Bullet;
import game.entities.mobiles.Tank;
import game.entities.statics.Block;
import game.entities.statics.BlockType;
import game.entities.statics.Bonus;

import java.util.ArrayList;

public class Tile implements Comparable<Tile> {
    private int x, y;
    private ArrayList<Bullet> bullets;
    private Block block;
    private Bonus bonus;
    private Tank tank;

    Tile(int x, int y) {
        this.x = x;
        this.y = y;
        bullets = new ArrayList<>();
        block = new Block(this, BlockType.AIR);
        tank = null;
    }

    ArrayList<Bullet> bullets() {
        return bullets;
    }

    private void addBullet(Bullet entity) {
        bullets.add(entity);
    }
    private void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Block block() {
        return block;
    }
    void setBlock(Block block) {
        this.block = block;
    }

    Tank tank() {
        return tank;
    }

    void clear(MobileEntity entity) {
        if (entity instanceof Tank) {
            tank = null;
        } else {
            removeBullet((Bullet)entity);
        }
    }

    void set(MobileEntity entity) {
        if (entity instanceof Tank) {
            tank = (Tank) entity;
        } else {
            addBullet((Bullet) entity);
        }
    }

    boolean isMoving() {
        return block.getType() != BlockType.WATER && isEmpty();
    }

    private boolean isEmpty() {
        return (!block.isBreakable()) && tank == null;
    }

    @Override
    public int compareTo(Tile o) {
        return (o.x < x) ? -1 : (o.x == x) ? (Integer.compare(y, o.y)) : 1;
    }
}
