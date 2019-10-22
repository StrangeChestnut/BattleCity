package game.level;

import game.entities.MobileEntity;
import game.entities.mobiles.Bullet;
import game.entities.mobiles.Tank;
import game.entities.statics.Block;

import java.util.ArrayList;
import java.util.Random;

public class Location {
    private Tile[][] map;
    private Tile[][] tiles;

    public Tile[][] tiles() {
        return tiles;
    }

    public Location(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Location(Tile[][] map, int x, int y) {
        Tile[][] tiles = new Tile[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                tiles[i][j] = map[x + i][y + j];
            }
        }
        this.tiles = tiles;
        this.map = map;
    }

    public Location(Location location, int[] v) {
        this.map = location.map;
        this.tiles = new Tile[2][2];

        int x, y;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                x = location.tiles[i][j].getX() + v[0];
                y = location.tiles[i][j].getY() + v[1];
                this.tiles[i][j] = location.map[x][y];
            }
        }
    }

    public void set(MobileEntity entity) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                tiles[i][j].set(entity);
            }
        }
    }
    public void clear(MobileEntity entity) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                tiles[i][j].clear(entity);
            }
        }
    }

    private boolean isEmpty() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (!tiles[i][j].isMoving()) return false;
            }
        }
        return true;
    }

    public int numberEmptyTiles() {
        int n = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (tiles[i][j].isMoving()) n++;
            }
        }
        return n;
    }

    public Tank firstTank() {
        Tank tank;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                tank = tiles[i][j].tank();
                if (tank != null) return tank;
            }
        }
        return null;
    }

    public Block block(int i, int j) {
        return tiles[i][j].block();
    }

    public ArrayList<Bullet> bullets() {
        ArrayList<Bullet> bullets = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                bullets.addAll(tiles[i][j].bullets());
            }
        }
        return bullets;
    }

    public int contains(Bullet bullet) {
        int result = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (tiles[i][j].bullets().contains(bullet)) {
                    result++;
                }
            }
        }
        return result;
    }

    static Location random(Tile[][] map) {
        Random random = new Random();
        Location location;
        do {
            location = new Location(map, random.nextInt(5), random.nextInt(25));
        } while (location.numberEmptyTiles() != 4);
        return location;
    }

    public boolean equals(Location location) {
        Tile[][] tiles = location.tiles;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (tiles[i][j].compareTo(this.tiles[i][j]) != 0) return false;
            }
        }
        return true;
    }
}
