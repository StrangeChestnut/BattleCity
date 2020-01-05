package game.entities.mobiles;

import game.entities.Direction;
import game.entities.statics.Block;
import game.level.*;

public class Player extends Tank {
    private boolean[] events;

    public Player(int x, int y) {
        super(x, y,false);
        events = new boolean[5];
        setSpeed(TankType.SPEED);
    }

    public Player(Tank tank) {
        super(tank);
    }

    public void updateEvent(int event, boolean bool) {
        this.events[event] = bool;
    }

    @Override
    public void ai(Block[][] map) {
        if (isControl()) {
            if (events[0]) {
                setDirection(Direction.WEST);
                move(new Location(getX(), getY(), getDirection(), map));
            } else if (events[1]) {
                setDirection(Direction.EAST);
                move(new Location(getX(), getY(), getDirection(), map));
            } else if (events[2]) {
                setDirection(Direction.NORTH);
                move(new Location(getX(), getY(), getDirection(), map));
            } else if (events[3]) {
                setDirection(Direction.SOUTH);
                move(new Location(getX(), getY(), getDirection(), map));
            }
        } else {
            move(new Location(getX(), getY(), getDirection(), map));
        }


        if (events[4]) {
            if (canShot()) {
                shot();
            }
        }
    }

    @Override
    public void move(Location location) {
        if (location.getBox() != null) {
            super.move(location);
        }
    }

    public void newEvents() {
        events = new boolean[5];
    }
}
