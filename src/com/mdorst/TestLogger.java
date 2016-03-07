package com.mdorst;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This class serves as a simple testing framework.
 * <p>
 * Calls to {@code assert*} are tracked, and error messages are stored
 * for a later call to {@code done()}, which will either print
 * "Passed!" or "Failed." and a list of failed assertions if applicable.
 */
public class TestLogger {
    private List<String> log;
    private PrintStream stream;
    private int testCount;

    private class Color {
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String RESET = "\u001B[0m";
    }

    public void assertTrue(boolean assertion, String error) {
        if (assertion) {
            stream.print(Color.GREEN + "." + Color.RESET);
        } else {
            stream.print(Color.RED + "F" + Color.RESET);
            log.add(Color.RED + error + Color.RESET);
        }
        testCount++;
    }

    public void assertTrue(boolean assertion) {
        assertTrue(assertion, "Invalid assertion");
    }

    public void assertFalse(boolean assertion, String error) {
        assertTrue(!assertion, error);
    }

    public void assertFalse(boolean assertion) {
        assertTrue(!assertion);
    }

    public void assertEqual(Object o1, Object o2, String error) {
        assertTrue(Objects.equals(o1, o2), error);
    }

    public void assertEqual(Object o1, Object o2) {
        assertTrue(Objects.equals(o1, o2), o1 + " != " + o2);
    }

    /**
     * Prints "n passed, n failed", and displays any failed test messages
     */
    public void done() {
        stream.println("\n" + Color.GREEN + (testCount - log.size()) + " passed, " +
                Color.RED + log.size() + " failed." + Color.RESET);
        log.forEach(stream::println);
    }

    public TestLogger(PrintStream ps) {
        stream = ps;
        log = new LinkedList<>();
    }

    public TestLogger() {
        this(System.out);
    }
}
