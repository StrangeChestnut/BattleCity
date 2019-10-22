package frame.panels;

import frame.MyFrame;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(624, 624);
        setBackground(Color.BLACK);
        initLabel();
        initButtons();
    }

    private void initLabel() {
        JLabel label = new JLabel("BATTLE CITY");
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setBackground(null);
        label.setFont(new Font(Font.DIALOG, Font.BOLD, 80));
        label.setForeground(Color.RED);
        add(label);
    }

    private void initButtons() {
        JButton game = new JButton("PLAY");
        initButton(game);
        game.addActionListener(e -> MyFrame.switchPane(getParent(), this, new GamePanel()));

        JButton constructor = new JButton("BUILD");
        initButton(constructor);
        constructor.addActionListener(e -> MyFrame.switchPane(getParent(), this, new BuildPanel()));

        JButton exit = new JButton("EXIT");
        initButton(exit);
        exit.addActionListener(e -> System.exit(0));
    }

    private void initButton(JButton button) {
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.ORANGE);
        button.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
        add(button);
    }
}
