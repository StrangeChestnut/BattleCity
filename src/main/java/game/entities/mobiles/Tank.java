package game.entities.mobiles;

import com.google.gson.annotations.Expose;
import game.entities.Direction;

import game.entities.MobileEntity;
import game.level.Location;
import game.entities.statics.Block;

import java.util.Random;

public class Tank extends MobileEntity {
    @Expose
    private Bullet bullet;
    @Expose
    private int bulletSpeed;
    @Expose
    private boolean isControl = true;

    public Tank(int speed, int health, int bulletSpeed) {
        this(0,0, true);
        setSpeed(speed);
        setHealth(health);
        setBulletSpeed(bulletSpeed);
    }

    public Tank(int x, int y, boolean isEnemy) {
        super(x, y, Direction.NORTH, TankType.SPEED, isEnemy);
        bulletSpeed = TankType.BULLET_SPEED;
        bullet = new Bullet();
    }

    public Tank (Tank tank) {
        super(tank.getX(),tank.getY(),tank.getDirection(),tank.getSpeed(), tank.isEnemy());
        setHealth(tank.getHealth());
        bulletSpeed = tank.getBulletSpeed();
        isControl = tank.isControl;
        bullet = tank.getBullet();
    }

    void shot() {
        bullet = new Bullet(this);
        bullet.launchIn(game);
    }

    boolean canShot() {
        return !bullet.isFly();
    }

    @Override
    public void ai(Block[][] map) {
        Location location = new Location(getX(), getY(), getDirection(), map);
        if (!location.isClearToMove()) {
            if (isControl) {
                setDirection(Direction.random());

                if (canShot()) {
                    shot();
                }
                return;

            } else {
                super.move(location);
            }
        } else {
            move(location);
        }

        if (canShot() && new Random().nextInt(32) == 0) {
            shot();
        }
    }

    @Override
    public void move(Location newLocation) {
        if (newLocation.numberMovingTiles() == 2) {
            Location preLocation = new Location(getX(), getY(), newLocation.getMap());
            Location.swipe(newLocation, preLocation, this);
            setX(newLocation.getX());
            setY(newLocation.getY());
        }
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }
    private void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
    public Bullet getBullet() {
        return bullet;
    }

    public void setControl(boolean control) {
        isControl = control;
    }
    boolean isControl() {
        return isControl;
    }
}
