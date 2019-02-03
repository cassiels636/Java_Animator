package animator.view;

import animator.controller.IController;
import animator.model.SimpleAnimationModel;

/**
 * This is the interface for the view. All the views are created
 * inside their own constructors. The only view that can be edited/interacted
 * with is the Interactive view. The methods here can be used to modify the
 * Interactive view. In all other views, these methods are Unsupported.
 */
public interface ViewInterface {

  /**
   * This method is used to connect the controller(listener) to the Interactive view
   * so that the user can interact with the view.
   * @param listener  controller
   */
  void setListener(IController listener);

  /**
   * This updates the tempo to the given values.
   * @param newTempo    new tempo
   */
  void setTempo(double newTempo);

  /**
   * This method is used only by the Interactive view. It assigns the tempo to the new
   * given value, updates the the tempo shown in the view, and continues running or pausing
   * the animation at the current tick.
   *
   * @param newTempo new tempo
   */
  void updateTempo(double newTempo);

  /**
   * This returns the current tempo of the animation.
   * @return  current tempo
   */
  double getTempo();

  /**
   * This method is only used by the Interactive view. It toggles the loop function of the
   * animation and continues playing or pausing the animation.
   */
  void loopAnimation();

  /**
   * This method is only used by the Interactive view and restarts the animation from the
   * beginning.
   */
  void restartAnimation();

  /**
   * This method is only used by the Interactive view. It plays a paused animation.
   * When unpausing/playing, the animation continues playing from the tick it was
   * paused at.
   */
  void pauseAnimation();

  /**
   * This method is only used by the Interactive view. It toggles the play/pause
   * function and updates the view to state whether the animation can be played
   * or paused
   */
  void togglePlayOrPause();

  /**
   * This method exports the model into the specified SVG file.
   * @param fileName  SVG file name
   */
  void svgAnimation(String fileName);

  /**
   * This method is only used by the Interactive view. It adds a shape chosen by the user
   * to the new subset model.
   * @param shapeName    action by user that includes shape
   * @param subsetModel  subset model
   */
  void addToSubset(String shapeName, SimpleAnimationModel subsetModel);

  /**
   * This method opens a dialog box that displays the list of shapes currently
   * in the subset. It also includes a description of how to remove shapes.
   * @param subsetModel   subset model
   */
  void showSubsetList(SimpleAnimationModel subsetModel);

  /**
   * This method is only used by the Interactive view. It plays the subset animation
   * from the given starting tick in the current window.
   * @param subsetStart starting tick
   * @param subsetModel subset model
   */
  void playSubset(int subsetStart, SimpleAnimationModel subsetModel);

  /**
   * Generates a SVG view based on the subset selected in the interface, saves the file to a user
   * specified location.
   *
   * @param fileName The file name to save the SVG file as.
   * @param subsetModel   subset model
   */
  void svgSubset(String fileName, SimpleAnimationModel subsetModel);

  /**
   * This method opens a dialog box with the specified text.
   * @param dialog  dialop string
   */
  void createMessageDialog(String dialog);

  /**
   * This method is used by both the Visual and Interactive views. It plays an
   * animation starting from the given tick. This method schedules all the tasks required
   * each tick to draw an animation. The method creates a task based on the timeline and
   * the animations within the timeline.
   * @param startTime   starting tick
   */
  void startAnimation(int startTime);

  /**
   * This method is only used by the Interactive view. It plays the animation based on a
   * given tick that can be supplied by the scrubber/slider on the UI. It goes and draws
   * an animation at the given tick.
   *
   * @param tick    tick at which to draw the animation
   */
  void scrubberPlay(int tick);
}
