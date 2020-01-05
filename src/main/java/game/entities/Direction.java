package game.entities;

import java.util.Random;

public enum Direction {
    WEST(-1, 0),
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1) ;

    private int[] v;

    Direction(int i, int i1) {
        this.v = new int[]{i, i1};
    }

    public int[] getVector() {
        return v;
    }

    public static Direction random() {
        Direction direction = null;
        switch (new Random().nextInt(4)) {
            case 0:
                direction = Direction.WEST;
                break;
            case 1:
                direction = Direction.EAST;
                break;
            case 2:
                direction = Direction.NORTH;
                break;
            case 3:
                direction = Direction.SOUTH;
                break;
        }
        return direction;
    }

    public boolean isOpposite(Direction direction) {
        return (v[0] + direction.v[0]  + v[1] + direction.v[1]) == 0;
    }
}
