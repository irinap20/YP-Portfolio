
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import model.Image;
import model.ILayer;
import model.ImageInterface;
import model.Layer;
import model.RGB;
import org.junit.Test;

/**
 * Testing the basic operations of a layer class.
 */
public class LayerTest {

  @Test
  public void testConstructor2() {
    RGB purple = new RGB(128, 0, 128);
    RGB cyan = new RGB(0, 255, 255);
    RGB[][] pixels = new RGB[][]{{purple}, {cyan}};
    Image img = new Image(pixels);
    ILayer layer = new Layer(img);
    assertEquals(img, layer.getImage());
  }

  @Test(expected = IllegalStateException.class)
  public void testConstructorOpEmpty() {
    ILayer layer = new Layer();
    ImageInterface layerImg = layer.getImage();
  }

  @Test
  public void testConstructor1() {
    RGB purple = new RGB(128, 0, 128);
    RGB cyan = new RGB(0, 255, 255);
    RGB[][] pixels = new RGB[][]{{purple}, {cyan}};
    Image img = new Image(pixels);
    ILayer layer = new Layer(img);
    assertEquals(img, layer.getImage());
    assertEquals(2, layer.getImage().getHeight());
    assertEquals(1, layer.getImage().getWidth());
    assertEquals(255, layer.getImage().getMaxValue());
    assertEquals(purple, layer.getImage().getPixel(0, 0));
    assertEquals(cyan, layer.getImage().getPixel(0, 1));
    assertTrue(layer.getVisibility());
  }

  @Test(expected = IllegalStateException.class)
  public void getPixelOpEmpty() {
    ILayer layer = new Layer();
    layer.getImage().getPixel(0, 0);
  }

  @Test
  public void getPixel() {
    RGB purple = new RGB(128, 0, 128);
    RGB[][] pixels = new RGB[][]{{purple}};
    Image img = new Image(pixels);
    ILayer layer = new Layer(img);
    assertEquals(purple, layer.getImage().getPixel(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPixelIdxOutOfBound() {
    RGB purple = new RGB(128, 0, 128);
    RGB[][] pixels = new RGB[][]{{purple}};
    Image img = new Image(pixels);
    ILayer layer = new Layer(img);
    assertEquals(purple, layer.getImage().getPixel(1, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void getHeightOpEmpty() {
    ILayer layer = new Layer();
    layer.getImage().getHeight();
  }

  @Test
  public void getHeight() {
    RGB purple = new RGB(128, 0, 128);
    RGB[][] pixels = new RGB[][]{{purple}};
    Image img = new Image(pixels);
    ILayer layer = new Layer(img);
    assertEquals(1, layer.getImage().getHeight());
  }

  @Test(expected = IllegalStateException.class)
  public void getWidthOpEmpty() {
    ILayer layer = new Layer();
    layer.getImage().getWidth();
  }

  @Test
  public void getWidth() {
    RGB purple = new RGB(128, 0, 128);
    RGB[][] pixels = new RGB[][]{{purple}};
    Image img = new Image(pixels);
    ILayer layer = new Layer(img);
    assertEquals(1, layer.getImage().getWidth());
  }

  @Test(expected = IllegalStateException.class)
  public void getMaxValueOpEmpty() {
    ILayer layer = new Layer();
    layer.getImage().getMaxValue();
  }


  @Test
  public void getMaxValue() {
    RGB purple = new RGB(128, 0, 128);
    RGB[][] pixels = new RGB[][]{{purple}};
    Image img = new Image(pixels);
    ILayer layer = new Layer(img);
    assertEquals(255, layer.getImage().getMaxValue());
    Image imgHighMaxval = new Image(pixels, 300);
    ILayer layer2 = new Layer(imgHighMaxval);
    assertEquals(300, layer2.getImage().getMaxValue());
  }

  /*
   @Test
   public void writeOpEmpty() {
     ILayer layer = new Layer();
     assertEquals("true\n", layer.write());
   }


   @Test
   public void write() {
     RGB purple = new RGB(128, 0, 128);
     RGB cyan = new RGB(0, 255, 255);
     RGB[][] pixels = new RGB[][]{{purple}, {cyan}};
     Image img = new Image(pixels);
     ILayer layer = new Layer(img);
     assertEquals("P3\n"
         + "2 1\n"
         + "255\n"
         + "128\n"
         + "0\n"
         + "128\n"
         + "0\n"
         + "255\n"
         + "255\n"
         + "true\n", layer.write());
   }

 */
  @Test(expected = IllegalStateException.class)
  public void getImageOpEmpty() {
    ILayer layer = new Layer();
    ImageInterface layerImg = layer.getImage();
  }

  @Test
  public void getImage() {
    RGB purple = new RGB(128, 0, 128);
    RGB cyan = new RGB(0, 255, 255);
    RGB[][] pixels = new RGB[][]{{purple}, {cyan}};
    Image img = new Image(pixels);
    ILayer layer = new Layer(img);
    assertEquals(img, layer.getImage());
  }

  @Test(expected = IllegalStateException.class)
  public void getPixelsOpEmpty() {
    ILayer layer = new Layer();
    RGB[][] layerPixels = layer.getImage().getPixels();
  }

  @Test
  public void getPixels() {
    RGB purple = new RGB(128, 0, 128);
    RGB cyan = new RGB(0, 255, 255);
    RGB[][] pixels = new RGB[][]{{purple}, {cyan}};
    Image img = new Image(pixels);
    ILayer layer = new Layer(img);
    assertTrue(Arrays.deepEquals(pixels, layer.getImage().getPixels()));
  }

  @Test
  public void setVisibility() {
    ILayer layer = new Layer();
    layer.setVisibility(false);
    assertFalse(layer.getVisibility());
    RGB purple = new RGB(128, 0, 128);
    RGB cyan = new RGB(0, 255, 255);
    RGB[][] pixels = new RGB[][]{{purple}, {cyan}};
    Image img = new Image(pixels);
    ILayer newLayer = new Layer(img);
    newLayer.setVisibility(true);
    assertTrue(newLayer.getVisibility());
  }

  @Test
  public void setImage() {
    RGB purple = new RGB(128, 0, 128);
    RGB cyan = new RGB(0, 255, 255);
    RGB[][] pixels = new RGB[][]{{purple}, {cyan}};
    Image img = new Image(pixels);

    ILayer layer = new Layer();
    layer.setImage(img);
    assertEquals(img, layer.getImage());

    RGB gray = new RGB(128, 128, 128);
    RGB maroon = new RGB(128, 0, 0);
    RGB[][] newPixels = new RGB[][]{{gray}, {maroon}};
    Image img2 = new Image(newPixels);

    ILayer newLayer = new Layer(img);
    newLayer.setImage(img2);
    assertEquals(img2, newLayer.getImage());
  }

  @Test
  public void getVisibility() {
    ILayer layer = new Layer();
    assertTrue(layer.getVisibility());
    layer.setVisibility(false);
    assertFalse(layer.getVisibility());
  }
}