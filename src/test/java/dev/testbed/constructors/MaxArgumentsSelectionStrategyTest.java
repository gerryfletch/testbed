package dev.testbed.constructors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stub.DependencyX;
import stub.DependencyY;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MaxArgumentsSelectionStrategyTest {

    private ConstructorSelectionStrategy strategy;

    @BeforeEach
    void setup() {
        this.strategy = SelectionStrategy.MAX_ARGUMENTS;
    }

    @Test
    @DisplayName("it should return an empty constructor for a class with no constructor")
    void noConstructor() {
        Constructor<NoConstructorStub> constructor = strategy.getConstructor(NoConstructorStub.class);

        assertThat(constructor.getParameterCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("it should return the public constructor of a class with an equal private constructor")
    void similarPublicAndPrivateConstructors() {

    }

    @Test
    @DisplayName("it should select the constructor with the maximum number of arguments")
    void maxArgumentsConstructor() {

    }

    class NoConstructorStub {

    }

    class SimilarPublicAndPrivateConstructors {

        public SimilarPublicAndPrivateConstructors(DependencyX x) {

        }

        private SimilarPublicAndPrivateConstructors(DependencyY y) {

        }

    }

    class MaxArgumentsConstructor {

        public MaxArgumentsConstructor(DependencyX dependencyX) {

        }

        public MaxArgumentsConstructor(DependencyX dependencyX, DependencyY dependencyY) {

        }

    }
}