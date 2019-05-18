package dev.testbed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import stub.ClassUnderTest;
import stub.DependencyX;
import stub.DependencyY;

import static org.assertj.core.api.Assertions.assertThat;

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

    class TestBuilderStub extends TestBed<ClassUnderTest, TestBuilderStub> {

        TestBuilderStub() {
            super(ClassUnderTest.class);
        }

    }

}