package animator.model;

import animator.model.enums.AnimateTypes;

public class RotateShape extends Animations {
  /**
   * Constructor for the RotateShape animation. Takes in parameters that define
   * RotateShape animation and validates the occurring times.
   *
   * @param shape       shape to be changed
   * @param degrees1    shape's old rotation in degrees
   * @param degrees2    shape's new rotation in degrees
   * @param time1       beginning time of change
   * @param time2       end time of change
   */
  public RotateShape(IAnimatedShape shape, Integer degrees1, Integer degrees2, Integer time1,
                     Integer time2) {
    this.type = AnimateTypes.ROTATE;
    this.changedShape = shape;
    this.color1 = changedShape.getInitialColor();
    this.sizeParams1 = changedShape.getInitialSize();
    this.position1 = changedShape.getInitialPosition();
    this.rotation1 = degrees1;
    this.rotation2 = degrees2;
    this.time1 = time1;
    this.time2 = time2;
    if (!validateAnimationTimes()) {
      throw new IllegalArgumentException("Invalid times for an animation");
    }
  }
}
