package animator.model;

import java.util.List;

import animator.model.enums.AnimateTypes;

/**
 * This is the interface for the Animations class.
 * This holds all the methods that can be used to read and modify
 * characteristics of an animation performed on a shape.
 */
public interface IAnimations {

  Integer getTime1();

  Integer getTime2();

  AnimateTypes getAnimateType();

  IAnimatedShape getChangedShape();

  IPosition2D getPosition1();

  IPosition2D getPosition2();

  IRGB getColor1();

  IRGB getColor2();

  List<Double> getSizeParams1();

  List<Double> getSizeParams2();

  Integer getRotation1();

  Integer getRotation2();

  void setPosition1(IPosition2D position1);

  void setColor1(IRGB color1);

  void setSizeParams1(List<Double> sizeParams1);

  void setRotation1(Integer rotation1);
}
