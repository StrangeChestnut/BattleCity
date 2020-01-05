package game.entities.mobiles;

import game.entities.statics.Block;

import java.util.Random;

public enum TankType {

    CT() {
        public Tank createTank(Block[][] map) {
            return new Tank(SPEED,DEFAULT_HEALTH, BULLET_SPEED);
        }
    },

    APC() {
        public Tank createTank(Block[][] map) {
            return new Tank(MORE_SPEED,DEFAULT_HEALTH, BULLET_SPEED);
        }
    },

    RFT() {
        public Tank createTank(Block[][] map) {
            return new Tank(SPEED,DEFAULT_HEALTH, MORE_BULLET_SPEED);
        }
    },

    HT() {
        public Tank createTank(Block[][] map) {
            return new Tank(SPEED, 1 + R.nextInt(MAX_HEALTH),BULLET_SPEED);
        }
    };


    public abstract Tank createTank(Block[][] map);
    private final static Random R = new Random();


    public static final int SPEED = 240;
    public static final int MORE_SPEED = 120;
    public static final int BULLET_SPEED = 10;
    public static final int MORE_BULLET_SPEED = 40;
    public static final int DEFAULT_HEALTH = 1;
    public static final int MAX_HEALTH = 4;


    public static TankType random() {
        TankType type;
        switch (R.nextInt(4)) {
            case 1:
                type = APC;
                break;
            case 2:
                type = RFT;
                break;
            case 3:
                type = HT;
                break;
            default:
                type = CT;
        }
        return type;
    }
}
