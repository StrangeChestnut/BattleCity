package frame;

import java.util.EventListener;

public interface IDrawListener extends EventListener {
    public void drawEvent(DrawEventArg e);
}
