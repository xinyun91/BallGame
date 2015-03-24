package engine.events;

public class EngineMouseEvent implements InputEvent{

    private int _x, _y;
    
    public EngineMouseEvent(int x, int y) {
        // TODO Auto-generated constructor stub
        _x = x;
        _y = y;
    }

    public int getX(){
        return _x;
    }
    
    public int getY(){
        return _y;
    }
    
    @Override
    public InputEventType getType() {
        // TODO Auto-generated method stub
        return InputEvent.InputEventType.MouseEvent;
    }

}
