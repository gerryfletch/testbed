package dev.testbed.dependencies;

import dev.testbed.dependencies.exceptions.UnknownDependencyException;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Storage, retrieval, and encapsulation of the internal mocking of dependencies.
 */
public class Dependencies {

    private final Map<Class, Object> dependenciesMap;

    /**
     * Stores constructor parameters as mocked objects.
     */
    public <T> Dependencies(Constructor<T> constructor) {
        this.dependenciesMap = Arrays.stream(constructor.getParameterTypes())
                .collect(Collectors.toMap(c -> c, Mockito::mock));
    }

    public <C> C getDependency(Class<C> dependencyClass) {
        if (! this.dependenciesMap.containsKey(dependencyClass)) {
            throw new UnknownDependencyException(dependencyClass);
        }

        return (C) this.dependenciesMap.get(dependencyClass);
    }

}
