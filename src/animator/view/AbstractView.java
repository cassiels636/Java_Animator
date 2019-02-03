package animator.view;

import animator.model.IAnimatedShape;
import animator.model.IAnimations;
import animator.model.SimpleAnimationModel;

import java.util.List;

/**
 * This class implements the view interface and holds the values that are used
 * and shared by all three view types. This class also extends the JFrame class
 * which is utilized by the visual view.
 */
public abstract class AbstractView implements ViewInterface {
  protected SimpleAnimationModel model;
  protected List<IAnimatedShape> shapes;
  protected List<IAnimations> animations;
  protected List<List<IAnimations>> timeline;
  protected double tempo;
  protected boolean isLooped;

  /**
   * Constructor for an abstract view.
   * @param animationModel The model to transfer to a view.
   * @param tempo Speed of the animation, as ticks per second.
   */
  public AbstractView(SimpleAnimationModel animationModel, double tempo) {
    model = animationModel;
    this.shapes = model.getShapes();
    this.animations = model.getAnimations();
    this.timeline = model.getTimeline();

    if (tempo > 0) {
      this.tempo = tempo;
    }
    else {
      throw new IllegalArgumentException("The tempo cannot be equal to or fall below 0");
    }
  }

  /**
   * This returns the current tempo of the animation.
   * @return current tempo
   */
  @Override
  public double getTempo() {
    return tempo;
  }

  /**
   * This updates the tempo to the given values.
   * @param tempo    new tempo
   */
  @Override
  public void setTempo(double tempo) {
    this.tempo = tempo;
  }
}
