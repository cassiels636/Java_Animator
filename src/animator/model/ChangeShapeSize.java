package animator.model;

import java.util.List;

import animator.model.enums.AnimateTypes;

public class ChangeShapeSize extends Animations {
  /**
   * Constructor for ChangesShapeSize Animation. Takes in parameters that
   * define a changeShapeSize animation and validates the occurring times.
   * @param changedShape    shape to change
   * @param sizeParams1     old size parameters
   * @param sizeParams2     new size parameters
   * @param time1           beginning time of change
   * @param time2           end time of change
   */
  public ChangeShapeSize(IAnimatedShape changedShape, List<Double> sizeParams1,
                         List<Double> sizeParams2, Integer time1, Integer time2) {
    this.type = AnimateTypes.CHANGESIZE;
    this.changedShape = changedShape;
    this.position1 = changedShape.getInitialPosition();
    this.color1 = changedShape.getInitialColor();
    this.rotation1 = changedShape.getInitialRotation();
    this.sizeParams1 = sizeParams1;
    this.sizeParams2 = sizeParams2;
    this.time1 = time1;
    this.time2 = time2;
    if (!validateAnimationTimes()) {
      throw new IllegalArgumentException("Invalid times for an animation");
    }
  }
}
