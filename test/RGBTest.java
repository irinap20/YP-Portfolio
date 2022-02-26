import model.RGB;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the RGB class, its constructors and methods.
 */
public class RGBTest {

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalConstructorRedNeg() {
    new RGB(-2, 0, 200);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalConstructorGreenNeg() {
    new RGB(200, -1, 200);
  }

  @Test
  public void testEqual() {
    RGB color1 = new RGB(200, 20, 200);
    RGB color2 = new RGB(200, 20, 200);

    Assert.assertEquals(color1, color2);
  }


  @Test
  public void testHashcode() {
    RGB color1 = new RGB(200, 20, 200);
    Assert.assertEquals(20220, color1.hashCode());
  }

  @Test
  public void testGetRed() {
    RGB color1 = new RGB(200, 20, 200);
    Assert.assertEquals(200, color1.getRed());
  }


  @Test
  public void testGetGreen() {
    RGB color1 = new RGB(200, 20, 200);
    Assert.assertEquals(20, color1.getGreen());
  }

  @Test
  public void testGetBlue() {
    RGB color1 = new RGB(200, 20, 100);
    Assert.assertEquals(100, color1.getBlue());
  }


  @Test
  public void testGetValue() {
    RGB color1 = new RGB(200, 20, 100);
    Assert.assertEquals(200, color1.getValue(0));
    Assert.assertEquals(20, color1.getValue(1));
    Assert.assertEquals(100, color1.getValue(2));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetValueIllegal() {
    RGB color1 = new RGB(200, 20, 100);
    color1.getValue(3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueIllegalNeg() {
    RGB color1 = new RGB(200, 20, 100);
    color1.getValue(-1);
  }

}
