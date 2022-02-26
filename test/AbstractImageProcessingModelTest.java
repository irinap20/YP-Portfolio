import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.Stack;
import junit.framework.TestCase;
import model.Blur;
import model.Greyscale;
import model.ILayer;
import model.ImageEditors;
import model.ImageInterface;
import model.Image;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.ImageProgram;
import model.MultiImageProcessingModel;
import model.MultiImageProcessingModelImpl;
import model.RGB;
import model.Rainbow;
import model.Sepia;
import model.Sharpening;
import org.junit.Test;

/**
 * Testing the simple image processing model and the layered image processing model implementation
 * class.
 */
public abstract class AbstractImageProcessingModelTest {

  /**
   * Creates an image processing model according to the class.
   *
   * @return the corresponding image processing model in each class
   */
  protected abstract ImageProcessingModel model();

  /**
   * Testing the simple image processing model.
   */
  public static final class ImageProcessingModelImplTest extends AbstractImageProcessingModelTest {

    @Override
    protected ImageProcessingModel model() {
      return new ImageProcessingModelImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullImgEditor() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image img = new Image(pixels);
      ImageProcessingModel model = model();
      model.setImage(img);
      model.setEffect(null);
    }

    @Test
    public void applyOperationMultipleColorTransformation() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image img = new Image(pixels);
      ImageEditors ie = new Sepia();
      ImageProcessingModel model = model();
      model.setImage(img);
      model.setEffect(ie);
      assertEquals(img, model.getImage());
      model.applyOperation();
      assertNotEquals(img, model.getImage());
      ImageInterface expected = ie.apply(img);
      assertEquals(expected, model.getImage());
      model.applyOperation();
      assertNotEquals(expected, model.getImage());
      assertEquals(ie.apply(expected), model.getImage());
    }

