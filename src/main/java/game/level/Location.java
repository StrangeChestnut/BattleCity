package game.level;

import game.entities.*;
import game.entities.mobiles.*;
import game.entities.statics.Block;
import game.entities.statics.BlockType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Location {
    private Block[][] box;
    private Block[][] map;

    public static void swipe(Location newLocation, Location preLocation, MobileEntity entity) {
        preLocation.remove(entity);
        newLocation.add(entity);
    }

    public static void remove(MobileEntity entity, Block[][] map) {
        for (int i = entity.getX(); i < entity.getX() + 2; i++) {
            for (int j = entity.getY(); j < entity.getY() + 2; j++) {
                map[i][j].clear(entity);
            }
        }
    }

    public static boolean notHave(BlockType type, int x, int y, Block[][] map) {
        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                if (map[i][j].getType() == type) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Tank randomTank(int x, int y, Block[][] map) {
        Tank tank;
        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                tank = map[i][j].getTank();
                if (tank != null) {
                    return tank;
                }
            }
        }
        return null;
    }

    public Block[][] getMap() {
        return map;
    }

    public Location(int x, int y, Block[][] map) {
        Block[][] box = new Block[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                box[i][j] = map[x + i][y + j];
            }
        }
        this.box = box;
        this.map = map;
    }

    public Location(int x0, int y0, Direction direction, Block[][] map) {
        int[] v = direction.getVector();
        box = new Block[2][2];

        int x, y;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                x = x0 + v[0] + i;
                y = y0 + v[1] + j;
                if (0 <= x && x < map.length && 0 <= y && y < map.length) {
                    box[i][j] = map[x][y];
                } else {
                    box = null;
                    return;
                }
            }
        }
        this.map = map;
    }

    public static Location random(Block[][] map) {
        Random random = new Random();
        Location location;
        do {
            location = new Location(random.nextInt(25), random.nextInt(5), map);
        } while (location.numberMovingTiles() != 4);
        return location;
    }

    public void add(MobileEntity entity) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                box[i][j].add(entity);
            }
        }
        entity.setX(getX());
        entity.setY(getY());
    }

    private void remove(MobileEntity entity) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                box[i][j].clear(entity);
            }
        }
    }

    public int numberMovingTiles() {
        int n = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                 if (box[i][j].isMoving()) n++;
            }
        }
        return n;
    }

//    public List<Bullet> getBullets() {
//        List<Bullet> bullets = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                bullets.addAll(box[i][j].getBullets());
//            }
//        }
//        return bullets;
//    }
//
//    public int numberOfContains(Bullet bullet) {
//        int result = 0;
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                if (box[i][j].getBullets().contains(bullet)) {
//                    result++;
//                }
//            }
//        }
//        return result;
//    }

    public Block[][] getBox() {
        return box;
    }
    public Block getBlock(int i, int j) {
        return box[i][j];
    }

    public int getX() {
        return box[0][0].getX();
    }
    public int getY() {
        return box[0][0].getY();
    }

    public boolean isClearToMove() {
        return getBox() != null && numberMovingTiles() == 2;
    }
}
