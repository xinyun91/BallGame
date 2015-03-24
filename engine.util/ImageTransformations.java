package engine.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.HashMap;

public class ImageTransformations {
    // public static
    private static HashMap<VolatileImage, VolatileImage> _cache;

    public static Image flipHorizontal(VolatileImage img) {
        if(img == null){
            throw new NullPointerException("Cannot perform an image transformation on a null image.");
        }
        
        if (_cache == null) {
            _cache = new HashMap<>();
        }

        if (img != null && _cache.containsKey(img)) {
            return _cache.get(img);
        }

        Graphics2D g;

        int w = img.getWidth();
        int h = img.getHeight();

        // draw the vol to a buff img to flip it around
        BufferedImage buff = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDefaultConfiguration()
                .createCompatibleImage(w, h, img.getTransparency());

        g = buff.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        // Flip the image horizontally
        AffineTransform tx;
        AffineTransformOp op;

        tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-w, 0);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        buff = op.filter(buff, null);

        // draw the buff to the original image
        VolatileImage vol = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDefaultConfiguration()
                .createCompatibleVolatileImage(w, h, buff.getTransparency());

        g = vol.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(buff, 0, 0, null);
        g.dispose();

        _cache.put(img, vol);

        return vol;
    }
}
