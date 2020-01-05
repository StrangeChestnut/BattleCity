package game.entities.statics;

import com.google.gson.annotations.Expose;
import game.entities.Entity;
import game.entities.MobileEntity;
import game.entities.mobiles.Bullet;
import game.entities.mobiles.Tank;

import java.util.List;

public class Block extends Entity implements Comparable<Block> {
    @Expose
    private BlockType type;
    @Expose
    private boolean isBreakable;
    @Expose
    private boolean isMoving;
    private Bonus bonus;
    private Tank tank;

    public Block(int x, int y, BlockType type, boolean isBreakable, boolean isMoving) {
        setHealth(1);
        setX(x);
        setY(y);

        this.type = type;
        this.isBreakable = isBreakable;
        this.isMoving = isMoving;

        tank = null;
    }

    public Block (Block block) {
        type = block.getType();
        isMoving = block.isMoving;
        isBreakable = block.isBreakable;
        setHealth(block.getHealth());
        setX(block.getX());
        setY(block.getY());
    }

    public Block() {}

//    public List<Bullet> getBullets() {
//        return bullets;
//    }
//
//    private void addBullet(Bullet entity) {
//        if (bullets == null) {
//            bullets = new ArrayList<>();
//        }
//        bullets.add(entity);
//    }
//    private void removeBullet(Bullet bullet) {
//        bullets.remove(bullet);
//    }

    public BlockType getType() {
        return type;
    }
    public void setType(BlockType type) {
        this.type = type;
    }

    public boolean isBreakable() {
        return isBreakable;
    }

    public void doEvent() {}


    public Tank getTank() {
        return tank;
    }

    public void clear(MobileEntity entity) {
        if (entity instanceof Tank) {
            tank = null;
        }
    }

    public void add(MobileEntity entity) {
        if (entity instanceof Tank) {
            tank = (Tank) entity;
        }
    }

    public boolean isMoving() {
        return isMoving && tank == null;
    }

    @Override
    public int compareTo(Block o) {
        return (o.getX() < getX()) ? -1 : (o.getX() == getX()) ? (Integer.compare(getY(), o.getY())) : 1;
    }

    public void setAir() {
        type = BlockType.AIR;
        isBreakable = false;
        isMoving = true;
    }
}
