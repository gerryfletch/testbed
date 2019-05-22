package dev.testbed.constructors.strategies.annotations;

import dev.testbed.constructors.strategies.ConstructorSelectionStrategy;

import java.lang.reflect.Constructor;

/**
 * Search the constructors for Spring's @Autowired annotation.
 */
public class AutowiredSelectionStrategy implements ConstructorSelectionStrategy {

    private final String autowiredClassName = "org.springframework.beans.factory.annotation.Autowired";

    @Override
    public <T> Constructor<T> getConstructor(Class<T> classUnderTest) {

        for (Constructor constructor : classUnderTest.getConstructors()) {
            if (ClassAnnotationUtility.constructorHasAnnotation(constructor, this.autowiredClassName)) {
                return constructor;
            }
        }

        return null;
    }

}
