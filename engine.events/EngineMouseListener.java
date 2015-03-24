package engine.events;

import java.awt.Rectangle;

public interface EngineMouseListener {
    public void onClick(EngineMouseEvent me);
    
    public Rectangle getBoundingBox();
}
