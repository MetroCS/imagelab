package imagelab;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

public class ImgProviderTest {
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
     * Each instance of an ImgProvider should have its own unique ID attribute.
     */
    @Test
    public void constructorShouldAssignUniqueIDs() {
        ImgProvider provider1 = new ImgProvider();
        ImgProvider provider2 = new ImgProvider();
        ImgProvider provider3 = new ImgProvider();

        assertNotEquals(provider1.getid(), provider2.getid());
        assertNotEquals(provider2.getid(), provider3.getid());
        assertNotEquals(provider1.getid(), provider3.getid());
    }
}
