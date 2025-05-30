package com.example;

import org.testng.TestNG;
import org.testng.annotations.Test;

public class TestHarness {
	@Test
	public static void main(String[] args) {
        String tc = System.getenv("TEST_CASE");
        TestNG testng = new TestNG();

        switch (tc) {
            case "tc001":
                testng.setTestClasses(new Class[]{TC001.class});
                break;
            case "tc002":
                testng.setTestClasses(new Class[]{TC002.class});
                break;
            case "tc003":
                testng.setTestClasses(new Class[]{TC003.class});
                break;
            default:
                testng.setTestClasses(new Class[]{TC001.class, TC002.class, TC003.class});
                break;
        }
        testng.run();
    }
}
