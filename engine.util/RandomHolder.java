package engine.util;

import java.util.Random;

public abstract class RandomHolder {
    private static Random _instance;
    
    public static Random getInstance(){
        return _instance == null ? _instance = new Random() : _instance;
    }
}
