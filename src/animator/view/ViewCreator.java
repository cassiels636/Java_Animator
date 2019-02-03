package animator.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import animator.controller.Controller;
import animator.model.SimpleAnimationModel;

/**
 * This class represents a factory of views. It takes in a view type
 * and creates the corresponding view.
 */
public class ViewCreator {
  /**
   * This method creates a view based on the given parameters.
   *
   * @param type   view type
   * @param model  model the view is based on
   * @param tempo  ticks per sec
   * @param output location of where to send the view to
   */
  public static void create(ViewType type, SimpleAnimationModel model, int tempo,
                            String output) throws IOException {
    switch (type) {
      case TEXT:
        if (output.equals("out")) {
          new TextView(model, tempo);
        } else {
          new TextView(model, tempo, output);
        }
        break;
      case VISUAL:
        new VisualView(model, tempo);
        break;
      case SVG:
        if (output.equals("out")) {
          new SVGView(model, tempo);
        } else {
          new SVGView(model, tempo, output);
        }
        break;
      case INTERACTIVE:
        ViewInterface viewI = new InteractiveView(model, tempo);
        if (!output.equals("out")) {
          new Controller(model, viewI, output);
        }
        else {
          new Controller(model, viewI);
        }
        break;
      default:
        throw new IllegalArgumentException("Invalid view type");
    }
  }

  /**
   * An enumeration for the representation of different view types.
   */
  public enum ViewType {
    TEXT, VISUAL, SVG, INTERACTIVE, NULL
  }
}
