package lab6.handlers;

import engine.Entity;
import engine.collision.CollisionListener;

public class Collisionhandler2 implements CollisionListener {

    public Collisionhandler2() {

    }
    @Override
    public void collisionOccurred(Entity e) {
        // TODO Auto-generated method stub
        e.setXVelocity(-e.getXVelocity());
    }
    

}
