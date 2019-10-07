package imagelab;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * DynaPanel is a class to allow for dynamically displaying images.
 * For use as a singleton, ignore the constructor and use getDynPan()
 * to retrieve the singleton object each time.
 * @author Dr. Jody Paul
 * @version 1.1
 */
public class DynaPanel extends JPanel implements ILPanel {
    /** Serialization version. */
    private static final long serialVersionUID = 11L;

    /** Default height of the title text. */
    public static final int TITLE_HEIGHT = 40;
    /** Default background color. */
    public static final Color FRAME_BG_COLOR = new Color(255,0,0,0);
    
    /** Singleton object. */
    private static DynaPanel dynPanSingleton;

    /**
     * Factory method to get the canvas singleton object.
     */
    public static DynaPanel getDynPan() {
        if(dynPanSingleton == null) {
            dynPanSingleton = new DynaPanel((Image)null);
        }
        dynPanSingleton.setVisible(true);
        return dynPanSingleton;
    }

    // Instance Variables
    private Color backgroundColour;
    private Image panelImage;
    private Graphics graphic;
    
    /**
     * Create a default DynaPanel.
     */
    public DynaPanel() {
        super(true);
        panelImage = null;
        System.out.println("DynaPanel: default panelImage = " + panelImage);
   }

    /**
     * Create a DynaPanel.
     * @param img image to be displayed
     */
    public DynaPanel(Image img) {
        super(true);
        panelImage = img;
        System.out.println("DynaPanel: panelImage = " + panelImage);
    }

    /**
     * Create a DynaPanel.
     * @param imp image to be displayed
     */
    public DynaPanel(ImgProvider imp) {
        super(true);
        int width  = imp.getWidth();
        int height = imp.getHeight();
        panelImage = imp.getImage();
        System.out.println("DynaPanel: panelImage = " + panelImage);
    }

    /**
     * Draw a given image onto the canvas.
     * @param image the image object to be drawn on the canvas
     */
    public void newImage(Image image) {
        System.out.println("DynaPanel:draw(i): image = " + image);
        panelImage = image;
    }

    /**
     * Draw a given image onto the panel and change the frame title.
     * @param image the image object to be drawn on the canvas
     * @param title the text to use as the frame title
     */
    public void draw(ImgProvider imp) {
        newImage(imp.getImage());
    }
  

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number 
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } 
        catch (Exception e) {
            // ignoring exception at the moment
        }
    }

    /**
     * Dynamic (re-)paint.
     */
    public void paint(Graphics g) {
        g.drawImage(panelImage, 0, 0, this);
    }
}
