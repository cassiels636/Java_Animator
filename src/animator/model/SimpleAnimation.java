package animator.model;

import java.util.ArrayList;
import java.util.List;

import animator.model.enums.AnimateTypes;
import animator.model.enums.ShapeType;
import animator.util.TweenModelBuilder;

/**
 * This is the class that represents the model of the SimpleAnimation.
 * An instance of this model represents an animation through shapes, their
 * characteristics, and changes to their characteristics over time.
 */
public class SimpleAnimation implements SimpleAnimationModel {
  private List<IAnimatedShape> shapes;
  private List<IAnimations> animations;
  private List<List<IAnimations>> timeline;
  private List<ILayer> layers;

  /**
   * The constructor for the SimpleAnimation that initializes the Lists
   * that hold all the shapes and animations in the model.
   */
  public SimpleAnimation() {
    shapes = new ArrayList<>();
    animations = new ArrayList<>();
    timeline = new ArrayList<>();
    layers = new ArrayList<>();
  }

  /**
   * Creates a new AnimatedShape object holding the shape's parameters.
   * Then adds the shape to the List of shapes and adds its appearance and
   * disappearance into the timeline based on the time they occur.
   * UPDATED APRIL 3RD, 2018:
   * - Adds the Appear and Disappear animation.
   * - Changed the adding to shapes list logic so it works with SVG.
   *
   * @param name    shape name
   * @param type    shape type
   * @param color1  shape color
   * @param initial shape's initial position
   * @param params  shape's size
   * @param time1   time of appearance
   * @param time2   time of disappearance
   */
  @Override
  public void createShape(String name, ShapeType type, IRGB color1,
                          IPosition2D initial, List<Double> params, Integer time1, Integer time2) {
    IAnimatedShape shape1 = new AnimatedShape(name, type, color1, initial, params, time1, time2);
    layers.add(new Layer("layer", 0));
    shape1.setLayer(layers.get(0));
    layers.get(0).addShape(shape1);
    shapes.add(shape1);

    if (timeline.size() < time2) {
      for (int i = timeline.size(); i <= time2; i++) {
        timeline.add(i, new ArrayList<>());
      }
    }
    timeline.get(time1).add(new ShapeAppears(shape1, time1));
    timeline.get(time2).add(new ShapeDisappears(shape1, time2));
    for (int i = (time1 + 1); i < time2; i++) {
      timeline.get(i).add(new StillShape(shape1, i));
    }

    // ADDS IN THE APPEAR AND DISAPPEAR TIMES TO animations
    addNewAnimInTimeOrder(new ShapeAppears(shape1, time1));
    addNewAnimInTimeOrder(new ShapeDisappears(shape1, time2));
  }

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
  @Override
  public void createShape(String name, ShapeType type, IRGB color1, IPosition2D initial,
                          List<Double> params, Integer time1, Integer time2, String layer) {
    IAnimatedShape shape1 = new AnimatedShape(name, type, color1, initial, params, time1, time2);

    for (int i = 0; i < layers.size(); i++) {
      if (layers.get(i).getLayerName().equals(layer)) {
        shape1.setLayer(layers.get(i));
        layers.get(i).addShape(shape1);
      }
    }
    shapes.add(shape1);

    if (timeline.size() < time2) {
      for (int i = timeline.size(); i <= time2; i++) {
        timeline.add(i, new ArrayList<>());
      }
    }

    addInitialShapeAnims(new ShapeAppears(shape1, time1), time1);
    addInitialShapeAnims(new ShapeDisappears(shape1, time2), time2);
    addNewAnimInTimeOrder(new ShapeAppears(shape1, time1));
    addNewAnimInTimeOrder(new ShapeDisappears(shape1, time2));

    for (int i = (time1 + 1); i < time2; i++) {
      addInitialShapeAnims(new StillShape(shape1, i), i);
    }
  }