    @Test
    public void applyOperationFiltering() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image img = new Image(pixels);
      ImageEditors ie = new Blur();
      ImageProcessingModel model = model();
      model.setImage(img);
      model.setEffect(ie);
      assertEquals(img, model.getImage());
      model.applyOperation();
      assertNotEquals(img, model.getImage());
      ImageInterface expected = ie.apply(img);
      assertEquals(expected, model.getImage());
    }

    @Test
    public void getEffect() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image img = new Image(pixels);
      ImageEditors ie = new Greyscale();
      ImageProcessingModel model = model();
      model.setImage(img);
      model.setEffect(ie);
      assertEquals(ie, model.getEffect());
    }

    @Test
    public void setImageAfterStart() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image img = new Image(pixels);
      ImageEditors ie = new Greyscale();
      ImageProcessingModel model = model();
      model.setImage(img);
      model.setEffect(ie);
      RGB[][] newPixels = new RGB[][]{{red}, {green}};
      Image newImg = new Image(newPixels);
      model.setImage(newImg);
      assertEquals(1, model.getWidthImage());
      assertEquals(2, model.getHeightImage());
    }

    @Test
    public void setImage() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image img = new Image(pixels);
      ImageProcessingModel model = model();
      model.setImage(img);
      assertEquals(img, model.getImage());
    }

    @Test
    public void applyOperation() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image img = new Image(pixels);
      ImageEditors ie = new Greyscale();
      ImageProcessingModel model = model();
      model.setImage(img);
      model.setEffect(ie);
      assertEquals(img, model.getImage());
      model.applyOperation();
      assertNotEquals(img, model.getImage());
      ImageInterface expected = ie.apply(img);
      assertEquals(expected, model.getImage());
    }


    @Test
    public void applyOperationMultipleFiltering() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image img = new Image(pixels);
      ImageEditors ie = new Sharpening();
      ImageProcessingModel model = model();
      model.setImage(img);
      model.setEffect(ie);
      assertEquals(img, model.getImage());
      model.applyOperation();
      assertNotEquals(img, model.getImage());
      ImageInterface expected = ie.apply(img);
      assertEquals(expected, model.getImage());
      model.applyOperation();
      assertNotEquals(expected, model.getImage());
      assertEquals(ie.apply(expected), model.getImage());
    }

    @Test
    public void setEffectAfterStart() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image img = new Image(pixels);
      ImageEditors ie = new Sepia();
      ImageProcessingModel model = model();
      model.setImage(img);
      model.setEffect(ie);
      ImageEditors newIe = new Blur();
      model.setEffect(newIe);
      assertTrue(model.getEffect() instanceof Blur);
    }

    @Test
    public void getImage() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image img = new Image(pixels);
      ImageEditors ie = new Greyscale();
      ImageProcessingModel model = model();
      model.setImage(img);
      model.setEffect(ie);
      assertEquals(img, model.getImage());
    }

    @Test
    public void testConstructor() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image img = new Image(pixels);
      ImageEditors ie = new Greyscale();
      ImageProcessingModel model = model();
      model.setImage(img);
      model.setEffect(ie);
      assertEquals(2, model.getHeightImage());
      assertEquals(2, model.getWidthImage());
      assertEquals(img, model.getImage());
      assertEquals(ie, model.getEffect());
    }
  }

  /**
   * Testing the layered image processing model.
   */
  public static final class MultiImageProcessingModelImplTest extends
      AbstractImageProcessingModelTest {

    @Override
    protected ImageProcessingModel model() {
      return new MultiImageProcessingModelImpl();
    }

    @Test
    public void createLayer() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      assertTrue(model.getAll().isEmpty());
      model.createLayer();
      assertEquals(1, model.getAll().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeLayerIndexBelow0() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.removeLayer(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeLayerIndexAboveSize() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.removeLayer(0);
    }

    @Test
    public void removeLayer() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.createLayer();
      model.createLayer();
      assertEquals(2, model.getAll().size());
      assertEquals(2, model.getCurrent());
      model.removeLayer(1);
      assertEquals(1, model.getAll().size());
      assertEquals(1, model.getCurrent());
      model.removeLayer(0);
      assertTrue(model.getAll().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveLayerIndexOutOfBound() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.moveLayer(0, 0);
    }

    @Test
    public void moveLayerSameDest() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.createLayer();
      Stack<ILayer> temp = model.getAll();
      model.moveLayer(0, 0);
      assertEquals(temp, model.getAll());
    }

    @Test
    public void moveLayer() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.createLayer();
      model.createLayer();
      model.setCurrent(0);
      RGB purple = new RGB(128, 0, 128);
      RGB cyan = new RGB(0, 255, 255);
      RGB[][] pixels = new RGB[][]{{purple}, {cyan}};
      Image img = new Image(pixels);
      model.setImage(img);
      Stack<ILayer> temp = model.getAll();
      model.moveLayer(1, 0);
      assertNotEquals(temp, model.getAll());
      model.moveLayer(0, 1);
      assertEquals(temp, model.getAll());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCurrentIndexOutOfBound() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.setCurrent(0);
    }

    @Test
    public void setCurrent() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.createLayer();
      model.createLayer();
      model.visible(0, false);
      model.visible(1, false);

      model.setCurrent(1);
      assertEquals(2, model.getCurrent());
      TestCase.assertFalse(model.getAll().get(1).getVisibility());
    }

    @Test(expected = IllegalArgumentException.class)
    public void visibleIndexOutOfBound() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.createLayer();

      model.visible(-1, false);
    }

    @Test
    public void visible() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.createLayer();
      model.setCurrent(0);
      assertEquals(1, model.getCurrent());
      assertTrue(model.getAll().get(0).getVisibility());
      model.visible(0, false);
      assertFalse(model.getAll().get(0).getVisibility());
    }

    @Test(expected = IllegalStateException.class)
    public void isVisibleNoCurrent() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.isVisible();
    }

    @Test
    public void isVisible() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.createLayer();
      model.setCurrent(0);
      assertTrue(model.isVisible());
    }

    @Test(expected = IllegalArgumentException.class)
    public void isThisVisibleIndexOutOfBound() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.isThisVisible(0);
    }

    @Test
    public void isThisVisible() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.createLayer();
      assertTrue(model.isThisVisible(0));
      model.getAll().get(0).setVisibility(false);
      assertFalse(model.isThisVisible(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawNull() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.draw(null);
    }

    @Test
    public void draw() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.createLayer();
      ImageProgram ip = new Rainbow(10, 7);
      assertEquals(255, model.draw(ip).getMaxValue());
      assertEquals(7, model.draw(ip).getHeight());
      assertEquals(10, model.draw(ip).getWidth());
      RGB green = new RGB(0, 255, 0);
      assertEquals(green, model.draw(ip).getPixel(3, 3));
    }

    @Test
    public void getAll() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      assertTrue(model.getAll().isEmpty());
      model.createLayer();
      assertEquals(1, model.getAll().size());
    }

    @Test
    public void getCurrentNoCurrent() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      assertEquals(0, model.getCurrent());
    }

    @Test
    public void getCurrent() {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      model.createLayer();
      model.setCurrent(0);
      assertEquals(1, model.getCurrent());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullImg() {
    ImageEditors ie = new Greyscale();
    ImageProcessingModel model = model();
    model.setEffect(ie);
    model.setImage(null);
  }



  @Test(expected = IllegalStateException.class)
  public void getImageBeforeStart() {
    ImageProcessingModel model = model();
    ImageInterface img = model.getImage();
  }


  @Test(expected = IllegalArgumentException.class)
  public void setImageNull() {
    ImageProcessingModel model = model();
    model.setImage(null);
  }



  @Test(expected = IllegalStateException.class)
  public void getEffectBeforeStart() {
    ImageProcessingModel model = model();
    ImageEditors ie = model.getEffect();
  }



  @Test(expected = IllegalArgumentException.class)
  public void setEffectNull() {
    ImageProcessingModel model = model();
    model.setEffect(null);
  }

  @Test
  public void setEffect() {
    ImageProcessingModel model = model();
    ImageEditors ie = new Sepia();
    model.setEffect(ie);
    assertEquals(ie, model.getEffect());
  }




  @Test(expected = IllegalStateException.class)
  public void getHeightImageBeforeStart() {
    ImageProcessingModel model = model();
    int height = model.getHeightImage();
  }

  @Test
  public void getHeightImage() {
    ImageProcessingModel model = new ImageProcessingModelImpl();
    RGB blue = new RGB(0, 0, 255);
    RGB yellow = new RGB(255, 255, 0);
    RGB[][] pixels = new RGB[][]{{blue, yellow}};
    Image img = new Image(pixels);
    model.setImage(img);
    assertEquals(1, model.getHeightImage());
  }

  @Test(expected = IllegalStateException.class)
  public void getWidthImageBeforeStart() {
    ImageProcessingModel model = model();
    int width = model.getWidthImage();
  }

  @Test
  public void getWidthImage() {
    ImageProcessingModel model = new ImageProcessingModelImpl();
    RGB blue = new RGB(0, 0, 255);
    RGB yellow = new RGB(255, 255, 0);
    RGB[][] pixels = new RGB[][]{{blue, yellow}};
    Image img = new Image(pixels);
    model.setImage(img);
    assertEquals(2, model.getWidthImage());
  }

  @Test(expected = IllegalStateException.class)
  public void applyOperationBeforeStart() {
    ImageProcessingModel model = model();
    model.applyOperation();
  }


}