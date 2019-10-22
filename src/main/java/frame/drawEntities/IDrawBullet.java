package frame.drawEntities;

import game.entities.mobiles.Bullet;

import java.awt.*;

public class IDrawBullet implements IDraw {
    private Bullet bullet;
    public IDrawBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    @Override
    public void draw(Graphics g) {
        if (!bullet.isDead()) {
            g.setColor(Color.WHITE);
            int y = bullet.location().tiles()[0][0].getX(), x = bullet.location().tiles()[0][0].getY(), len = TANK_SIZE / 4;
            g.fillRect(x * TILE_SIZE + (TANK_SIZE - len) / 2, y * TILE_SIZE + (TANK_SIZE - len) / 2, len, len);
        }
    }
}