  /**
   * Copies a given shape and adds in into the model.
   * @param shape   shape to copy
   */
  @Override
  public void copyShape(IAnimatedShape shape) {
    createShape(shape.getShapeName(), shape.getShapeType(), shape.getInitialColor(),
            shape.getInitialPosition(), shape.getInitialSize(), shape.getAppearTime(),
            shape.getDisappearT());
  }

  /**
   * Creates and adds a new layer to the animation with the specified
   * layer name and order number. The lower the order number the closer
   * the layer is to being the bottom-most layer. Sorts layers in a list
   * from smallest order number to highest.
   *
   * @param name    layer name
   * @param order   layer order number
   */
  @Override
  public void addLayer(String name, int order) {
    layers.add(new Layer(name, order));

    //Sort layers from smallest to largest order value
    for (int i = 0; i < layers.size() - 1; i++) {
      int min = i;
      for (int j = (i + 1); j < layers.size(); j++) {
        if (layers.get(j).getOrder() < layers.get(min).getOrder()) {
          min = j;
        }
      }
      ILayer temp = layers.get(min);
      layers.remove(min);
      layers.add(min, layers.get(i));
      layers.remove(i);
      layers.add(i, temp);
    }
  }

  /**
   * Sets the layer of the shape with specified name to the given layer.
   *
   * @param shape   shape name
   * @param layer   layer to set shape to
   */
  @Override
  public void setShapeLayer(String shape, ILayer layer) {
    getShapeByName(shape).setLayer(layer);
  }

  /**
   * Copies a given animation and adds in into the model.
   * @param animate   animation to copy
   */
  @Override
  public void copyAnimation(IAnimations animate) {
    if (getShapeByName(animate.getChangedShape().getShapeName()) != null) {
      addNewAnimInTimeOrder(animate);
      addNewAnimToTimeline(animate);
      updateBeginAnimaPositions();
      updateBeginAnimaColors();
      updateBeginAnimaSizes();
      updateBeginAnimaRotations();
    } else {
      throw new IllegalArgumentException("Animation's shape does not exist in animation.");
    }
  }

  /**
   * Gets a shape from the shape List based on the index.
   *
   * @param shapeIndex shape's index
   * @return specified shape
   */
  @Override
  public IAnimatedShape getShape(int shapeIndex) {
    IAnimatedShape animatedShape = shapes.get(shapeIndex);
    return animatedShape;
  }

  /**
   * This returns a shape with a name that matches the given name.
   * @param name    shape name
   * @return        shape with shape name
   */
  @Override
  public IAnimatedShape getShapeByName(String name) {
    for (int i = 0; i < shapes.size(); i++) {
      if (shapes.get(i).getShapeName().equals(name)) {
        return shapes.get(i);
      }
    }
    return null;
  }

  /**
   * Gets the list of shapes.
   * @return  shape list
   */
  @Override
  public List<IAnimatedShape> getShapes() {
    return this.shapes;
  }

  /**
   * Gets the list of animations.
   * @return  animation list
   */
  @Override
  public List<IAnimations> getAnimations() {
    updateBeginAnimaPositions();
    updateBeginAnimaColors();
    updateBeginAnimaSizes();
    updateBeginAnimaRotations();
    return this.animations;
  }

  /**
   * Gets the timeline list.
   * @return  timeline
   */
  @Override
  public List<List<IAnimations>> getTimeline() {
    updateBeginAnimaPositions();
    updateBeginAnimaColors();
    updateBeginAnimaSizes();
    updateBeginAnimaRotations();
    return this.timeline;
  }

  /**
   * Creates a new move Animation based on the parameters. Tests if its a valid
   * move and then adds it to the List of Animations and the timeline.
   *
   * @param shape       shape to be moved
   * @param newPosition shape's new position
   * @param time1       beginning time of move
   * @param time2       end time of move
   */
  @Override
  public void moveShape(IAnimatedShape shape, IPosition2D newPosition, Integer time1,
                        Integer time2) {
    IAnimations move = new MoveShape(shape, calcCurrPosition(shape, time1), newPosition,
            time1, time2);
    if (!isValidAnimation(move)) {
      throw new IllegalArgumentException("Invalid move");
    }

    addNewAnimInTimeOrder(move);
    addNewAnimToTimeline(move);
  }

