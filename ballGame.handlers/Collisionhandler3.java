package ballGame.handlers;

import engine.Entity;
import engine.collision.CollisionListener;

public class Collisionhandler3 implements CollisionListener {

    public Collisionhandler3() {
        
    }

    @Override
    public void collisionOccurred(Entity e) {
        // TODO Auto-generated method stub
        if (e.getXCoordinate() > 400){
        System.out.println("Blue Wins!");
        }
        else {
            System.out.println("Green Wins!");
        }
        System.exit(0);
    }

}
