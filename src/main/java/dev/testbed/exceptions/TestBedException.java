package dev.testbed.exceptions;

/**
 * Wrapper class for any Mockito exceptions we might occur.
 */
public class TestBedException extends RuntimeException {
    public TestBedException(Exception e) {
        super(e);
    }
}