# Image Processing

The aim of this project is to create an image editor which
follows the Model, View, Controller (MVC) design pattern.
This file, Assignment 05, contains the Model component of the application.
The following assignments may add new features to this image editor.

##Model  

###ImageUtil
The main purpose of the ImageUtil class is to read (import) and export a file.
Given the filename in String, the readPPM method reads the file and converts it 
to a workable Image object, specifically into ImagePPM. The export method takes in filename and 
content of edited Image in String format and creates a new file. Therefore, the export method
is independent of the model. The model converts the image to a String and sends it to the export()
method in this class.

####ImageUtilAddition
Handles the reading, writing, and exporting of the image file. PPM was the file type supported in
the ImageUtil class. JPEG and PNG are the additional file types that were supported in the new
ImageUtilAddition class. Upon reading in an image file, the image would be converted into an
ImageInterface. Upon writing, the image would be converted into a formatted string. Upon exporting,
the image would be saved in the res folder with a desired file name.

###ImageProcessingModelState
The main purpose of this interface is to show the 'model state' to the users. The ImageProcessingModelState is an interface containing the observer methods that may be
useful in future assignments. 
The method includes: getImage(), getEffect(), getHeightImage(), and getWidthImage().
These methods  will allow the client to 
view the model state in the controller component. The returned results for 
all methods in this interface are immutable. 

###ImageProcessingModel 
ImageProcessing model represents the central component of MVC that manages the logic of the application. 
The model extends the ImageProcessingModelState. In addition to the observer methods, this interface
includes methods such as apply() and write() that manipulate the actual image. The apply() method
applies the filter to the image while the write() method creates a String of contents that could later be exported using the 
export() method in ImageUtil class.
The client can make changes without editing the original code simply by adding a new class that uses this interface. 
 

####MultiImageProcessingModel
Represents the new model that supports layering in image processing. This new model inherits
the old functionality from the previous simple model and has the new functions of adding/removing
layers, setting the current layer and visibility, creating an computer generated image. Its subclass
MultiImageProcessingModelImpl class is an implementation of the layered image processing model.

####ImageProcessingModelImpl
This class implements the methods for ImageProcessingModel. The main purpose of this class is to call/apply the editor to the given
image and convert the contents of the Image to String for export.
It allows the application of various editor methods such as Filtering and ColorTransformation on an Image and
converts the edited image content to String.
The model also allows the clients to load an image or effect
to its program. This maybe useful for cases where the ImageProcessingModel is created with no field
initialization. 

###Image Editors
ImageEditors is an interface that represents the filters and other editing tools in the image processing 
program. This interface can be extended or implemented when a new image editing tool 
needs to be added. The 'apply(Image img)' method in ImageEditors interface will be ubiquitous among all editor classes and hence was 
written as a public method in the interface. 
When the method is called on the different classes, delegation will occur. 

####Filtering
Filtering implements the ImageEditors interface as it is a type of Image Editor.
All filtering operations require a kernel that has an odd-length side, and they always overlap the kernel to the image to multiply/add their values. 
The only distinctions between the different types of filtering operations is the kernel values. For code effeciency and organization, 
Filtering class is used as an abstract class which contains all the codes to perform
filtering. If more operations need to be added, it can simply extend this class. 

- Blur: Blur sends its specified kernel to the abstract class through the constructor.
  The abstract class performs the operation using the given kernel. 
  ```java
    public Blur() {
    super(new double[][]{{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.062}});
  }

- Sharpening: Sharpening sends its specified kernel to the abstract class through the constructor.
  The abstract class performs the operation using the given kernel.
  ```java
  public Sharpening() {
    super(new double[][]{{-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}});
  }
####Color Transformation 
ColorTransformation implements the ImageEditors interface as it is a type of Image Editor.
All color transformation operations require a matrix that can carry out the mathematical operations on each color and
the three RGB channels. Similar to filtering, the only distinctions between the different types of color transformations
is the matrix values. If more operations need to be added, it can simply extend this class and send its matrix values through
the constructor.

- Greyscale: Greyscale sends its specified matrix to the abstract class through the constructor.
  The abstract class performs the operation using the given matrix.
  ```java 
  public Greyscale() {
    super(new double[][]{{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}});
  }
- Sepia: Sepia sends its specified matrix to the abstract class through the constructor.
  The abstract class performs the operation using the given matrix.
  ```java
   public Sepia() {
    super(new double[][]{{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}});
  }
 

###ImageInterface
The ImageInterface class represents the image that this program is able to operate on. 
This interface is open to extension when the program needs to work with a new image type other than
PPM. 

####ImagePPM 
The ImagePPM class represents the image data in PPM format. It contains pixels, height of image, 
width of the image, and max value. This class overrides the toString, equal and hashCode method which 
may be essential and useful when ImagePPM objects need to be compared in future assignments.

