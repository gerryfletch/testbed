package dev.testbed;

import dev.testbed.build.ClassBuilder;
import dev.testbed.constructors.SelectionStrategy;
import dev.testbed.dependencies.Dependencies;
import dev.testbed.dependencies.exceptions.UnknownDependencyException;
import dev.testbed.exceptions.TestBedException;

import java.lang.reflect.Constructor;

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
    private final Constructor<T> constructor;
    private final SelectionStrategy selectionStrategy;
    private Dependencies dependencies;

    /**
     * Creates a new TestBed with a default constructor selection strategy of Max Arguments.
     *
     * @param classUnderTest to create TestBed for.
     * @see SelectionStrategy for a list of strategies and their descriptions.
     */
    public TestBed(Class<T> classUnderTest) {
        this(classUnderTest, SelectionStrategy.MAX_ARGUMENTS);
    }

    /**
     * @param classUnderTest    to create TestBed for.
     * @param selectionStrategy to use on the Class Under Test.
     * @see SelectionStrategy for a list of strategies and their descriptions.
     */
    public TestBed(Class<T> classUnderTest, SelectionStrategy selectionStrategy) {
        this.classUnderTest = classUnderTest;
        this.constructor = selectionStrategy.getConstructor(classUnderTest);
        this.selectionStrategy = selectionStrategy;
        this.dependencies = this.createDependencies();
    }

    /**
     * @return an instantiated Class Under Test.
     * @throws TestBedException if the object instantiation fails.
     */
    public T build() throws TestBedException {
        return new ClassBuilder<>(this.constructor, this.dependencies).buildClassUnderTest();
    }

    /**
     * @param dependencyClass the type of dependency to return.
     * @param <C>             the type instantiated dependency to return, inferred from the class.
     * @return the instantiated dependency.
     * @throws UnknownDependencyException if the dependency has not been created.
     */
    public <C> C getDependency(Class<C> dependencyClass) throws UnknownDependencyException {
        return this.dependencies.getDependency(dependencyClass);
    }

    /**
     * @param dependencyClass the class to assign the dependency.
     * @param dependency      the object to be injected into the Class Under Test constructor.
     * @throws UnknownDependencyException if a dependency is provided that is not in the Class Under Test constructor.
     */
    public B setDependency(Class dependencyClass, Object dependency) {
        this.dependencies.setDependency(dependencyClass, dependency);
        return (B) this;
    }

    private Dependencies createDependencies() {
        if (this.selectionStrategy == SelectionStrategy.NONE) {
            return new Dependencies();
        } else {
            return new Dependencies(constructor);
        }
    }
}