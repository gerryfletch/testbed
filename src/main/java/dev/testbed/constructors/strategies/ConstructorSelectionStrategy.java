package dev.testbed.constructors.strategies;

import java.lang.reflect.Constructor;

/**
 * Any constructor selector algorithms should implement this class so they can be used for class-specific TestBeds.
 */
public interface ConstructorSelectionStrategy {

    <T> Constructor<T> getConstructor(Class<T> classUnderTest);

}