package dev.testbed.build;

import dev.testbed.dependencies.Dependencies;
import dev.testbed.exceptions.TestBedException;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
     * Instantiates a class under test with the arguments as specified, ignoring any preset dependencies or
     * constructors found.
     * @param arguments ordered array of objects to inject to the Class Under Test constructor.
     * @return an instantiated Class Under Test.
     * @throws TestBedException if the constructor does not exist, or the object instantiation fails.
     */
    public T buildClassUnderTest(Class<T> classUnderTest, Object... arguments) throws TestBedException {
        Class[] parameters = this.getOrderedParameters(arguments);

        try {
            return classUnderTest.getConstructor(parameters).newInstance(arguments);
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

    private List<ConstructorArgument> getOrderedConstructorArguments() {
        Class[] objectParameters = constructor.getParameterTypes();
        return Arrays.stream(objectParameters)
                .map(p -> new ConstructorArgument(dependencies.getDependency(p), p))
                .collect(Collectors.toList());
    }

    private List<ConstructorArgument> getOrderedConstructorArguments(Object[] arguments) {
        return Arrays.stream(arguments).map(ConstructorArgument::new).collect(Collectors.toList());
    }

    /**
     * Maps an array of constructor arguments into classes, performing a check on the superclass to see if the
     * argument was mocked.
     *
     * @param arguments of a constructor.
     * @return the classes of the arguments.
     */
    private Class[] getOrderedParameters(Object[] arguments) {
        Class[] parameters = new Class[arguments.length];

        for (int i = 0; i < arguments.length; i++) {
            Class argClass = arguments[i].getClass();
            Class superClass = argClass.getSuperclass();

            if (superClass.equals(Object.class)) {
                // If the superclass is Object, it is likely the object has not been mocked, so provide the base class
                parameters[i] = argClass;
            } else {
                parameters[i] = superClass;
            }
        }

        return parameters;
    }

}
