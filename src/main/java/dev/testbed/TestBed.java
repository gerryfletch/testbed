package dev.testbed;

import dev.testbed.constructors.ConstructorSelectionStrategy;
import dev.testbed.constructors.SelectionStrategy;
import dev.testbed.dependencies.Dependencies;
import dev.testbed.dependencies.exceptions.UnknownDependencyException;

import java.lang.reflect.Constructor;
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
    private final Constructor<T> constructor;
    private final Dependencies dependencies;

    /**
     * @param classUnderTest to create TestBed for.
     * @param constructorSelectionStrategy to use on the Class Under Test.
     * @see SelectionStrategy for a list of strategies and their descriptions.
     */
    public TestBed(Class<T> classUnderTest, ConstructorSelectionStrategy constructorSelectionStrategy) {
        this.classUnderTest = classUnderTest;
        this.constructor = constructorSelectionStrategy.getConstructor(classUnderTest);
        this.dependencies = new Dependencies(constructor);
    }

    /**
     * Creates a new TestBed with a default constructor selection strategy of Max Arguments.
     * @param classUnderTest to create TestBed for.
     * @see SelectionStrategy for a list of strategies and their descriptions.
     */
    public TestBed(Class<T> classUnderTest) {
        this(classUnderTest, SelectionStrategy.MAX_ARGUMENTS);
    }

    /**
     * @param dependencyClass the type of dependency to return.
     * @param <C> the type instantiated dependency to return, inferred from the class.
     * @return the instantiated dependency.
     * @throws UnknownDependencyException if the dependency has not been created.
     */
    public <C> C getDependency(Class<C> dependencyClass) throws UnknownDependencyException {
        return this.dependencies.getDependency(dependencyClass);
    }
}
