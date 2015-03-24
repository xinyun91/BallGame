package engine.collision;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;

import engine.Entity;

public class CollisionManager implements ICollisionManager{
    private ArrayList<Entity> _objects;
    private LinkedList<Entity> _objectsToBeRemoved;
    private LinkedList<Entity> _objectsToBeAdded;

//    private boolean _debug;

    public CollisionManager() {
        _objects = new ArrayList<>();
        _objectsToBeAdded = new LinkedList<>();
        _objectsToBeRemoved = new LinkedList<>();

//        _debug = false;
    }

//    public void setDebug(boolean b) {
//        _debug = b;
//    }

    public void addCollisionObject(Entity cl) {
        synchronized (_objectsToBeAdded) {
            _objectsToBeAdded.push(cl);
        }
    }

    public void removeCollisionObject(Entity cl) {
        synchronized (_objectsToBeRemoved) {
            _objectsToBeRemoved.push(cl);
        }
    }

    public void checkCollisions() {
                
        for (int i = 0; i < _objects.size(); i++) {
            Rectangle r = _objects.get(i).getRect();

            if (r == null) {
                continue;
            }

            for (int j = i + 1; j < _objects.size(); j++) {
                if (i == j) {
                    continue;
                }

                if (_objects.get(j).getRect() == null) {
                    continue;
                }

                if (r.intersects(_objects.get(j).getRect())) {
                    System.out.print(_objects.get(j).getClass().getName());
                    System.out.println(" collided with "+_objects.get(j).getClass().getName());
                    _objects.get(i).fireCollisionOccurred(_objects.get(j));
                    _objects.get(j).fireCollisionOccurred(_objects.get(i));
                }
            }
        }
        
        //commit any changes to the arraylist of objects
        commitChanges();
        
    }

    private void commitChanges() {
        synchronized (_objectsToBeAdded) {
            while(!_objectsToBeAdded.isEmpty()){
                _objects.add(_objectsToBeAdded.poll());
            }
        }
        
        synchronized (_objectsToBeRemoved) {
            while(!_objectsToBeRemoved.isEmpty()){
                _objects.remove(_objectsToBeRemoved.poll());
            }
        }
    }

    /**
     * Returns the direction of the collision of two rectangles. The src
     * rectangle is the object that has collided with the dest rectangle.
     * Therefore, a src rectangle that hits a dest rectangle on the right will
     * return EAST.
     * 
     * Note: This is computationally expensive!
     * 
     * TODO: Use information to cut back on a few checks. ie, check location of
     * src and determine which lines are more likely in relation to the two
     * rectangles.
     * 
     * @param src
     *            the colliding rectangle
     * @param dest
     *            the collided rectangle
     * 
     * @return the location of the collision on the dest rectangle
     */
    static public CollisionDirection getCollisionDirection(Rectangle src,
            Rectangle dest) {
        Double x, y, width, height;
        x = dest.getX();
        y = dest.getY();
        width = dest.getWidth();
        height = dest.getHeight();

        // make the left line
        Line2D.Double left = new Line2D.Double(x, y, x, y + height);
        Line2D.Double bottom = new Line2D.Double(x, y + height, x + width, y
                + height);
        Line2D.Double right = new Line2D.Double(x + width, y, x + width, y
                + height);
        Line2D.Double top = new Line2D.Double(x + width, y, x, y);

        // first check corners then individual sides
        // checks will be made in the following order: NE,SE,SW,NW,N,E,S,W

        if (src.intersectsLine(top) && src.intersectsLine(right)) {
            return CollisionDirection.NORTHEAST;
        } else if (src.intersectsLine(bottom) && src.intersectsLine(right)) {
            return CollisionDirection.SOUTHEAST;
        } else if (src.intersectsLine(bottom) && src.intersectsLine(left)) {
            return CollisionDirection.SOUTHWEST;
        } else if (src.intersectsLine(top) && src.intersectsLine(left)) {
            return CollisionDirection.NORTHWEST;
        } else if (src.intersectsLine(top)) {
            return CollisionDirection.NORTH;
        } else if (src.intersectsLine(right)) {
            return CollisionDirection.EAST;
        } else if (src.intersectsLine(bottom)) {
            return CollisionDirection.SOUTH;
        } else if (src.intersectsLine(left)) {
            return CollisionDirection.WEST;
        } else if (src.intersects(dest)) {
            return CollisionDirection.INSIDE;
        } else {
            return CollisionDirection.OUTSIDE; // should really never get here
        }

    }
}
