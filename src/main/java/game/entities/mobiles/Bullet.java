package game.entities.mobiles;

import game.entities.MobileEntity;
import game.level.Location;

public class Bullet extends MobileEntity {
    private Tank tank;

    Bullet(Tank tank) {
        super(tank.location(), tank.getDirection(), tank.bulletSpeed());
        this.tank = tank;
    }

    public Tank tank() {
        return tank;
    }

    public boolean flyTo(Bullet bullet) {
        int number = location().contains(bullet);
        return number == 2 ? (getDirection().isOpposite(bullet.getDirection())) : number == 4;
    }

    @Override
    public void move() {
        try {
            updMoveCldwn();
            if (moveCooldown() == 0) {
                Location location = location();
                Location newLocation = new Location(location, getDirection().getVector());

                location.clear(this);
                newLocation.set(this);
                setLocation(newLocation);

                newMoveCldwn();
            }
        } catch (IndexOutOfBoundsException e) {
            setHealth(0);
        }
    }
}
