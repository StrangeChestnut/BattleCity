package game;

import game.entities.*;
import game.entities.mobiles.*;
import game.entities.statics.*;
import game.level.*;

import java.util.Random;

public class Game {
    private Random random = new Random();

    private Status status;
    private Level level;
    private Player player;

    public Game(Level level) {
        this.level = level;
        player = new Player();
        level.addPlayer(player);
        status = Status.RUN;
    }

    public void update() {
        cleanLevel();

        player.update();
        updateLevel();

        collision();
        updateStatus();
    }

    private void updateStatus() {
        // || level.baseIsBreak()
       if (player.isDead()) {
           status = Status.LOOSE;
       } else if (level.enemiesIsDead()) {
           status = Status.WIN;
       }
    }

    private void cleanLevel() {
        cleanDeadBlocks();
        cleanDeadMobileEntities();
    }

    private void cleanDeadBlocks() {
        Tile[][] map = level.map();
        Block block;
        for (Tile[] tiles : map) {
            for (Tile tile : tiles) {
                block = tile.block();
                if (block.isDead()) block.setType(BlockType.AIR);
            }
        }
    }

    private void cleanDeadMobileEntities() {
        for (MobileEntity entity : level.entities()) {
            if (entity.isDead()) level.deleteEntity(entity);
        }
    }

    private void updateLevel() {
        trySpawnTanks();
        tankAI();
        entitiesMove();
    }

    private void trySpawnTanks() {
        while (level.spawnedTanks().size() < 4 && !level.waitingTanks().isEmpty()) {
            level.spawnTank();
        }
    }

    private void tankAI() {
        for (Tank tank : level.spawnedTanks()) {
            if (tank.moveCooldown() == 0) {
                tank.setDirection(Direction.random());
            }
            tryShot(tank);
        }
    }

    private void tryShot(Tank tank) {
        if (random.nextInt(3) == 0 && tank.canShot()) {
            level.addBullet(tank.shot());
        }
        tank.decShotCooldown();
    }

    private void entitiesMove() {
        for (MobileEntity entity : level.entities()) {
            entity.move();
        }
    }

    private void collision() {
        for (Bullet entity : level.bullets()) {
            if (!entity.isDead()) collision(entity);
        }
    }

    private void collision(Bullet bullet) {
        Location location = bullet.location();

        Tank tank = location.firstTank();
        if (tank != null && !tank.equals(bullet.tank()) && isDifference(tank, bullet.tank())) {
            damage(bullet);
            damage(tank);
            return;
        }

        if (tryBreakBlocks(location)) {
            damage(bullet);
            return;
        }

        for (Bullet bulletToCheck : location.bullets()) {
            if (isDifference(bullet.tank(), bulletToCheck.tank()) && bullet.flyTo(bulletToCheck)) {
                damage(bullet);
                damage(bulletToCheck);
                return;
            }
        }
    }

    private void damage(Entity entity) {
        entity.setHealth(entity.getHealth() - 1);
    }

    private boolean isDifference(Tank tank1, Tank tank2) {
        return !(tank1.equals(tank2) || (level.hasEnemyTank(tank2) && level.hasEnemyTank(tank1)));
    }

    private boolean tryBreakBlocks(Location location) {
        boolean broken = false;
        Block block;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                block = location.block(i, j);
                if (block.isBreakable()) {
                    damage(block);
                    broken = true;
                }
            }
        }
        return broken;
    }

    public Level level() {
        return level;
    }
    public Player player() {
        return player;
    }
    public Status status() {
        return status;
    }
}
