package imagelab;

import imagelab.*;
import sound.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.util.Iterator;
import javax.swing.*;
import java.io.*;
/**
 * ImgProvider is responsible for managing a single image
 * (loading, filtration, rendering, etc.).
 * @author Dr. Aaron Gordon
 * @author Dr. Jody Paul
 * @version 1.1
 */
public class ImgProvider extends JComponent {
    /** Serialization version. */
    private static final long serialVersionUID = 11L;
    static Thread playThread;
    static boolean  all;
    /** true if this ImgProvider currently holds an image; false otherwise. */
    boolean         isLoaded;
    /** Image height in pixels. */
    int             pixheight;
    /** Image width in pixels. */
    int             pixwidth;
    /** The raw image. */
    Image           img;
    /** Holders for the color and alpha components of the image. */
    short [][]      red, green, blue, alpha;
    /** To retrieve pixels from the image. */
    PixelGrabber    grab;
    /** Holder for the pixels from the image. */
    int []          pix;
    /** X-axis increment used for trimming the image. */
    protected int   xinc= 0;
    /** Y-axis increment used for trimming the image. */
    protected int   yinc = 0;
    /** Holder for the filename of the file that contains the image. */
    protected String imgName;
    /** Used for assigning unique IDs to ImgProviders. Incremented when used. */
    protected static int count = 0;
    /** Identification used to distinguish one ImgProvider from another. */
    protected int id;
    protected ImageLab lab;

    /** No-argument constructor.  Sets name to empty string. */
    public ImgProvider() {
        this("");
    } // constructor

    /**
     * Constructor that accepts a filename.
     * @param name The name of the file containing the image
     */
    public ImgProvider(String name) {
        imgName = name;
        isLoaded = false;
        id = ++count;
    } // constructor

    /** 
     * Retrieve this ImgProvider's unique id.
     * For ImageLab's internal use.
     */
    public int getid() {
        return id;
    }//getid

    /**
     * Create a B&W image object based on the parameter.
     * Uses the instance variable pix as destination.
     * @param img 2D array of black-and-white pixel values (0-255)
     */
    public void setBWImage(short [][] img) {
        int spot = 0;  //index into pix
        int tmp;
        int alpha = 255;
        pixheight = img.length;
        pixwidth  = img[0].length;
        pix = new int[pixheight * pixwidth];
        for (int row = 0; row<pixheight; row++) {
            for (int col=0; col< pixwidth; col++) {
                tmp = alpha;
                tmp = tmp << 8;
                tmp += img[row][col];
                tmp = tmp << 8;
                tmp += img[row][col];
                tmp = tmp << 8;
                tmp += img[row][col];
                pix[spot++] = tmp;
            }//for col
        }//for row
        separateColors();
        isLoaded = true;
    }//setBWImage

    /** 
     * Return the image in black and white.
     * @return 2D array of pixel grey-values (0 to 255)
     */
    public short[][] getBWImage() {
        //read in image into pix[]
        if (!isLoaded) readinImage();
        toBW();         //convert it to black and white
    
        //copy from int []pix to short [][]b and filter outliers
        short [][] b = new short[pixheight][pixwidth];
        int spot = 0;
        short tmp;
        for (int r = 0; r<pixheight; r++) {
            for (int c=0; c<pixwidth; c++) {
                tmp = (short) (pix[spot++] & 255);
                b[r][c] = tmp;
            }//for c
        }//for r
        //showImage(b,"B & W with compressed range of values");
        return b;
    }//getBWImage

