package lab6;

import java.awt.Color;
import javax.swing.Timer;
import lab6.handlers.Collisionhandler1;
import lab6.handlers.Collisionhandler2;
import lab6.handlers.Collisionhandler3;
import lab6.handlers.Paddlehandler;
import lab6.handlers.Timerhandler;
import lab6.entities.Ball;
import lab6.entities.Paddle;
import lab6.entities.Wall;
import engine.Game;


public class Pong {
    public Pong() {
    
        Game g = new Game(800, 600);
        g.setBackgroundColor(Color.black);
        
        Wall north = new Wall(0, 0, 800, 10, Color.gray);
        Wall south = new Wall(0, 590, 800, 10, Color.gray);
        Wall east = new Wall(790, 10, 10, 580, Color.blue);
        Wall west = new Wall(0, 10, 10, 580, Color.green);
        g.addEntity(north);
        g.addEntity(south);
        g.addEntity(east);
        g.addEntity(west);
        
        Paddle left = new Paddle(30, 255, 15, 50, 0, 0, Color.blue);
        Paddle right = new Paddle(755, 255, 15, 50, 0, 0,  Color.green);
        g.addEntity(left);
        g.addEntity(right);
        g.addKeyListener(new Paddlehandler(left,-5,'a'));
        g.addKeyListener(new Paddlehandler(left,5,'s'));
        g.addKeyListener(new Paddlehandler(right,-5,'k'));
        g.addKeyListener(new Paddlehandler(right,5,'l'));
        
        Ball ball = new Ball(400, 300, 8, 1, 2, Color.yellow);
        g.addEntity(ball);    
        
        Timer t = new Timer(15, new Timerhandler(g));
        t.start();
        
        north.addCollisionListener(new Collisionhandler1());
        south.addCollisionListener(new Collisionhandler1());
        left.addCollisionListener(new Collisionhandler2());
        right.addCollisionListener(new Collisionhandler2());
        east.addCollisionListener(new Collisionhandler3());
        west.addCollisionListener(new Collisionhandler3());
        
    }

}
