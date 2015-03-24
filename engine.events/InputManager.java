package engine.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class InputManager implements ActionListener, MouseListener, KeyListener {
    private ArrayList<InputEvent> _queue;

    public InputManager() {
        _queue = new ArrayList<>();
    }

    public InputEvent poll() {
        
        return _queue.remove(0);
    }

    public ArrayList<InputEvent> poll2(){
        ArrayList<InputEvent> tmp = _queue;
        _queue = new ArrayList<>();
        
        return tmp;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();

        switch (i) {
        case KeyEvent.VK_UP:
            _queue.add(new ArrowKeyEvent(ArrowKeyEvent.ArrowKey.UP,
                    ArrowKeyEvent.ArrowAction.PRESSED));
            break;
        case KeyEvent.VK_DOWN:
            _queue.add(new ArrowKeyEvent(ArrowKeyEvent.ArrowKey.DOWN,
                    ArrowKeyEvent.ArrowAction.PRESSED));
            break;
        case KeyEvent.VK_LEFT:
            _queue.add(new ArrowKeyEvent(ArrowKeyEvent.ArrowKey.LEFT,
                    ArrowKeyEvent.ArrowAction.PRESSED));
            break;
        case KeyEvent.VK_RIGHT:
            _queue.add(new ArrowKeyEvent(ArrowKeyEvent.ArrowKey.RIGHT,
                    ArrowKeyEvent.ArrowAction.PRESSED));
            break;
        default:
            break;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();

        switch (i) {
        case KeyEvent.VK_UP:
            _queue.add(new ArrowKeyEvent(ArrowKeyEvent.ArrowKey.UP,
                    ArrowKeyEvent.ArrowAction.RELEASED));
            break;
        case KeyEvent.VK_DOWN:
            _queue.add(new ArrowKeyEvent(ArrowKeyEvent.ArrowKey.DOWN,
                    ArrowKeyEvent.ArrowAction.RELEASED));
            break;
        case KeyEvent.VK_LEFT:
            _queue.add(new ArrowKeyEvent(ArrowKeyEvent.ArrowKey.LEFT,
                    ArrowKeyEvent.ArrowAction.RELEASED));
            break;
        case KeyEvent.VK_RIGHT:
            _queue.add(new ArrowKeyEvent(ArrowKeyEvent.ArrowKey.RIGHT,
                    ArrowKeyEvent.ArrowAction.RELEASED));
            break;
        default:
            break;
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        _queue.add(new EngineKeyEvent(e.getKeyChar()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        _queue.add(new EngineMouseEvent(e.getX(),e.getY()));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}
