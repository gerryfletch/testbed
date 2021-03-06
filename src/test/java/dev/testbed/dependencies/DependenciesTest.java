package dev.testbed.dependencies;

import dev.testbed.dependencies.exceptions.UnknownDependencyException;
import dev.testbed.exceptions.TestBedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import stub.DependencyX;
import stub.DependencyY;
import stub.DependencyZ;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
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

    @Nested
    class SetDependency {

        Dependencies dependencies;

        @BeforeEach
        void setup() {
            Constructor classStubConstructor = ClassStub.class.getConstructors()[0];
            dependencies = new Dependencies(classStubConstructor);
        }

        @Test
        @DisplayName("it should replace an existing mocked dependency")
        void replacingDependency() {
            DependencyX newDependency = new DependencyX();

            assertThat(dependencies.getDependency(DependencyX.class)).isNotEqualTo(newDependency);

            dependencies.setDependency(DependencyX.class, newDependency);

            assertThat(dependencies.getDependency(DependencyX.class)).isEqualTo(newDependency);
        }

        @Test
        @DisplayName("it should throw an UnknownDependencyException if the new dependency is not in the constructor")
        void setUnknownDependency() {
            assertThatThrownBy(() -> dependencies.setDependency(String.class, ""))
                    .isInstanceOf(UnknownDependencyException.class);
        }

    }

    @Nested
    class SetNewDependencies {

        Dependencies dependencies;

        @BeforeEach
        void setup() {
            Constructor classStubConstructor = ClassStub.class.getConstructors()[0];
            dependencies = new Dependencies(classStubConstructor);
        }

        @Test
        @DisplayName("it should store a mocked version the dependencies")
        void storesMocks() {
            dependencies.setNewDependencies(DependencyZ.class);

            DependencyZ dependencyZ = dependencies.getDependency(DependencyZ.class);

            assertThat(dependencyZ.getClass().toGenericString()).containsIgnoringCase("MockitoMock");
        }

    }


    @Nested
    class SetNewDependency {

        Dependencies dependencies;

        @BeforeEach
        void setup() {
            Constructor classStubConstructor = ClassStub.class.getConstructors()[0];
            dependencies = new Dependencies(classStubConstructor);
        }

        @Test
        @DisplayName("it should overwrite any existing dependency")
            // This shouldn't ever really happen, but if a dev does use both constructor dep and instantiating deps,
            // this should overwrite it for simplicity.
        void overwritesDependency() {
            DependencyX dependencyX = mock(DependencyX.class);

            DependencyX storedDependency = dependencies.getDependency(DependencyX.class);

            assertThat(dependencyX).isNotEqualTo(storedDependency);

            dependencies.setNewDependency(DependencyX.class, dependencyX);

            DependencyX newStoredDependency = dependencies.getDependency(DependencyX.class);

            assertThat(dependencyX).isEqualTo(newStoredDependency);
        }

        @Test
        @DisplayName("it should store the dependency, available via getDependency")
        void storesDependency() {
            BadDependencyStub badDependencyStub = new BadDependencyStub("");

            dependencies.setNewDependency(BadDependencyStub.class, badDependencyStub);

            assertThat(dependencies.getDependency(BadDependencyStub.class)).isEqualTo(badDependencyStub);
        }

        @Test
        @DisplayName("it should use PowerMock.whenNew to store the dependency")
        void usesPowerMock() {
            // stub. not sure how to test this yet. TODO
        }


    }

    class ClassStub {
        private final DependencyX dependencyX;
        private final DependencyY dependencyY;
        private final DependencyZ dependencyZ;

        public ClassStub(DependencyX dependencyX, DependencyY dependencyY) {
            this.dependencyX = dependencyX;
            this.dependencyY = dependencyY;

            this.dependencyZ = new DependencyZ();
        }

        public DependencyX getDependencyX() {
            return dependencyX;
        }

        public DependencyY getDependencyY() {
            return dependencyY;
        }

        public DependencyZ getDependencyZ() {
            return dependencyZ;
        }
    }

    class BadDependencyStub {
        public BadDependencyStub(String x) {
        }
    }
}