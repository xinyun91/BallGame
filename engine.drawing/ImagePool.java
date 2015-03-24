package engine.drawing;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImagePool {
    private static ImagePool _ip = null;

    private HashMap<String, VolatileImage> _images;

    private ImagePool() {
        _images = new HashMap<>();
    }

    public static ImagePool getInstace() {
        if (_ip == null) {
            _ip = new ImagePool();
        }

        return _ip;
    }

    public void registerImage(String key, VolatileImage img) {
        if (img == null) {
            throw new NullPointerException();
        }

        _images.put(key, img);
    }

    public boolean registerImage(URL url) {
        if (_images.containsKey(url.toString())) {
            return true;
        } else {
            try {

                BufferedImage tmp = ImageIO.read(url);

                if (tmp == null) {
                    throw new NoSuchFileException("Could not find the file!");
                }

                VolatileImage dst = GraphicsEnvironment
                        .getLocalGraphicsEnvironment()
                        .getDefaultScreenDevice()
                        .getDefaultConfiguration()
                        .createCompatibleVolatileImage(tmp.getWidth(),
                                tmp.getHeight(), Transparency.BITMASK);

                Graphics2D g2d = (Graphics2D) dst.getGraphics();
                g2d.setComposite(AlphaComposite.Src);
                g2d.drawImage(tmp, 0, 0, null);
                g2d.dispose();

                _images.put(url.toString(), dst);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return false;
            }

            return true;
        }

    }

    public boolean registerImage(String file) {
        if (_images.containsKey(file)) {
            return true;
        } else {
            try {

                BufferedImage tmp = ImageIO.read(new File(file));

                if (tmp == null) {
                    throw new NoSuchFileException("Could not find the file!");
                }

                VolatileImage dst = GraphicsEnvironment
                        .getLocalGraphicsEnvironment()
                        .getDefaultScreenDevice()
                        .getDefaultConfiguration()
                        .createCompatibleVolatileImage(tmp.getWidth(),
                                tmp.getHeight(), Transparency.BITMASK);

                Graphics2D g2d = (Graphics2D) dst.getGraphics();
                g2d.setComposite(AlphaComposite.Src);
                g2d.drawImage(tmp, 0, 0, null);
                g2d.dispose();

                _images.put(file, dst);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return false;
            }

            return true;
        }
    }

    public boolean hasImage(String s) {
        return _images.containsKey(s);
    }

    public VolatileImage getImage(String key) {
        return _images.get(key);
    }

    public VolatileImage getImage(URL key) {
        return _images.get(key.toString());
    }

    public void removeImage(String key) {
        _images.remove(key);
    }
}
