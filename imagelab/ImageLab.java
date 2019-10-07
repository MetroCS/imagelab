package imagelab;

/* ImageLab.java */
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
/**
 *  ImageLab is a platform for image filter development.  ImageLab
 *  begins by building a menu of all available filters (those .class files
 *  that implement the {@see ImageFilter ImageFilter} interface).
 *  [TODO: User interface to allow chosing directory to search for filters.
 *  [TODO: User interface to allow displaying images a line or pixel at a time.
 *  @author Dr. Aaron Gordon
 *  @author Dr. Jody Paul
 *  @version 1.8.1
 */
public class ImageLab {
    /** Version Identification. */
    public static final String VERSION = "ImageLab 1.8";

    /** Default location for filters. */
    public static final String FILTER_DIR = "filters";

    /** The application's main frame. */
    static JFrame frame;

    /** Holds all open images (ImgProvider objects). */
    static List<ImgProvider> images = new ArrayList<ImgProvider>();

    /** The directory that holds filter classes.  (TODO) */
    static String filterDir = FILTER_DIR;

    /** Holds the actual filter objects. */
    static List<ImageFilter> filters;

    /** The current image provider. */
    static ImgProvider impro;

    /** A copy of <CODE>this</CODE>. */
    static ImageLab theLab;

    /** Accessible menu bar for ImageLab. */
    public static JMenuBar menubar;

    /**
     * Application entry point.
     * @param args ignored
     */
    public static void main(final String [] args) {
        ImageLab ilab = new ImageLab();
    }

