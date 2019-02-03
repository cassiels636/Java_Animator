# Java Animator

This project uses the MVC design pattern to create an animator which displays shapes and animation specified by an input text file.

## Running the Jar File

Requirements: Java SE Development Kit 9

```
java -cp out/JavaAnimator.jar animator.EasyAnimator -if inputs/2layerstest.txt -iv interactive -speed 15
```

## Input Options

* **if** - Required input that specifies the input file that contains a description of an animation
* **iv** - Required input that specifics the type of animator. The types are `interactive`, `visual`, `svg`, or `text`
* **o** - Optional input used to specify the name of the output file when using the `svg` or `text` views
* **speed** - Optional input that specifies the speed of the animation in ticks per second. Default speed is 1 tick per second

## Shape and Animation Types

This animator can display ovals and rectangles(also squares and circles) in any size or color at any 2D position. By adding different animations, you can make the shape perform different moves. The animation options are MoveShape, ChangeShapeColor, ChangeShapeSize, and RotateShape. These animations can occur on their own or at the same time.

## View Types

#### Text View

This view a writes a list of shapes and animations to a specified output file.

#### SVG View

The SVG view creates an SVG file, that can be saved to a specified output file. This output file can be run in any popular browser to view the animation.

#### Visual View

The visual view opens a new window and displays the animation there. 

#### Interactive View

This view opens a new window and allows the user to play the animation in a more interactive way. The interactive views has buttons to play, pause, restart, change the tempo, and loop the animation. 

Other functions in this view are creating and viewing a subset of shapes and exporting the animations to a specified SVG file. In order to add or remove shapes from a subset, there is a dropdown that lists all the shapes in the animation by shape name and shape type. By selecting one, the shape is added to the subset and by reselecting it, it is removed from the subset. There is also a button that will open a new window and display the list of shapes currently in the subset. Next, there is also a button that will play the subset animation in the current window. Lastly, there are two buttons for exporting to a SVG file. One exports the subset and the other exports the entire animation. If there was a file name specified in the command line, the file will export to there. Otherwise, a dialog box will pop up asking for the user to input a file name to export the SVG view to.

#### Interactive View Example
![Interactive View](https://github.com/cassiels636/Java_Animator/blob/master/hybridView.PNG)
