package dev.testbed.constructors.strategies.arguments;

import dev.testbed.constructors.exceptions.NoClassConstructorsException;
import dev.testbed.constructors.strategies.ConstructorSelectionStrategy;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;

public class MaxSelectionStrategy implements ConstructorSelectionStrategy {

    /**
     * @param classUnderTest to scan for constructors.
     * @return the public constructor of the class which has the most arguments.
     */
    @Override
    public <T> Constructor<T> getConstructor(Class<T> classUnderTest) {
        return (Constructor<T>) Arrays.stream(classUnderTest.getConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new NoClassConstructorsException(classUnderTest));
    }

}
