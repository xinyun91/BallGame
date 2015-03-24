package lab6.entities;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;

import engine.Entity;
import engine.collision.CollisionListener;
import engine.drawing.ICanvas;

public class Paddle implements Entity {
    
    private int _x;
    private int _y;
    private int _width;
    private int _height;
    private int _vx;
    private int _vy;
    private Color _color;
    private Collection <CollisionListener> _cl;
    
    public Paddle(int x, int y, int width, int height, int vx, int vy, Color color) {
        
        _x = x;
        _y = y;
        _width = width;
        _height = height;
        _vx = vx;
        _vy = vy;
        _color = color;
        _cl = new ArrayList <CollisionListener> ();
        
    }

    @Override
    public Rectangle getRect() {
        // TODO Auto-generated method stub
        return new Rectangle(_x, _y, _width, _height);
    }

    @Override
    public void draw(ICanvas dc) {
        dc.drawFilledRectangle(_x, _y, _width, _height, _color);
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        _x = getXCoordinate() + getXVelocity();
        _y = getYCoordinate() + getYVelocity();
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
