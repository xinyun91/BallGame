package ballGame.entities;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;

import engine.Entity;
import engine.collision.CollisionListener;
import engine.drawing.ICanvas;

public class Ball implements Entity{
    private int _x;
    private int _y;
    private int _d;
    private int _vx;
    private int _vy;
    private Color _color;
    private Collection <CollisionListener> _cl; 
    
    public Ball(int x, int y, int d, int vx, int vy, Color color){
        
        _x = x;
        _y = y;
        _d = d;
        _vx = vx;
        _vy = vy;
        _color = color;
        _cl = new ArrayList <CollisionListener> ();
        
    }

    @Override
    public Rectangle getRect() {
        // TODO Auto-generated method stub
        return new Rectangle (_x, _y, _d, _d);
    }

    @Override
    public void draw(ICanvas dc) {
        dc.drawFilledCircle(_x, _y, _d, _color);
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update() {
        _x = getXCoordinate() + _vx;
        _y = getYCoordinate() + _vy; 
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getXCoordinate() {
        // TODO Auto-generated method stub
        return _x;
    }

    @Override
    public int getYCoordinate() {
        // TODO Auto-generated method stub
        return _y;
    }

    @Override
    public int getXVelocity() {
        // TODO Auto-generated method stub
        return _vx;
    }

    @Override
    public int getYVelocity() {
        // TODO Auto-generated method stub
        return _vy;
    }

    @Override
    public void setXVelocity(int velocity) {
        // TODO Auto-generated method stub
        _vx = velocity;
    }

    @Override
    public void setYVelocity(int velocity) {
        // TODO Auto-generated method stub
        _vy = velocity;
    }

    @Override
    public void addCollisionListener(CollisionListener cl) {
        // TODO Auto-generated method stub
        _cl.add(cl);
    }

    @Override
    public void removeCollisionListener(CollisionListener cl) {
        // TODO Auto-generated method stub
        _cl.remove(cl);
    }

    @Override
    public void fireCollisionOccurred(Entity e) {
        // TODO Auto-generated method stub
        for (CollisionListener cl: _cl) {
            cl.collisionOccurred(e);
        }
    }

}
