package filters;

import imagelab.ImageFilter;
import imagelab.ImgProvider;

/**
 * An imageLab filter that flips the image horizontally.
 */
public class HFlip implements ImageFilter {

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
    short[][] im = ip.getBWImage();
    int height = im.length;
    int width = im[0].length;
    for (int r = 0; r < height; r++) {
      for (int c = 0, x = width - 1; c < x; c++, x--) {
        tmp = im[r][c];
        im[r][c] = im[r][x];
        im[r][x] = tmp;
      } //for c
    } //for r;

    filteredImage = new ImgProvider();
    filteredImage.setBWImage(im);
    filteredImage.showPix("Flipped Horizontally");
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
    return "HFlip(BW)";
  } //getMenuLabel

}
