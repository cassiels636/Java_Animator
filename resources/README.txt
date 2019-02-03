This project is for creating a SimpleAnimation using the MVC pattern.

The model consists of these classes and their interfaces:
    -AnimatedShape: represents a shape in the animation
    -Animations: explained below
    -Position2D: represents a 2D position with x and y parameters
    -RGB: represents a color with red, green, and blue values
    -Layer: represents a layer of shapes in the animation
    -SimpleAnimation: explained below

Types of animations that extend the Animations class:
    -MoveShape
    -ChangeShapeColor
    -ChangeShapeSize
    -RotateShape
    -ShapeAppears
    -ShapeDisappears
    -StillShape: represents a shape when no animations is being performed on it

SimpleAnimation model class:
    This class contains methods to create/remove shapes and animations and to calculate the
    position, color, or size of a shape at a given tick. The model keeps track of these
    using 3 Lists: a List of AnimatedShapes, a List of Animations, and a timeline(List of
    Lists of Animations). The shapes List contains all shapes in order or appearance time.
    The animations List contains all Animations, except StillShape animations, in order of
    start time. Lastly, each List in the timeline represents one tick in the animation. The
    List at each tick then contains all animations that occur at that tick. This way if an
    Animation occurs from t=10 to t=20, that Animation should be in all Lists in the timeline
    from index 10 to index 20. All methods in this class modify and upkeep these Lists to
    represent the animation.

SimpleAnimation view types:
    -TextView: a String of the animation's shapes and animations
    -SVGView: a String that represents an SVG file of the animation
    -VisualView: opens a window and plays the animation
    -InteractiveView: explained below

Interactive view class:
    Functionality:
        -Playing: done by clicking a button
        -Pausing: done by clicking a button
        -Restarting: done by clicking a button
        -Tempo Changing: done by clicking a button
        -Animation Looping: done by clicking a button
        -Creating a Subset of Shapes: done by selecting shapes from a dropdown
        -Playing a Subset: done by clicking a button
        -View Subset List of Shapes: done by clicking a button
        -Export Subset to SVG: done by clicking a button
        -Export Animation to SVG: done by clicking a button
        -Scrubbing: done by using a slider

    An AbstractVisualView is used to share the code between the visual and interactive
    view as both views play the animation in a new window. Other functions in the interactive
    view are creating and viewing a subset of shapes and exporting the animations to a
    specified SVG file. In order to add or remove shapes from a subset, there is a dropdown
    that lists all the shapes in the animation by shape name and shape type. By selecting one,
    the shape is added to the subset and by reselecting it, it is removed from the subset.
    Also for exporting animations to an SVG, if there was a file name specified in the command
    line(the -o argument), the file will export to that file. Otherwise, a dialog box will pop
    up asking for the user to input a file name to export the SVG view to.
