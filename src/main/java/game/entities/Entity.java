package game.entities;

import game.level.Location;

public abstract class Entity {
    private Location location;
    private int health;

    public Entity (Location location) {
        this.location = location;
        health = 1;
    }

    public Location location() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
