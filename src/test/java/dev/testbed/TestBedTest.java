package dev.testbed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import stub.ClassUnderTest;
import stub.DependencyX;
import stub.DependencyY;

import static org.assertj.core.api.Assertions.assertThat;

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

        }

    }

}

class TestBuilderStub extends TestBed<ClassUnderTest, TestBuilderStub> {

    TestBuilderStub() {
        super(ClassUnderTest.class);
    }

}
