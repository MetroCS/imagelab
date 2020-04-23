package filters;

import imagelab.ImageFilter;
import imagelab.ImgProvider;

/**
 * An imageLab filter that swaps the red and blue values of each pixel.
 */
public class RBSwap implements ImageFilter {

  /**
   * The filtered image.
   */
  private ImgProvider filteredImage;

  /**
   * The filter itself.
   *
   * @param ip the image to be filtered.
   */
  public void filter(final ImgProvider ip) {
    short[][] red = ip.getRed();
    short[][] blue = ip.getBlue();

    filteredImage = new ImgProvider();
    filteredImage.setColors(blue, ip.getGreen(), red, ip.getAlpha());
    filteredImage.showPix("Red <=> Blue");
  } //filter

  /**
   * Retrieve the filtered image.
   *
   * @return the filtered image.
   */
  public ImgProvider getImgProvider() {
    return filteredImage;
  } //getImgProvider

  /**
   * Retrieve the name of the filter to add to the menu.
   *
   * @return the filter's menu item label
   */
  public String getMenuLabel() {
    return "Red-Blue Swap";
  } //getMenuLabel

}
