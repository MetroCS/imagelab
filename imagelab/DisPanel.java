package imagelab;
import imagelab.*;
import java.awt.*;
import java.awt.image.*;
/** 
 * Used by the ImageLab package to display an image.
 * @author Aaron Gordon
 * @author Jody Paul
 * @version 1.1
 */
public class DisPanel extends Panel implements ILPanel {
    /** Serialization version. */
    private static final long serialVersionUID = 11L;
    
    /** This panel's image. */
    Image img;
    
    /**
     * Constructor that takes an Image object to display.
     * @param im the Image object
     */
    public DisPanel(Image im) {
        img = im;
    }
    
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, this);
    }

    /**
     * Change the Image associated with this Panel.
     * @param im the new Image object
     */
    public void newImage(Image im) {
        img = im;
    }   
}