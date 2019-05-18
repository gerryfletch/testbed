package dev.testbed.constructors;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

/**
 * Default strategies available via TestBed to select constructors for dependency injection.
 */
public enum SelectionStrategy implements ConstructorSelectionStrategy {

    MAX_ARGUMENTS(MaxArgumentsSelectionStrategy::new);

    private final ConstructorSelectionStrategy selectionStrategy;

    SelectionStrategy(Supplier<ConstructorSelectionStrategy> selectionStrategy) {
        this.selectionStrategy = selectionStrategy.get();
    }

    @Override
    public <T> Constructor<T> getConstructor(Class<T> classUnderTest) {
        return selectionStrategy.getConstructor(classUnderTest);
    }
}
