package imagelab;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Graphics frame used for dynamic display of an image.
 * For use as a singleton, ignore the constructor and use getDynDisplay()
 * to retrieve the singleton object each time.
 * @author Dr. Jody Paul
 * @version 1.1.1
 */
public class DynDisplayImage extends DisplayImage   {
    /** Serialization version. */
    private static final long serialVersionUID = 11L;

    /** Image for this frame. */
    Image img;
    /** Display panel for this frame. */
    private DynaPanel pane;
    /** Singleton object. */
    private DynDisplayImage singleton;
    
    /**
     * This constructor takes the image object to display
     * and a string to use as the title of the window.
     * @param imp the image object to display
     * @param title the window title
     */
    public DynDisplayImage(ImgProvider imp, String title) {
        super(imp, title);
    }
    
    /**
     * This constructor takes the image object to display,
     * a string to use as the title of the window, and a
     * third parameter that indicates that the display is
     * to be done a line at a time.
     * @param imp the image object to display
     * @param title the window title
     * @param slow if present, indicates slow display
     */
    public DynDisplayImage(ImgProvider imp, String title, boolean slow) {
        super(imp, title, slow);
    }

    /**
     * Retrieve the singleton object.
     * @return the singleton DynDisplayImage object
     */
    public DynDisplayImage getDynDisplay() {
        if (singleton == null) {
            singleton = new DynDisplayImage((ImgProvider)null, "DynDisplayImage");
        };
        return singleton;
    }
    
    /**
     * Change the displayed image in this object.
     * @param imp the image object to display
     */
    public void changeImage(ImgProvider imp, String title) {
        setTitle(title);
        improvider  = imp;
        img         = imp.getImage();
        pane        = new DynaPanel(imp);
        getContentPane().add(pane,"Center");
        int width;
        int height;
        while (-1 == (width = img.getWidth(null))) {
            //System.out.println("DynDisplayImage:constructor - first while");
            try {
                    Thread.sleep(50);
                } catch (Exception e){}
        }
    
        while (-1 == (height = img.getHeight(null))) {
            //System.out.println("DynDisplayImage:constructor - second while");
            try {
                    Thread.sleep(50);
                } catch (Exception e){}
        }
        setBounds(0,0,width,height+TITLE_HEIGHT);
        repaint();
        setVisible(true);
    }

}