    /** Read in the image. */
    public void readinImage() {
        img = getToolkit().getImage(imgName);
        if (img==null) System.err.println("\n\n**ImgProvider: getImage: img is null!!! ***\n\n");
        int width   = img.getWidth(null)  - xinc;
        int height  = img.getHeight(null) - yinc;
        grab        = new PixelGrabber(img, xinc, yinc, width, height, true); //forceRGB=true
        try {
            grab.grabPixels();
        } catch(Exception e) {
            System.err.println("ImgProvider:getBWImage: pixel grabbing failed!!");
            return;
            //System.exit(-1);
        }
        pix = (int []) grab.getPixels();
        pixwidth = img.getWidth(null)-xinc;
        pixheight = img.getHeight(null)-yinc;
        isLoaded = true;
        separateColors();
        //System.out.println("pix width and height are: " + pixwidth + ",  " + pixheight);
        if (all) showPix("Original in Color");      //display original picture
        try { Thread.sleep(300);}catch(Exception e){}
    }//readinImage
    
    
    /**
     * Cut out x columns and y rows from the NW corner of the image.
     * @param x the number of columns to remove
     * @param y the number of rows to remove
     */
    public void setTrim(int x, int y) { 
        xinc = x;
        yinc = y;
    }//setTrim
    
    /** Convert from color to gray scale (black and white). */
    private void toBW() {
        int  alpha, red, green, blue, black;
    
        for (int i=0; i<pix.length; i++) {
            int num = pix[i];
            blue = num & 255;
            num = num >> 8;
            green = num & 255;
            num = num >> 8;
            red = num & 255;
            num = num >> 8;
            alpha = num & 255;
            black = (red + green + blue) / 3;
            num = alpha;
            num = (num << 8) + black;
            num = (num << 8) + black;
            num = (num << 8) + black;
            pix[i] = num;
        }
        if (all) showPix("Black and White");
        try { Thread.sleep(300);}catch(Exception e){}
    }//toBW
    
    
    /** Alias for showPix. (Syntactic sugar) */
    public void showImage(String name) {
        showPix(name);
    }//showImage
    
    /**
     * Display this image in a window.
     * @param name The title for the window.
     */
    public void showPix(String name) {
        //System.out.println("ImgProvider:showPix:  before readIn");
        if (!isLoaded) readinImage();
        //System.out.println("ImgProvider:showPix:  after readIn");
        img = getToolkit().createImage(
                new MemoryImageSource(pixwidth, pixheight, pix, 0, pixwidth));
        //System.out.println("ImgProvider:showPix:  before displayImage");
        DisplayImage dis = new DisplayImage(this,name,true);
        //System.out.println("ImgProvider:showPix:  after displayImage");
        try { Thread.sleep(100);}catch(Exception e){}       //make sure image has time to display
    }//showPix
        
    /**
     * Pull the image apart into its RGB and Alpha components.
     */
    void separateColors() {
        if (pix == null) return;
        alpha   = new short[pixheight][pixwidth];
        red     = new short[pixheight][pixwidth];
        green   = new short[pixheight][pixwidth];
        blue    = new short[pixheight][pixwidth];
        int spot = 0;       //index into pix
        for (int r = 0;  r< pixheight; r++) {
            for (int c = 0; c < pixwidth; c++) {
                int num = pix[spot++];
                blue[r][c]   = (short)(num & 255);
                num = num >> 8;
                green[r][c]  = (short)(num & 255);
                num = num >> 8;
                red[r][c]    = (short)(num & 255);
                num = num >> 8;
                alpha[r][c]  = (short)(num & 255);
            }//for c
        }//for r
    }//separateColors
    
    /**
     * Set the RGB and Alpha components for this image.
     * @param rd 2D array that represents the image's red component
     * @param g  2D array that represents the image's green component
     * @param b  2D array that represents the image's blue component
     * @param al 2D array that represents the image's alpha channel
     */
    public void setColors(short[][]rd, short[][]g, short[][]b, short[][]al) {
        pixheight = rd.length;
        pixwidth  = rd[0].length;
        red     = new short[pixheight][pixwidth];
        green   = new short[pixheight][pixwidth];
        blue    = new short[pixheight][pixwidth];
        alpha   = new short[pixheight][pixwidth];
        pix     = new int[pixwidth * pixheight];
        int tmp;
        int spot = 0;
        for (int r = 0;  r< pixheight; r++) {
            for (int c = 0; c < pixwidth; c++) {
                red[r][c]   = rd[r][c];
                green[r][c] = g[r][c];
                blue[r][c]  = b[r][c];
                alpha[r][c] = al[r][c];
                tmp = alpha[r][c];
                tmp = tmp << 8;
                tmp += red[r][c];
                tmp = tmp << 8;
                tmp += green[r][c];
                tmp = tmp << 8;
                tmp += blue[r][c];
                pix[spot++] = tmp;
            }//for c
        }//for r
        isLoaded = true;
    }//setColors
    
