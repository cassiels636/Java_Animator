package animator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import animator.model.SimpleAnimation;
import animator.view.AbstractView;
import animator.view.TextView;

import static junit.framework.Assert.assertEquals;


public class AnimationViewTests {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(System.out);
    System.setErr(System.err);
  }

  @Test
  public void testText() throws IOException {
    String[] args = {"-if", "smalldemo.txt", "-iv", "text", "-o", "out", "-speed", "2"};
    EasyAnimator.main(args);
    assertEquals("Shapes:\n" +
            "Name: R\n" +
            "Type: rectangle\n" +
            "Min-corner: (200.0, 200.0), Width: 50.0, Height: 100.0, Color: (1.0, 0.0, 0.0)\n" +
            "Appears at t=0.5s\n" +
            "Disappears at t=50.0s\n" +
            "\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (500.0, 100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0, 0.0, 1.0)\n" +
            "Appears at t=3.0s\n" +
            "Disappears at t=50.0s\n" +
            "\n" +
            "Shape R moves from (200.0, 200.0) to (300.0, 300.0) from t=5.0s to t=25.0s\n" +
            "Shape C moves from (500.0, 100.0) to (500.0, 400.0) from t=10.0s to t=35.0s\n" +
            "Shape C changes color from (0.0, 0.0, 1.0) to (0.0, 1.0" +
            ", 0.0) from t=25.0s to t=40.0s\n" +
            "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 fro" +
            "m t=25.5s to t=35.0s\n" +
            "Shape R moves from (300.0, 300.0) to (200.0, 200.0) from t=35.0s to t=50.0s\n",
            outContent.toString());
  }

  @Test
  public void testText2() throws IOException {
    String[] args = {"-if", "toh-3.txt", "-iv", "text", "-o", "out", "-speed", "20"};
    EasyAnimator.main(args);
    assertEquals("Shapes:\n" +
            "Name: disk1\n" +
            "Type: rectangle\n" +
            "Min-corner: (190.0, 180.0), Width: 20.0, Height: 30.0, Color: (0.0, 0.2, 0.4)\n" +
            "Appears at t=0.05s\n" +
            "Disappears at t=15.1s\n" +
            "\n" +
            "Name: disk2\n" +
            "Type: rectangle\n" +
            "Min-corner: (167.5, 210.0), Width: 65.0, Height: 30.0, Color: (0.0, 1.0, 0.2)\n" +
            "Appears at t=0.05s\n" +
            "Disappears at t=15.1s\n" +
            "\n" +
            "Name: disk3\n" +
            "Type: rectangle\n" +
            "Min-corner: (145.0, 240.0), Width: 110.0, Height: 30.0, Color: (0.0, 0.2, 0.7)\n" +
            "Appears at t=0.05s\n" +
            "Disappears at t=15.1s\n" +
            "\n" +
            "Shape disk1 moves from (190.0, 180.0) to (190.0, 50.0) from t=1.25s to t=1.75s\n" +
            "Shape disk1 moves from (190.0, 50.0) to (490.0, 50.0) from t=1.8s to t=2.3s\n" +
            "Shape disk1 moves from (490.0, 50.0) to (490.0, 240.0) from t=2.35s to t=2.85s\n" +
            "Shape disk2 moves from (167.5, 210.0) to (167.5, 50.0) from t=2.85s to t=3.35s\n" +
            "Shape disk2 moves from (167.5, 50.0) to (317.5, 50.0) from t=3.4s to t=3.9s\n" +
            "Shape disk2 moves from (317.5, 50.0) to (317.5, 240.0) from t=3.95s to t=4.45s\n" +
            "Shape disk1 moves from (490.0, 240.0) to (490.0, 50.0) from t=4.45s to t=4.95s\n" +
            "Shape disk1 moves from (490.0, 50.0) to (340.0, 50.0) from t=5.0s to t=5.5s\n" +
            "Shape disk1 moves from (340.0, 50.0) to (340.0, 210.0) from t=5.55s to t=6.05s\n" +
            "Shape disk3 moves from (145.0, 240.0) to (145.0, 50.0) from t=6.05s to t=6.55s\n" +
            "Shape disk3 moves from (145.0, 50.0) to (445.0, 50.0) from t=6.6s to t=7.1s\n" +
            "Shape disk3 moves from (445.0, 50.0) to (445.0, 240.0) from t=7.15s to t=7.65s\n" +
            "Shape disk1 moves from (340.0, 210.0) to (340.0, 50.0) from t=7.65s to t=8.15s\n" +
            "Shape disk3 changes color from (0.0, 0.2, 0.7) to (0.0, 1.0, 0.0) from t=7.65s to " +
            "t=8.05s\n" +
            "Shape disk1 moves from (340.0, 50.0) to (190.0, 50.0) from t=8.2s to t=8.7s\n" +
            "Shape disk1 moves from (190.0, 50.0) to (190.0, 240.0) from t=8.75s to t=9.25s\n" +
            "Shape disk2 moves from (317.5, 240.0) to (317.5, 50.0) from t=9.25s to t=9.75s\n" +
            "Shape disk2 moves from (317.5, 50.0) to (467.5, 50.0) from t=9.8s to t=10.3s\n" +
            "Shape disk2 moves from (467.5, 50.0) to (467.5, 210.0) from t=10.35s to t=10.85s\n" +
            "Shape disk1 moves from (190.0, 240.0) to (190.0, 50.0) from t=10.85s to t=11.35s\n" +
            "Shape disk2 changes color from (0.0, 1.0, 0.2) to (0.0, 1.0, 0.0) from t=10.85s to " +
            "t=11.25s\n" +
            "Shape disk1 moves from (190.0, 50.0) to (490.0, 50.0) from t=11.4s to t=11.9s\n" +
            "Shape disk1 moves from (490.0, 50.0) to (490.0, 180.0) from t=11.95s to t=12.45s\n" +
            "Shape disk1 changes color from (0.0, 0.2, 0.4) to (0.0, 1.0, 0.0) from t=12.45s to " +
            "t=12.85s\n", outContent.toString());
  }

  @Test
  public void testSVG() throws IOException {
    String[] args = {"-if", "smalldemo.txt", "-iv", "svg", "-o", "out", "-speed", "20"};
    EasyAnimator.main(args);
    assertEquals("<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"1000\" heigh" +
            "t=\"1000\">\n" +
            "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\" style=\"" +
            "fill:RGB(255, 0, 0)\" visibility=\"hidden\">\n" +
            "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" begin=\"" +
            "0.05s\" dur=\"0.0s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 0.0 L 100.0 100.0\" begin=\"0.5s\" dur=\"2.0s\" fi" +
            "ll=\"freeze\" />\n" +
            "<animate attributeName=\"width\" attributeType=\"XML\" begin=\"2.55s\" dur=\"0" +
            ".95s\" fill=\"freeze\" from=\"50.0\" to=\"25.0\" /> \n" +
            "<animate attributeName=\"height\" attributeType=\"XML\" begin=\"2.55s\" dur=\"" +
            "0.95s\" fill=\"freeze\" from=\"100.0\" to=\"100.0\" />\n" +
            "<animateMotion path=\"M 100.0 100.0 L 0.0 0.0\" begin=\"3.5s\" dur=\"1.5s\" fi" +
            "ll=\"freeze\" />\n" +
            "</rect>\n" +

            "<ellipse cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" style=\"fill:RGB(0," +
            " 0, 255)\" visibility=\"hidden\">\n" +
            "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" begin=\"" +
            "0.3s\" dur=\"0.0s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 0.0 L 0.0 300.0\" begin=\"1.0s\" dur=\"2.5s\" fill" +
            "=\"freeze\" />\n" +
            "<animate attributeName=\"fill\" attributeType=\"CSS\" from=\"RGB(0, 0, 255)\" " +
            "to=\"RGB(0, 255, 0)\" begin=\"2.5s\" dur=\"1.5s\" fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "</svg>\n" +
            "</body>\n" +
            "</html>\n", outContent.toString());
  }

  @Test
  public void testSVG2() throws IOException {
    String[] args = {"-if", "toh-3.txt", "-iv", "svg", "-o", "out", "-speed", "20"};
    EasyAnimator.main(args);
    assertEquals("<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"1000\" he" +
            "ight=\"1000\">\n" +
            "<rect id=\"disk1\" x=\"190.0\" y=\"180.0\" width=\"20.0\" height=\"30.0\" s" +
            "tyle=\"fill:RGB(0, 49, 90)\" visibility=\"hidden\">\n" +
            "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" begi" +
            "n=\"0.05s\" dur=\"0.0s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 0.0 L 0.0 -130.0\" begin=\"1.25s\" dur=\"0.5s\"" +
            " fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 -130.0 L 300.0 -130.0\" begin=\"1.8s\" dur=\"0." +
            "5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 -130.0 L 300.0 60.0\" begin=\"2.35s\" dur=\"0" +
            ".5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 60.0 L 300.0 -130.0\" begin=\"4.45s\" dur=\"0" +
            ".5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 -130.0 L 150.0 -130.0\" begin=\"5.0s\" dur=\"" +
            "0.5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 -130.0 L 150.0 30.0\" begin=\"5.55s\" dur=\"0" +
            ".5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 30.0 L 150.0 -130.0\" begin=\"7.65s\" dur=\"0" +
            ".5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 -130.0 L 0.0 -130.0\" begin=\"8.2s\" dur=\"0." +
            "5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 -130.0 L 0.0 60.0\" begin=\"8.75s\" dur=\"0.5s\"" +
            " fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 60.0 L 0.0 -130.0\" begin=\"10.85s\" dur=\"0.5s" +
            "\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 -130.0 L 300.0 -130.0\" begin=\"11.4s\" dur=\"0" +
            ".5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 -130.0 L 300.0 0.0\" begin=\"11.95s\" dur=\"0" +
            ".5s\" fill=\"freeze\" />\n" +
            "<animate attributeName=\"fill\" attributeType=\"CSS\" from=\"RGB(0, 49, 90)" +
            "\" to=\"RGB(0, 255, 0)\" begin=\"12.45s\" dur=\"0.4s\" fill=\"freeze\" />\n" +
            "</rect>\n" +

            "<rect id=\"disk2\" x=\"167.5\" y=\"210.0\" width=\"65.0\" height=\"30.0\" s" +
            "tyle=\"fill:RGB(6, 247, 41)\" visibility=\"hidden\">\n" +
            "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" begi" +
            "n=\"0.05s\" dur=\"0.0s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 0.0 L 0.0 -160.0\" begin=\"2.85s\" dur=\"0.5s\"" +
            " fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 -160.0 L 150.0 -160.0\" begin=\"3.4s\" dur=\"0." +
            "5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 -160.0 L 150.0 30.0\" begin=\"3.95s\" dur=\"0" +
            ".5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 30.0 L 150.0 -160.0\" begin=\"9.25s\" dur=\"0" +
            ".5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 150.0 -160.0 L 300.0 -160.0\" begin=\"9.8s\" dur=\"" +
            "0.5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 -160.0 L 300.0 0.0\" begin=\"10.35s\" dur=\"0" +
            ".5s\" fill=\"freeze\" />\n" +
            "<animate attributeName=\"fill\" attributeType=\"CSS\" from=\"RGB(6, 247, 41" +
            ")\" to=\"RGB(0, 255, 0)\" begin=\"10.85s\" dur=\"0.4s\" fill=\"freeze\" />\n" +
            "</rect>\n" +

            "<rect id=\"disk3\" x=\"145.0\" y=\"240.0\" width=\"110.0\" height=\"30.0\" " +
            "style=\"fill:RGB(11, 45, 175)\" visibility=\"hidden\">\n" +
            "<set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\" begi" +
            "n=\"0.05s\" dur=\"0.0s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 0.0 L 0.0 -190.0\" begin=\"6.05s\" dur=\"0.5s\"" +
            " fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 0.0 -190.0 L 300.0 -190.0\" begin=\"6.6s\" dur=\"0." +
            "5s\" fill=\"freeze\" />\n" +
            "<animateMotion path=\"M 300.0 -190.0 L 300.0 0.0\" begin=\"7.15s\" dur=\"0." +
            "5s\" fill=\"freeze\" />\n" +
            "<animate attributeName=\"fill\" attributeType=\"CSS\" from=\"RGB(11, 45, 17" +
            "5)\" to=\"RGB(0, 255, 0)\" begin=\"7.65s\" dur=\"0.4s\" fill=\"freeze\" />\n" +
            "</rect>\n" +

            "</svg>\n" +
            "</body>\n" +
            "</html>\n", outContent.toString());
  }

  @Test
  public void testSVG3() throws IOException {
    String[] args = {"-if", "buildings.txt", "-iv", "svg", "-o", "buildings.svg", "-speed", "20"};
    EasyAnimator.main(args);
    assertEquals("", outContent.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAbstractExceptionZero() {
    AbstractView av = new TextView(new SimpleAnimation(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAbstractExceptionNeg() {
    AbstractView av = new TextView(new SimpleAnimation(), -1);
  }

  // Wrong shape type in text file.
  @Test(expected = IllegalStateException.class)
  public void testIllegalStatetException() throws IOException {
    String[] args = {"-if", "invalid-shape.txt", "-iv", "svg", "-o", "out", "-speed", "20"};
    EasyAnimator.main(args);
  }

  @Test
  public void testVisual() throws IOException, InterruptedException {
    String[] args = {"-if", "smalldemo.txt", "-iv", "visual", "-o", "out", "-speed", "10"};
    EasyAnimator.main(args);
    Thread.sleep(30000);
  }

  @Test
  public void testInteractive() throws IOException, InterruptedException {
    String[] args = {"-if", "smalldemo.txt", "-iv", "interactive", "-o", "out", "-speed", "10"};
    EasyAnimator.main(args);
    Thread.sleep(30000);
  }

  @Test
  public void testSingleShapeRotate() throws IOException, InterruptedException {
    String[] args = {"-if", "rotateshape.txt", "-iv", "interactive", "-o", "out", "-speed", "20"};
    EasyAnimator.main(args);
    Thread.sleep(300000);
  }

  @Test
  public void testSVGSingleShapeRotate() throws IOException {
    String[] args = {"-if", "rotateshape.txt", "-iv", "svg", "-o", "rotateShape.xml", "-speed", "20"};
    EasyAnimator.main(args);
  }

  @Test
  public void testRotateAndMoveShape() throws IOException, InterruptedException {
    String[] args = {"-if", "rotateandmove.txt", "-iv", "interactive", "-speed", "20"};
    EasyAnimator.main(args);
    Thread.sleep(300000);
  }

  @Test
  public void testNormalInteractive() throws IOException, InterruptedException {
    String[] args = {"-if", "buildings.txt", "-iv", "interactive", "-o", "hello.xml", "-speed", "20"};
    EasyAnimator.main(args);
    Thread.sleep(300000);
  }

  @Test
  public void testLayerInteractive() throws IOException, InterruptedException {
    String[] args = {"-if", "multilayertest.txt", "-iv", "interactive", "-speed", "20"};
    EasyAnimator.main(args);
    Thread.sleep(300000);
  }

  @Test
  public void test2LayerInteractive() throws IOException, InterruptedException {
    String[] args = {"-if", "2layerstest.txt", "-iv", "interactive", "-speed", "20"};
    EasyAnimator.main(args);
    Thread.sleep(300000);
  }
}
