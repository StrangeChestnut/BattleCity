package game.entities.mobiles;

import game.entities.Direction;
import game.level.*;

public class Player extends Tank {
    private Level level;
    private boolean[] events;

    public Player() {
        super(new Location(new Tile[2][2]));
        events = new boolean[5];
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void update() {
        if (events[0]) {
            setDirection(Direction.NORTH);
            move();
        } else if (events[1]) {
            setDirection(Direction.SOUTH);
            move();
        } else if (events[2]) {
            setDirection(Direction.WEST);
            move();
        } else if (events[3]) {
            setDirection(Direction.EAST);
            move() ;
        } else {
           updMoveCldwn();
        }
        if (events[4]) {
            if (canShot()) {
                level.addBullet(shot());
            }
        }
        decShotCooldown();
    }

    public void updateEvent(int event, boolean bool) {
        this.events[event] = bool;
    }
}