    /**
     * Retrieve the image's green component.
     * @return A 2D array of values from 0 to 255.
     */
    public short[][] getRed() {
        int nrows = red.length;
        int ncols = red[0].length;
        short [] [] redcp = new short[nrows][ncols];
        for (int r = 0; r < nrows; r++) 
            for (int c = 0; c < ncols; c++)
                redcp[r][c] = red[r][c];
        return redcp;
    }//getRed
    
    /**
     * Retrieve the image's green component.
     * @return A 2D array of values from 0 to 255.
     */
    public short[][] getGreen() {
        int nrows = green.length;
        int ncols = green[0].length;
        short [] [] thecopy = new short[nrows][ncols];
        for (int r = 0; r < nrows; r++) 
            for (int c = 0; c < ncols; c++)
                thecopy[r][c] = green[r][c];
        return thecopy;
    }//getGreen
    
    /**
     * Retrieve the image's blue component.
     * @return A 2D array of values from 0 to 255.
     */
    public short[][] getBlue() {
        int nrows = blue.length;
        int ncols = blue[0].length;
        short [] [] thecopy = new short[nrows][ncols];
        for (int r = 0; r < nrows; r++) 
            for (int c = 0; c < ncols; c++)
                thecopy[r][c] = blue[r][c];
        return thecopy;
    }//getBlue
    
    /** 
     * Retrieve the image's alpha component.
     * @return A 2D array with values from 0 to 255.
     */
    public short[][] getAlpha() {
        int nrows = alpha.length;
        int ncols = alpha[0].length;
        short [] [] al = new short[nrows][ncols];
        for (int r = 0; r < nrows; r++) 
            for (int c = 0; c < ncols; c++)
                al[r][c] = alpha[r][c];
        return al;
    }
    
    /**
     * retrieve the image's width.
     */
    public int getWidth() { return pixwidth; }
    
    /**
     * Retrieve the image's height.
     */
    public int getHeight() { return pixheight; }
    
    /** 
     * Retrieve the image's raw image.
     * Note that img is not always consistent with RGBA arrays.
     * @return The image's raw image
     */
    public Image getImage() {
        return img;
    }//getImage
    
    /**
     * Called when the window containing this image is selected.
     */
    public void setActive() {
        if (lab != null)    lab.setActive(this);
        else                System.err.println("*** error ** ImgProvider:setActive - no lab");
    }
    
    /** Called when the window containing this image is closed. */
    public void setInactive() {
        if (lab != null) {
            lab.setInactive(this);
            ImageLab.impro = null;
        }
        else                System.err.println("*** error ** ImgProvider:setInactive - no lab");
    }//setInactive
    
    /**
     * Used by ImageLab to register itself with this ImgProvider.
     */
    void setLab(ImageLab iml) {
        lab = iml;
    }
    
    /** Used by ImageLab to save an image to a file. */
    void save() {
        JFileChooser fd;
        JFrame myframe = new JFrame();      //to have a parent
        fd = new JFileChooser();
        int returnVal = fd.showSaveDialog(myframe);
        String fname;
        File  theFile;
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(myframe,"Encountered a problem in ImgProvider.save()" +
                    "\n- Please try again.");
            return;
        }
        fname = fd.getSelectedFile().getName();
        theFile = fd.getSelectedFile();
    
        BufferedImage bufim = new BufferedImage(pixwidth,pixheight,BufferedImage.TYPE_INT_RGB);
        bufim.setRGB(0,0,pixwidth,pixheight,pix,0,pixwidth);
    
