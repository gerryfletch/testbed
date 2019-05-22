package dev.testbed.constructors.strategies;

import dev.testbed.constructors.strategies.annotations.AutowiredSelectionStrategy;
import dev.testbed.constructors.strategies.annotations.InjectSelectionStrategy;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

public enum AnnotationSelectionStrategy implements ConstructorSelectionStrategy {

    AUTOWIRED(AutowiredSelectionStrategy::new),
    INJECT(InjectSelectionStrategy::new);

    private final ConstructorSelectionStrategy selectionStrategy;

    AnnotationSelectionStrategy(Supplier<ConstructorSelectionStrategy> selectionStrategy) {
        this.selectionStrategy = selectionStrategy.get();
    }

    @Override
    public <T> Constructor<T> getConstructor(Class<T> classUnderTest) {
        return selectionStrategy.getConstructor(classUnderTest);
    }
}
