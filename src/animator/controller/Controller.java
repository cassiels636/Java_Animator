package animator.controller;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import animator.model.SimpleAnimation;
import animator.model.SimpleAnimationModel;
import animator.view.ViewInterface;

/**
 * This class represents the controller for the SimpleAnimation model and view.
 * This controller is interacts with the interactive view to communicate
 * the user's input so that the view can respond accordingly. This controller
 * class also holds the subset model.
 */
public class Controller implements IController {
  private SimpleAnimationModel model;
  private SimpleAnimationModel subsetModel;
  private ViewInterface view;
  String out;

  /**
   * Constructor for the controller that takes in a model and a view.
   * @param m   model
   * @param v   view
   */
  public Controller(SimpleAnimationModel m, ViewInterface v) {
    model = m;
    view = v;
    subsetModel = new SimpleAnimation();
    view.setListener(this);
  }

  /**
   * A second constructor for the controller. This also takes in a model and
   * a view but it also takes in a file name to send a svg file to.
   * @param m   model
   * @param v   view
   * @param out output file name
   */
  public Controller(SimpleAnimationModel m, ViewInterface v, String out) {
    model = m;
    view = v;
    this.out = out;
    subsetModel = new SimpleAnimation();
    view.setListener(this);
  }

  /**
   * This is the action class that reads the user's input or actions with the
   * interactive view. Depending on the action, it calls upon the view to respond
   * accordingly to the action. Some actions included are play/pause, restart,
   * increase/decrease tempo, and creating a subset.
   * @param e   user's action
   */
  @Override
  public void action(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "start":
        view.togglePlayOrPause();
        break;
      case "restart":
        view.restartAnimation();
        break;
      case "increase tempo":
        view.updateTempo(view.getTempo() + 1);
        break;
      case "decrease tempo":
        view.updateTempo(view.getTempo() - 1);
        break;
      case "loop":
        view.loopAnimation();
        break;
      case "SVG animation":
        if (out == null) {
          out = JOptionPane.showInputDialog("Please enter a file name ending in .xml or "
                  + " .txt to save the SVG view to.");
        }
        view.svgAnimation(out);
        view.createMessageDialog("SVG view was saved.");
        break;
      case "add shape to subset":
        if (e.getSource() instanceof JComboBox) {
          JComboBox<String> box = (JComboBox<String>) e.getSource();
          String item = (String) box.getSelectedItem();
          view.addToSubset(item, subsetModel);
        }
        break;
      case "view subset":
        view.showSubsetList(subsetModel);
        break;
      case "play subset":
        view.playSubset(0, subsetModel);
        break;
      case "SVG subset":
        if (out == null) {
          out = JOptionPane.showInputDialog("Please enter a file name ending in .xml or "
                  + " .txt to save the SVG view to.");
        }
        view.svgSubset(out, subsetModel);
        view.createMessageDialog("SVG view of the subset was saved.");
        break;
      default:
        throw new IllegalArgumentException("Invalid action command");
    }
  }

  /**
   * This is the changeAction method that reads the user's input through a slider.
   * Depending on the user's interaction with the slider, the controller will call
   * upon the view to respond accordingly.
   *
   * @param e   user's slider action
   */
  @Override
  public void changeAction(ChangeEvent e) {
    JSlider src = (JSlider) e.getSource();
    if (src.getValueIsAdjusting()) {
      view.scrubberPlay(src.getValue());
    }
  }
}
