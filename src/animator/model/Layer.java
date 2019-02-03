package animator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class that represents a layer in the animation. A layer holds
 * a list of shapes in the layer, the shape name, and order number of the
 * layer. The lower the order number the closer the layer is to being the
 * bottom-most layer.
 */
public class Layer extends SimpleAnimation implements ILayer {
  private List<IAnimatedShape> layerShapes;
  private String layerName;
  private int order;

  /**
   * Constructor for a layer.
   * @param layerName   name of the layer
   * @param order       order number of the layer
   */
  public Layer(String layerName, int order) {
    this.layerName = layerName;
    this.order = order;
    layerShapes = new ArrayList<>();
  }

  /**
   * Adds a shape to this layer.
   * @param shape   shape to be added
   */
  @Override
  public void addShape(IAnimatedShape shape) {
    layerShapes.add(shape);
  }

  @Override
  public String getLayerName() {
    return this.layerName;
  }

  @Override
  public int getOrder() {
    return order;
  }

  @Override
  public List<IAnimatedShape> getLayerShapes() {
    return this.layerShapes;
  }
}
