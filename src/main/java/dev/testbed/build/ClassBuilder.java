package dev.testbed.build;

import dev.testbed.dependencies.Dependencies;
import dev.testbed.exceptions.TestBedException;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * Holds internal logic of class building with reflection.
 */
public class ClassBuilder<T> {

    private final Constructor<T> constructor;
    private Dependencies dependencies;

    public ClassBuilder(Constructor<T> constructor, Dependencies dependencies) {
        this.constructor = constructor;
        this.dependencies = dependencies;
    }

    /**
     * @return an instantiated Class Under Test, with dependencies injected into the constructor.
     * @throws TestBedException if the object instantiation fails.
     */
    public T buildClassUnderTest() throws TestBedException {
        Object[] constructorArguments = this.getOrderedConstructorArguments();

        try {
            return constructor.newInstance(constructorArguments);
        } catch (Exception e) {
            throw new TestBedException(e);
        }
    }

    /**
     * @return an ordered list of dependencies to be injected into the CUT constructor.
     */
    private Object[] getOrderedConstructorArguments() {
        Class[] objectParameters = constructor.getParameterTypes(); // ordered array of parameters
        return Arrays.stream(objectParameters).map(param -> dependencies.getDependency(param)).toArray();
    }

}
