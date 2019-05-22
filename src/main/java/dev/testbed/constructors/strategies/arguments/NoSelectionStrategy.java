package dev.testbed.constructors.strategies.arguments;

import dev.testbed.constructors.strategies.ConstructorSelectionStrategy;

import java.lang.reflect.Constructor;

public class NoSelectionStrategy implements ConstructorSelectionStrategy {

    @Override
    public <T> Constructor<T> getConstructor(Class<T> classUnderTest) {
        return null;
    }

}
