package dev.testbed;

import java.util.HashMap;
import java.util.Map;

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
@SuppressWarnings("unchecked")
public class TestBed<T, B> {

    private final Class<T> classUnderTest;
    private final Map<Class, Object> dependencyMap = new HashMap<>();

    /**
     * Creates a new TestBed for your Class Under Test.
     */
    public TestBed(Class<T> classUnderTest) {
        this.classUnderTest = classUnderTest;
    }

    /**
     * @param dependencyClass to lookup.
     * @return the instantiated dependency for the Class Under Test.
     */
    public <C> C getDependency(Class<C> dependencyClass) {
        return (C) this.dependencyMap.get(dependencyClass);
    }
}