        try {
            if (!ImageIO.write(bufim,"jpeg",theFile))
                    System.err.println("Couldn't write file - save failed");
            //else System.out.println("File written");
        } catch (IOException ioe) {
            System.err.println("Attempt to save file failed.");
        }//catch
    }//save
    
    /**
     * Used by ImageLab to render an image as sound.
     * Three channels of sound are created, using the 
     * average the Red, Green and Blue values of each
     * row to establish the pitches for the channels.
     * The average of Hue, Saturation and Brightness
     * values of each row are used as the velocities
     * of the Red, Green and Blue notes respectively.
     */
    public void play() {

        playThread = new Thread(() -> {
            short[][] red = getRed();     // Red plane
            short[][] green = getGreen(); // Green plane
            short[][] blue = getBlue();   // Blue plane
            short[][] bw = getBWImage();  // Black & white image
            short[][] alpha = getAlpha(); // Alpha channel
            short[][] hue;
            short[][] saturation;
            short[][] brightness;

            int height = bw.length;
            int width = bw[0].length;

            //System.out.println("Playing image number " + getid());

            Tune tune = new Tune();
            /* A 7-octave pentatonic scale. */
            Scale scale = new Scale();
            for (int i = -3; i < 4; i++) {
                scale.addPitch(Note.C + (12 * i));
                scale.addPitch((Note.C + 3) + (12 * i));
                scale.addPitch((Note.C + 5) + (12 * i));
                scale.addPitch((Note.C + 7) + (12 * i));
                scale.addPitch((Note.C + 10) + (12 * i));
            }
            int pitchRange = scale.numPitches();
            Chord chord;
            int[] velocity = {0, 0, 0};
            int velocityRange = Note.VRANGE;
            int tempo = Note.DE / 2;
            int rowSum = 0;
            int redSum = 0;
            int greenSum = 0;
            int blueSum = 0;
            float[] hsb = {0, 0, 0};
            float hueSum = 0;
            float satSum = 0;
            float brtSum = 0;

            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    rowSum += (bw[row][column]);
                    redSum += (red[row][column]);
                    greenSum += (green[row][column]);
                    blueSum += (blue[row][column]);
                    java.awt.Color
                            .RGBtoHSB(red[row][column], green[row][column], blue[row][column],
                                      hsb);
                    hueSum += hsb[0];
                    satSum += hsb[1];
                    brtSum += hsb[2];
                }//for column
                velocity[0] = (int) (Note.VPP + (velocityRange * (hueSum / width)));
                velocity[1] = (int) (Note.VPP + (velocityRange * (satSum / width)));
                velocity[2] = (int) (Note.VPP + (velocityRange * (brtSum / width)));
                chord = new Chord();
                chord.addNote(new Note(0, (scale.getPitch(
                        pitchRange * redSum / width / 256)), tempo, velocity[0]));
                chord.addNote(new Note(1, (scale.getPitch(pitchRange * greenSum / width / 256)), tempo,
                                       velocity[1]));
                chord.addNote(new Note(2, (scale.getPitch(
                        pitchRange * blueSum / width / 256)), tempo, velocity[2]));
                tune.addChord(chord);
                rowSum = 0;
                redSum = 0;
                greenSum = 0;
                blueSum = 0;
                hueSum = 0;
                satSum = 0;
                brtSum = 0;
            }//for row
            int[] instruments = {Note.VIBES, Note.PIZZICATO, Note.MELODIC_TOM};
            Music m = new Music(3, instruments);
            m.playTune(tune);
        });
        playThread.start();



    }
    
    /**
     * Display this image a line at a time in a window.
     * @param name The title for the window.
     */
    public void showPixNew(String name) {
        System.out.println("ImgProvider:showSlow: Before readinImage");
        if (!isLoaded) readinImage();
        System.out.println("ImgProvider:showSlow: After readinImage");
        img = getToolkit().createImage(
                new MemoryImageSource(pixwidth, pixheight, pix, 0, pixwidth));
        DynDisplayImage dImage1 = new DynDisplayImage(this,name,true);
        dImage1.setVisible(true);
        dImage1.repaint();
        System.out.println("ImgProvider:showSlow: Constructed DynaPanel");
        System.out.println("ImgProvider:showSlow: size is (" +
                            pixwidth + ", " + pixheight + ")");
        try {
            Thread.sleep(1000);  //give image time to display
        } catch(Exception e){ }
        dImage1.changeImage(this,"Second Pass");
        System.out.println("ImgProvider:showSlow: Second Pass");
    }

}
