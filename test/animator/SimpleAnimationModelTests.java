package animator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import animator.model.AnimatedShape;
import animator.model.IAnimatedShape;
import animator.model.IPosition2D;
import animator.model.IRGB;
import animator.model.Position2D;
import animator.model.RGB;
import animator.model.SimpleAnimation;
import animator.model.SimpleAnimationModel;
import animator.model.enums.ShapeType;

import static org.junit.Assert.assertEquals;

public class SimpleAnimationModelTests {
  @Test
  public void animatedShapeToStringTest() {
    IRGB color1 = new RGB(0.0, 1.0, 0.0);
    IPosition2D initial = new Position2D(10.0, 4.0);
    List<Double> params = new ArrayList<>();
    params.add(6.0);
    params.add(8.0);
    IAnimatedShape shape1 = new AnimatedShape("Shape1", ShapeType.RECTANGLE, color1,
            initial, params, 10, 100);
    assertEquals("Name: Shape1\nType: rectangle\nMin-corner: (10.0, 4.0), Width: "
            + "6.0, Height: 8.0, Color: (0.0, 1.0, 0.0)\nAppears at t=10\n"
            + "Disappears at t=100", shape1.toString());
  }

  @Test
  public void createShapeTest() {
    SimpleAnimationModel anime = new SimpleAnimation();
    anime.createShape("My Shape", ShapeType.CIRCLE, new RGB(0.0, 0.0, 1.0),
            new Position2D(573.04, 42.2493), Collections.singletonList(5.0), 0, 50);
    assertEquals("Name: My Shape\nType: circle\nCenter: (573.0, 42.2), Radius: "
            + "5.0, Color: (0.0, 0.0, 1.0)\nAppears at t=0\n"
            + "Disappears at t=50", anime.getShapeStatus(0));
  }

  @Test
  public void moveShapeTest() {
    SimpleAnimationModel anime = new SimpleAnimation();
    anime.createShape("R", ShapeType.SQUARE, new RGB(0.0, 0.0, 1.0),
            new Position2D(573.04, 42.2493), Collections.singletonList(5.0), 0, 50);
    anime.moveShape(anime.getShape(0), new Position2D(10.0, 20.0), 10, 20);
    assertEquals("Shapes:\n" +
            "Name: R\n" +
            "Type: square\n" +
            "Min-corner: (573.0, 42.2), Side length: 5.0, Color: (0.0, 0.0, 1.0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=50\n" +
            "\n" +
            "Shape R moves from (573.0, 42.2) to (10.0, 20.0) from t=10 to t=20\n",
            anime.printAnimation());
  }

  @Test
  public void changeShapeSizeTest() {
    SimpleAnimationModel anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    size.add(4.0);
    size.add(9.0);
    List<Double> newSize = new ArrayList<>();
    newSize.add(10.0);
    newSize.add(15.0);
    anime.createShape("R", ShapeType.OVAL, new RGB(1.0, 0.0, 0.0),
            new Position2D(573.04, 42.2493), size, 0, 50);
    anime.changeShapeSize(anime.getShape(0), newSize, 10, 25);
    assertEquals("Shapes:\n" +
            "Name: R\n" +
            "Type: oval\n" +
            "Center: (573.0, 42.2), X radius: 4.0, Y radius: 9.0, Color: (1.0, 0.0, 0.0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=50\n" +
            "\n" +
            "Shape R scales from X radius: 4.0, Y radius: 9.0 to X radius: 10.0, Y radius: 15.0 " +
            "from t=10 to t=25\n", anime.printAnimation());
  }

  @Test
  public void changeShapeColorTest() {
    SimpleAnimationModel anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    size.add(100.0048);
    size.add(492.2);
    size.add(400.99);
    anime.createShape("B", ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size, 150, 250);
    anime.changeShapeColor(anime.getShape(0), new RGB(0.75, 0.0, 0.5), 200, 201);
    assertEquals("Shapes:\n" +
                    "Name: B\n" +
                    "Type: polygon\n" +
                    "Center: (1000.0, 5000.0), Side 1 length: 100.0048, Side 2 length: 492.2, " +
                    "Side 3 length: 400.99, Color: (0.5, 0.5, 0.5)\n" +
                    "Appears at t=150\n" +
                    "Disappears at t=250\n" +
                    "\n" +
                    "Shape B changes color from (0.5, 0.5, 0.5) to (0.8, 0.0, 0.5) from t=200 " +
                    "to t=201\n",
            anime.printAnimation());
  }

