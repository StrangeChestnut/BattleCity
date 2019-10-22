package game.entities.mobiles;

import game.entities.Direction;

import game.entities.MobileEntity;
import game.level.Location;

public class Tank extends MobileEntity {
    private int shotSpeed;
    private int shotCooldown;
    private int bulletSpeed;

    public Tank() {
        this(null);
    }
    Tank(Location location) {
        super(location, Direction.EAST, 4);
        bulletSpeed = 1;
        shotSpeed = 16;
        shotCooldown = 0;
    }

    public Bullet shot() {
        shotCooldown = shotSpeed;
        return new Bullet(this);
    }

    public boolean canShot() {
        return shotCooldown == 0;
    }

    public void decShotCooldown() {
        if (shotCooldown > 0) --shotCooldown;
    }

    int bulletSpeed() {
        return bulletSpeed;
    }

    @Override
    public void move() {
        try {
            updMoveCldwn();
            if (moveCooldown() == 0) {
                Location location = location();
                Location newLocation = new Location(location, getDirection().getVector());

                if (newLocation.numberEmptyTiles() == 2) {
                    location.clear(this);
                    newLocation.set(this);
                    setLocation(newLocation);

                    newMoveCldwn();
                }
            }
        } catch (IndexOutOfBoundsException ignored) { }
    }
}
