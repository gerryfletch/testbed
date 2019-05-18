package dev.testbed.constructors;

import java.lang.reflect.Constructor;

public class MaxArgumentsConstructorSelection implements ConstructorSelectionStrategy {

    @Override
    public <T> Constructor<T> getConstructor(Class<T> classUnderTest) {
        return null;
    }

}
