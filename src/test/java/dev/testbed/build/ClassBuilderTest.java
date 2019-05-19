package dev.testbed.build;

import dev.testbed.dependencies.Dependencies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import stub.ClassUnderTest;
import stub.DependencyX;
import stub.DependencyY;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ClassBuilderTest {

    @Nested
    class BuildClassUnderTest {
        Constructor constructor;
        Dependencies dependencies;

        @BeforeEach
        void setup() {
            constructor = ClassUnderTest.class.getConstructors()[0];
            dependencies = new Dependencies(constructor);
        }

        @Test
        @DisplayName("it should return an instantiated class under test")
        void buildsCUT() {
            ClassUnderTest classUnderTest = new ClassBuilder<ClassUnderTest>(constructor, dependencies)
                    .buildClassUnderTest();

            assertThat(classUnderTest).isNotNull();
        }

        @Test
        @DisplayName("it should have mocked dependencies")
        void hasMockedDependencies() {
            ClassUnderTest classUnderTest = new ClassBuilder<ClassUnderTest>(constructor, dependencies)
                    .buildClassUnderTest();

            classUnderTest.triggerX();
            classUnderTest.triggerX();
            classUnderTest.triggerY();

            verify(dependencies.getDependency(DependencyX.class), times(2)).action();
            verify(dependencies.getDependency(DependencyY.class), times(1)).action();
        }

        @Test
        @DisplayName("it should throw a TestBedException if the constructor fails to instantiate")
        void failToInstantiate() {
            // stub test as this cannot currently be tested.
        }
    }

}