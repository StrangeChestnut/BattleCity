package frame.drawEntities;

import game.entities.mobiles.Player;
import game.entities.mobiles.Tank;

import java.awt.*;

public class IDrawTank implements IDraw {
    private Tank tank;
    public IDrawTank(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void draw(Graphics g) {
        if ((tank instanceof Player) || !tank.isDead()) {
            int y = tank.location().tiles()[0][0].getX(), x = tank.location().tiles()[0][0].getY(), len = TANK_SIZE / 4;

            g.setColor(tank instanceof Player ? Color.ORANGE : Color.GRAY);
            g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TANK_SIZE, TANK_SIZE);
            g.setColor(Color.WHITE);

            switch (tank.getDirection()) {
                case NORTH:
                    g.fillRect(x * TILE_SIZE + (TANK_SIZE - len) / 2, y * TILE_SIZE, len, len);
                    break;
                case SOUTH:
                    g.fillRect(x * TILE_SIZE + (TANK_SIZE - len) / 2, y * TILE_SIZE + TANK_SIZE - len, len, len);
                    break;
                case WEST:
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE + (TANK_SIZE - len) / 2, len, len);
                    break;
                case EAST:
                    g.fillRect(x * TILE_SIZE + TANK_SIZE - len, y * TILE_SIZE + (TANK_SIZE - len) / 2, len, len);
                    break;
            }
        }
    }
}
