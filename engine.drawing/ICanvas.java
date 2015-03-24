package engine.drawing;

import java.awt.Color;
import java.awt.Image;

/**
 * 
 * @author ginonott
 * 
 */
public interface ICanvas {
    public void drawLine(int x, int y, int x2, int y2);
    
    public void drawLine(int x, int y, int x2, int y2, int thickness, Color c);
        
    public void fill(Color c);

    public void drawRectange(int x, int y, int width, int height);

    public void drawRectangle(int x, int y, int width, int height, Color c);

    public void drawFilledRectangle(int x, int y, int width, int height, Color c);
    
    public void drawCircle(int x, int y, int size, Color c);

    public void drawFilledCircle(int x, int y, int size, Color c);

    public void drawImage(Image img, int x, int y);

    public void setBackgroundColor(Color c);

    public void resize(int width, int height);

    public void scale(boolean b, double scale);

    public void drawText(String s, int size, int x, int y);
    
    public void drawText(String s, int size, int x, int y, Color c);
}
