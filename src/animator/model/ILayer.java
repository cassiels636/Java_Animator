package animator.model;

import java.util.List;

/**
 * This is the interface for the layer class. Layers hold a list of
 * shapes located in the layer. It also holds its name and its order number.
 * The lower the order number the closest it is to being the bottom layer.
 */
public interface ILayer {

  /**
   * Adds a shape to this layer.
   * @param shape   shape to be added
   */
  void addShape(IAnimatedShape shape);

  String getLayerName();

  int getOrder();

  List<IAnimatedShape> getLayerShapes();
}
