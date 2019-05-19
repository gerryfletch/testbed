package dev.testbed.build;

import dev.testbed.dependencies.Dependencies;
import dev.testbed.exceptions.TestBedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import stub.ClassUnderTest;
import stub.DependencyX;
import stub.DependencyY;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

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

    @Nested
    class BuildClassUnderTestWithArguments {

        Constructor<ClassUnderTest> constructor;
        Dependencies dependencies;
        ClassBuilder<ClassUnderTest> classBuilder;

        @BeforeEach
        void setup() {
            constructor = (Constructor<ClassUnderTest>) ClassUnderTest.class.getConstructors()[0];
            dependencies = new Dependencies(constructor);
            classBuilder = new ClassBuilder<>(constructor, dependencies);
        }

        @Test
        @DisplayName("it should instantiate the CUT with correct mocked dependencies")
        void buildsCUT() {
            ClassUnderTest classUnderTest = classBuilder.buildClassUnderTest(
                    ClassUnderTest.class,
                    mock(DependencyX.class),
                    mock(DependencyY.class)
            );
            classUnderTest.triggerX();
            classUnderTest.triggerY();

            verify(classUnderTest.getDependencyX()).action();
            verify(classUnderTest.getDependencyY()).action();
        }

        @Test
        @DisplayName("it should instantiate the CUT with correct dependencies")
        void buildsCUTWithoutMocks() {
            DependencyX dependencyX = new DependencyX();
            DependencyY dependencyY = new DependencyY();

            ClassUnderTest classUnderTest = classBuilder.buildClassUnderTest(
                    ClassUnderTest.class,
                    dependencyX,
                    dependencyY
            );

            assertThat(dependencyX).isEqualTo(classUnderTest.getDependencyX());
            assertThat(dependencyY).isEqualTo(classUnderTest.getDependencyY());
        }

        @Test
        @DisplayName("it should store the arguments as dependencies")
        void argumentsStoredAsDependencies() {
            DependencyX dependencyX = new DependencyX();
            DependencyY dependencyY = new DependencyY();

            assertThat(dependencies.getDependency(DependencyX.class)).isNotEqualTo(dependencyX);
            assertThat(dependencies.getDependency(DependencyY.class)).isNotEqualTo(dependencyY);

            classBuilder.buildClassUnderTest(ClassUnderTest.class, dependencyX, dependencyY);

            assertThat(dependencies.getDependency(DependencyX.class)).isEqualTo(dependencyX);
            assertThat(dependencies.getDependency(DependencyY.class)).isEqualTo(dependencyY);
        }

        @Test
        @DisplayName("it should throw an exception if incorrect constructor parameters are provided")
        void invalidConstructor() {
            // Missing DependencyY
            assertThatThrownBy(() -> classBuilder.buildClassUnderTest(ClassUnderTest.class, new DependencyX()))
                    .isInstanceOf(TestBedException.class);
        }
    }

}