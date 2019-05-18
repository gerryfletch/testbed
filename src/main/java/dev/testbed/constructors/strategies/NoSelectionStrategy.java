package dev.testbed.constructors.strategies;

import dev.testbed.constructors.ConstructorSelectionStrategy;

import java.lang.reflect.Constructor;

public class NoSelectionStrategy implements ConstructorSelectionStrategy {

    @Override
    public <T> Constructor<T> getConstructor(Class<T> classUnderTest) {
        return null;
    }

}