  /**
   * Creates a new changeColor Animation based on the parameters. Tests if its a valid
   * changeColor and then adds it to the List of Animations and the timeline.
   *
   * @param shape    shape to be changed
   * @param newColor shape's new color
   * @param time1    beginning time of color change
   * @param time2    end time of color change
   */
  @Override
  public void changeShapeColor(IAnimatedShape shape, IRGB newColor, Integer time1,
                               Integer time2) {
    IAnimations colorChange = new ChangeShapeColor(shape, calcCurrColor(shape, time1),
            newColor, time1, time2);
    if (!isValidAnimation(colorChange)) {
      throw new IllegalArgumentException("Invalid color change");
    }

    addNewAnimInTimeOrder(colorChange);
    addNewAnimToTimeline(colorChange);
  }

  /**
   * Creates a new sizeChange Animation based on the parameters. Tests if its a valid
   * sizeChange and then adds it to the List of Animations and the timeline.
   *
   * @param shape         shape to be changed
   * @param newSizeParams shape's new size
   * @param time1         beginning time of size change
   * @param time2         end time of size change
   */
  @Override
  public void changeShapeSize(IAnimatedShape shape, List<Double> newSizeParams, Integer time1,
                              Integer time2) {
    IAnimations sizeChange = new ChangeShapeSize(shape, calcCurrSize(shape, time1),
            newSizeParams, time1, time2);
    if (!isValidAnimation(sizeChange)) {
      throw new IllegalArgumentException("Invalid size change");
    }

    addNewAnimInTimeOrder(sizeChange);
    addNewAnimToTimeline(sizeChange);
  }

  /**
   * Rotates a given shape to the specified rotation(given in degress).
   *
   * @param shape     shape to be changed
   * @param rotation  position(in degrees) to rotate shape to
   * @param time1     beginning time of rotation
   * @param time2     end time of rotation
   */
  @Override
  public void rotateShape(IAnimatedShape shape, Integer rotation, Integer time1, Integer time2) {
    IAnimations rotateShape = new RotateShape(shape, calcCurrRotation(shape, time1), rotation,
            time1, time2);
    if (!isValidAnimation(rotateShape)) {
      throw new IllegalArgumentException("Invalid rotation");
    }

    addNewAnimInTimeOrder(rotateShape);
    addNewAnimToTimeline(rotateShape);
  }

  /**
   * Returns the String representation of the entire animation including
   * all shapes and Animations.
   *
   * @return animation as a String
   */
  @Override
  public String printAnimation() {
    StringBuilder shapesString = new StringBuilder("Shapes:\n");
    StringBuilder animationString = new StringBuilder();

    for (int i = 0; i < shapes.size(); i++) {
      shapesString.append(shapes.get(i).toString());
      shapesString.append("\n\n");
    }

    for (int i = 0; i < animations.size(); i++) {
      if (animations.get(i).getAnimateType() != AnimateTypes.APPEAR
              && animations.get(i).getAnimateType() != AnimateTypes.DISAPPEAR) {
        animationString.append(animations.get(i).toString());
        if (i != (animations.size() - 1)) {
          animationString.append("\n");
        }
      }
    }
    return shapesString.append(animationString).toString();
  }

  /**
   * Returns the String representation of a shape based on the shape's index.
   *
   * @param shapeIndex shape's index
   * @return shape
   */
  @Override
  public String getShapeStatus(int shapeIndex) {
    return shapes.get(shapeIndex).toString();
  }

