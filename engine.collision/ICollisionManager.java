package engine.collision;

import engine.Entity;

public interface ICollisionManager {
    public void checkCollisions();
    public void addCollisionObject(Entity cl);
    public void removeCollisionObject(Entity cl);
}
