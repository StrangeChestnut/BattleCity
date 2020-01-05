package graphics;

import game.utils.DataConverter;
import game.Status;
import game.entities.mobiles.Bullet;
import game.entities.mobiles.Tank;
import game.level.Level;
import game.entities.statics.Block;
import graphics.draws.*;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private game.Game game;

    public String[] getLevels() {
        String[] list = DataConverter.LEVELS.list();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                String name = list[i];
                list[i] = name.substring(0, name.lastIndexOf('.'));
            }
        }
        return list;
    }

    public boolean launchSave(String levelName) {
        Level save = DataConverter.readSave(levelName);
        if (save != null) {
            game = new game.Game(save);
            return true;
        }
        game = new game.Game(DataConverter.readLevel(levelName));
        return false;
    }

    public void launch() {
        String levelName = game.getLevel().getName();
        DataConverter.tryDeleteSave(levelName);
        game = new game.Game(DataConverter.readLevel(levelName));
    }

    public void startGame() {
        game.setStatus(Status.RUN);
        if (!game.isAlive()) {
            game.start();
        }
    }

    public void save() {
        if (game != null && game.getStatus() != null && game.getStatus().isRunning()) {
            DataConverter.writeSave(game.getLevel());
        }
    }

    public void checkEvent(KeyCode code, boolean isDoing) {
        if (code == KeyCode.SPACE) {
            game.getPlayer().updateEvent(4, isDoing);
        }

        int index = getIndex(code);
        if (index != -1) {
            game.getPlayer().updateEvent  (index, isDoing);
        }
    }

    private int getIndex(KeyCode code) {
        int i;
        switch (code) {
            case A:
                i = 0;
                break;
            case D:
                i = 1;
                break;
            case W:
                i = 2;
                break;
            case S:
                i = 3;
                break;
            default:
                i = -1;
        }
        return i;
    }

    public boolean tryPause() {
        if (isGameRun()) {
            game.setStatus(Status.PAUSE);
            return true;
        }
        return false;
    }

    boolean isGameRun() {
       return game.getStatus() == Status.RUN;
    }

    boolean isGameOver() {
        return !game.getStatus().isRunning();
    }

    public String getGameOverText() {
        return "YOU " + game.getStatus().toString();
    }

    public boolean isBusy() {
        return game != null;
    }

    List<Drawable> readEntities() {
        List<Drawable> entities = new ArrayList<>();
        Drawable drawable;

        for (Block[] blocks : game.getMap()) {
            for (Block block : blocks) {
                drawable = new DrawBlock(block);
                entities.add(drawable);
            }
        }

        for (Tank tank : game.getLevel().getTanks()) {
            drawable = tank.isEnemy() ? new DrawTank(tank) : new DrawPlayer(tank);
            entities.add(drawable);


            Bullet bullet = tank.getBullet();
            if (bullet.isFly()) {
                drawable = new DrawBullet(bullet);
                entities.add(drawable);
            }
        }
        return entities;
    }
}
