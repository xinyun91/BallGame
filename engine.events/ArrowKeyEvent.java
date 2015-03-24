package engine.events;

public class ArrowKeyEvent implements InputEvent{
    public enum ArrowKey { UP, DOWN, RIGHT, LEFT }
    public enum ArrowAction {PRESSED, RELEASED, TYPED }
    
    private ArrowKey _key;
    private ArrowAction _action;
    
    public ArrowKeyEvent(ArrowKey key, ArrowAction action){
        _key = key;
        _action = action;
    }
    
    public ArrowKey getKeyEvent(){
        return _key;
    }
    
    public ArrowAction getAction(){
        return _action;
    }
    
    @Override
    public InputEventType getType() {
        // TODO Auto-generated method stub
        return InputEvent.InputEventType.ArrowKeyEvent;
    }

}
