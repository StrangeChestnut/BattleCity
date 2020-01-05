package game;

import game.entities.*;
import game.entities.mobiles.*;
import game.entities.statics.*;
import game.level.*;

public class Game extends Thread {
    private Status status;
    private Level level;
    private Player player;

    public Game(Level level) {
        this.level = level;
        this.player = level.getPlayer();

        if (level.getEnemies().isEmpty())
            level.initTanks();
        if (level.getPlayer() == null)
            initPlayer();

    }

    private void initPlayer() {
        player = new Player(Level.SPAWN[0],  Level.SPAWN[1]);
        Location location = new Location(Level.SPAWN[0], Level.SPAWN[1], level.getMap());
        location.add(player);
        level.setPlayer(player);
    }

    private void update() {
        cleanLevel();

        //updateEntities();

        trySpawn();

        updateStatus();

        collision();
    }

    private void trySpawn() {
        Tank tank = level.trySpawnTank();
        while (tank != null) {
            tank.launchIn(this);
            tank = level.trySpawnTank();
        }
    }

    private void updateStatus() {
        if (player.isDead() || level.baseIsBreak()) {
            status = Status.LOOSE;
        } else if (level.allEnemiesIsDead()) {
            status = Status.WIN;
        }
    }

    private void cleanLevel() {
        cleanDeadBlocks();
        cleanDeadMobileEntities();
    }

    private void cleanDeadBlocks() {
        Block[][] map = level.getMap();
        for (Block[] blocks : map) {
            for (Block block : blocks) {
                if (block.isDead()) {
                     block.setAir();
                }
            }
        }
    }

    private void cleanDeadMobileEntities() {
        for (Tank tank : level.getTanks()) {
            Bullet bullet = tank.getBullet();
            if (bullet.isFly() && bullet.isDead()) {
                bullet.setFly(false);
            }
            if (tank.isDead()) {
                level.destroy(tank);
            }
        }
    }

    private void collision() {
        for (Bullet bullet : level.getBullets()) {
            if (!bullet.isDead()) {
                collision(bullet);
            }
        }
        for (Tank  tank: level.getTanks()) {
            checkControl(tank);
        }
    }

    private void checkControl(Tank tank) {
        if (new Location(tank.getX(), tank.getY(), tank.getDirection(), level.getMap()).isClearToMove()) {
            tank.setControl(Location.notHave(BlockType.ICE, tank.getX(), tank.getY(), level.getMap()));
        } else {
            tank.setControl(true);
        }
    }

    private void collision(Bullet bullet) {

        Tank tankToCheck = Location.randomTank(bullet.getX(), bullet.getY(), level.getMap());
        if (tankToCheck != null && !tankToCheck.isDead() && tankToCheck.isDifferent(bullet)) {
            damage(bullet);
            damage(tankToCheck);
            return;
        }

        if (tryBreakBlocks(bullet.getX(), bullet.getY())) {
            damage(bullet);
            //return;
        }
    }

    private void damage(Entity entity) {
        entity.setHealth(entity.getHealth() - 1);
    }

    private boolean tryBreakBlocks(int x, int y) {
        boolean broken = false;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Block block = level.getMap()[x + i][y + j];
                if (block.isBreakable()) {
                    damage(block);
                    broken = true;
                }
            }
        }
        return broken;
    }

    @Override
    public void run() {
        while (status.isRunning()) {
            if (status == Status.RUN) {
                update();
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException ignored) {}
        }
    }

    @Override
    public void start() {
        super.start();
        for (MobileEntity entity : level.getMobiles()) {
            entity.launchIn(this);
        }
    }

    public Level getLevel() {
        return level;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
        level.setPlayer(player);
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public Block[][] getMap() {
        return level.getMap();
    }
}
