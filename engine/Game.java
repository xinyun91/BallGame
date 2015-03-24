package engine;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import engine.collision.CollisionManager;
import engine.collision.ICollisionManager;
import engine.drawing.BufferedDrawingCanvas;
import engine.events.ArrowKeyEvent;
import engine.events.EngineArrowKeyListener;
import engine.events.EngineKeyEvent;
import engine.events.EngineKeyListener;
import engine.events.EngineMouseEvent;
import engine.events.EngineMouseListener;
import engine.events.InputEvent;
import engine.events.InputManager;
import engine.util.EntitySort;
import engine.util.EntitySort.SortOptions;

public class Game {

    private class InputThread implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub

            while (_running) {

                ArrayList<InputEvent> _ie = _inputManager.poll2();

                for (int i2 = 0; i2 < _ie.size(); i2++) {
                    InputEvent ie = _ie.get(i2);

                    if (ie != null) {
                        switch (ie.getType()) {
                        case ActionEvent:
                            // unimplemented
                            break;
                        case ArrowKeyEvent:
                            for (int i = 0; i < _arrowListener.size(); i++) {
                                _arrowListener.get(i).inputEvent(
                                        (ArrowKeyEvent) ie);
                            }

                            break;
                        case KeyEvent:
                            for (int i = 0; i < _keyListeners.size(); i++) {
                                _keyListeners.get(i).inputEvent(
                                        (EngineKeyEvent) ie);
                            }
                            break;
                        case MouseEvent:
                            EngineMouseEvent eme = (EngineMouseEvent) ie;

                            for (int i = 0; i < _mouseListeners.size(); i++) {
                                synchronized (_layers) {
                                    if (_mouseListeners.get(i).getBoundingBox()
                                            .contains(eme.getX(), eme.getY())) {
                                        _mouseListeners.get(i).onClick(eme);
                                    }
                                }

                            }

                            break;
                        default:
                            break;

                        }
                    }
                }

                try {
                    synchronized (lock) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private ArrayList<EngineArrowKeyListener> _arrowListener;
    private ICollisionManager _collisionManager;
    private BufferedDrawingCanvas _dc;

    // keeps track of where in the struct are entities for quick removal
    private HashMap<Entity, Integer> _entityLocation;

    private Thread _inputThread;

    private InputManager _inputManager;

    private JFrame _jf;

    private ArrayList<EngineKeyListener> _keyListeners;

    private ArrayList<ArrayList<Entity>> _layers;

    private ArrayList<EngineMouseListener> _mouseListeners;

    private Object lock;

    private volatile boolean _running;

//    private int entities;
    
    private Color _backgroundColor;

    public Game(int displayWidth, int displayHeight) {
        this(displayWidth, displayHeight, 60, false);
    }

    public Game(int displayWidth, int displayHeight, int fps) {
        this(displayWidth, displayHeight, fps, false);
    }

    Game(int displayWidth, int displayHeight, int fps, boolean debug) {

        // set up the drawing canvas
        _jf = new JFrame();

        _backgroundColor = Color.BLACK;
        
        _jf.setOpacity(1.0f);

        _dc = new BufferedDrawingCanvas(displayWidth, displayHeight, this);

        _dc.setFocusable(true);

        _jf.add(_dc);

        _jf.setVisible(true);

        _jf.pack();

        _jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // init the collision manager
        // _collisionManager = new CollisionManager();
        _collisionManager = new CollisionManager();
        // add the input manager
        _inputManager = new InputManager();
        _dc.addKeyListener(_inputManager);
        _dc.addMouseListener(_inputManager);

        // init the data structures

        // holds all of the layers
        _layers = new ArrayList<ArrayList<Entity>>();

        // start off with 1 layer (0)
        _layers.add(new ArrayList<Entity>());

        // stores where entities are (which layer
        _entityLocation = new HashMap<>();

        _keyListeners = new ArrayList<>();
        _mouseListeners = new ArrayList<>();
        _arrowListener = new ArrayList<>();

        _running = true;
        // make the game Thread

        lock = new Object();

        _inputThread = new Thread(new InputThread());
        _inputThread.start();
    }
    
    public void addKeyListener(KeyListener listener) {
        _dc.addKeyListener(listener);
    }

    public void removeKeyListener(KeyListener listener) {
        _dc.removeKeyListener(listener);
    }

    public void getInput() {
        synchronized (lock) {
            lock.notify();
        }
    }

    public void updateEntities() {
        ArrayList<Entity> finishedEntities = new ArrayList<>();

        synchronized (_layers) {
            for (int i = 0; i < _layers.size(); i++) {
                for (int j = 0; j < _layers.get(i).size(); j++) {
                    Entity e = _layers.get(i).get(j);

                    if (e.isDone()) {
                        finishedEntities.add(e);
                    }
                }
            }

            // remove all done entities
            for (Entity e : finishedEntities) {
                removeEntity(e);
            }

            for (int i = 0; i < _layers.size(); i++) {
                for (int j = 0; j < _layers.get(i).size(); j++) {

                    _layers.get(i).get(j).update();
                }
            }
        }

    }

    public void checkCollision() {
        synchronized (_collisionManager) {
            _collisionManager.checkCollisions();
        }
    }

    public void draw() {
        _dc.fill(_backgroundColor);

        do {
            synchronized (_layers) {
                for (int i = 0; i < _layers.size(); i++) {
                    // sort the entities so that they draw properly
                    // that is so if an entity has a higher y, it will be
                    // draw first

                    EntitySort.sortEntities(_layers.get(i), SortOptions.Y_AXIS);

                    for (int j = 0; j < _layers.get(i).size(); j++) {
                        _layers.get(i).get(j).draw(_dc);
                    }

                }
            }
        } while (_dc.contentsLost());

//        _dc.flip();

        _dc.repaint();

        Toolkit.getDefaultToolkit().sync();
    }

    public void addCollisionObject(Entity cl) {
        synchronized (_collisionManager) {
            _collisionManager.addCollisionObject(cl);
        }
    }

    public void addEngineArrowKeyListener(EngineArrowKeyListener b) {
        _arrowListener.add(b);
    }

    public void addEngineKeyListener(EngineKeyListener b) {
        _keyListeners.add(b);
    }

    public void addEngineMouseListener(EngineMouseListener eml) {
        _mouseListeners.add(eml);
    }

    public void addEntity(Entity e) {
        addEntity(e, 0);
    }

    /**
     * Places an entity in the nth layer. If the layer does not exist it will
     * add it.
     * 
     * @param e
     *            the entity to add to the game
     * @param layer
     *            the layer to draw it on
     */
    public void addEntity(Entity e, Integer layer) {
        addCollisionObject(e);
        synchronized (_layers) {
            if (!_entityLocation.containsKey(e)) {

//                entities++;

                _entityLocation.put(e, layer);

                if (layer >= _layers.size()) {
                    for (int i = _layers.size(); i < layer + 1; i++) {
                        _layers.add(new ArrayList<Entity>());
                    }

                }

                _layers.get(layer).add(e);
            }
        }
    }

    /**
     * 
     * @return all the list containing all of the entities. Note, any access to
     *         this should be synchronized!!!
     */
    public ArrayList<ArrayList<Entity>> getEntities() {
        return _layers;
    }

    public void quit() {
        _running = false;

        _jf.setVisible(false);
        _jf.dispose();
    }

    void removeCollisionObject(Entity cl) {
        synchronized (_layers) {
            _collisionManager.removeCollisionObject(cl);
        }
    }

    public void removeEngineArrowKeyListener(EngineArrowKeyListener b) {
        _arrowListener.remove(b);
    }

    public void removeEngineKeyListener(EngineKeyListener b) {
        _keyListeners.remove(b);
    }

    public void removeEngineMouseListener(EngineMouseListener eml) {
        _mouseListeners.remove(eml);
    }

    public void removeEntity(Entity e) {
        synchronized (_layers) {
//            entities--;
            _layers.get(_entityLocation.get(e)).remove(e);
        }
    }
    
    public void setBackgroundColor(Color c){
        _backgroundColor = c;
    }

    public Color getBackgroundColor() {
        // TODO Auto-generated method stub
        return _backgroundColor;
    }
}
