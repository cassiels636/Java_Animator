package animator.model;

import animator.model.enums.AnimateTypes;

public class ShapeAppears extends Animations {
  /**
   * Constructor for ShapeAppears Animation. Takes in parameters that
   * define a shapeAppears animation.
   * UPDATED APRIL 3RD, 2018:
   * Initialised time2.
   *
   * @param shape   affected shape
   * @param time1   time of appearance
   */
  public ShapeAppears(IAnimatedShape shape, Integer time1) {
    this.type = AnimateTypes.APPEAR;
    this.changedShape = shape;
    this.position1 = shape.getInitialPosition();
    this.color1 = shape.getInitialColor();
    this.sizeParams1 = shape.getInitialSize();
    this.rotation1 = shape.getInitialRotation();
    this.time1 = time1;
    this.time2 = time1;
  }
}
