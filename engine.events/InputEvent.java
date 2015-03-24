package engine.events;

public interface InputEvent {
    public enum InputEventType {KeyEvent, ArrowKeyEvent, MouseEvent, ActionEvent}
    
    public InputEventType getType();
}
