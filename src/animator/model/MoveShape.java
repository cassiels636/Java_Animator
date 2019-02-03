package animator.model;

import animator.model.enums.AnimateTypes;

public class MoveShape extends Animations {
  /**
   * Constructor for MoveShape Animation. Takes in parameters that
   * define a moveShape animation and validates the occurring times.
   * @param changedShape  shape to change
   * @param position1     old (x, y) position
   * @param position2     new (x, y) position
   * @param time1         beginning time of change
   * @param time2         end time of change
   */
  public MoveShape(IAnimatedShape changedShape, IPosition2D position1, IPosition2D position2,
                   Integer time1, Integer time2) {
    this.type = AnimateTypes.MOVE;
    this.changedShape = changedShape;
    this.color1 = changedShape.getInitialColor();
    this.sizeParams1 = changedShape.getInitialSize();
    this.rotation1 = changedShape.getInitialRotation();
    this.position1 = position1;
    this.position2 = position2;
    this.time1 = time1;
    this.time2 = time2;
    if (!validateAnimationTimes()) {
      throw new IllegalArgumentException("Invalid times for an animation");
    }
  }
}
