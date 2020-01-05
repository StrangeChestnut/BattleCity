package game.entities.mobiles;

import com.google.gson.annotations.Expose;
import game.entities.MobileEntity;
import game.level.Location;
import game.entities.statics.Block;

public class Bullet extends MobileEntity {
    @Expose
    private boolean isFly;

    Bullet(Tank tank) {
        super(tank.getX(), tank.getY(), tank.getDirection(), tank.getBulletSpeed(), tank.isEnemy());
        isFly = true;
    }

    protected Bullet() {
        super(0, 0, null, 1, true);
    }

    @Override
    public synchronized void ai(Block[][] map) {
        move(new Location(getX(), getY(), getDirection(), map));
    }

    public boolean isFly() {
        return isFly;
    }

    public void setFly(boolean isFly) {
        this.isFly = isFly;
    }
}