###ImageProgram
This class represents programmatically created images. The types of programmatically created images vary; creating
an interface for this image type will allow extension if new types of image programming such as drawing Rainbow needs to be 
added later. 
####CheckerBoard 
CheckerBoard class is an example of ImageProgram; it implements the ImageProgram interface. 
The constructor takes in tile size, number of tiles and number of colors.  

###IRGB
IRGB is an interface for RGB (color) value. The interface was created to make the
code more organized and extensible in the future. 

####RGB 
RGB represents color and consists of the three channels (red, blue, and green). 
This class includes methods such as getRed(), getBlue(), getGreen(), and getValue(int index). These 
methods return integers that are immutable. 

###ILayer 
Represents a layer in the image processing model. Its subclass Layer consists of an image of type
ImageInterface and a boolean indicating its visibility. The type of the image was Optional because
a layer may or may not has an image loaded and having the field to be null is not a good design.
The boolean field represented the visibility of this layer in the image: True - visible,
False - invisible.

#View

##ImageEditorView
Takes care of the view of this program. It is capable of printing out the model information as a
string and render messages to a specific Appendable destination. Its method toString would output
the model as a formatted string. The renderMessage method would render a given message to its
Appendable object. The renderInformation method renders the current state of the model to a
destination as a formatted string. The ImageEditorViewImpl is an implementation of this interface.

##IEditorView 
###GUIView 
The GUIView class utilizes java swing to create an interactive image editor
program. We added a new interface and class because the old image editor view 
could not support the graphical user interface.

#Controller

##ImageEditorController
The controller of the new image processing model, deals with saving, loading, and executing the
image processing. It supports a variety of text commands like create-layer, save, load, current.
Upon listening to those commands, it will perform the corresponding tasks. At the end of each task,
the controller would let the user view the current state and wait for the next user input.
The ImageEditorControllerImpl is an implementation of this interface. It consists of a run method
for every supported operations.

##Interactive Controller 
For assignment 7, we had to implement a new functionality which was very different from what we 
implemented for assignment 6. We had to create an interactive image editor using the GUI view 
design (java swing). Our approach to adding this new feature was to create a new controller that 
would take care of this completely new design. Although we used the same pattern/code implementations
for certain methods, the methods in the interactive controller was different in that they were
able to perform certain commands without being dependent on the start() method. Instead of creating
private inner classes and making the start() method send the commands to respective inner classes
(command pattern), the controller in this assignment can take in commands as its parameters. This
helped us to make the view more independent of the controller and minimize coupling. 

###Features Interface 
Furthermore, we created the features interface which consisted of methods that the View 
class could call on by stating: Features.<method>. This, again, allowed the view and controller more
independent of each other. 

##Design Change 

###Assignment 6
ILayer interface and Layer class were added to support the implementation of layers. ILayer
interface extended the ImageInterface so that it is still a type of ImageInterface, supporting
previous image operations and helped with backward compatibility. The MultiImageProcessingModel
interface and its class deals with the layered image operations and supported the single image
processing model at the same time. The ImageEditorView interface is a newly added view to our
program. It is responsible for displaying the current information of the image processing program
and also messages to be shown to the user. The new ImageEditorController interface is responsible
for handling the commands passed by the user and perform the required operations or displaying
information. The newly added ImageUtilAddition class helped with handling two extra image file
types: JPEG and PNG. It is an extension of the original ImageUtil class to keep supporting PPM
format. Other than the above, the design of our program have stayed the same.

###Assignment 7

For Assignment 7, we implemented a new interface for the view of this program 
as well as a new controller that uses the new view and the multiImageEditorModel. 
We decided to create a new controller to support GUI. More specifically, we needed methods 
that would be responsible for individual responses that come from the View segment of the program. 
The controller had to be more dynamic and responsive to the inputs from View and the old controller 
did not have such behavior. In order to minimize the coupling
between the view and controller, we also implemented the Features interface 
that would serve as the bridge between view and controller.
The new interface for view and the Features interface will be useful in the future when
we have to add more methods and operations to this program. Using these interfaces will 
not only help the clients understand the code better, but easier for them to 
add new features to this program without having to change anything from the existing design.

For the extra credit, we implemented additional operations: Mosaic and Downscaling. 
To make these operations work, we created a new class for each of these features and 
made them implement the ImageEditors interface. Because these methods were 
slightly different from the existing editors, we did have to add an extra 
statement in the applyoperation method of our new model. 

##Image Citation
-	Image1 and Image2 are both free to use. They are from Pexels.
    
- Image1 is a photo by Skyler Ewing from Pexels: https://www.pexels.com/photo/colorful-male-specie-of-eastern-bluebird-starting-flight-6676551/
- Image2 is a photo by cottonbro from Pexels: https://www.pexels.com/photo/white-and-brown-concrete-building-4937197/ 
- cartoon https://www.vanityfair.com/hollywood/2021/04/trailer-for-space-jam-a-new-legacy-reveals-a-cartoon-lebron-james-and-ip-synergy
