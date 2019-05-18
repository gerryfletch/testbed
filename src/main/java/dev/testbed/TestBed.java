package dev.testbed;

/**
 * TestBed provides dependency management for unit testing, and a framework to write tests within.
 *
 * <p>
 * You can visit <a href="https://testbed.dev">https://testbed.dev</a> to learn how to get started with testbed, the
 * pro's and con's of its use, and to discover unit testing techniques.
 * </p>
 *
 * @param <T> the Class Under Test.
 * @param <B> the implementing Builder.
 */
public class TestBed<T, B> {

    private final Class<T> classUnderTest;

    public TestBed(Class<T> classUnderTest) {
        this.classUnderTest = classUnderTest;
    }

    public <C> C getDependency(Class<C> dependencyClass) {
        return (C) "";
    }
}
