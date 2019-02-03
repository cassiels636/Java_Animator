package animator.model;

import animator.model.enums.AnimateTypes;

public class ShapeDisappears extends Animations {
  /**
   * Constructor for ShapeDisappears Animation. Takes in parameters that
   * define a shapeDisappears animation.
   * @param shape   affected shape
   * @param time1   time of disappearance
   */
  public ShapeDisappears(IAnimatedShape shape, Integer time1) {
    this.type = AnimateTypes.DISAPPEAR;
    this.changedShape = shape;
    this.position1 = shape.getInitialPosition();
    this.color1 = shape.getInitialColor();
    this.sizeParams1 = shape.getInitialSize();
    this.rotation1 = shape.getInitialRotation();
    this.time1 = time1;
    this.time2 = time1;
  }
}
