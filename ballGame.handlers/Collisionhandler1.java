package ballGame.handlers;

import engine.Entity;
import engine.collision.CollisionListener;

public class Collisionhandler1 implements CollisionListener {
    
    public Collisionhandler1() {

    }

    @Override
    public void collisionOccurred(Entity e) {
        // TODO Auto-generated method stubs    
        //if north or south wall hits with the entity e, e negates its y velocity
        e.setYVelocity(-e.getYVelocity());
    
    }
}
