package dev.testbed.dependencies;

import dev.testbed.dependencies.exceptions.UnknownDependencyException;
import dev.testbed.exceptions.TestBedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import stub.DependencyX;
import stub.DependencyY;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

class DependenciesTest {

    @Nested
    class Construction {

        @Test
        @DisplayName("it should throw a TestBed exception if a dependency is unmockable")
        void badDependency() {
            // Mockito doesn't like Strings because they are a final class. If I have a dependency injected String,
            // all hell will break loose!
            assertThatThrownBy(() -> new Dependencies(BadDependencyStub.class.getConstructors()[0]))
                    .isInstanceOf(TestBedException.class);
        }

    }

    @Nested
    class GetDependency {

        Dependencies dependencies;

        @BeforeEach
        void setup() {
            Constructor classStubConstructor = ClassStub.class.getConstructors()[0];
            dependencies = new Dependencies(classStubConstructor);
        }

        @Test
        @DisplayName("it should return the dependencies of a constructor")
        void correctDependenciesReturned() {
            assertThat(dependencies.getDependency(DependencyX.class)).isNotNull();
            assertThat(dependencies.getDependency(DependencyX.class)).isInstanceOf(DependencyX.class);

            assertThat(dependencies.getDependency(DependencyY.class)).isNotNull();
            assertThat(dependencies.getDependency(DependencyY.class)).isInstanceOf(DependencyY.class);
        }

        @Test
        @DisplayName("it should mock the dependencies")
        void dependenciesMocked() {
            DependencyX dependencyX = dependencies.getDependency(DependencyX.class);
            DependencyY dependencyY = dependencies.getDependency(DependencyY.class);

            dependencyX.action();
            dependencyY.action();

            verify(dependencyX).action();
            verify(dependencyY).action();
        }

        @Test
        @DisplayName("it should throw an UnknownDependencyException if it does not exist")
        void unknownDependencyThrowsException() {
            assertThatThrownBy(() -> dependencies.getDependency(String.class))
                    .isInstanceOf(UnknownDependencyException.class);
        }

    }

    class ClassStub {
        public ClassStub(DependencyX dependencyX, DependencyY dependencyY) {
        }
    }

    class BadDependencyStub {
        public BadDependencyStub(String x) {
        }
    }
}