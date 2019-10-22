package frame.drawEntities;

import java.awt.*;

public interface IDraw {
    int TILE_SIZE = 24;
    int TANK_SIZE = TILE_SIZE * 2;
    void draw(Graphics g);
}
