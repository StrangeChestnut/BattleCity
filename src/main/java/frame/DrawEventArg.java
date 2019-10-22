package frame;
import java.awt.*;

public class DrawEventArg {
    private final Object object;
    private final Graphics graphics;

    public Object getObject() {
        return object;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public DrawEventArg(Object object, Graphics graphics) {
        this.object = object;
        this.graphics = graphics;
    }
}
