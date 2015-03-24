package engine.events;

public interface EngineArrowKeyListener {
    default public void inputEvent(ArrowKeyEvent ev){
        switch(ev.getKeyEvent()){
        case DOWN:
            keyDown();
            break;
        case LEFT:
            keyLeft();
            break;
        case RIGHT:
            keyRight();
            break;
        case UP:
            keyUp();
            break;
        default:
            break;
        
        }
    }
    
    public void keyLeft();
    
    public void keyRight();
    
    public void keyUp();
    
    public void keyDown();
}
