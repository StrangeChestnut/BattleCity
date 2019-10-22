package frame.panels;

import frame.DrawEventArg;
import frame.MyFrame;
import frame.drawEntities.IDrawBlock;
import game.entities.statics.BlockType;
import game.level.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileWriter;


public class BuildPanel extends DrawPanel {
    private FileWriter writer;

    private DrawPanel drawField;
    private JPanel drawPanel;

    private JPanel brushPanel;
    private JButton[] brushes;

    private JPanel buttonPanel;

    private BlockType brush;
    private IDrawBlock[][] map;

    public BuildPanel() {
        super();
        setSize(624,624);
        setLayout(new BorderLayout());

        brush = BlockType.BRICK;
        map = new IDrawBlock[Level.size][Level.size];
        initMap();

        brushPanel = new JPanel();
        brushPanel.setPreferredSize(new Dimension(84,540));
        brushPanel.setLayout(new GridBagLayout());
        brushPanel.setBackground(Color.RED);
        initBrushes();

        drawField = new DrawPanel();
        drawField.setPreferredSize(new Dimension(540,540));
        drawField.addDrawListener(this::drawEventPerformed);
        drawField.repaint();
        addAction();

        drawPanel = new JPanel();
        drawPanel.setLayout(new BorderLayout());
        drawPanel.setPreferredSize(new Dimension(624,540));
        drawPanel.add(drawField, BorderLayout.CENTER);
        drawPanel.add(brushPanel,BorderLayout.EAST);

        add(drawPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(624, 84));
        buttonPanel.setLayout(new BorderLayout());
        initButtons();

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = new IDrawBlock(i, j);
            }
        }
    }

    private void addAction() {
            drawField.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    addBlock(e);
                }
            });
            drawField.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    addBlock(e);
                }
            });
    }

    private void addBlock(MouseEvent e) {
        try {
            int x = e.getX(), y = e.getY();
            int j = x * 26 / (drawField.getHeight());
            int i = y * 26 / (drawField.getHeight());
            map[i][j] = new IDrawBlock(i, j, brush);
            repaint();
        } catch (IndexOutOfBoundsException ignored) {}
    }

    private void initButtons() {
        JButton load = new JButton("LOAD");
        JButton save = new JButton("SAVE");
        JButton clear = new JButton("CLEAR");
        JButton back = new JButton("BACK");
        save.addActionListener(e -> {

        });
        clear.addActionListener(e -> {
            map = new IDrawBlock[Level.size][Level.size];
            initMap();
            repaint();
        });
        back.addActionListener(e -> MyFrame.switchPane(getParent(), this, new MenuPanel()));
        buttonPanel.add(load, BorderLayout.WEST);
        buttonPanel.add(save, BorderLayout.CENTER);
        buttonPanel.add(clear, BorderLayout.EAST);
        buttonPanel.add(back, BorderLayout.SOUTH);
    }

    private void initBrushes() {
        BlockType[] types = BlockType.values();
        brushes = new JButton[types.length];
        JButton button;
        for (int i = 0; i < types.length; i++) {
            BlockType type = types[i];
            button = new JButton(type.name());
            button.setBackground(IDrawBlock.getColor(type));
            button.setFocusPainted(false);
            button.addActionListener(e -> brush = type);
            button.setPreferredSize(new Dimension(84,77));
            brushes[i] = button;
            brushPanel.add(button, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 0, 0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0),
                    0, 0));
        }
    }

    private void drawEventPerformed(DrawEventArg event) {
        Graphics g = event.getGraphics();
        drawMap(g);
        drawGrid(g);
    }

    private void drawMap(Graphics g) {
        for (IDrawBlock[] row : map) {
            for (IDrawBlock block : row) {
                block.draw(g, (double)drawField.getHeight() / 26);
            }
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        double step = (double) drawField.getHeight() / 26;
        for (int i = 0; i < 27; i++) {
            g.drawLine(0, (int)(i * step), drawField.getWidth(), (int)(i * step));
            g.drawLine((int)(i * step), 0, (int)(i * step), drawField.getHeight());
        }
    }
}


