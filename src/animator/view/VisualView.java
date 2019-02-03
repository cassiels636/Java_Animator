package animator.view;

import animator.controller.IController;
import animator.model.SimpleAnimationModel;

/**
 * This class represents a visual view. It creates a window and
 * draws an animation in the window based on a given model.
 */
public class VisualView extends AbstractVisualView {
  /**
   * Constructor for a visual view.
   *
   * @param animationModel Model to represent in a visual view.
   * @param tempo          Tempo, representing the desired ticks per second.
   */
  public VisualView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);

    frame.pack();
    frame.setVisible(true);
    startAnimation(0);
  }

  /**
   * This method is used only by the Interactive view. It assigns the tempo to the new
   * given value, updates the the tempo shown in the view, and continues running or pausing
   * the animation at the current tick.
   *
   * @param newTempo new tempo
   */
  @Override
  public void updateTempo(double newTempo) {
    throw new UnsupportedOperationException("This view does not support tempo updates.");
  }

  /**
   * This method is only used by the Interactive view. It plays a paused animation.
   * When unpausing/playing, the animation continues playing from the tick it was
   * paused at.
   */
  @Override
  public void pauseAnimation() {
    throw new UnsupportedOperationException("This view does not support pausing.");
  }

  /**
   * This method is only used by the Interactive view. It toggles the play/pause
   * function and updates the view to state whether the animation can be played
   * or paused
   */
  @Override
  public void togglePlayOrPause() {
    throw new UnsupportedOperationException("This view does not support pausing.");
  }

  /**
   * This method is used to connect the controller(listener) to the Interactive view
   * so that the user can interact with the view.
   * @param listener  controller
   */
  @Override
  public void setListener(IController listener) {
    throw new UnsupportedOperationException("This view does not support listeners.");
  }

  /**
   * This method is only used by the Interactive view. It toggles the loop function of the
   * animation and continues playing or pausing the animation.
   */
  @Override
  public void loopAnimation() {
    throw new UnsupportedOperationException("This view does not support animation looping.");
  }

  /**
   * This method is only used by the Interactive view and restarts the animation from the
   * beginning.
   */
  @Override
  public void restartAnimation() {
    throw new UnsupportedOperationException("This view does not support animation restart.");
  }

  /**
   * This method exports the model into the specified SVG file.
   * @param fileName  SVG file name
   */
  @Override
  public void svgAnimation(String fileName) {
    throw new UnsupportedOperationException("This view does not support exporting to svg files.");
  }

  /**
   * This method is only used by the Interactive view. It adds a shape chosen by the user
   * to the new subset model.
   * @param shapeName    action by user that includes shape
   * @param subsetModel  subset model
   */
  @Override
  public void addToSubset(String shapeName, SimpleAnimationModel subsetModel) {
    throw new UnsupportedOperationException("This view does not support subset animations.");
  }

  /**
   * This method opens a dialog box that displays the list of shapes currently
   * in the subset. It also includes a description of how to remove shapes.
   * @param subsetModel   subset model
   */
  @Override
  public void showSubsetList(SimpleAnimationModel subsetModel) {
    throw new UnsupportedOperationException("This view does not support subset animations.");
  }

  /**
   * This method is only used by the Interactive view. It plays the subset animation
   * from the given starting tick in the current window.
   * @param subsetStart starting tick
   * @param subsetModel subset model
   */
  @Override
  public void playSubset(int subsetStart, SimpleAnimationModel subsetModel) {
    throw new UnsupportedOperationException("This view does not support subset animations.");
  }

  /**
   * Generates a SVG view based on the subset selected in the interface, saves the file to a user
   * specified location.
   *
   * @param fileName The file name to save the SVG file as.
   * @param subsetModel subset model
   */
  @Override
  public void svgSubset(String fileName, SimpleAnimationModel subsetModel) {
    throw new UnsupportedOperationException("This view does not support subset animations.");
  }

  /**
   * This method is only used by the Interactive view. It plays the animation based on a
   * given tick that can be supplied by the scrubber/slider on the UI. It goes and draws
   * an animation at the given tick.
   *
   * @param tick    tick at which to draw the animation
   */
  @Override
  public void scrubberPlay(int tick) {
    throw new UnsupportedOperationException("This view does not support scrubbing");
  }

  /**
   * This method opens a dialog box with the specified text.
   * @param dialog  dialop string
   */
  @Override
  public void createMessageDialog(String dialog) {
    throw new UnsupportedOperationException("This view does not support message dialogs.");
  }
}