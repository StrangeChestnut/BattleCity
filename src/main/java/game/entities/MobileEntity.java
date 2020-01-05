package game.entities;

import com.google.gson.annotations.Expose;
import game.Game;
import game.Status;
import game.level.Location;
import game.entities.statics.Block;

public abstract class MobileEntity extends Entity implements Runnable {
    private static final int size = 2;

    @Expose
    private Direction direction;
    @Expose
    private int speed;
    @Expose
    private boolean isEnemy;

    protected Game game;

    public MobileEntity(int x, int y, Direction direction, int speed, boolean isEnemy) {
        super();
        setX(x);
        setY(y);
        this.direction = direction;
        this.speed = speed;
        this.isEnemy = isEnemy;
    }

    public boolean isDifferent(MobileEntity two) {
        return this.isEnemy != two.isEnemy;
    }

    public boolean isEnemy() {
        return isEnemy;
    }
    protected void setEnemy(boolean isEnemy) {
        this.isEnemy = isEnemy;
    }

    public Direction getDirection() {
        return direction;
    }
    protected void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }
    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    public void move(Location newLocation) {
        if (newLocation.getBox() != null) {
            Location preLocation = new Location(getX(), getY(), newLocation.getMap());
            Location.swipe(newLocation, preLocation, this);
        } else {
            setHealth(0);
        }
    }

    public abstract void ai(Block[][] map);

    @Override
    public void run() {
        while (game.getStatus() == Status.RUN && !isDead()) {
            ai(game.getMap());
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ignored) {}
        }
    }

    public void launchIn(Game game) {
        this.game = game;
        if (!isDead()) {
            Thread thread = new Thread(this);
            thread.start();
        }
    }
}

