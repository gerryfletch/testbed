package dev.testbed.constructors.strategies.annotations;

import dev.testbed.constructors.strategies.ConstructorSelectionStrategy;

import java.lang.reflect.Constructor;

public class AutowiredSelectionStrategy implements ConstructorSelectionStrategy {
    @Override
    public <T> Constructor<T> getConstructor(Class<T> classUnderTest) {
        return null;
    }
}
