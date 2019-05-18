package dev.testbed.constructors;

import dev.testbed.constructors.exceptions.NoClassConstructorsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stub.DependencyX;
import stub.DependencyY;
import stub.classes.*;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MaxArgumentsSelectionStrategyTest {

    private ConstructorSelectionStrategy strategy;

    @BeforeEach
    void setup() {
        this.strategy = SelectionStrategy.MAX_ARGUMENTS;
    }

    @Test
    @DisplayName("it should return an empty constructor for a class with no constructor")
    void noConstructor() {
        Constructor<NoConstructorClass> constructor = strategy.getConstructor(NoConstructorClass.class);

        assertThat(constructor.getParameterCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("it should return the public constructor of a class with an equal private constructor")
    void similarPublicAndPrivateConstructors() {
        Constructor<SimilarPublicAndPrivateConstructorsClass> constructor = strategy.getConstructor(SimilarPublicAndPrivateConstructorsClass.class);


        assertThat(constructor.getParameterCount()).isEqualTo(1);
        assertThat(constructor.getParameterTypes()[0]).isEqualTo(DependencyX.class);
    }

    @Test
    @DisplayName("it should select the constructor with the maximum number of arguments")
    void maxArgumentsConstructor() {
        Constructor<MaxArgumentsConstructorClass> constructor = strategy.getConstructor(MaxArgumentsConstructorClass.class);

        assertThat(constructor.getParameterCount()).isEqualTo(2);
        assertThat(constructor.getParameterTypes()).isEqualTo(new Class[]{DependencyX.class, DependencyY.class});
    }

    @Test
    @DisplayName("it should throw a NoClassConstructorException if the class has a private constructor")
    void privateConstructorClass() {
        assertThatThrownBy(() -> strategy.getConstructor(PrivateConstructorClass.class))
                .isInstanceOf(NoClassConstructorsException.class);
    }

    @Test
    @DisplayName("it should throw a NoClassConstructorException if the class has a package private constructor")
    void packagePrivateConstructorClass() {
        assertThatThrownBy(() -> strategy.getConstructor(PackagePrivateConstructorClass.class))
                .isInstanceOf(NoClassConstructorsException.class);
    }

}