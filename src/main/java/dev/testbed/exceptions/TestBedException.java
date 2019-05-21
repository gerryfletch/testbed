package dev.testbed.exceptions;

/**
 * Wrapper class for any Mockito exceptions that might occur.
 */
public class TestBedException extends RuntimeException {
    public TestBedException(Exception e) {
        super(e);
    }

    public TestBedException(String message, Exception e) {
        super(message, e);
    }
}
