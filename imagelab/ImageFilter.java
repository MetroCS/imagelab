package imagelab;

/**
 * The ImageFilter interface describes the necessary
 * methods for actual image filters.
 *
 * @author Dr. Aaron Gordon
 * @author Dr. Jody Paul
 * @version 1.1
 */
public interface ImageFilter {
    /**
     * Does the actual filtering.
     *
     * @param ip the image to be filtered
     */
    void filter(ImgProvider ip);

    /**
     * Returns the filtered image.
     *
     * @return the filtered image
     */
    ImgProvider getImgProvider();

    /**
     * Returns the label to be used in the Filter menu.
     *
     * @return the name of this filter
     */
    String getMenuLabel();
}
