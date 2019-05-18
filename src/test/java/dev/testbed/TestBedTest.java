package dev.testbed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import stub.ClassUnderTest;
import stub.DependencyX;
import stub.DependencyY;
import stub.UnusedDependency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for TestBed integrating with stubbed dependencies and CUT.
 *
 * @see stub.ClassUnderTest
 * @see stub.DependencyX
 * @see stub.DependencyY
 */
class TestBedTest {

    @Nested
    class DependencyInjectedConstructor {

        @Nested
        class GetDependency {

            @Test
            @DisplayName("it should store each constructor argument as a dependency")
            void dependenciesStored() {
                TestBuilderStub testBuilderStub = new TestBuilderStub();

                DependencyX dependencyX = testBuilderStub.getDependency(DependencyX.class);
                DependencyY dependencyY = testBuilderStub.getDependency(DependencyY.class);

                assertThat(dependencyX).isNotNull();
                assertThat(dependencyY).isNotNull();
            }

            @Test
            @DisplayName("the stored dependencies have been mocked by default")
            void dependenciesMocked() {
                TestBuilderStub testBuilderStub = new TestBuilderStub();

                DependencyX dependencyX = testBuilderStub.getDependency(DependencyX.class);
                DependencyY dependencyY = testBuilderStub.getDependency(DependencyY.class);

                dependencyX.action();
                dependencyY.action();

                verify(dependencyX).action();
                verify(dependencyY).action();
            }

            @Test
            @DisplayName("it should throw a DependencyNotExistsException if I get a non-existent dependency")
            void dependencyNotExists() {
                TestBuilderStub testBuilderStub = new TestBuilderStub();

                assertThatThrownBy(() -> testBuilderStub.getDependency(UnusedDependency.class));
            }

        }

    }

    class TestBuilderStub extends TestBed<ClassUnderTest, TestBuilderStub> {

        TestBuilderStub() {
            super(ClassUnderTest.class);
        }

    }

}