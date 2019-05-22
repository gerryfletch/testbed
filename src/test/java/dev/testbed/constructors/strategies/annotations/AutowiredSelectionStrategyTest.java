package dev.testbed.constructors.strategies.annotations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import stub.DependencyX;
import stub.DependencyY;
import stub.DependencyZ;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;

class AutowiredSelectionStrategyTest {

    @Nested
    class hasAnnotation {
        Constructor<ClassUnderTest> constructor;

        @BeforeEach
        void setup() {
            constructor = new AutowiredSelectionStrategy().getConstructor(ClassUnderTest.class);
        }

        @Test
        @DisplayName("it should return a non-null constructor")
        void returnsConstructor() {
            assertThat(constructor).isNotNull();
        }

        @Test
        @DisplayName("it should have the @Autowired annotation")
        void hasAutowiredAnnotation() {
            assertThat(constructor.getAnnotation(Autowired.class)).isNotNull();
        }

        class ClassUnderTest {
            @Autowired
            public ClassUnderTest(DependencyZ dependencyZ) {
            }

            public ClassUnderTest(DependencyX dependencyX, DependencyY dependencyY) {
            }
        }
    }

    @Nested
    class noAnnotation {

        @Test
        @DisplayName("it should return null")
        void returnsNull() {
            Constructor<ClassUnderTest> constructor = new AutowiredSelectionStrategy().getConstructor(ClassUnderTest.class);

            assertThat(constructor).isNull();
        }

        class ClassUnderTest {
            public ClassUnderTest(DependencyX dependencyX, DependencyY dependencyY) {
            }
        }
    }

}