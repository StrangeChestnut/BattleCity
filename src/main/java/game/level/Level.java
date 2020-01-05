package game.level;

import com.google.gson.annotations.Expose;
import game.entities.MobileEntity;
import game.entities.mobiles.Bullet;
import game.entities.mobiles.Player;
import game.entities.mobiles.Tank;
import game.entities.mobiles.TankType;
import game.entities.statics.Block;
import game.entities.statics.BlockType;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private String name;

    public static final int SIZE = 26;
    public static final int TANKS = 4;
    public static final int QUEUE_SIZE = 20;
    public static final int[] SPAWN = new int[]{9, 24};


    @Expose
    private Player player;
    @Expose
    private Block[][] map;
    @Expose
    private List<TankType> queue;
    @Expose
    private List<Tank> enemies;

    private List<Tank> deadEnemies;
    private List<Block> base;

    public Level() {
        map = new Block[SIZE][SIZE];

        base = new ArrayList<>(4);

        enemies = new ArrayList<>(TANKS);
        deadEnemies = new ArrayList<>(TANKS);
        queue = new ArrayList<>(QUEUE_SIZE);

        initMap();
    }

    private void initMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = BlockType.AIR.createBlock(i, j);
            }
        }
    }

    public void initTanks() {
        enemies = new ArrayList<>(TANKS);

        if (queue.isEmpty()) {
            for (int i = 0; i < QUEUE_SIZE; i++) {
                queue.add(TankType.random());
            }
        }
    }

    public void destroy(Tank tank) {
        if (enemies.remove(tank)) {
            Location.remove(tank, map);
            deadEnemies.add(tank);
        }
    }

    public Tank trySpawnTank() {
        if (!queue.isEmpty() && enemies.size() < 4) {
            Tank tank = queue.remove(0).createTank(map);
            Location.random(map).add(tank);
            enemies.add(tank);
            return tank;
        }
        return null;
    }

    public boolean baseIsBreak() {
        if (base.isEmpty()) {
            return false;
        }

        for (Block block : base) {
            if (block.isDead()) {
                return true;
            }
        }
        return false;
    }


    public boolean allEnemiesIsDead() {
        return queue.isEmpty() && enemies.isEmpty();
    }

    public List<Tank> getEnemies() {
        return enemies;
    }

    public List<Tank> getTanks() {
        List<Tank> tanks = new ArrayList<>(enemies);
        tanks.add(player);
        return tanks;
    }

    public List<Bullet> getBullets() {
        List<Bullet> bullets = new ArrayList<>();

        List<Tank> tanks = new ArrayList<>(getTanks());
        tanks.addAll(deadEnemies);

        for (Tank tank : tanks) {
            Bullet bullet = tank.getBullet();
            if (bullet.isFly()) {
                bullets.add(bullet);
            }
        }
        return bullets;
    }

    public List<MobileEntity> getMobiles() {
        List<MobileEntity> entities = new ArrayList<>(getTanks());
        entities.addAll(getBullets());
        return entities;
    }

    public String getName() {
        return name;
    }
    public void setName(String text) {
        name = text;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }

    public Block[][] getMap() {
        return map;
    }
    public List<Block> getBase() {
        return base;
    }

    public List<TankType> getQueue() {
        return queue;
    }
}
