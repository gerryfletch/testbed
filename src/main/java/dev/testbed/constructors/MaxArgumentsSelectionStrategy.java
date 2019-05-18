package dev.testbed.constructors;

import java.lang.reflect.Constructor;

public class MaxArgumentsSelectionStrategy implements ConstructorSelectionStrategy {

    /**
     * @param classUnderTest to scan for constructors.
     * @return the public constructor of the class which has the most arguments.
     */
    @Override
    public <T> Constructor<T> getConstructor(Class<T> classUnderTest) {
        return null;
    }

}
