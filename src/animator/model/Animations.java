package animator.model;

import java.util.List;

import animator.model.enums.AnimateTypes;

/**
 * Class for containing all animation types and their corresponding info.
 */
public class Animations implements IAnimations {
  protected IAnimatedShape changedShape;
  public AnimateTypes type;
  protected Integer time1;
  protected Integer time2;
  protected IRGB color1;
  protected IRGB color2;
  protected List<Double> sizeParams1;
  protected List<Double> sizeParams2;
  protected IPosition2D position1;
  protected IPosition2D position2;
  protected Integer rotation1;
  protected Integer rotation2;

  @Override
  public Integer getTime1() {
    return time1;
  }

  @Override
  public Integer getTime2() {
    return time2;
  }

  @Override
  public AnimateTypes getAnimateType() {
    return type;
  }

  @Override
  public IAnimatedShape getChangedShape() {
    return changedShape;
  }

  @Override
  public IPosition2D getPosition1() {
    return position1;
  }

  @Override
  public IPosition2D getPosition2() {
    return position2;
  }

  @Override
  public IRGB getColor1() {
    return color1;
  }

  @Override
  public IRGB getColor2() {
    return color2;
  }

  @Override
  public List<Double> getSizeParams1() {
    return sizeParams1;
  }

  @Override
  public List<Double> getSizeParams2() {
    return sizeParams2;
  }

  @Override
  public Integer getRotation1() {
    return rotation1;
  }

  @Override
  public Integer getRotation2() {
    return rotation2;
  }

  @Override
  public void setPosition1(IPosition2D position1) {
    this.position1 = position1;
  }

  @Override
  public void setColor1(IRGB color1) {
    this.color1 = color1;
  }

  @Override
  public void setSizeParams1(List<Double> sizeParams1) {
    this.sizeParams1 = sizeParams1;
  }

  @Override
  public void setRotation1(Integer rotation1) {
    this.rotation1 = rotation1;
  }

  protected boolean validateAnimationTimes() {
    return ((changedShape.getAppearTime() <= time1) && (changedShape.getDisappearT() >= time2));
  }

  @Override
  public String toString() {
    StringBuilder newString = new StringBuilder("Shape " + changedShape.getShapeName());
    switch (type) {
      case MOVE:
        newString.append(" moves from ");
        newString.append(position1.toString());
        newString.append(" to ");
        newString.append(position2.toString());
        newString.append(" from t=");
        newString.append(time1);
        newString.append(" to t=");
        newString.append(time2);
        break;
      case CHANGECOLOR:
        newString.append(" changes color from ");
        newString.append(color1.toString());
        newString.append(" to ");
        newString.append(color2.toString());
        newString.append(" from t=");
        newString.append(time1);
        newString.append(" to t=");
        newString.append(time2);
        break;
      case CHANGESIZE:
        newString.append(" scales from ");
        newString.append(changedShape.sizeParamsToString(sizeParams1));
        newString.append(" to ");
        newString.append(changedShape.sizeParamsToString(sizeParams2));
        newString.append(" from t=");
        newString.append(time1);
        newString.append(" to t=");
        newString.append(time2);
        break;
      case ROTATE:
        newString.append(" rotates from ");
        newString.append(rotation1);
        newString.append(" degrees to ");
        newString.append(rotation2);
        newString.append(" degrees from t=");
        newString.append(time1);
        newString.append(" to t=");
        newString.append(time2);
        break;
      case APPEAR:
        newString.append(" appears at t=");
        newString.append(time1);
        break;
      case DISAPPEAR:
        newString.append(" disappears at t=");
        newString.append(time1);
        break;
      case STILL:
        newString.append(" is still at position ");
        newString.append(position1.toString());
        break;

      default:
        throw new IllegalArgumentException("Operation type not found.");
    }
    return newString.toString();
  }
}
