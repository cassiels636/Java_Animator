package animator.model;

import java.util.Objects;

public class RGB implements IRGB {
  private final double red;
  private final double green;
  private final double blue;

  /**
   * Initialize this object to the specified color.
   */
  public RGB(double red, double green, double blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public Double getRed() {
    return red;
  }

  @Override
  public Double getGreen() {
    return green;
  }

  @Override
  public Double getBlue() {
    return blue;
  }

  /**
   * This method converts the RGB value into a format that is readable
   * by the SVG.
   * @return  string SVG color
   */
  @Override
  public String toStringSVG() {
    return String.format("(%d, %d, %d)", (int)(this.red * 255), (int)(this.green * 255),
            (int)(this.blue * 255));
  }

  @Override
  public String toString() {
    return String.format("(%.1f, %.1f, %.1f)", this.red, this.green, this.blue);
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof RGB)) {
      return false;
    }

    RGB that = (RGB) a;

    return ((Math.abs(this.red - that.red) < 0.01) && (Math.abs(this.green - that.green) < 0.01)
            && (Math.abs(this.blue - that.blue) < 0.01));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }
}
