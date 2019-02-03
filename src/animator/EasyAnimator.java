package animator;

import java.io.IOException;
import java.util.Scanner;

import animator.model.SimpleAnimation;
import animator.model.SimpleAnimationModel;
import animator.util.AnimationFileReader;
import animator.util.TweenModelBuilder;
import animator.view.ViewCreator;

/**
 * This class holds the main method for a simple animation.
 * It creates a model and view.
 */
public class EasyAnimator {
  /**
   * This main method takes in a list of arguments and creates a model and a
   * view based on that model. The argument list specifies an input file, a view
   * type, an output method, and a tempo.
   *
   * @param args argument List
   * @throws IOException Thrown when file is not found.
   */
  public static void main(String[] args) throws IOException {
    Scanner scan;
    SimpleAnimationModel model = new SimpleAnimation();
    TweenModelBuilder<SimpleAnimationModel> builder = new SimpleAnimation.Builder();
    ViewCreator createView;
    String inputFile = "";
    ViewCreator.ViewType viewType = ViewCreator.ViewType.NULL;
    String output = "out";
    int tempo = 1;
    AnimationFileReader reader = new AnimationFileReader();
    String s1 = "";

    for (int i = 0; i < args.length; i++) {
      s1 = args[i];
      switch (s1) {
        case "-if":
          inputFile = args[i + 1];
          model = reader.readFile(inputFile, builder);
          i++;
          break;
        case "-iv":
          viewType = getViewType(args[i + 1]);
          i++;
          break;
        case "-o":
          output = args[i + 1];
          i++;
          break;
        case "-speed":
          tempo = Integer.parseInt(args[i + 1]);
          i++;
          break;
        default:
          throw new IllegalArgumentException("Invalid arguments: " + s1);
      }
    }

    createView = new ViewCreator();
    if ((model == null) || (viewType.equals(ViewCreator.ViewType.NULL))) {
      throw new IllegalArgumentException("Missing input file or view type argument\n" + inputFile
              + ", " + viewType + ", " + output + ", " + tempo);
    } else {
      createView.create(viewType, model, tempo, output);
    }
  }

  /**
   * This method converts a String to specified view type.
   *
   * @param view String view type
   * @return ViewType
   */
  private static ViewCreator.ViewType getViewType(String view) {
    ViewCreator.ViewType returnView;
    switch (view) {
      case "text":
        returnView = ViewCreator.ViewType.TEXT;
        break;
      case "visual":
        returnView = ViewCreator.ViewType.VISUAL;
        break;
      case "svg":
        returnView = ViewCreator.ViewType.SVG;
        break;
      case "interactive":
        returnView = ViewCreator.ViewType.INTERACTIVE;
        break;
      default:
        throw new IllegalArgumentException("Invalid view type argument");
    }
    return returnView;
  }
}
