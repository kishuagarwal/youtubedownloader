import junit.framework.TestCase;

public class HttpUtilsTest extends TestCase {

    public void testCleanUp(){
        String link = "http://www.youtube.com  //something";
        link = HttpUtils.linkCleanup(link);
        assertEquals(link,"http://www.youtube.com");
    }



}