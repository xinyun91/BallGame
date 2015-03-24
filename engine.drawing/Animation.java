package engine.drawing;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.net.URL;
import java.util.NoSuchElementException;

import engine.errors.InvalidFrameException;

public class Animation {
    private ImagePool _imgPool;

    private int _numAcross;

    private int _frameHeight, _frameWidth;

    private int _currentFrame;

    private int _maxFrames;

    private String _imgKey;

    private long timeBetweenFrames;

    private long lastFrame;

    private boolean _loop;

    public Animation(String file, int numAcross, int numDown) {
        _imgKey = file;

        _imgPool = ImagePool.getInstace();

        _numAcross = numAcross;

        _loop = true;

        init(file, numAcross, numDown);
    }

    public Animation(URL url, int numAcross, int numDown) {
        _imgKey = url.toString();

        _imgPool = ImagePool.getInstace();

        _numAcross = numAcross;

        _loop = true;

        init(url, numAcross, numDown);
    }

    private void init(URL url, int across, int down) {
        boolean needToCache = !_imgPool.hasImage(url.toString());

        if (needToCache) {
            _imgPool.registerImage(url);
        }

        VolatileImage tmp = _imgPool.getImage(url.toString());

        _frameWidth = tmp.getWidth() / across;
        _frameHeight = tmp.getHeight() / down;

        _maxFrames = across * down;

        _currentFrame = 0;
        lastFrame = 0;
        timeBetweenFrames = 0;

        // prepare to cache

        // copy vol. image into a buffered image
        // this is going to be an expensive process but it only needs to
        // be done once
        if (needToCache) {
            BufferedImage dst = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration()
                    .createCompatibleImage(tmp.getWidth(), tmp.getHeight(),
                            tmp.getTransparency());

            Graphics2D g2d = (Graphics2D) dst.getGraphics();
            g2d.setComposite(AlphaComposite.Src);
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            cacheSubImages(dst);
        }

    }

    private void init(String img, int across, int down) {
        boolean needToCache = !_imgPool.hasImage(img);

        if (needToCache) {
            _imgPool.registerImage(img);
        }

        VolatileImage tmp = _imgPool.getImage(img);

        _frameWidth = tmp.getWidth() / across;
        _frameHeight = tmp.getHeight() / down;

        _maxFrames = across * down;

        _currentFrame = 0;
        lastFrame = 0;
        timeBetweenFrames = 0;

        // prepare to cache

        // copy vol. image into a buffered image
        // this is going to be an expensive process but it only needs to
        // be done once
        if (needToCache) {
            BufferedImage dst = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration()
                    .createCompatibleImage(tmp.getWidth(), tmp.getHeight(),
                            tmp.getTransparency());

            Graphics2D g2d = (Graphics2D) dst.getGraphics();
            g2d.setComposite(AlphaComposite.Src);
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            cacheSubImages(dst);
        }
    }

    private void cacheSubImages(BufferedImage tmp) {
        for (int i = 0; i < _maxFrames; i++) {
            _imgPool.registerImage(_imgKey + i, loadVolatileAnimationImage(tmp));
            skipFrame();
        }
    }

    public Animation setMinTimeBetweenFrames(long millis) {
        timeBetweenFrames = millis;
        return this;
    }

    public Animation setMaxFrames(int mf) {
        _maxFrames = mf;
        return this;
    }

    public Animation setLoop(boolean loop) {
        _loop = loop;
        return this;
    }

    public VolatileImage getNextAnimation() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        if ((System.currentTimeMillis() - lastFrame) > timeBetweenFrames) {
            skipFrame();
            lastFrame = System.currentTimeMillis();
        }

        return _imgPool.getImage(_imgKey + _currentFrame);
    }

    public boolean hasNext() {
        return _currentFrame < _maxFrames - 1 || _loop;
    }

    private VolatileImage loadVolatileAnimationImage(BufferedImage img) {
        int row = 0, col;

        if (_currentFrame == 0) {
            row = 0;
            col = 0;
        } else {
            col = (_currentFrame % _numAcross);

            row = (_currentFrame / _numAcross);
        }

        // get the subimage and then prepare to transfer it to a volatile image
        img = img.getSubimage(col * _frameWidth, row * _frameHeight,
                _frameWidth, _frameHeight); // img.getSubimage(x, y, w, h)

        VolatileImage dst = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .createCompatibleVolatileImage(_frameWidth, _frameHeight,
                        img.getTransparency());

        Graphics2D g2d = (Graphics2D) dst.createGraphics();
        g2d.setComposite(AlphaComposite.Src);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return dst;

    }

    public VolatileImage getCurrentAnimation() {
        return _imgPool.getImage(_imgKey + _currentFrame);
    }

    public VolatileImage getPreviousAnimation() {
        previousFrame();

        return getCurrentAnimation();
    }

    public void skipFrame() {
        _currentFrame = (_currentFrame + 1 < (_maxFrames)) ? _currentFrame + 1
                : 0;
    }

    public void previousFrame() {
        if (_currentFrame > 0) {
            _currentFrame--;
        } else {
            _currentFrame = _maxFrames - 1;
        }
    }

    public void setAnimationFrame(int set) {
        if (set < _maxFrames && set >= 0) {
            _currentFrame = set;
        } else {
            throw new InvalidFrameException();
        }
    }

    public int getCurrentFrame() {
        return _currentFrame;
    }

    public int getWidth() {
        return _frameWidth;
    }

    public int getHeight() {
        return _frameHeight;
    }
}
