package com.mdorst;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This class serves as a simple testing framework.
 * <p>
 * Calls to {@code assert*} and {@code log} are tracked, and the
 * associated messages are displayed on call to {@code done},
 * depending on the verbosity settings.
 */
public class TestRunner {
    private List<String> log;
    private PrintStream stream;
    private int passCount;
    private int failCount;
    /**
     * If {@code false}, only failed test expectations will be
     * displayed. If {@code true}, all expectations and log messages
     * will be displayed.
     * Default: {@code false}
     */
    public boolean verbose = false;

    private class Color {
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String RESET = "\u001B[0m";
    }

    public void assertTrue(boolean assertion, String expectation) {
        if (assertion) {
            stream.print(Color.GREEN + "." + Color.RESET);
            if (verbose)
                log.add(Color.GREEN + expectation + Color.RESET);
            passCount++;
        } else {
            stream.print(Color.RED + "F" + Color.RESET);
            log.add(Color.RED + expectation + Color.RESET);
            failCount++;
        }
    }

    public void assertTrue(boolean assertion) {
        assertTrue(assertion, "Invalid assertion");
    }

    public void assertFalse(boolean assertion, String expectation) {
        assertTrue(!assertion, expectation);
    }

    public void assertFalse(boolean assertion) {
        assertTrue(!assertion);
    }

    public void assertEqual(Object o1, Object o2, String expectation) {
        assertTrue(Objects.equals(o1, o2), expectation);
    }

    public void assertEqual(Object o1, Object o2) {
        if (Objects.equals(o1, o2)) {
            assertTrue(true, o1 + " == " + o2);
        } else {
            assertTrue(false, o1 + " != " + o2);
        }
    }

    public void log(String message) {
        if (verbose) log.add(message);
    }

    /**
     * Prints "n passed, n failed", and displays any failed test expectations.
     * Displays passed test expectations and log messages if {@code verbose == true}
     */
    public void done() {
        stream.println("\n" + Color.GREEN + passCount + " passed, " +
                Color.RED + failCount + " failed." + Color.RESET);
        log.forEach(stream::println);
    }

    public TestRunner(PrintStream ps) {
        stream = ps;
        log = new LinkedList<>();
    }

    public TestRunner() {
        this(System.out);
    }
}
