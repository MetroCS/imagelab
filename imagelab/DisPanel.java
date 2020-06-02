package imagelab;

import java.awt.Panel;
import java.awt.Image;
import java.awt.Graphics;

/**
 * Used by the ImageLab package to display an image.
 *
 * @author Aaron Gordon
 * @author Jody Paul
 * @version 1.1
 */
public class DisPanel extends Panel implements ILPanel {
    /** Serialization version. */
    private static final long serialVersionUID = 11L;

    /** This panel's image. */
    private Image img;

    /**
     * Constructor that takes an Image object to display.
     *
     * @param im the Image object
     */
    public DisPanel(final Image im) {
        img = im;
    }

    /**
     * Draw the Image onto the Graphic.
     *
     * @param g the Graphic
     */
    public void paint(final Graphics g) {
        g.drawImage(img, 0, 0, this);
    }

    /**
     * Change the Image associated with this Panel.
     *
     * @param im the new Image object
     */
    public void newImage(final Image im) {
        img = im;
    }
}
