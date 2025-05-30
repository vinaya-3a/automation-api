
import org.testng.TestNG;


public class TestHarness {
    public static void main(String[] args) {
        String tc = System.getenv("TEST_CASE");

        TestNG testng = new TestNG();
        if ("tc001".equals(tc)) {
            testng.setTestClasses(new Class[] { TC001.class });
        } else if ("tc002".equals(tc)) {
            testng.setTestClasses(new Class[] { TC002.class });
        } else if ("tc003".equals(tc)) {
            testng.setTestClasses(new Class[] { TC003.class });
        } else {
            testng.setTestClasses(new Class[] { TC001.class, TC002.class, TC003.class });
        }
        testng.run();
    }
}