  /**
   * Removes an Animation from the timeline and animation List that is
   * occuring to a specified shape at a specified time.
   * The time does not have to be the beginning or the end of the Animation,
   * it can be at any point between the two.
   *
   * @param shape shape being animated
   * @param type  animation type
   * @param time  time of animation
   */
  @Override
  public void removeAnimation(IAnimatedShape shape, AnimateTypes type, Integer time) {
    Integer time1 = 0;
    Integer time2 = 0;
    for (int i = 0; i < timeline.get(time).size(); i++) {
      if (timeline.get(time).get(i).getChangedShape().equals(shape)
              && timeline.get(time).get(i).getAnimateType().equals(type)) {
        time1 = timeline.get(time).get(i).getTime1();
        time2 = timeline.get(time).get(i).getTime2();
        break;
      }
      if (i == (timeline.get(time).size() - 1)) {
        throw new IllegalArgumentException("Invalid animation to remove");
      }
    }
    for (int j = time1; j <= time2; j++) {
      for (int k = 0; k < timeline.get(j).size(); k++) {
        if (timeline.get(j).get(k).getChangedShape().equals(shape)
                && timeline.get(j).get(k).getAnimateType().equals(type)) {
          timeline.get(j).remove(k);
        }
      }
    }

    for (int i = 0; i < animations.size(); i++) {
      if (animations.get(i).getChangedShape().equals(shape)
              && animations.get(i).getAnimateType().equals(type)
              && (animations.get(i).getTime1().equals(time1))
              && (animations.get(i).getTime2().equals(time2))) {
        animations.remove(i);
        break;
      }
      if (i == (animations.size() - 1)) {
        throw new IllegalArgumentException("Invalid animation to remove");
      }
    }
  }

