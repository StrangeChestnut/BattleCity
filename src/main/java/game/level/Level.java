package game.level;

import game.entities.MobileEntity;
import game.entities.mobiles.Bullet;
import game.entities.mobiles.Player;
import game.entities.mobiles.Tank;
import game.entities.statics.*;
import java.util.ArrayList;

public class Level {
    public static final int size = 26;
    private Tile[][] map;
    private Block[][] base;
    private int[] spawn;

    private ArrayList<Bullet> bullets;
    private ArrayList<Tank> spawnedTanks;
    private ArrayList<Tank> waitingTanks;

    private Level() {
        map = new Tile[size][size];
        spawn = new int[]{24, 0};
        base = new Block[2][2];

        bullets = new ArrayList<>();
        spawnedTanks = new ArrayList<>();
        waitingTanks = new ArrayList<>();

        initMap();
        initTanks();
    }

    public Level(String filePath) {
        this();
    }

    private void initMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile tile = new Tile(i, j);
                map[i][j] = tile;
                tile.setBlock(new Block(tile, BlockType.AIR));
            }
        }
    }

    private void initTanks() {
        for (int i = 0; i < 8; i++) {
            waitingTanks.add(new Tank());
        }
    }

    public void spawnTank() {
        Location location = Location.random(map);
        Tank tank = waitingTanks.remove(0);

        location.set(tank);
        tank.setLocation(location);

        spawnedTanks.add(tank);
    }

    public boolean enemiesIsDead() {
        return spawnedTanks.isEmpty() && waitingTanks.isEmpty();
    }

    public boolean baseIsBreak() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (base[i][j].isDead()) return true;
            }
        }
        return false;
    }
    public boolean hasEnemyTank(Tank tank) {
        return spawnedTanks.contains(tank);
    }

    public void addPlayer(Player player) {
        Location location = new Location(map, spawn[0], spawn[1]);
        player.setLocation(location);
        location.set(player);
        player.setLevel(this);
    }

    public void deleteEntity(MobileEntity entity) {
        entity.location().clear(entity);
        bullets.remove(entity);
        spawnedTanks.remove(entity);
    }



    public Tile[][] map() {
        return map;
    }
    public ArrayList<Tank> spawnedTanks() {
        return spawnedTanks;
    }
    public ArrayList<Tank> waitingTanks() {
        return waitingTanks;
    }
    public ArrayList<Bullet> bullets() {
        return bullets;
    }
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public ArrayList<MobileEntity> entities() {
        ArrayList<MobileEntity> entities = new ArrayList<>();
        entities.addAll(bullets);
        entities.addAll(spawnedTanks);
        return entities;
    }
}
