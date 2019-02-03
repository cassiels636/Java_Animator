package animator.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import animator.model.IAnimations;
import animator.model.SimpleAnimationModel;
import animator.model.enums.ShapeType;

/**
 * This class extends the AbstractView class. It holds the methods
 * in order to create a window or frame and paint on that window. Both
 * the visual and interactive views extend from this class.
 */
public abstract class AbstractVisualView extends AbstractView {
  private List<Float> red;
  private List<Float> green;
  private List<Float> blue;
  private List<ShapeType> shapeType;
  private List<Integer> xPosition;
  private List<Integer> yPosition;
  private List<List<Double>> size;
  private List<Integer> rotation;
  private Integer animationPeriod;
  protected Timer timer;
  protected int currTick;
  protected boolean isPaused;

  protected JFrame frame;
  protected JPanel mainPanel;
  protected DrawingPane drawingPanel;
  protected JScrollPane drawingScollPane;

  /**
   * Constructor for an abstract view.
   *
   * @param animationModel The model to transfer to a view.
   * @param tempo          Speed of the animation, as ticks per second.
   */
  public AbstractVisualView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);
    timer = new Timer();
    animationPeriod = (timeline.size());// - 1) * 100;
    isLooped = false;
    isPaused = true;
    currTick = 0;

    frame = new JFrame("Simple Animation!");
    frame.setLocation(200, 25);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(true);
    frame.setPreferredSize(new Dimension(1000, 600));
    frame.setLayout(new BorderLayout());

    drawingPanel = new DrawingPane();
    drawingScollPane = new JScrollPane(drawingPanel);
    drawingScollPane.setPreferredSize(new Dimension(1000, 600));

    mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(drawingScollPane, BorderLayout.CENTER);
    frame.add(mainPanel, BorderLayout.CENTER);
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
    int scheduleTick = startTime;
    //currTick = startTime;
    TimerTask task;

    for (int i = 0; i < timeline.size(); i++) {
      //final int FINALI = currTick;
      final int FINALI = scheduleTick;
      List<IAnimations> animationTime = timeline.get(FINALI);

      task = new TimerTask() {
        @Override
        public void run() {
          initializeParams();
          addShapeParamsAtTimeT(animationTime, FINALI, timeline);
          drawingPanel.repaint();
          currTick = (currTick + 1) % timeline.size();
        }
      };
      scheduleTimerTasks(task, i);
      scheduleTick = (scheduleTick + 1) % timeline.size();
      //currTick = (currTick + 1) % timeline.size();
      if ((!isLooped) & (scheduleTick == 0) & (startTime != 0)) {
        break;
      }
    }
  }

  /**
   * This method initializes the param lists that will be used to
   * draw shapes.
   */
  protected void initializeParams() {
    red = new ArrayList<>();
    green = new ArrayList<>();
    blue = new ArrayList<>();
    shapeType = new ArrayList<>();
    xPosition = new ArrayList<>();
    yPosition = new ArrayList<>();
    size = new ArrayList<>();
    rotation = new ArrayList<>();
  }

  /**
   * This method creates a shape in the window using the Graphics class.
   *
   * @param g graphics
   */
  private void drawShape(Graphics g, int index) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(new Color(red.get(index), green.get(index), blue.get(index)));
    AffineTransform oldTransform = g2d.getTransform();
    AffineTransform newTransform = g2d.getTransform();

    switch (shapeType.get(index)) {
      case RECTANGLE:
        Rectangle2D rect = new Rectangle2D.Double(xPosition.get(index), yPosition.get(index),
                size.get(index).get(0).intValue(), size.get(index).get(1).intValue());
        newTransform.rotate(Math.toRadians(rotation.get(index)),rect.getCenterX(),
                + rect.getCenterY());
        g2d.setTransform(newTransform);
        g2d.fill(rect);
        break;
      case OVAL:
        Ellipse2D oval = new Ellipse2D.Double(xPosition.get(index), yPosition.get(index),
                (size.get(index).get(0).intValue() * 2), (size.get(index).get(1).intValue() * 2));
        newTransform.rotate(Math.toRadians(rotation.get(index)), oval.getCenterX(),
                oval.getCenterY());
        g2d.setTransform(newTransform);
        g2d.fill(oval);
        break;
      case SQUARE:
        Rectangle2D square = new Rectangle2D.Double(xPosition.get(index), yPosition.get(index),
                size.get(index).get(0).intValue(), size.get(index).get(0).intValue());
        newTransform.rotate(Math.toRadians(rotation.get(index)), square.getCenterX(),
                square.getCenterY());
        g2d.setTransform(newTransform);
        g2d.fill(square);
        break;
      case CIRCLE:
        Ellipse2D circle = new Ellipse2D.Double(xPosition.get(index), yPosition.get(index),
                (size.get(index).get(0).intValue() * 2), (size.get(index).get(0).intValue() * 2));
        newTransform.rotate(Math.toRadians(rotation.get(index)), circle.getCenterX(),
                circle.getCenterY());
        g2d.setTransform(newTransform);
        g2d.fill(circle);
        break;
      default:
        throw new IllegalArgumentException("Invalid shape type");
    }
    g2d.setTransform(oldTransform);
  }

  /**
   * This assigns values that will be drawn corresponding to a given animation at a given time.
   *
   * @param animation animation to draw
   * @param time      time of draw
   */
  private void singleAnimationChange(IAnimations animation, int time) {
    switch (animation.getAnimateType()) {
      case MOVE:
        xPosition.add(calcTweening(animation.getPosition1().getX(),
                animation.getPosition2().getX(), animation.getTime1(), animation.getTime2(),
                time).intValue());
        yPosition.add(calcTweening(animation.getPosition1().getY(),
                animation.getPosition2().getY(), animation.getTime1(), animation.getTime2(),
                time).intValue());
        break;
      case CHANGESIZE:
        java.util.List<Double> newSize = new ArrayList<>();
        for (int i = 0; i < animation.getSizeParams1().size(); i++) {
          newSize.add(calcTweening(animation.getSizeParams1().get(i),
                  animation.getSizeParams2().get(i), animation.getTime1(), animation.getTime2(),
                  time));
        }
        size.add(newSize);
        break;
      case CHANGECOLOR:
        red.add(calcTweening(animation.getColor1().getRed(), animation.getColor2().getRed(),
                animation.getTime1(), animation.getTime2(), time).floatValue());
        green.add(calcTweening(animation.getColor1().getGreen(),
                animation.getColor2().getGreen(), animation.getTime1(), animation.getTime2(),
                time).floatValue());
        blue.add(calcTweening(animation.getColor1().getBlue(),
                animation.getColor2().getBlue(), animation.getTime1(), animation.getTime2(),
                time).floatValue());
        break;
      case ROTATE:
        rotation.add(calcTweening(animation.getRotation1().doubleValue(), animation.getRotation2()
                .doubleValue(), animation.getTime1(), animation.getTime2(), time).intValue());
        break;
      case APPEAR:
        break;
      case DISAPPEAR:
        break;
      case STILL:
        addStillShape(animation);
        break;
      default:
        throw new IllegalArgumentException("Invalid animation type");
    }
  }

  /**
   * This method assigns values that will be drawn that correspond to when a shape first appears
   * or when a shape is staying still at the given time.
   *
   * @param animation animation to draw
   */
  private void addStillShape(IAnimations animation) {
    red.add(animation.getColor1().getRed().floatValue());
    green.add(animation.getColor1().getGreen().floatValue());
    blue.add(animation.getColor1().getBlue().floatValue());
    xPosition.add(animation.getPosition1().getX().intValue());
    yPosition.add(animation.getPosition1().getY().intValue());
    size.add(animation.getSizeParams1());
    rotation.add(animation.getRotation1());
  }

  /**
   * This method calculates the tweening value of an animation at a given time.
   * This method is used for move, change color, and change size animations.
   *
   * @param initVal   initial value
   * @param finalVal  final value
   * @param initTick  time of initial value
   * @param finalTick time of final value
   * @param tick  current time
   * @return value at current time
   */
  private Double calcTweening(Double initVal, Double finalVal, Integer initTick,
                              Integer finalTick, Integer tick) {
    Integer t1 = (finalTick - tick);
    Integer t2 = (tick - initTick);
    Integer t3 = (finalTick - initTick);
    Double v1 = t1.doubleValue() / t3.doubleValue();
    Double v2 = t2.doubleValue() / t3.doubleValue();
    return (initVal * v1) + (finalVal * v2);
  }

  /**
   * This method also adds the animation's shapes into the shape params lists
   * but it adds the shapes that are specified in the subset model. Only the
   * shapes that appear at the given time are added.
   * @param animationTime   list of animations at time T
   * @param timelineIndex   time T
   * @param animationsAtT  subset's timeline
   */
  protected void addShapeParamsAtTimeT(List<IAnimations> animationTime, int timelineIndex,
                                       List<List<IAnimations>> animationsAtT) {
    for (int j = 0; j < animationsAtT.get(timelineIndex).size(); j++) {
      String nextShape = "";

      shapeType.add(animationTime.get(j).getChangedShape().getShapeType());
      singleAnimationChange(animationTime.get(j), timelineIndex);

      if ((j + 1) < animationTime.size()) {
        nextShape = animationTime.get(j + 1).getChangedShape().getShapeName();
      }

      while (nextShape.equals(animationTime.get(j).getChangedShape().getShapeName())) {
        singleAnimationChange(animationTime.get(j + 1), timelineIndex);
        j++;
        if ((j + 1) < animationTime.size()) {
          nextShape = animationTime.get(j + 1).getChangedShape().getShapeName();
        } else {
          nextShape = "";
        }
      }
      addRemainingShapeParams(animationTime.get(j));
    }
  }

  /**
   * This method is used in the addShapeParamsAtTimeT. It adds the remaining shape
   * params into the lists that are not changed in the animation.
   * @param animation   animation being added
   */
  private void addRemainingShapeParams(IAnimations animation) {
    int shapeCount = shapeType.size();
    if (red.size() < shapeCount) {
      red.add(animation.getColor1().getRed().floatValue());
    }
    if (green.size() < shapeCount) {
      green.add(animation.getColor1().getGreen().floatValue());
    }
    if (blue.size() < shapeCount) {
      blue.add(animation.getColor1().getBlue().floatValue());
    }
    if (xPosition.size() < shapeCount) {
      xPosition.add(animation.getPosition1().getX().intValue());
    }
    if (yPosition.size() < shapeCount) {
      yPosition.add(animation.getPosition1().getY().intValue());
    }
    if (size.size() < shapeCount) {
      size.add(animation.getSizeParams1());
    }
    if (rotation.size() < shapeCount) {
      rotation.add(animation.getRotation1());
    }
  }

  /**
   * This method schedules painting tasks depending on if the animation
   * is looped or not.
   * @param task  task to schedule
   * @param time  time to schedule to
   */
  protected void scheduleTimerTasks(TimerTask task, int time) {
    if (isLooped) {
      timer.scheduleAtFixedRate(task, (long) ((time / tempo) * 1000),
              (long) (animationPeriod / tempo) * 1000);
    }
    else {
      timer.schedule(task, (long) ((time / tempo) * 1000));
    }
  }

  /**
   * This class represents a panel that can be drawn on to create shapes
   * at each tick. In this class the paintComponent method is overrode
   * in order to paint all shapes at each tick.
   */
  protected class DrawingPane extends JPanel {
    private DrawingPane() {
      setLayout(new BorderLayout());
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(1000, 1000);
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (shapeType != null) {
        for (int i = 0; i < shapeType.size(); i++) {
          drawShape(g, i);
        }
      }
    }
  }
}