  /**
   * Removes a shape at the specified shapeIndex and all related Animations
   * from the animations and timeline Lists.
   *
   * @param shapeIndex shape index to be removed
   */
  @Override
  public void removeShape(int shapeIndex) {
    IAnimatedShape tempShape = shapes.get(shapeIndex);
    shapes.remove(shapeIndex);
    for (int i = 0; i < timeline.size(); i++) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if (timeline.get(i).get(j).getChangedShape().equals(tempShape)) {
          timeline.get(i).remove(j);
        }
      }
    }
    for (int i = 0; i < animations.size(); i++) {
      if (animations.get(i).getChangedShape().equals(tempShape)) {
        animations.remove(i);
      }
    }
  }

  /**
   * Removes a specified shape and its animations from the animation.
   * @param name  shape name to be removed
   */
  @Override
  public void removeShapeByName(String name) {
    for (int i = 0; i < shapes.size(); i++) {
      if (shapes.get(i).getShapeName().equals(name)) {
        removeShape(i);
      }
    }
  }

  /**
   * This method calculates a shape's position at a given time. If a shape is
   * moving at the given time, then this method will return the new position the
   * shape is moving to.
   *
   * @param shape shape
   * @param time  time of position
   * @return shape's Position2D at time
   */
  @Override
  public IPosition2D calcCurrPosition(IAnimatedShape shape, int time) {
    for (int i = time; i > -1; i--) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if ((timeline.get(i).get(j).getChangedShape() == shape)
                & (timeline.get(i).get(j).getAnimateType() == AnimateTypes.MOVE)) {
          return timeline.get(i).get(j).getPosition2();
        }
      }
    }
    return shape.getInitialPosition();
  }

  /**
   * This method calculates a shape's color at a given time. If a shape is
   * changing color at the given time, then this method will return the new
   * color the shape is changing to.
   *
   * @param shape shape
   * @param time  time of color
   * @return shape's RGB at time
   */
  @Override
  public IRGB calcCurrColor(IAnimatedShape shape, int time) {
    for (int i = time; i > -1; i--) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if ((timeline.get(i).get(j).getChangedShape() == shape)
                & (timeline.get(i).get(j).getAnimateType() == AnimateTypes.CHANGECOLOR)) {
          return timeline.get(i).get(j).getColor2();
        }
      }
    }
    return shape.getInitialColor();
  }

  /**
   * This method calculates a shape's size at a given time. If a shape is
   * changing size at the given time, then this method will return the new
   * size the shape is changing to.
   *
   * @param shape shape
   * @param time  time of size
   * @return shape's size at time
   */
  @Override
  public List<Double> calcCurrSize(IAnimatedShape shape, int time) {
    for (int i = time; i > -1; i--) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if ((timeline.get(i).get(j).getChangedShape() == shape)
                & (timeline.get(i).get(j).getAnimateType() == AnimateTypes.CHANGESIZE)) {
          return timeline.get(i).get(j).getSizeParams2();
        }
      }
    }
    return shape.getInitialSize();
  }

  /**
   * Calculates a shape's rotation at a given time. If a shape is rotating at
   * the given time, then this method will return the new rotation the shape
   * is changing to,
   * @param shape   shape
   * @param time    time of rotation
   * @return        shape's current rotation in degrees
   */
  @Override
  public Integer calcCurrRotation(IAnimatedShape shape, int time) {
    for (int i = time; i > -1; i--) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if ((timeline.get(i).get(j).getChangedShape() == shape)
                & (timeline.get(i).get(j).getAnimateType() == AnimateTypes.ROTATE)) {
          return timeline.get(i).get(j).getRotation2();
        }
      }
    }
    return shape.getInitialRotation();
  }

  /**
   * Calculates a shape's center point given the shape type, its reference
   * point position, and its size.
   * @param type    shape type
   * @param pos     shape's ref point position
   * @param size    shape's size parameters
   * @return        shape's center position
   */
  @Override
  public IPosition2D calcShapeCenter(ShapeType type, IPosition2D pos, List<Double> size) {
    switch (type) {
      case RECTANGLE:
        return new Position2D(pos.getX() + (size.get(0) / 2), pos.getY() + (size.get(0) / 2));
      case OVAL:
        return pos;
      case SQUARE:
        return new Position2D(pos.getX() + (size.get(0) / 2), pos.getY() + (size.get(0) / 2));
      case CIRCLE:
        return pos;
      default:
        throw new IllegalArgumentException("Invalid shape type");
    }
  }

  /**
   * Checks if the given Animation is valid and that it does not overlap
   * with another Animation of the same type occurring at the same time on
   * the same shape.
   *
   * @param animate Animation to validate
   * @return true, if Animation is valid
   */
  private boolean isValidAnimation(IAnimations animate) {
    for (int i = animate.getTime1(); i <= animate.getTime2(); i++) {
      for (int j = 0; j < timeline.get(i).size(); j++) {
        if ((timeline.get(i).get(j).getAnimateType() == animate.getAnimateType())
                && (animate.getChangedShape().equals(timeline.get(i).get(j).getChangedShape()))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Adds an Animation to the animations List in the order of beginning time.
   *
   * @param obj Animation to add
   */
  private void addNewAnimInTimeOrder(IAnimations obj) {
    if (animations.isEmpty()) {
      animations.add(obj);
    } else {
      for (int i = 0; i < animations.size(); i++) {
        if (obj.getTime1() <= animations.get(i).getTime1()) {
          animations.add(i, obj);
          break;
        } else if (i == (animations.size() - 1)) {
          animations.add(obj);
          break;
        }
      }
    }
  }

  /**
   * Adds an Animation to the timeline List at all time instances
   * that the Animation occurs during.
   *
   * @param obj Animation to add
   */
  private void addNewAnimToTimeline(IAnimations obj) {
    for (int i = obj.getTime1(); i <= obj.getTime2(); i++) {
      List<IAnimations> animations1 = timeline.get(i);
      if (animations1.isEmpty()) {
        animations1.add(obj);
      }
      else {
        for (int j = 0; j < animations1.size(); j++) {
          if (animations1.get(j).getChangedShape().equals(obj.getChangedShape())) {
            if (animations1.get(j).getAnimateType() == AnimateTypes.STILL) {
              animations1.remove(j);
            }
            animations1.add(j, obj);
            break;
          }
        }
      }
    }
  }

  /**
   * This method updates all positions of animations within the timeline and
   * animations Lists. If a move animation is added, this method will update
   * the animations after the added one to have the correct starting position.
   */
  private void updateBeginAnimaPositions() {
    IPosition2D currPosition;
    IPosition2D nextPosition;
    IAnimatedShape currShape;
    for (int i = 0; i < shapes.size(); i++) {
      currShape = shapes.get(i);
      currPosition = currShape.getInitialPosition();
      nextPosition = currPosition;
      for (int j = 0; j < timeline.size(); j++) {
        for (int k = 0; k < timeline.get(j).size(); k++) {
          if (timeline.get(j).get(k).getChangedShape().getShapeName()
                  .equals(currShape.getShapeName())) {
            timeline.get(j).get(k).setPosition1(currPosition);
            if (timeline.get(j).get(k).getAnimateType() == AnimateTypes.MOVE) {
              if (timeline.get(j).get(k).getTime2() == j) {
                nextPosition = timeline.get(j).get(k).getPosition2();
              }
            }
          }
        }
        currPosition = nextPosition;
      }
      currPosition = currShape.getInitialPosition();
      for (int j = 0; j < animations.size(); j++) {
        if (animations.get(j).getChangedShape().getShapeName().equals(currShape.getShapeName())) {
          animations.get(j).setPosition1(currPosition);
          if (animations.get(j).getAnimateType() == AnimateTypes.MOVE) {
            currPosition = animations.get(j).getPosition2();
          }
        }
      }
    }
  }

  /**
   * This method updates all colors of animations within the timeline and
   * animations Lists. If a change color animation is added, this method will
   * update the animations after the added one to have the correct starting
   * color.
   */
  private void updateBeginAnimaColors() {
    IRGB currColor;
    IRGB nextColor;
    IAnimatedShape currShape;
    for (int i = 0; i < shapes.size(); i++) {
      currShape = shapes.get(i);
      currColor = currShape.getInitialColor();
      nextColor = currColor;
      for (int j = 0; j < timeline.size(); j++) {
        for (int k = 0; k < timeline.get(j).size(); k++) {
          if (timeline.get(j).get(k).getChangedShape().getShapeName()
                  .equals(currShape.getShapeName())) {
            timeline.get(j).get(k).setColor1(currColor);
            if (timeline.get(j).get(k).getAnimateType() == AnimateTypes.CHANGECOLOR) {
              if (timeline.get(j).get(k).getTime2() == j) {
                nextColor = timeline.get(j).get(k).getColor2();
              }
            }
          }
        }
        currColor = nextColor;
      }
      currColor = currShape.getInitialColor();
      for (int j = 0; j < animations.size(); j++) {
        if (animations.get(j).getChangedShape().getShapeName().equals(currShape.getShapeName())) {
          animations.get(j).setColor1(currColor);
          if (animations.get(j).getAnimateType() == AnimateTypes.CHANGECOLOR) {
            currColor = animations.get(j).getColor2();
          }
        }
      }
    }
  }

  /**
   * This method updates all sizes of animations within the timeline and
   * animations Lists. If a change size animation is added, this method will
   * update the animations after the added one to have the correct starting size.
   */
  private void updateBeginAnimaSizes() {
    List<Double> currSize;
    List<Double> nextSize;
    IAnimatedShape currShape;
    for (int i = 0; i < shapes.size(); i++) {
      currShape = shapes.get(i);
      currSize = currShape.getInitialSize();
      nextSize = currSize;
      for (int j = 0; j < timeline.size(); j++) {
        for (int k = 0; k < timeline.get(j).size(); k++) {
          if (timeline.get(j).get(k).getChangedShape().getShapeName()
                  .equals(currShape.getShapeName())) {
            timeline.get(j).get(k).setSizeParams1(currSize);
            if (timeline.get(j).get(k).getAnimateType() == AnimateTypes.CHANGESIZE) {
              if (timeline.get(j).get(k).getTime2() == j) {
                nextSize = timeline.get(j).get(k).getSizeParams2();
              }
            }
          }
        }
        currSize = nextSize;
      }
      currSize = currShape.getInitialSize();
      for (int j = 0; j < animations.size(); j++) {
        if (animations.get(j).getChangedShape().getShapeName().equals(currShape.getShapeName())) {
          animations.get(j).setSizeParams1(currSize);
          if (animations.get(j).getAnimateType() == AnimateTypes.CHANGESIZE) {
            currSize = animations.get(j).getSizeParams2();
          }
        }
      }
    }
  }

  /**
   * This method updates all rotations of animations within the timeline and
   * animations Lists. If a rotate animation is added, this method will
   * update the animations after the added one to have the correct starting rotation.
   */
  private void updateBeginAnimaRotations() {
    Integer currRotation;
    Integer nextRotation;
    IAnimatedShape currShape;
    for (int i = 0; i < shapes.size(); i++) {
      currShape = shapes.get(i);
      currRotation = currShape.getInitialRotation();
      nextRotation = currRotation;
      for (int j = 0; j < timeline.size(); j++) {
        for (int k = 0; k < timeline.get(j).size(); k++) {
          if (timeline.get(j).get(k).getChangedShape().getShapeName()
                  .equals(currShape.getShapeName())) {
            this.timeline.get(j).get(k).setRotation1(currRotation);
            if (timeline.get(j).get(k).getAnimateType() == AnimateTypes.ROTATE) {
              if (timeline.get(j).get(k).getTime2() == j) {
                nextRotation = timeline.get(j).get(k).getRotation2();
              }
            }
          }
        }
        currRotation = nextRotation;
      }
      currRotation = currShape.getInitialRotation();
      for (int j = 0; j < animations.size(); j++) {
        if (animations.get(j).getChangedShape().getShapeName().equals(currShape.getShapeName())) {
          animations.get(j).setRotation1(currRotation);
          if (animations.get(j).getAnimateType() == AnimateTypes.ROTATE) {
            currRotation = animations.get(j).getRotation2();
          }
        }
      }
    }
  }

  /**
   * This method is only used by the create shape methods. It adds the shape's
   * APPEAR, DISAPPEAR, and STILL animations based on the shape's layer. So that
   * the shapes in the bottom-most layer would be first in the timeline List.
   *
   * @param a       animation to be added
   * @param time    tick when the animation occurs
   */
  private void addInitialShapeAnims(IAnimations a, int time) {
    if (timeline.get(time).isEmpty()) {
      timeline.get(time).add(a);
    }
    else {
      for (int i = 0; i < timeline.get(time).size(); i++) {
        if (timeline.get(time).get(i).getChangedShape().getLayer().getOrder()
                > a.getChangedShape().getLayer().getOrder()) {
          timeline.get(time).add(i, a);
          break;
        }
        else if (i == (timeline.get(time).size() - 1)) {
          timeline.get(time).add(a);
          break;
        }
      }
    }
  }

  public static final class Builder implements TweenModelBuilder<SimpleAnimationModel> {
    private SimpleAnimationModel model = new SimpleAnimation();

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addOval(String name, float cx, float cy,
                                                           float xRadius, float yRadius, float red,
                                                           float green, float blue, int startOfLife,
                                                           int endOfLife) {
      List<Double> params = new ArrayList<Double>();
      params.add((double) xRadius);
      params.add((double) yRadius);
      this.model.createShape(name, ShapeType.OVAL, new RGB(red, green, blue),
              new Position2D(cx, cy), params, startOfLife, endOfLife);
      // ADD THE APPEAR AND DISAPPEAR TIMES HERE.
      return this;
    }

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addOval(String name, String layer,
                                                           float cx, float cy, float xRadius,
                                                           float yRadius, float red,
                                                           float green, float blue,
                                                           int startOfLife, int endOfLife) {
      List<Double> params = new ArrayList<>();
      params.add((double) xRadius);
      params.add((double) yRadius);
      this.model.createShape(name, ShapeType.OVAL, new RGB(red, green, blue),
              new Position2D(cx, cy), params, startOfLife, endOfLife, layer);
      return this;
    }


    @Override
    public TweenModelBuilder<SimpleAnimationModel> addRectangle(String name, float lx, float ly,
                                                                float width, float height,
                                                                float red, float green, float blue,
                                                                int startOfLife, int endOfLife) {
      List<Double> params = new ArrayList<Double>();
      params.add((double) width);
      params.add((double) height);
      this.model.createShape(name, ShapeType.RECTANGLE, new RGB(red, green, blue),
              new Position2D(lx, ly), params, startOfLife, endOfLife);
      return this;
    }

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addRectangle(String name, String layer,
                                                                float lx, float ly, float width,
                                                                float height, float red,
                                                                float green, float blue,
                                                                int startOfLife, int endOfLife) {
      List<Double> params = new ArrayList<Double>();
      params.add((double) width);
      params.add((double) height);
      this.model.createShape(name, ShapeType.RECTANGLE, new RGB(red, green, blue),
              new Position2D(lx, ly), params, startOfLife, endOfLife, layer);
      return this;
    }

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addMove(String name, float moveFromX,
                                                           float moveFromY, float moveToX,
                                                           float moveToY, int startTime,
                                                           int endTime) {
      IAnimatedShape shapeToChange;
      if (shapeToChange(name) != null) {
        shapeToChange = shapeToChange(name);
        this.model.moveShape(shapeToChange, new Position2D(moveToX, moveToY), startTime, endTime);
      }

      return this;
    }

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addColorChange(String name, float oldR,
                                                                  float oldG, float oldB,
                                                                  float newR, float newG,
                                                                  float newB, int startTime,
                                                                  int endTime) {
      IAnimatedShape shapeToChange;
      if (shapeToChange(name) != null) {
        shapeToChange = shapeToChange(name);
        this.model.changeShapeColor(shapeToChange, new RGB(newR, newG, newB), startTime, endTime);
      }

      return this;
    }

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addScaleToChange(String name, float fromSx,
                                                                    float fromSy, float toSx,
                                                                    float toSy, int startTime,
                                                                    int endTime) {
      IAnimatedShape shapeToChange;
      if (shapeToChange(name) != null) {
        shapeToChange = shapeToChange(name);
        List<Double> newParams = new ArrayList<Double>();
        newParams.add((double) toSx);
        newParams.add((double) toSy);
        this.model.changeShapeSize(shapeToChange, newParams, startTime, endTime);
      }

      return this;
    }

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addRotateChange(String name, int fromRot,
                                                                   int toRot, int startTime,
                                                                   int endTime) {
      IAnimatedShape shapeToChange;
      if (shapeToChange(name) != null) {
        shapeToChange = shapeToChange(name);
        this.model.rotateShape(shapeToChange, toRot, startTime, endTime);
      }
      return this;
    }

    @Override
    public TweenModelBuilder<SimpleAnimationModel> addLayer(String name, int order) {
      this.model.addLayer(name, order);
      return this;
    }

    @Override
    public SimpleAnimationModel build() {
      return this.model;
    }

    private IAnimatedShape shapeToChange(String name) {
      IAnimatedShape shape;
      for (int i = 0; i < this.model.getShapes().size(); i++) {
        if (this.model.getShapes().get(i).getShapeName().equals(name)) {
          shape = this.model.getShapes().get(i);
          return shape;
        }
      }
      return null;
    }
  }
}
