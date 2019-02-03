package animator.model;

import java.util.List;

import animator.model.enums.AnimateTypes;
import animator.model.enums.ShapeType;

/**
 * The interface for the SimpleAnimation model. Consist of methods that can be used to
 * create an animation. This includes create shapes and defining their characteristics
 * and creating animations performed on shapes and their characteristics.
 */
public interface SimpleAnimationModel {
  /**
   * Creates a shape by specifying the initial name, shape type, color, and size.
   * Also specifies the initial position and at what point in time the shape
   * appears and disappears.
   * @param name    shape name
   * @param type    shape type
   * @param color1  shape color
   * @param initial shape's initial position
   * @param params  shape's size
   * @param time1   time of appearance
   * @param time2   time of disappearance
   */
  void createShape(String name, ShapeType type, IRGB color1, IPosition2D initial,
                   List<Double> params, Integer time1, Integer time2);

  /**
   * Overloaded method for creating a shape. Includes all parameters of the previous
   * create shape method plus a parameter to specify the layer the shape is in.
   *
   * @param name      shape name
   * @param type      shape type
   * @param color1    shape color
   * @param initial   shape's initial position
   * @param params    shape's size
   * @param time1     time of appearance
   * @param time2     time of disappearance
   * @param layer     layer the shape is in
   */
  void createShape(String name, ShapeType type, IRGB color1, IPosition2D initial,
                   List<Double> params, Integer time1, Integer time2, String layer);

  /**
   * Copies a given shape and adds in into the model.
   * @param shape   shape to copy
   */
  void copyShape(IAnimatedShape shape);

  /**
   * Creates and adds a new layer to the animation with the specified
   * layer name and order number. The lower the order number the closer
   * the layer is to being the bottom-most layer.
   * @param name    layer name
   * @param order   layer order number
   */
  void addLayer(String name, int order);

  /**
   * Sets the layer of the shape with specified name to the given layer.
   *
   * @param shape   shape name
   * @param layer   layer to set shape to
   */
  void setShapeLayer(String shape, ILayer layer);

  /**
   * Copies a given animation and adds in into the model.
   * @param animate   animation to copy
   */
  void copyAnimation(IAnimations animate);

  /**
   * Gets a specified shape in the animation.
   * @param shapeIndex    shape's index
   * @return              shape
   */
  IAnimatedShape getShape(int shapeIndex);

  /**
   * This returns a shape with a name that matches the given name.
   * @param name    shape name
   * @return        shape with shape name
   */
  IAnimatedShape getShapeByName(String name);

  /**
   * Gets the list of shapes.
   * @return  shape list
   */
  List<IAnimatedShape> getShapes();

  /**
   * Gets the list of animations.
   * @return  animation list
   */
  List<IAnimations> getAnimations();

  /**
   * Gets the timeline list.
   * @return  timeline
   */
  List<List<IAnimations>> getTimeline();

  /**
   * Moves a specified shape to a different position at the specified
   * point in time.
   * @param shape         shape to be moved
   * @param newPosition   shape's new position
   * @param time1         beginning time of move
   * @param time2         end time of move
   */
  void moveShape(IAnimatedShape shape, IPosition2D newPosition, Integer time1, Integer time2);

  /**
   * Changes the color of the specified shape.
   * @param shape     shape to be changed
   * @param newColor  shape's new color
   * @param time1     beginning time of color change
   * @param time2     end time of color change
   */
  void changeShapeColor(IAnimatedShape shape, IRGB newColor, Integer time1,
                        Integer time2);

  /**
   * Changes the size params of a given shape.
   * @param shape           shape to be changed
   * @param newSizeParams   shape's new size
   * @param time1           beginning time of size change
   * @param time2           end time of size change
   */
  void changeShapeSize(IAnimatedShape shape, List<Double> newSizeParams, Integer time1,
                       Integer time2);

  /**
   * Rotates a given shape to the specified rotation(given in degress).
   *
   * @param shape     shape to be changed
   * @param rotation  position(in degrees) to rotate shape to
   * @param time1     beginning time of rotation
   * @param time2     end time of rotation
   */
  void rotateShape(IAnimatedShape shape, Integer rotation, Integer time1, Integer time2);

  /**
   * Get the entire animation description as a String for all shapes.
   * @return   String representation of animation
   */
  String printAnimation();

  /**
   * Get a single shapes animation description as a String.
   * @param shapeIndex  shape's index
   * @return            String representation of shape
   */
  String getShapeStatus(int shapeIndex);

  /**
   * Removes a specified animation from the List of animations.
   * @param shape   shape being animated
   * @param type    animation type
   * @param time    time of animation
   */
  void removeAnimation(IAnimatedShape shape, AnimateTypes type, Integer time);

  /**
   * Removes a specified shape and its animations from the animation.
   * @param shapeIndex  shape index to be removed
   */
  void removeShape(int shapeIndex);

  /**
   * Removes a specified shape and its animations from the animation.
   * @param name  shape name to be removed
   */
  void removeShapeByName(String name);

  /**
   * This method calculates a shape's position at a given time. If a shape is
   * moving at the given time, then this method will return the new position the
   * shape is moving to.
   * @param shape   shape
   * @param time    time of position
   * @return        shape's Position2D at time
   */
  IPosition2D calcCurrPosition(IAnimatedShape shape, int time);

  /**
   * This method calculates a shape's color at a given time. If a shape is
   * changing color at the given time, then this method will return the new
   * color the shape is changing to.
   * @param shape   shape
   * @param time    time of color
   * @return        shape's RGB at time
   */
  IRGB calcCurrColor(IAnimatedShape shape, int time);

  /**
   * This method calculates a shape's size at a given time. If a shape is
   * changing size at the given time, then this method will return the new
   * size the shape is changing to.
   * @param shape   shape
   * @param time    time of size
   * @return        shape's size at time
   */
  List<Double> calcCurrSize(IAnimatedShape shape, int time);

  /**
   * Calculates a shape's rotation at a given time. If a shape is rotating at
   * the given time, then this method will return the new rotation the shape
   * is changing to,
   * @param shape   shape
   * @param time    time of rotation
   * @return        shape's current rotation in degrees
   */
  Integer calcCurrRotation(IAnimatedShape shape, int time);

  /**
   * Calculates a shape's center point given the shape type, its reference
   * point position, and its size.
   * @param type    shape type
   * @param pos     shape's ref point position
   * @param size    shape's size parameters
   * @return        shape's center position
   */
  IPosition2D calcShapeCenter(ShapeType type, IPosition2D pos, List<Double> size);
}
