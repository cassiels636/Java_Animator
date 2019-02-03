package animator.model;

/**
 * This is the interface for the RGB class.
 * It provides methods to access the red, green, and blue values.
 */
public interface IRGB {

  Double getRed();

  Double getGreen();

  Double getBlue();

  /**
   * This method converts the RGB value into a format that is readable
   * by the SVG.
   * @return  string SVG color
   */
  String toStringSVG();
}
