package filters;

import imagelab.ImageFilter;
import imagelab.ImgProvider;

/**
 * An imageLab filter that does not change the image. This class is provided as
 * a starting point for creating new filters.
 *
 * @author Dr. Aaron Gordon
 * @author Dr. Jody Paul
 */
public class Generic implements ImageFilter {

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
    short tmp;
    short[][] red = ip.getRed();     // Red plane
    short[][] green = ip.getGreen(); // Green plane
    short[][] blue = ip.getBlue();   // Blue plane
    short[][] bw = ip.getBWImage();  // Black & white image
    short[][] alpha = ip.getAlpha(); // Alpha channel

    int height = bw.length;
    int width = bw[0].length;

    //System.out.println("Filtering image number " + ip.getid());

    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        tmp = bw[row][column];
        bw[row][column] = tmp;
      } //for column
    } //for row

    filteredImage = new ImgProvider();
    filteredImage.setBWImage(bw);
    filteredImage.setColors(red, green, blue, alpha);
    filteredImage.showPix("Generic");
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
    return "Generic";
  } //getMenuLabel

}
