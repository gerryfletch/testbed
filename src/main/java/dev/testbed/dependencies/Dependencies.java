package dev.testbed.dependencies;

import dev.testbed.dependencies.exceptions.UnknownDependencyException;
import dev.testbed.exceptions.TestBedException;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Storage, retrieval, and encapsulation of the internal mocking of dependencies.
 */
public class Dependencies {

    private final Map<Class, Object> dependenciesMap;

    /**
     * Useful when there is no constructor selection strategy, this will instantiate an empty map of dependencies.
     */
    public Dependencies() {
        this.dependenciesMap = new HashMap<>();
    }

    /**
     * Stores constructor parameters as mocked objects.
     */
    public <T> Dependencies(Constructor<T> constructor) {
        try {
            this.dependenciesMap = Arrays.stream(constructor.getParameterTypes())
                    .collect(Collectors.toMap(c -> c, Mockito::mock));
        } catch (Exception e) {
            throw new TestBedException(e);
        }
    }

    /**
     * @param dependencyClass the type of dependency to return.
     * @param <C>             the type instantiated dependency to return, inferred from the class.
     * @return the instantiated dependency.
     * @throws UnknownDependencyException if the dependency has not been created.
     */
    public <C> C getDependency(Class<C> dependencyClass) throws UnknownDependencyException {
        if (!this.dependenciesMap.containsKey(dependencyClass)) {
            throw new UnknownDependencyException(dependencyClass);
        }

        return (C) this.dependenciesMap.get(dependencyClass);
    }

    /**
     * @param dependencyClass the class to assign the dependency.
     * @param dependency      the object to be injected into the Class Under Test constructor.
     * @throws UnknownDependencyException if a dependency is provided that is not in the Class Under Test constructor.
     */
    public void setDependency(Class dependencyClass, Object dependency) throws UnknownDependencyException {
        if (!this.dependenciesMap.containsKey(dependencyClass)) {
            throw new UnknownDependencyException(dependencyClass);
        }

        this.dependenciesMap.put(dependencyClass, dependency);
    }

    /**
     * Mocks the provided classes and delegates whenNew logic.
     *
     * @param dependencyClass to mock.
     */
    public void setNewDependencies(Class... dependencyClass) {
        Arrays.stream(dependencyClass).forEach(c -> this.setNewDependency(c, PowerMockito.mock(c)));
    }

    /**
     * @param dependencyClass the class to assign the dependency.
     * @param dependency      the object to be returned when the class is instantiated.
     * @throws TestBedException if PowerMock fails to find any arguments for the dependency class.
     */
    public void setNewDependency(Class dependencyClass, Object dependency) throws TestBedException {
        try {
            whenNew(dependencyClass).withAnyArguments().thenReturn(dependency);
        } catch (Exception e) {
            throw new TestBedException("PowerMock failed to stub " + dependencyClass + " with any arguments.", e);
        }

        this.dependenciesMap.put(dependencyClass, dependency);
    }
}
