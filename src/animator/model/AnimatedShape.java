package animator.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.enums.RefPointType;
import cs3500.animator.model.enums.ShapeType;

/**
 * Class that represents a shape that can be put into the animation.
 * This class holds all the parameters necessary to draw a shape.
 */
public class AnimatedShape implements IAnimatedShape {
  protected String shapeName;
  protected ShapeType shapeType;
  protected IRGB initialColor;
  protected RefPointType refPoint;
  protected IPosition2D initialPosition;
  protected List<Double> initialSize;
  protected Integer initialRotation;
  protected Integer appearTime;
  protected Integer disappearTime;
  protected ILayer layer;

  /**
   * Constructor for the AnimatedShape. Initializes all shape variables
   * using the given parameters.
   * @param shapeName       shape name
   * @param shape           shape type
   * @param initialColor    shape color
   * @param initialPosition shape's initial position
   * @param size            shape's size
   * @param appearTime      shape's appearance time
   * @param disappearTime   shape's disappearance time
   */
  public AnimatedShape(String shapeName, ShapeType shape, IRGB initialColor,
                       IPosition2D initialPosition, List<Double> size, Integer appearTime,
                       Integer disappearTime) {
    this.shapeName = shapeName;
    this.shapeType = shape;
    this.initialColor = initialColor;
    this.initialRotation = 0;

    if ((shapeType == ShapeType.RECTANGLE) | (shapeType == ShapeType.SQUARE)) {
      refPoint = RefPointType.MINCORNER;
    }
    else {
      refPoint = RefPointType.CENTER;
    }

    this.initialPosition = initialPosition;
    this.initialSize = new ArrayList<>();
    if (!validateShapeType(size)) {
      throw new IllegalArgumentException("Illegal shape size parameters");
    }
    this.initialSize = size;
    this.appearTime = appearTime;
    this.disappearTime = disappearTime;
    this.layer = new Layer("", 0);
  }

  @Override
  public IPosition2D getInitialPosition() {
    return initialPosition;
  }

  @Override
  public IRGB getInitialColor() {
    return initialColor;
  }

  @Override
  public List<Double> getInitialSize() {
    return initialSize;
  }

  @Override
  public Integer getInitialRotation() {
    return initialRotation;
  }

  @Override
  public String getShapeName() {
    return shapeName;
  }

  @Override
  public Integer getAppearTime() {
    return appearTime;
  }

  @Override
  public Integer getDisappearT() {
    return disappearTime;
  }

  @Override
  public ShapeType getShapeType() {
    return shapeType;
  }

  @Override
  public RefPointType getRefPoint() {
    return refPoint;
  }

  @Override
  public ILayer getLayer() {
    return layer;
  }

  @Override
  public void setLayer(ILayer layer) {
    this.layer = layer;
  }

  @Override
  public String toString() {
    return "Name: " + shapeName + "\nType: " + shapeType.getStringValue() + "\n"
            + refPoint.getStringValue() + ": " + initialPosition.toString() + ", "
            + sizeParamsToString(initialSize) + ", Color: " + initialColor.toString()
            + "\nAppears at t=" + String.valueOf(appearTime) + "\nDisappears at t="
            + String.valueOf(disappearTime);
  }

  /**
   * Converts the given size parameters into a String based on the shape's type.
   * @param size    shape's size parameters
   * @return        string representation of the shape's size
   */
  public String sizeParamsToString(List<Double> size) {
    StringBuilder stringSize = new StringBuilder();
    switch (shapeType) {
      case RECTANGLE:
        stringSize.append("Width: ");
        stringSize.append(size.get(0));
        stringSize.append(", Height: ");
        stringSize.append(size.get(1));
        break;
      case SQUARE:
        stringSize.append("Side length: ");
        stringSize.append(size.get(0));
        break;
      case OVAL:
        stringSize.append("X radius: ");
        stringSize.append(size.get(0));
        stringSize.append(", Y radius: ");
        stringSize.append(size.get(1));
        break;
      case CIRCLE:
        stringSize.append("Radius: ");
        stringSize.append(size.get(0));
        break;
      default:
        stringSize.append("Side 1 length: ");
        stringSize.append(size.get(0));
        for (int i = 1; i < size.size(); i++) {
          stringSize.append(", Side ");
          stringSize.append(i + 1);
          stringSize.append(" length: ");
          stringSize.append(size.get(i));
        }
        break;
    }
    return stringSize.toString();
  }

  /**
   * Validates a shape based on the size parameters. Verifies that no one side
   * is larger than the sum of the rest of the shape's sides.
   * @param sizeParams   side lengths
   * @return             true, if valid shape
   */
  private boolean validateShapeType(List<Double> sizeParams) {
    int numSides = sizeParams.size();
    double side;
    double sideSum = 0;

    if (numSides > 2) {
      for (int i = 0; i < numSides; i++) {
        side = sizeParams.get(i);
        for (int j = 0; j < numSides; j++) {
          if (j == i) {
            continue;
          }
          sideSum += sizeParams.get(j);
        }
        if (side >= sideSum) {
          return false;
        }
      }
    }
    switch (shapeType) {
      case RECTANGLE:
      case OVAL:
        return (numSides == 2);
      case SQUARE:
      case CIRCLE:
        return (numSides == 1);
      default:
        return (numSides > 2);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AnimatedShape)) {
      return false;
    }

    AnimatedShape that = (AnimatedShape) obj;

    return (this.shapeName.equals(that.shapeName) && (this.shapeType == that.shapeType)
            && this.initialColor.equals(that.initialColor) && (this.refPoint == that.refPoint)
            && this.initialPosition.equals(that.initialPosition) && this.initialSize
            .equals(that.initialSize) && (this.initialRotation.equals(that.initialRotation))
            && (this.appearTime.equals(that.appearTime)) && (this.disappearTime.equals(that
            .disappearTime)));
  }

  @Override
  public int hashCode() {
    int hash = 17;
    hash = 31 * hash + shapeName.hashCode();
    hash = 31 * hash + shapeType.hashCode();
    hash = 31 * hash + initialColor.hashCode();
    hash = 31 * hash + refPoint.hashCode();
    hash = 31 * hash + initialPosition.hashCode();
    hash = 31 * hash + initialSize.hashCode();
    hash = 31 * hash + initialRotation;
    hash = 31 * hash + appearTime;
    hash = 31 * hash + disappearTime;
    return hash;
  }
}
