package dev.testbed.constructors.strategies;

import dev.testbed.constructors.strategies.arguments.MaxSelectionStrategy;
import dev.testbed.constructors.strategies.arguments.NoSelectionStrategy;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

/**
 * Default strategies available via TestBed to select constructors for dependency injection.
 */
public enum ArgumentSelectionStrategy implements ConstructorSelectionStrategy {

    MAX_ARGUMENTS(MaxSelectionStrategy::new),
    NONE(NoSelectionStrategy::new);

    private final ConstructorSelectionStrategy selectionStrategy;

    ArgumentSelectionStrategy(Supplier<ConstructorSelectionStrategy> selectionStrategy) {
        this.selectionStrategy = selectionStrategy.get();
    }

    @Override
    public <T> Constructor<T> getConstructor(Class<T> classUnderTest) {
        return selectionStrategy.getConstructor(classUnderTest);
    }
}
