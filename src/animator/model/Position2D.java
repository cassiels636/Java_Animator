package animator.model;

import java.util.Objects;

/**
 * This class represents a 2D position.
 */
public final class Position2D implements IPosition2D {
  private final double x;
  private final double y;

  /**
   * Constructor for Position2D that creates a an (x, y) pair.
   */
  public Position2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public Double getX() {
    return x;
  }

  @Override
  public Double getY() {
    return y;
  }

  @Override
  public String toString() {
    return String.format("(%.1f, %.1f)", this.x, this.y);
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Position2D)) {
      return false;
    }

    Position2D that = (Position2D) a;

    return ((Math.abs(this.x - that.x) < 0.01) && (Math.abs(this.y - that.y) < 0.01));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}