    /** Constructor. */
    public ImageLab() {
        theLab  = this;
        filters = new ArrayList<ImageFilter>();
        frame   = new JFrame(VERSION);
        Container cpane = frame.getContentPane();

        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();

        cpane.setLayout(new java.awt.FlowLayout());

        //jButton1.setIcon(new javax.swing.ImageIcon("open.gif"));
        //jButton1.setMnemonic('O');
        jButton1.setText("Open Image File");
        jButton1.setToolTipText("Select an image file to load");
        jButton1.addActionListener(
            new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    ImgProvider improvider;
                    FileDialog fd;
                    fd = new FileDialog(frame, "Pick an image", FileDialog.LOAD);
                    fd.setVisible(true);
                    String theFile = fd.getFile();
                    String theDir = fd.getDirectory();
                    //System.out.println("The file's name is " + theDir + theFile);
                    improvider = new ImgProvider(theDir + theFile);
                    improvider.setLab(theLab);
                    improvider.showImage(theDir + theFile);
                    images.add(improvider);
                    impro = improvider;
                }
            }
        );
        //cpane.add(jButton1);

        //jButton2.setIcon(new javax.swing.ImageIcon("exit.gif"));
        jButton2.setText("Exit ImageLab");
        jButton2.setToolTipText("Quit ImageLab");
        jButton1.addActionListener(
            new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    System.exit(0);
                }
            }
        );
        //cpane.add(jButton2);

        menubar = buildMenus();
        frame.setJMenuBar(menubar);
        frame.setBounds(400, 30, 300, 100);
        frame.setVisible(true);
    }

    /**
     * Creates the menus for this application.
     * @return a menu bar populated with menu items
     */
    private JMenuBar buildMenus() {
        JMenuBar mbar = new JMenuBar();
        JMenu file = new JMenu("File");
        mbar.add(file);
        JMenuItem open = new JMenuItem("Open", 'O');
        file.add(open);
        open.addActionListener(makeOpenListener());
        JMenuItem play = new JMenuItem("Play", 'P');
        file.add(play);
        play.addActionListener(makePlayListener());
        JMenuItem save = new JMenuItem("Save", 'S');
        file.add(save);
        save.addActionListener(makeSaveListener());
        JMenuItem quit = new JMenuItem("Quit", 'Q');
        file.add(quit);
        quit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actev) {
                    System.exit(0);
                }
            }
        );
        JMenu filter = new JMenu("Filter");
        mbar.add(filter);
        //Find filters and build corresponding menu items.
        //Look at each .class file in filterDir.
        //Do the classForName stuff and enter into filter menu.
        //System.out.println("**********finding filters***********");
        FileSystemView fsv = FileSystemView.getFileSystemView();
        //File [] fil = fsv.getFiles(new File("."),true);
        File [] fil = fsv.getFiles(new File(filterDir), true);
        //System.out.println("Found " + fil.length + " possible filters");
        String clName = " ";        //holds name of class
        for (int k=0; k<fil.length; k++) {
            if (fil[k].getName().endsWith("class")) {
                Class<?> cl;
                ImageFilter ifilter;
                try {
                    clName = fil[k].getName();
                    int spot = clName.lastIndexOf(".");
                    //clName = clName.substring(0,spot);
                    clName = "filters." + clName.substring(0,spot);
                    //System.out.println("Trying: " + clName);
                    cl = Class.forName(clName);
                    //System.out.println("Class for name is: " + cl);
                    Class<?>[] interfaces = cl.getInterfaces();
                    //System.out.println("Number of interfaces is " + interfaces.length);
                    boolean isFilter = false;
                    for (int j=0; j<interfaces.length; j++) {
                        //System.out.println("------->>>>>>>>>>" + interfaces[j].getName());
                        isFilter |= interfaces[j].getName().equals("imagelab.ImageFilter");
                    }//for ja
                    if (isFilter) {
                        ifilter = (ImageFilter) cl.newInstance();
                        //System.out.println("This is the one: " + fil[k].getName());
                        filters.add(ifilter);
                        JMenuItem jmi = new JMenuItem(ifilter.getMenuLabel());
                        filter.add(jmi);
                        jmi.addActionListener(makeActionListener(
                                (filters.get(filters.size() - 1))));
                    } //if
                } catch (Exception bigEx) {
                    System.err.println("Error in buildMenus, k = " + k);
                    System.err.println(">>> " + bigEx);
                }//catch
            }//if

        }//for k  

        return mbar;
    }//buildMenus

    /**
     * Return a File menu for a single image.
     * Provides "Play", "Save" and "Close".
     * @return the new File menu
     */
    public static JMenu newFileMenu(final DisplayImage who) {
        JMenu fileMenu = new JMenu("File");
        //        JMenuItem open = new JMenuItem("Open",'O');
        //        fileMenu.add(open);
        //        open.addActionListener(makeOpenListener());
        JMenuItem play = new JMenuItem("Play",'P');
        fileMenu.add(play);
        play.addActionListener(makePlayListener());
        JMenuItem save = new JMenuItem("Save",'S');
        fileMenu.add(save);
        save.addActionListener(makeSaveListener());
        JMenuItem close = new JMenuItem("Close",'C');
        fileMenu.add(close);
        close.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent actev) {
                    who.setVisible(false);
                    who.byebye();
                }
            });
        return fileMenu;
    }

    /**
     * Return a Filter menu for a single image.
     * Provides access to all filters.
     * @return the new Filter menu
     */
    public static JMenu newFilterMenu() {
        JMenu filterMenu = new JMenu("Filter");
        for (int i = 0; i < filters.size(); i++) {
            JMenuItem jmi = new JMenuItem(filters.get(i).getMenuLabel());
            filterMenu.add(jmi);
            jmi.addActionListener(makeActionListener(filters.get(i)));
        }
        return filterMenu;
    }

    /**
     * Builds a dedicated ActionListener for a specific ImageFilter.
     * @param imf the ImageFilter for which to make the ActionListener 
     */
    public static ActionListener makeActionListener (ImageFilter imf) {
        final ImageFilter theFilter = imf;
        final JFrame myframe = frame;
        return new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (impro == null) {
                    JOptionPane.showMessageDialog(myframe,"You must first select an image");
                    return;
                }
                //System.out.println("Using impro number " + impro.getid());
                theFilter.filter(impro);
                impro = theFilter.getImgProvider();
                impro.setLab(theLab);
                images.add(impro);
            }};
    }//makeActionListener

    /**
     * Create an ActionListener for opening an image file.
     */
    public static ActionListener makeOpenListener() {
        return new ActionListener() {       
            public void actionPerformed(ActionEvent e) {
                ImgProvider improvider;                 //an imgProvider to hold the image
                FileDialog fd;
                fd = new FileDialog(frame,"Pick an image",FileDialog.LOAD);
                fd.setVisible(true);
                String theFile = fd.getFile();
                String theDir = fd.getDirectory();
                //System.out.println("The file's name is " + theDir + theFile);
                improvider = new ImgProvider(theDir + theFile);
                improvider.setLab(theLab);
                improvider.showImage(theDir + theFile); 
                images.add(improvider);
                impro = improvider;             //current image provider is set
            }//actionPerformed
        };
    }// makeOpenListener

    /** 
     * Marks an image as the one in focus.
     * @param ip The ImgProvider responsible for the image
     */
    public void setActive(ImgProvider ip) {
        impro = ip;
        //System.out.println("Setting impro to " + impro.getid());
    }//setActive

    /** 
     * Marks an image as no longer the focus.
     * @param ip The ImgProvider responsible for the image
     */
    public void setInactive(ImgProvider ip) {
        if (impro == ip) impro = null;
    }//setInactive

    /**
     * Create an ActionListener for rendering an image in sound.
     */
    public static ActionListener makePlayListener() {
        final JFrame myframe = frame;
        return new ActionListener() {       
            public void actionPerformed(ActionEvent e) {
                ImgProvider improvider = impro;  //The imgProvider holding the image
                if (improvider == null) {
                    JOptionPane.showMessageDialog(myframe,"First select the image to play");
                    return;
                }//if
                improvider.play();
            }//actionPerformed
        };//new ActionListener
    }//makePlayListener

    /**
     * Create an ActionListener for saving an image to a file.
     */
    public static ActionListener makeSaveListener() {
        final JFrame myframe = frame;
        return new ActionListener() {       
            public void actionPerformed(ActionEvent e) {
                ImgProvider improvider = impro;         //an imgProvider to hold the image
                if (improvider == null) {
                    JOptionPane.showMessageDialog(myframe,"Select the image to save");
                    return;
                }//if
                improvider.save();
            }//actionPerformed
        };//new ActionListener
    }//makeSaveListener
}//ImageLab
