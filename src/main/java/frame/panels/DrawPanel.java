package frame.panels;

import frame.DrawEventArg;
import frame.IDrawListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
    private final List<IDrawListener> drawListeners;

    public DrawPanel() {
        super();
        drawListeners = new ArrayList<IDrawListener>();

        setSize(624, 624);
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    public void addDrawListener(IDrawListener listener) {
        if (drawListeners.indexOf(listener) == -1) {
            drawListeners.add(listener);
        }
    }

    public void removeDrawListener(IDrawListener listener) {
        drawListeners.remove(listener);
    }

    public IDrawListener[] getDrawListeners() {
        return drawListeners.toArray(new IDrawListener[drawListeners.size()]);
    }

    protected void fireDrawListener(Graphics graphics) {
        DrawEventArg event = new DrawEventArg(this, graphics);
        for (IDrawListener listener : drawListeners) {
            listener.drawEvent(event);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        fireDrawListener(g);
    }
}
