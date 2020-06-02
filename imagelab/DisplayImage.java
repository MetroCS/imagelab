package imagelab;

import javax.swing.JMenuBar;
import java.awt.Image;
import java.awt.Color;
import java.awt.image.MemoryImageSource;

/**
 * Graphics frame used to display an image.
 *
 * @author Dr. Aaron Gordon
 * @author Dr. Jody Paul
 * @version 1.1
 */
public class DisplayImage extends ILFrame {
    /** Serialization version. */
    private static final long serialVersionUID = 11L;
    /** The image of this frame. */
    private Image img;
    /** The display panel of this frame. */
    private DisPanel pane;

    /** Initial x-coordinate for window placement. */
    private static final int XINIT = 10;
    /** Initial y-coordinate for window placement. */
    private static final int YINIT = 20;
    /** x-coordinate delta for subsequent window. */
    private static final int XDELTA = 25;
    /** y-coordinate delta for subsequent window. */
    private static final int YDELTA = 25;
    /** Maximum x-coordinate before wrapping. */
    private static final int XMAX = 800;
    /** Maximum y-coordinate before wrapping. */
    private static final int YMAX = 600;
    /** Integer value of red in the DisplayImage background. */
    private static final int RED_VALUE = 255;
    /** Integer value of green in the DisplayImage background. */
    private static final int GREEN_VALUE = 255;
    /** Integer value of blue in the DisplayImage background. */
    private static final int BLUE_VALUE =255;
    /** DisplayImage sleep timing. */
    private static final int SLEEP_TIME = 50;
    /** x-coordinate for next window. */
    private static int xspot = XMAX;
    /** y-coordinate for next window. */
    private static int yspot = YMAX;

    /**
     * This constructor takes the image object to display
     * and a string to use as the title of the window.
     *
     * @param imp   the image object to display
     * @param title the window title
     */
    public DisplayImage(final ImgProvider imp, final String title) {
        this(imp, title, false);
    } //constructor

    /**
     * This constructor takes the image object to display,
     * a string to use as the title of the window, and a
     * third parameter that indicates that the display is
     * to be done a line at a time.
     *
     * @param imp   the image object to display
     * @param title the window title
     * @param slow  if present, indicates slow display
     */
    public DisplayImage(
        final ImgProvider imp, final String title, final boolean slow) {
        if (imp == null) {
            System.out.println("\n\n***DisplayImage: imp is null!!! ***\n\n");
        }
        setTitle(title);
        setUndecorated(false);
        setBackground(new Color(RED_VALUE, GREEN_VALUE, BLUE_VALUE));
        improvider = imp;
        int[] pix = imp.pix;
        int[] showpix = pix;
        int pixheight = imp.pixheight;
        int pixwidth = imp.pixwidth;
        img = getToolkit().createImage(
                new MemoryImageSource(
                    pixwidth, pixheight, showpix, 0, pixwidth));
        pane = new DisPanel(img);
        getContentPane().add(pane, "Center");
        int width;
        int height;
        while (-1 == (width = img.getWidth(null))) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (Exception e) {
            }
        }
        while (-1 == (height = img.getHeight(null))) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (Exception e) {
            }
        }
        // Cascade subsequent windows
        xspot = ((xspot + width + XDELTA) <= XMAX) ? xspot + XDELTA : XINIT;
        yspot = ((yspot + height + YDELTA + EXTRA_HEIGHT) <= YMAX) ? yspot + YDELTA : YINIT;
        setBounds(xspot, yspot, width, height + EXTRA_HEIGHT);
        WindowCloser wc = new WindowCloser(this);
        this.addWindowListener(wc);
        JMenuBar myMenuBar = new JMenuBar();
        myMenuBar.add(ImageLab.newFileMenu(this));
        myMenuBar.add(ImageLab.newFilterMenu());
        setJMenuBar(myMenuBar);
        setVisible(true);
    }

} //class
