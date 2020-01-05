package game.entities;

import com.google.gson.annotations.Expose;

public abstract class Entity {
    @Expose
    private int x;
    @Expose
    private int y;
    @Expose
    private int health;

    public Entity () {
        health = 1;
    }


    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDead() {
        return health <= 0;
    }
}
