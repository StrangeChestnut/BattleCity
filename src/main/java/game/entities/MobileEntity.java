package game.entities;

import game.level.Location;

public abstract class MobileEntity extends Entity {
    private Direction direction;
    private int speed;
    private int moveCooldown;

    public MobileEntity(Location location, Direction direction, int speed) {
        super(location);
        this.direction = direction;
        this.speed = speed;
        moveCooldown = 0;
    }

    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    protected void newMoveCldwn() {
        moveCooldown = speed;
    }
    protected void updMoveCldwn() {
        if (moveCooldown > 0) --moveCooldown;
    }
    public int moveCooldown() {
        return moveCooldown;
    }

    public abstract void move();

}

