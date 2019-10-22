package frame.drawEntities;

import game.entities.statics.*;

import java.awt.*;

public class IDrawBlock implements IDraw{
    private int x, y;
    private BlockType type;
    transient private Color c;
    public IDrawBlock(Block block) {
        this(block.location().tiles()[0][0].getX(), block.location().tiles()[0][0].getY(), block.getType());
        if (block.getHealth() < 2) c = c.darker();
    }
    public IDrawBlock(int x, int y, BlockType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        c = c = getColor(type);
    }

    public IDrawBlock(int x, int y) {
        this(x, y, BlockType.AIR);
    }

    public void draw(Graphics g) {
        draw(g, TILE_SIZE);
    }

    public static Color getColor(BlockType type){
        Color c = null;
        switch (type) {
            case BASE:
                c = new Color(117, 0, 123);
                break;
            case BRICK:
                c = new Color(163, 60, 47);
                break;
            case IRON:
                c = new Color(127, 141, 133);
                break;
            case LEAF:
                c = new Color(6, 123, 0);
                break;
            case WATER:
                c = new Color(13, 70, 123);
                break;
            case ICE:
                c = new Color(100, 255, 250);
                break;
            case AIR:
                c = new Color(0, 0, 0);
                break;
        }
        return c;
    }

    public void draw(Graphics g, double a) {
        g.setColor(c);
        g.fillRect((int)(y * a), (int)(x* a), (int)a,(int) a);
    }
}
