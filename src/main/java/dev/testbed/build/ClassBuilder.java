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
     * Instantiates a class under test with the arguments as specified, ignoring any preset dependencies or
     * constructors found.
     * @param arguments ordered array of objects to inject to the Class Under Test constructor.
     * @return an instantiated Class Under Test.
     * @throws TestBedException if the constructor does not exist, or the object instantiation fails.
     */
    public T buildClassUnderTest(Class<T> classUnderTest, Object... arguments) throws TestBedException {
        Class[] parameters = new Class[arguments.length];

        for (int i = 0; i < arguments.length; i++) {
            parameters[i] = arguments[i].getClass();
        }

        try {
            return classUnderTest.getConstructor(parameters).newInstance(arguments);
        } catch (Exception e) {
            throw new TestBedException(e);
        }
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
