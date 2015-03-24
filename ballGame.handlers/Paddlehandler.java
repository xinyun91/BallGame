package lab6.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import lab6.entities.Paddle;

public class Paddlehandler implements KeyListener {
    
    private Paddle _p; 
    private int _velocity;
    private char _ch;
    
    public Paddlehandler(Paddle p, int velocity, char ch) {

        _p = p;
        _velocity = velocity;
        _ch = ch;

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        e.getKeyChar();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyChar() == _ch ) {
        _p.setYVelocity(_velocity);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyChar() != _ch) {
        _p.setYVelocity(0);
        }
    }

}
