package graphics;

import graphics.controllers.GameScene;
import graphics.draws.*;

import javafx.animation.AnimationTimer;

import java.util.List;

public class GameRepainter extends AnimationTimer {
    private GameController controller;
    private GameScene scene;
    private List<Drawable> entities;

    public GameRepainter(GameController controller, GameScene scene) {
        this.controller = controller;
        this.scene = scene;
    }

    @Override
    public void handle(long l) {
        if (controller.isGameRun()) {
            clearScene();
            entities = controller.readEntities();
            repaint();
        } else if (controller.isGameOver()) {
            scene.gameOver();
        }

    }

    private void clearScene() {
        if (entities != null) {
            for (Drawable d : entities) {
                scene.remove(d.getImage());
            }
        }
    }

//    private void readEntities() {
//        for (Tank tank : game.getLevel().getTanks()) {
//            if (!(tank instanceof DrawTank) && !(tank instanceof DrawPlayer)) {
//                DrawTank drawTank = new DrawTank(tank, gameField);
//                entities.add(drawTank);
//                game.getLevel().getEnemies().remove(tank);
//                game.getLevel().getEnemies().add(drawTank);
//            }
//
//            Bullet bullet = tank.getBullet();
//            if (bullet.isFly() && !bullet.isDead()) {
//                if (!(bullet instanceof DrawBullet)) {
//                    DrawBullet b = new DrawBullet(bullet, gameField);
//                    entities.add(b);
//                    tank.setBullet(b);
//                }
//            }
//        }
//    }

    private void repaint() {
        entities.sort(Drawable.COMPARATOR);
        for (Drawable entity : entities) {
            entity.draw(scene.getGameField());
        }
    }
}
