package imagelab;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DisplayImageTest {
    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
    }

    /**
     * A DisplayImage created with null parameters should crash the program.
     */
    @Test(expected = NullPointerException.class)
    public void constructorWithNullImageShouldHaveNullValue() {
        DisplayImage imageOne = new DisplayImage(null, "Title");
    }

    /**
     * The title of the DisplayImage should not be equal to an incorrect title.
     */
    @Test
    public void titleShouldNotEqualWrongTitle() {
        ImageLab labOne = new ImageLab();
        ImgProvider providerOne = new ImgProvider("mandel.jpg");
        DisplayImage imageOne = new DisplayImage(providerOne, "Title");
        assertNotEquals(imageOne.getTitle(), "Ditsdle");
    }

    /**
     * The title of the DisplayImage should be equal to the correct title.
     */
    @Test
    public void titleShouldBeEqualToCorrectTitle() {
        ImageLab labOne = new ImageLab();
        ImgProvider providerOne = new ImgProvider("mandel.jpg");
        DisplayImage imageOne = new DisplayImage(providerOne, "Title");
        assertEquals(imageOne.getTitle(), "Title");
    }
}

