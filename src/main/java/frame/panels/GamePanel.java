package frame.panels;

import game.Game;
import game.Status;
import frame.DrawEventArg;
import frame.MyFrame;
import frame.drawEntities.IDraw;
import frame.drawEntities.IDrawBlock;
import frame.drawEntities.IDrawBullet;
import frame.drawEntities.IDrawTank;
import game.entities.MobileEntity;
import game.entities.mobiles.*;
import game.level.Level;
import game.level.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends DrawPanel {
    private Game game;
    private ArrayList<IDraw> entities;
    private Timer timer = new Timer(40, this::gameFPS);

    public GamePanel() {
        super();
        game = new Game(new Level("C:\\Users\\stran\\IdeaProjects\\Third\\BattleCity\\src\\maps\\map.json)"));
        initDraws();

        setSize(430, 453);

        addKeyListener(new PlayerListener());
        addKeyListener(new ShotListener());

        addDrawListener(this::drawEventPerformed);
        timer.start();
    }

    private void initDraws() {
        entities = new ArrayList<>();
        for (Tile[] tiles : game.level().map()) {
            for (Tile tile : tiles) {
                entities.add(new IDrawBlock(tile.block()));
            }
        }
        entities.add(new IDrawTank(game.player()));
    }

    private void gameFPS(ActionEvent event) {
        game.update();
        repaint();
        if (game.status() != Status.RUN) {
            JLabel label = new JLabel(game.status() == Status.WIN ? "YOU WON" : "YOU LOOSE");
            label.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
            label.setForeground(Color.ORANGE);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    MyFrame.switchPane(GamePanel.this.getParent(), GamePanel.this, new MenuPanel());
                }
            });
            add(label);
            updateUI();
            timer.stop();
        }
    }

    private class PlayerListener implements KeyListener {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            int i = key == KeyEvent.VK_UP ? 0 :
                    key == KeyEvent.VK_DOWN ? 1 :
                            key == KeyEvent.VK_LEFT ? 2 :
                                    key == KeyEvent.VK_RIGHT ? 3 : -1;
            if (i >= 0) {
                game.player().updateEvent(i, true);
            }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            int i = key == KeyEvent.VK_UP ? 0 :
                    key == KeyEvent.VK_DOWN ? 1 :
                            key == KeyEvent.VK_LEFT ? 2 :
                                    key == KeyEvent.VK_RIGHT ? 3 : -1;
            if (i >= 0) {
                game.player().updateEvent(i, false);
            }
        }

        public void keyTyped(KeyEvent e) {
            //...
        }
    }

    private class ShotListener implements KeyListener {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE)  game.player().updateEvent(4,true);
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE)  game.player().updateEvent(4, false);
        }

        public void keyTyped(KeyEvent e) {
            //...
        }
    }

    private void drawEventPerformed(DrawEventArg event) {
        Graphics g = event.getGraphics();

        for (MobileEntity entity : game.level().spawnedTanks()) {
            entities.add(new IDrawTank((Tank) entity));
        }
        for (MobileEntity entity : game.level().bullets()) {
            entities.add(new IDrawBullet((Bullet) entity));
        }

        for (IDraw entity : this.entities) {
            entity.draw(g);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        fireDrawListener(g);
    }
}