  @Test
  public void createMultipleShapesNoAnimationsTest() {
    SimpleAnimationModel anime = new SimpleAnimation();
    IRGB color1 = new RGB(0.0, 1.0, 0.0);
    IPosition2D initial = new Position2D(10.0, 4.0);
    List<Double> params = new ArrayList<>();
    params.add(6.0);
    params.add(8.0);
    List<Double> size1 = new ArrayList<>();
    size1.add(50.0);
    size1.add(50.0);
    size1.add(75.0);
    List<Double> size2 = new ArrayList<>();
    size2.add(100.0);
    size2.add(50.0);
    size2.add(25.0);
    size2.add(30.0);
    anime.createShape("Shape1", ShapeType.RECTANGLE, color1, initial, params,
            10, 100);
    anime.createShape("B", ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size1, 0, 70);
    anime.createShape("My Shape", ShapeType.CIRCLE, new RGB(0.0, 0.0, 1.0),
            new Position2D(573.04, 42.2493), Collections.singletonList(5.0), 0, 50);
    anime.createShape("Hi", ShapeType.POLYGON, new RGB(0.5, 0.0, 0.5),
            new Position2D(10, 19), size2, 5, 110);
    assertEquals("Shapes:\n" +
            "Name: Shape1\n" +
            "Type: rectangle\n" +
            "Min-corner: (10.0, 4.0), Width: 6.0, Height: 8.0, Color: (0.0, 1.0, 0.0)\n" +
            "Appears at t=10\n" +
            "Disappears at t=100\n" +
            "\n" +
            "Name: B\n" +
            "Type: polygon\n" +
            "Center: (1000.0, 5000.0), Side 1 length: 50.0, Side 2 length: 50.0, Side 3 length: " +
            "75.0, Color: (0.5, 0.5, 0.5)\n" +
            "Appears at t=0\n" +
            "Disappears at t=70\n" +
            "\n" +
            "Name: My Shape\n" +
            "Type: circle\n" +
            "Center: (573.0, 42.2), Radius: 5.0, Color: (0.0, 0.0, 1.0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=50\n" +
            "\n" +
            "Name: Hi\n" +
            "Type: polygon\n" +
            "Center: (10.0, 19.0), Side 1 length: 100.0, Side 2 length: 50.0, Side 3 length: " +
            "25.0, Side 4 length: 30.0, Color: (0.5, 0.0, 0.5)\n" +
            "Appears at t=5\n" +
            "Disappears at t=110\n" +
            "\n", anime.printAnimation());
  }

  @Test
  public void create1ShapeMultipleAnimationsTest() {
    SimpleAnimationModel anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    List<Double> newSize = new ArrayList<>();
    for (int a = 0; a < 100; a++) {
      size.add(10.0);
      newSize.add(15.0);
    }
    anime.createShape("B", ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size, 150, 250);
    anime.changeShapeSize(anime.getShape(0), newSize, 240, 250);
    anime.moveShape(anime.getShape(0), new Position2D(0, 0), 215, 230);
    anime.changeShapeColor(anime.getShape(0), new RGB(1.0, 0.5, 0.0), 200, 220);
    anime.moveShape(anime.getShape(0), new Position2D(10, 20), 160, 210);
    String testString1 = "Shape B moves from";
    String testString2 = "Shape B changes color from";
    String testString3 = "Shape B scales from";
    String testString4 = anime.printAnimation();
    assertEquals(true, (testString4.contains(testString1) && testString4.contains(testString2)
            && testString4.contains(testString3)));
  }

  @Test
  public void removeShapeTest() {
    SimpleAnimationModel anime = new SimpleAnimation();
    IRGB color1 = new RGB(0.0, 1.0, 0.0);
    IPosition2D initial = new Position2D(10.0, 4.0);
    List<Double> params = new ArrayList<>();
    params.add(6.0);
    params.add(8.0);
    anime.createShape("Shape1", ShapeType.RECTANGLE, color1, initial, params,
            10, 100);
    anime.createShape("My Shape", ShapeType.CIRCLE, new RGB(0.0, 0.0, 1.0),
            new Position2D(573.04, 42.2493), Collections.singletonList(5.0), 0, 50);
    anime.removeShape(0);
    assertEquals("Name: My Shape\nType: circle\nCenter: (573.0, 42.2), Radius: "
            + "5.0, Color: (0.0, 0.0, 1.0)\nAppears at t=0\n"
            + "Disappears at t=50", anime.getShape(0).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidPolygonTest() {
    SimpleAnimationModel anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    size.add(100.0);
    size.add(50.0);
    size.add(15.0);
    size.add(5.0);
    anime.createShape("Hi", ShapeType.POLYGON, new RGB(0.5, 0.0, 0.5),
            new Position2D(10, 19), size, 0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidAnimationTimesTest() {
    SimpleAnimationModel anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    List<Double> newSize = new ArrayList<>();
    for (int a = 0; a < 10; a++) {
      size.add(10.0);
      newSize.add(15.0);
    }
    anime.createShape("B", ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size, 150, 250);
    anime.changeShapeSize(anime.getShape(0), newSize, 240, 260);
  }

  @Test(expected = IllegalArgumentException.class)
  public void overlappingAnimationsTest() {
    SimpleAnimationModel anime = new SimpleAnimation();
    List<Double> size = new ArrayList<>();
    List<Double> newSize = new ArrayList<>();
    for (int a = 0; a < 10; a++) {
      size.add(10.0);
      newSize.add(15.0);
    }
    anime.createShape("B", ShapeType.POLYGON, new RGB(0.5, 0.5, 0.5),
            new Position2D(1000, 5000), size, 150, 250);
    anime.changeShapeSize(anime.getShape(0), newSize, 240, 250);
    anime.moveShape(anime.getShape(0), new Position2D(0, 0), 215, 230);
    anime.moveShape(anime.getShape(0), new Position2D(5, 5), 195, 220);
  }
}
