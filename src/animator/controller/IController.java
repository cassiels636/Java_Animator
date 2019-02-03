package animator.controller;

import java.awt.event.ActionEvent;

import javax.swing.event.ChangeEvent;

/**
 * This is the interface for the controller. It lists the public
 * method action which can be used to interact with the Interactive
 * view.
 */
public interface IController {
  /**
   * This is the action class that reads the user's input or actions with the
   * interactive view. Depending on the action, it calls upon the view to respond
   * accordingly to the action. Some actions included are play/pause, restart,
   * increase/decrease tempo, and creating a subset.
   *
   * @param e   user's action
   */
  void action(ActionEvent e);

  /**
   * This is the changeAction method that reads the user's input through a slider.
   * Depending on the user's interaction with the slider, the controller will call
   * upon the view to respond accordingly.
   *
   * @param e   user's slider action
   */
  void changeAction(ChangeEvent e);
}
