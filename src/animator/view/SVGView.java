package animator.view;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import animator.controller.IController;
import animator.model.IAnimatedShape;
import animator.model.IAnimations;
import animator.model.IPosition2D;
import animator.model.SimpleAnimationModel;

/**
 * A SVG view produces a XML output that represents the SVG view. It parses the model to the SVG
 * format.
 */
public class SVGView extends AbstractView {
  private String viewText;

  /**
   * Constructor for a SVG view.
   *
   * @param animationModel The model to take in to parse.
   * @param tempo          Tempo, representing the desired ticks per second.
   * @param output         The output file name string to output.
   */
  public SVGView(SimpleAnimationModel animationModel, double tempo, String output) {
    super(animationModel, tempo);
    makeSVG();

    try (PrintWriter out = new PrintWriter(output)) {
      out.println(viewText);
    } catch (FileNotFoundException e) {
      // Nothing here
    }
  }

  /**
   * Second constructor to write to the console.
   *
   * @param animationModel The model to take in to parse.
   * @param tempo          Tempo, representing the desired ticks per second.
   */
  public SVGView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);
    makeSVG();

    System.out.print(viewText);
  }

  /**
   * Constructs a SVG view and saves it in the viewText field. This will call the shapeCommand
   * method to fill in the details for each shape.
   */
  private void makeSVG() {
    StringBuilder sb = new StringBuilder();
    sb.append("<!DOCTYPE html>\n");
    sb.append("<html>\n");
    sb.append("<body>\n");
    sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"1000\" " +
            "height=\"1000\">\n");
    for (int i = 0; i < shapes.size(); i++) {
      sb.append(shapeCommand(shapes.get(i)) + "\n");
    }
    sb.append("</svg>\n");
    sb.append("</body>\n");
    sb.append("</html>\n");

    viewText = sb.toString();
  }

  /**
   * Constructs the animation of a shape.
   *
   * @param ani The animation of a shape to parse.
   * @return The string that represents a single animation that belongs to a shape.
   */
  private String animate(IAnimations ani) {
    switch (ani.getAnimateType()) {
      case MOVE:
        return "<animateMotion path=\"M " + (ani.getPosition1().getX()
                - ani.getChangedShape().getInitialPosition().getX()) + " "
                + (ani.getPosition1().getY() - ani.getChangedShape().getInitialPosition().getY())
                + " L " + (ani.getPosition2().getX() - ani.getChangedShape().getInitialPosition()
                .getX()) + " "
                + (ani.getPosition2().getY() - ani.getChangedShape().getInitialPosition().getY())
                + "\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\""
                + (ani.getTime2() - ani.getTime1()) / tempo + "s\" fill=\"freeze\" />";

      case CHANGECOLOR:
        return "<animate attributeName=\"fill\" attributeType=\"CSS\" from=\"RGB"
                + ani.getColor1().toStringSVG() + "\" to=\"RGB" + ani.getColor2().toStringSVG()
                + "\" begin=\"" + ani.getTime1() / tempo + "s\" dur=\""
                + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" />";

      case CHANGESIZE:
        return changeSize(ani);

      case ROTATE:
        IPosition2D center = model.calcShapeCenter(ani.getChangedShape().getShapeType(),
                ani.getPosition1(), ani.getSizeParams1());
        return "<animateTransform attributeName=\"transform\" attributeType=\"XML\" type=\"rotate"
                + "\" from=\"" + ani.getRotation1() + " " + center.getX() + " " + center.getY()
                + "\" to=\"" + ani.getRotation2() + " " + center.getX() + " " + center.getY()
                + "\" begin=\"" + ani.getTime1() / tempo + "s\" dur=\"" + ((ani.getTime2()
                - ani.getTime1()) / tempo) + "s\" additive=\"sum\" fill=\"freeze\" />";
      case APPEAR:
        return "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" />";

      case DISAPPEAR:
        /*return "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"hidden\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" />";*/
        return "";

      default:
        throw new IllegalArgumentException("Animation type not found.");
    }
  }

  /**
   * Constructs a shape to initialise and all animations that belong to this shape.
   *
   * @param shape The shape to be described in the SVG commands.
   * @return A string that contains the definition and all animations of a single shape.
   */
  private String shapeCommand(IAnimatedShape shape) {
    String animateCommands = "";
    for (int i = 0; i < animations.size(); i++) {
      if (animations.get(i).getChangedShape().getShapeName().equals(shape.getShapeName())
              && animations.get(i).getChangedShape().getShapeType().equals(shape.getShapeType())) {
        animateCommands = animateCommands + "\n" + animate(animations.get(i));
      }
    }
    switch (shape.getShapeType()) {
      case RECTANGLE:
        return "<rect id=\"" + shape.getShapeName() + "\" x=\"" + shape.getInitialPosition().getX()
                + "\" y=\"" + shape.getInitialPosition().getY() + "\" width=\""
                + shape.getInitialSize().get(0) + "\" height=\"" + shape.getInitialSize().get(1)
                + "\" style=\"fill:RGB" + shape.getInitialColor().toStringSVG() +
                "\" visibility=\"hidden\">"
                + animateCommands
                + "</rect>";
      case CIRCLE:
        return "<circle id=\"" + shape.getShapeName() + "\" cx=\""
                + shape.getInitialPosition().getX()
                + "\" cy=\"" + shape.getInitialPosition().getY() + "\" r=\""
                + shape.getInitialSize().get(0) + "\" stroke=RGB\""
                + shape.getInitialColor().toStringSVG()
                + "\" />"
                + animateCommands
                + "</circle>";
      case SQUARE:
        return "<rect id=\"" + shape.getShapeName() + "\" x=\"" + shape.getInitialPosition().getX()
                + "\" y=\"" + shape.getInitialPosition().getY() + "\" width=\""
                + shape.getInitialSize().get(0) + "\" height=\"" + shape.getInitialSize().get(0)
                + "\" style=\"fill:RGB" + shape.getInitialColor().toStringSVG() + "\">"
                + animateCommands
                + "</rect>";
      case OVAL:
        return "<ellipse cx=\"" + shape.getInitialPosition().getX() + "\" cy=\""
                + shape.getInitialPosition().getY() + "\" rx=\"" + shape.getInitialSize().get(0)
                + "\" ry=\"" + shape.getInitialSize().get(1) + "\" style=\"fill:RGB"
                + shape.getInitialColor().toStringSVG() + "\" visibility=\"hidden\">"
                + animateCommands
                + "</ellipse>";

      default:
        throw new IllegalArgumentException("Shape type not found");
    }
  }

  /**
   * Exclusively changes the size of a shape since changing size for every shape demands different
   * commands.
   *
   * @param ani The animation object that contains the details of changing a shape's size.
   * @return The string that describes the size change of a shape in SVG format.
   */
  private String changeSize(IAnimations ani) {
    IAnimatedShape shape = ani.getChangedShape();
    switch (shape.getShapeType()) {
      case RECTANGLE:
        return "<animate attributeName=\"width\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo
                + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\""
                + ani.getSizeParams1().get(0) + "\" to=\""
                + ani.getSizeParams2().get(0) + "\" /> \n" +
                "<animate attributeName=\"height\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\"" + ani.getSizeParams1().get(1) + "\" to=\""
                + ani.getSizeParams2().get(1) + "\" />";
      case CIRCLE:
        return "<animate attributeName=\"r\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo
                + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\""
                + ani.getSizeParams1().get(0) + "\" to=\""
                + ani.getSizeParams2().get(0) + "\" />";
      case SQUARE:
        return "<animate attributeName=\"width\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo
                + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\""
                + ani.getSizeParams1().get(0) + "\" to=\""
                + ani.getSizeParams2().get(0) + "\" /> \n" +
                "<animate attributeName=\"height\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\"" + ani.getSizeParams1().get(0) + "\" to=\""
                + ani.getSizeParams2().get(0) + "\" />";
      case OVAL:
        return "<animate attributeName=\"rx\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo
                + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\""
                + ani.getSizeParams1().get(0) + "\" to=\""
                + ani.getSizeParams2().get(0) + "\" /> \n" +
                "<animate attributeName=\"ry\" attributeType=\"XML\" begin=\""
                + ani.getTime1() / tempo + "s\" dur=\"" + (ani.getTime2() - ani.getTime1()) / tempo
                + "s\" fill=\"freeze\" from=\"" + ani.getSizeParams1().get(1) + "\" to=\""
                + ani.getSizeParams2().get(1) + "\" />";

      default:
        throw new IllegalArgumentException("Shape type not found");
    }
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
   * This method is used by both the Visual and Interactive views. It plays an
   * animation starting from the given tick. This method schedules all the tasks required
   * each tick to draw an animation. The method creates a task based on the timeline and
   * the animations within the timeline.
   * @param startTime   starting tick
   */
  @Override
  public void startAnimation(int startTime) {
    throw new UnsupportedOperationException("This view does not support visual views.");
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
   * This method is only used by the Interactive view. It plays a paused animation.
   * When unpausing/playing, the animation continues playing from the tick it was
   * paused at.
   */
  @Override
  public void pauseAnimation() {
    throw new UnsupportedOperationException("This view does not support animation pausing.");
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
   * This method is only used by the Interactive view. It toggles the play/pause
   * function and updates the view to state whether the animation can be played
   * or paused
   */
  @Override
  public void togglePlayOrPause() {
    throw new UnsupportedOperationException("This view does not support pausing.");
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
   * @param subsetModel subset model
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