package engine;

import java.awt.Rectangle;

import engine.collision.CollisionListener;
import engine.drawing.ICanvas;

public interface Entity {
    
    /**
     * Indicates whether this Entity should be removed at the
     * next update.  This method is defined in the interface
     * using the new Java 8 default implementation feature. 
     * @return true if this Entity is to be removed, false
     * otherwise.
     */
    default public boolean isDone() { return false; }

    /**
     * Should return the bounding rectangle for this Entity.
     * @return this Entity's bounding rectangle.
     */
    public Rectangle getRect();

    /**
     * Should draw a graphical representation of this Entity,
     * if any.
     * This method is called by the draw method of Game, which
     * supplies an appropriate ICanvas object as argument.
     * @param dc
     */
    public void draw(ICanvas dc);

    /**
     * Should update the position of this Entity.
     * This method is called by the updateEntities method 
     * of Game.
     */
    public void update();

    /**
     * Accessor method for the x-coordinate of this Entity
     * @return the x-coordinate of this Entity
     */
    public int getXCoordinate();
    
    /**
     * Accessor method for the y-coordinate of this Entity
     * @return the y-coordinate of this Entity
     */
    public int getYCoordinate();
    
    /**
     * Accessor method for the velocity of this Entity in the x direction
     * @return the x axis velocity of this Entity
     */
    public int getXVelocity();

    /**
     * Accessor method for the velocity of this Entity in the y direction
     * @return the y axis velocity of this Entity
     */
    public int getYVelocity();

    /**
     * Mutator method for the velocity of the Entity in the x direction
     * @param velocity - the new velocity
     */
    public void setXVelocity(int velocity);

    /**
     * Mutator method for the velocity of the Entity in the y direction
     * @param velocity - the new velocity
     */
    public void setYVelocity(int velocity);
    
    /**
     * Add a CollisionListener to this Entity
     * @param cl - the CollisionListener to be added
     */
    public void addCollisionListener(CollisionListener cl);

    /**
     * Remove a CollisionListener from this Entity
     * @param cl - the CollisionListener to be removed
     */
    public void removeCollisionListener(CollisionListener cl);

    /**
     * Notify all listeners that this Entity collided with the
     * Entity e
     * @param cl - the CollisionListener to be added
     */    
    public void fireCollisionOccurred(Entity e);
}
