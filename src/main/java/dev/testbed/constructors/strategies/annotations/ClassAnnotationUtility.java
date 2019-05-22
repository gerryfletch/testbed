package dev.testbed.constructors.strategies.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ClassAnnotationUtility {

    /**
     * @param constructor         to review.
     * @param annotationClassName fully qualified class name to search for.
     * @return true if the annotation, by class name, exists on the constructor.
     */
    public static boolean constructorHasAnnotation(Constructor constructor, String annotationClassName) {
        return Arrays.stream(constructor.getDeclaredAnnotations())
                .map(Annotation::annotationType)
                .map(Class::getName)
                .anyMatch(x -> x.equalsIgnoreCase(annotationClassName));
    }

}
