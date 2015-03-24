package engine.events;

public class EngineKeyEvent implements InputEvent{
    private char _ch;
    
    public EngineKeyEvent(char c){
        _ch = c;
    }
    
    @Override
    public InputEventType getType() {
        // TODO Auto-generated method stub
        return InputEvent.InputEventType.KeyEvent;
    }
    
    public char getChar(){
        return _ch;
    }

}
