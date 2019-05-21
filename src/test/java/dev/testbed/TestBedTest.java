package dev.testbed;

import dev.testbed.constructors.SelectionStrategy;
import dev.testbed.dependencies.exceptions.UnknownDependencyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import stub.ClassUnderTest;
import stub.DependencyX;
import stub.DependencyY;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

/**
 * Integrated tests for TestBed integrating with stubbed dependencies and CUT, using the builder as it should be.
 * Most of these scenarios have already been unit tested, but these aim to be higher level integration tests
 * utilising all aspects of TestBed.
 *
 * @see stub.ClassUnderTest
 * @see stub.DependencyX
 * @see stub.DependencyY
 */
class TestBedTest {

    @Nested
    class GetDependency {

        @Test
        @DisplayName("it should hold all dependencies of the Class Under Test")
        void dependenciesRetrieved() {
            TestBuilderStub testBuilderStub = new TestBuilderStub();

            DependencyX dependencyX = testBuilderStub.getDependency(DependencyX.class);
            DependencyY dependencyY = testBuilderStub.getDependency(DependencyY.class);

            assertThat(dependencyX).isNotNull();
            assertThat(dependencyY).isNotNull();
        }

    }

    @Nested
    class SetDependency {

        @Test
        @DisplayName("it should store a set dependency")
        void storesDependency() {
            DependencyX dependencyX = new DependencyX();
            TestBuilderStub builderStub = new TestBuilderStub().setDependency(DependencyX.class, dependencyX);

            DependencyX storedDependency = builderStub.getDependency(DependencyX.class);

            assertThat(storedDependency).isEqualTo(dependencyX);
        }

    }

    @Nested
    class WithNoConstructorSelectionStrategy {

        @Test
        @DisplayName("no dependencies should be available")
        void noDependenciesAvailable() {
            TestBuilderStub testBuilderStub = new TestBuilderStub(SelectionStrategy.NONE);

            assertThatThrownBy(() -> testBuilderStub.getDependency(DependencyX.class))
                    .isInstanceOf(UnknownDependencyException.class);

            assertThatThrownBy(() -> testBuilderStub.getDependency(DependencyY.class))
                    .isInstanceOf(UnknownDependencyException.class);
        }

        @Test
        @DisplayName("no dependencies can be set")
        void noDependenciesCanBeSet() {
            TestBuilderStub testBuilderStub = new TestBuilderStub(SelectionStrategy.NONE);

            assertThatThrownBy(() -> testBuilderStub.setDependency(DependencyX.class, new DependencyX()))
                    .isInstanceOf(UnknownDependencyException.class);
        }

    }

    @Nested
    class Build {

        @Test
        @DisplayName("it should create the class under test")
        void buildsCUT() {
            TestBuilderStub testBuilderStub = new TestBuilderStub();

            ClassUnderTest classUnderTest = testBuilderStub.build();

            assertThat(classUnderTest).isNotNull();
        }

        @Test
        @DisplayName("it should have mocked dependencies")
        void mockedDependencies() {
            TestBuilderStub testBuilderStub = new TestBuilderStub();

            ClassUnderTest classUnderTest = testBuilderStub.build();
            classUnderTest.triggerX();
            classUnderTest.triggerY();

            verify(testBuilderStub.getDependency(DependencyX.class)).action();
            verify(testBuilderStub.getDependency(DependencyY.class)).action();
        }

    }

    class TestBuilderStub extends TestBed<ClassUnderTest, TestBuilderStub> {

        TestBuilderStub() {
            super(ClassUnderTest.class);
        }

        TestBuilderStub(SelectionStrategy selectionStrategy) {
            super(ClassUnderTest.class, selectionStrategy);
        }

    }

}