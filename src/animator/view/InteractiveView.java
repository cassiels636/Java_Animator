package animator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import animator.controller.IController;
import animator.model.IAnimations;
import animator.model.SimpleAnimationModel;
import animator.model.enums.AnimateTypes;

/**
 * This class represents the interactive view where a user can choose to
 * play, pause, restart, loop, change tempo, create subset, and export
 * animations to a SVG file.
 */
public class InteractiveView extends AbstractVisualView {

  private JButton start;
  private JButton restart;
  private JButton upTempo;
  private JButton downTempo;
  private JRadioButton loop;
  private JLabel tempoDisplay;
  private JLabel dropdownDisplay;
  private JComboBox<String> dropdown;
  private JButton subsetList;
  private JButton playSubset;
  private JButton svgSubset;
  private JButton svgAnim;
  private JSlider scrubber;

  /**
   * Constructor for an InteractiveView. It creates panels, buttons, and labels
   * and adds them to the frame.
   *
   * @param animationModel The model to transfer to a view.
   * @param tempo          Speed of the animation, as ticks per second.
   */
  public InteractiveView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);

    JPanel mainButtonPanel = new JPanel();
    mainButtonPanel.setLayout(new FlowLayout());
    mainButtonPanel.setBorder(BorderFactory.createBevelBorder(1));

    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new FlowLayout());
    buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    start = new JButton("Play");
    start.setActionCommand("start");
    buttonPane.add(start);
    restart = new JButton("Restart");
    restart.setActionCommand("restart");
    buttonPane.add(restart);
    upTempo = new JButton("Increase Tempo");
    upTempo.setActionCommand("increase tempo");
    buttonPane.add(upTempo);
    downTempo = new JButton("Decrease Tempo");
    downTempo.setActionCommand("decrease tempo");
    buttonPane.add(downTempo);

    mainButtonPanel.add(buttonPane, FlowLayout.LEFT);

    tempoDisplay = new JLabel("Tempo: " + tempo);
    mainButtonPanel.add(tempoDisplay, FlowLayout.CENTER);

    JPanel radioPanel = new JPanel();
    radioPanel.setLayout(new FlowLayout());

    loop = new JRadioButton("Enable Looping");
    loop.setActionCommand("loop");
    radioPanel.add(loop);
    mainButtonPanel.add(radioPanel, FlowLayout.RIGHT);

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
    bottomPanel.setBorder(BorderFactory.createBevelBorder(1));

    JPanel subsetPanel = new JPanel();
    subsetPanel.setLayout(new FlowLayout());

    dropdownDisplay = new JLabel("Add shapes to new animation");
    subsetPanel.add(dropdownDisplay);
    dropdown = new JComboBox<>();
    dropdown.setActionCommand("add shape to subset");
    createShapeDropdown();
    subsetPanel.add(dropdown);
    subsetList = new JButton("Subset Shape List");
    subsetList.setActionCommand("view subset");
    subsetPanel.add(subsetList);
    playSubset = new JButton("Play Subset");
    playSubset.setActionCommand("play subset");
    subsetPanel.add(playSubset);
    svgSubset = new JButton("Export Subset to SVG");
    svgSubset.setActionCommand("SVG subset");
    subsetPanel.add(svgSubset);
    svgAnim = new JButton("Export Animation to SVG");
    svgAnim.setActionCommand("SVG animation");
    subsetPanel.add(svgAnim);

    JPanel sliderPanel = new JPanel();
    sliderPanel.setLayout(new BorderLayout());
    scrubber = new JSlider(JSlider.HORIZONTAL, 0, timeline.size() - 1, 0);
    scrubber.setMajorTickSpacing(timeline.size() / 10);
    scrubber.setMinorTickSpacing(timeline.size() / 5);
    scrubber.setPaintTicks(true);
    scrubber.setPaintLabels(true);
    sliderPanel.add(scrubber);

    bottomPanel.add(sliderPanel);
    bottomPanel.add(subsetPanel);

    mainPanel.add(mainButtonPanel, BorderLayout.NORTH);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    frame.pack();
    frame.validate();
    frame.setVisible(true);
  }

  /**
   * This method creates a comboBox and adds each shape from the list to it.
   */
  private void createShapeDropdown() {
    for (int i = 0; i < shapes.size(); i++) {
      dropdown.addItem("Shape " + shapes.get(i).getShapeName() + " ("
              + shapes.get(i).getShapeType() + ")");
    }
  }

  /**
   * This method is only used by the Interactive view. It toggles the play/pause
   * function and updates the view to state whether the animation can be played
   * or paused
   */
  public void togglePlayOrPause() {
    isPaused = !isPaused;
    timer.cancel();
    timer = new Timer();
    if (!isPaused) {
      startAnimation(currTick);
      start.setText("Pause");
    }
    else {
      pauseAnimation();
      start.setText("Play");
    }
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
    tempo = newTempo;
    tempoDisplay.setText("Tempo: \n" + tempo);
    timer.cancel();
    timer = new Timer();
    if (!isPaused & (!isLooped & (currTick == timeline.size() - 1))) {
      startAnimation(currTick);
    }
  }

  /**
   * This method is only used by the Interactive view. It toggles the loop function of the
   * animation and continues playing or pausing the animation.
   */
  @Override
  public void loopAnimation() {
    isLooped = !isLooped;
    timer.cancel();
    timer = new Timer();
    if (!isPaused) {
      startAnimation(currTick);
    }
  }

  /**
   * This method is only used by the Interactive view and restarts the animation from the
   * beginning.
   */
  @Override
  public void restartAnimation() {
    timer.cancel();
    timer = new Timer();
    start.setText("Pause");
    isPaused = false;
    startAnimation(0);
  }

  /**
   * This method is only used by the Interactive view. It plays a paused animation.
   * When unpausing/playing, the animation continues playing from the tick it was
   * paused at.
   */
  @Override
  public void pauseAnimation() {
    TimerTask task;
    final int FINAL_TICK = currTick;
    List<IAnimations> animationTime = timeline.get(FINAL_TICK);
    task = new TimerTask() {
      @Override
      public void run() {
        initializeParams();
        addShapeParamsAtTimeT(animationTime, FINAL_TICK, timeline);
        mainPanel.repaint();
      }
    };
    scheduleTimerTasks(task, currTick);
  }

  /**
   * This method exports the model into the specified SVG file.
   * @param fileName  SVG file name
   */
  @Override
  public void svgAnimation(String fileName) {
    ViewInterface vi = new SVGView(model, this.tempo, fileName);
  }

  /**
   * This method is only used by the Interactive view. It adds a shape chosen by the user
   * to the new subset model.
   *
   * @param item         action by user that includes shape
   * @param subsetModel  subset model
   */
  @Override
  public void addToSubset(String item, SimpleAnimationModel subsetModel) {
    String shapeName = item.split(" ")[1];
    if (subsetModel.getShapeByName(shapeName) == null) {
      subsetModel.copyShape(model.getShapeByName(shapeName));
      dropdownDisplay.setText("Added to Subset: " + shapeName);
      for (int i = 0; i < animations.size(); i++) {
        if (animations.get(i).getChangedShape().getShapeName().equals(shapeName)
                && (animations.get(i).getAnimateType() != AnimateTypes.APPEAR)
                && (animations.get(i).getAnimateType() != AnimateTypes.DISAPPEAR)) {
          subsetModel.copyAnimation(animations.get(i));
        }
      }
    } else {
      subsetModel.removeShapeByName(shapeName);
      dropdownDisplay.setText("Removed from Subset: " + shapeName);
    }
  }

  /**
   * This method is only used by the Interactive view. It plays the subset animation
   * from the given starting tick in the current window.
   *
   * @param subsetStart starting tick
   * @param subsetModel subset model
   */
  @Override
  public void playSubset(int subsetStart, SimpleAnimationModel subsetModel) {
    List<List<IAnimations>> subsetTimeline = subsetModel.getTimeline();
    timer.cancel();
    timer = new Timer();
    TimerTask task;

    for (int i = 0; i < subsetTimeline.size(); i++) {
      final int FINALI = i;
      task = new TimerTask() {
        @Override
        public void run() {
          initializeParams();
          addShapeParamsAtTimeT(subsetTimeline.get(FINALI), FINALI, subsetTimeline);
          mainPanel.repaint();
        }
      };
      scheduleTimerTasks(task, i);
    }
  }

  /**
   * Generates a SVG view based on the subset selected in the interface, saves the file to a user
   * specified location.
   *
   * @param fileName The file name to save the SVG file as.
   * @param subsetModel   subset model
   */
  @Override
  public void svgSubset(String fileName, SimpleAnimationModel subsetModel) {
    ViewInterface vi = new SVGView(subsetModel, this.tempo, fileName);
  }

  /**
   * This method opens a dialog box that displays the list of shapes currently
   * in the subset. It also includes a description of how to remove shapes.
   *
   * @param subsetModel   subset model
   */
  @Override
  public void showSubsetList(SimpleAnimationModel subsetModel) {
    StringBuilder subsetShapes = new StringBuilder("Shapes currently in the subset: \n");

    for (int i = 0; i < subsetModel.getShapes().size(); i++) {
      subsetShapes.append("   -Shape ");
      subsetShapes.append(subsetModel.getShape(i).getShapeName());
      subsetShapes.append(" (" + subsetModel.getShape(i).getShapeType());
      subsetShapes.append(")\n");
    }
    subsetShapes.append("Select shape in dropdown again to remove it from subset.");
    JOptionPane.showMessageDialog(frame, subsetShapes.toString());
  }

  /**
   * This method is only used by the Interactive view. It plays the animation based on a
   * given tick that can be supplied by the scrubber/slider on the UI. It goes and draws
   * an animation at the given tick.
   *
   * @param tick    tick at which to draw the animation
   */
  public void scrubberPlay(int tick) {
    isPaused = true;
    start.setText("Play");
    timer.cancel();
    timer = new Timer();
    TimerTask task;
    List<IAnimations> animationTime = timeline.get(tick);

    task = new TimerTask() {
      @Override
      public void run() {
        initializeParams();
        addShapeParamsAtTimeT(animationTime, tick, timeline);
        drawingPanel.repaint();
        currTick = (tick + 1) % timeline.size();
      }
    };
    scheduleTimerTasks(task, 0);
  }

  /**
   * This method opens a dialog box with the specified text.
   *
   * @param dialog  dialog string
   */
  @Override
  public void createMessageDialog(String dialog) {
    JOptionPane.showMessageDialog(frame, dialog);
  }

  /**
   * This method is used to connect the controller(listener) to the Interactive view
   * so that the user can interact with the view.
   *
   * @param listener controller
   */
  @Override
  public void setListener(IController listener) {
    ActionListener listen = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.action(e);
      }
    };
    start.addActionListener(listen);
    restart.addActionListener(listen);
    upTempo.addActionListener(listen);
    downTempo.addActionListener(listen);
    loop.addActionListener(listen);
    dropdown.addActionListener(listen);
    subsetList.addActionListener(listen);
    playSubset.addActionListener(listen);
    svgSubset.addActionListener(listen);
    svgAnim.addActionListener(listen);

    ChangeListener changeListener = new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        listener.changeAction(e);
      }
    };
    scrubber.addChangeListener(changeListener);
  }
}
