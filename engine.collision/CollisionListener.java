package engine.collision;

import engine.Entity;

public interface CollisionListener {
    void collisionOccurred(Entity e);
}
