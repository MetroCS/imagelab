package imagelab;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;

/**
 * DynaPanel is a class to allow for dynamically displaying images.
 * For use as a singleton, ignore the constructor and use getDynPan()
 * to retrieve the singleton object each time.
 *
 * @author Dr. Jody Paul
 * @version 1.1
 */
public class DynaPanel extends JPanel implements ILPanel {
    /** Serialization version. */
    private static final long serialVersionUID = 11L;

    /** Default height of the title text. */
    public static final int TITLE_HEIGHT = 40;
    /** Default background color. */
    public static final Color FRAME_BG_COLOR = new Color(255, 0, 0, 0);

    /** Singleton object. */
    private static DynaPanel dynPanSingleton;

    /**
     * Factory method to get the canvas singleton object.
     *
     * @return the DynPanSingleton Object
     */
    public static DynaPanel getDynPan() {
        if (dynPanSingleton == null) {
            dynPanSingleton = new DynaPanel((Image) null);
        }
        dynPanSingleton.setVisible(true);
        return dynPanSingleton;
    }

    /** RGB color for panel background. */
    private Color backgroundColour;
    /** Panel image to be displayed. */
    private Image panelImage;
    /** Component for graphic to be drawn on. */
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
     *
     * @param img image to be displayed
     */
    public DynaPanel(final Image img) {
        super(true);
        panelImage = img;
        System.out.println("DynaPanel: panelImage = " + panelImage);
    }

    /**
     * Create a DynaPanel.
     *
     * @param imp image to be displayed
     */
    public DynaPanel(final ImgProvider imp) {
        super(true);
        int width = imp.getWidth();
        int height = imp.getHeight();
        panelImage = imp.getImage();
        System.out.println("DynaPanel: panelImage = " + panelImage);
    }

    /**
     * Draw a given image onto the canvas.
     *
     * @param image object to be drawn on the canvas
     */
    public void newImage(final Image image) {
        System.out.println("DynaPanel:draw(i): image = " + image);
        panelImage = image;
    }

    /**
     * Draw a given image onto the panel and change the frame title.
     *
     * @param imp the image object to be drawn on the canvas
     */
    public void draw(final ImgProvider imp) {
        newImage(imp.getImage());
    }


    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     *
     * @param milliseconds the number of milliseconds to delay
     */
    public void wait(final int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // ignoring exception at the moment
        }
    }

    /**
     * Dynamic (re-)paint.
     *
     * @param g Component object being used to draw the image
     */
    public void paint(final Graphics g) {
        g.drawImage(panelImage, 0, 0, this);
    }
}
