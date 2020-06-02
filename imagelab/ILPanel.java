package imagelab;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Panel used to display images.
 *
 * @author Dr. Jody Paul
 * @version 1.1
 */
public interface ILPanel {
    /**
     * Renders the panel in a context.
     *
     * @param g the graphics context
     */
    void paint(Graphics g);

    /**
     * Change the Image associated with this Panel.
     *
     * @param im the new Image object
     */
    void newImage(Image im);
}
