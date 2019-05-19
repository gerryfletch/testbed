package dev.testbed.build;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import stub.DependencyX;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ConstructorArgumentTest {

    @Nested
    class WithMocks {

        @Test
        @DisplayName("it should store the class of the original object, not the mock")
        void storesClassOfDep() {
            DependencyX dependencyX = new DependencyX();
            DependencyX mockDependencyX = mock(DependencyX.class);

            ConstructorArgument constructorArgument = new ConstructorArgument(mockDependencyX);

            assertThat(constructorArgument.getArgumentClass()).isEqualTo(DependencyX.class);
            assertThat(constructorArgument.getArgument()).isNotEqualTo(dependencyX).isEqualTo(mockDependencyX);
        }

        @Test
        @DisplayName("it should store the provided argument")
        void storesArgument() {
            DependencyX dependencyX = new DependencyX();
            DependencyX mockDependencyX = mock(DependencyX.class);

            ConstructorArgument constructorArgument = new ConstructorArgument(mockDependencyX);

            assertThat(constructorArgument.getArgument()).isNotEqualTo(dependencyX).isEqualTo(mockDependencyX);
        }

    }

    @Nested
    class WithoutMocks {

        @Test
        @DisplayName("it should store the class of the dependency")
        void storesClassOfDep() {
            DependencyX dependencyX = new DependencyX();

            ConstructorArgument constructorArgument = new ConstructorArgument(dependencyX);

            assertThat(constructorArgument.getArgumentClass()).isEqualTo(DependencyX.class);
        }

        @Test
        @DisplayName("it should store the provided argument")
        void storesArgument() {
            DependencyX dependencyX = new DependencyX();

            ConstructorArgument constructorArgument = new ConstructorArgument(dependencyX);

            assertThat(constructorArgument.getArgument()).isEqualTo(dependencyX);
        }

    }

    @Nested
    class ProvidingBothClassAndArgument {

        @Test
        @DisplayName("it should store both as-is")
        void storesBothAsIs() {
            DependencyX dependencyX = new DependencyX();

            ConstructorArgument constructorArgument = new ConstructorArgument(dependencyX, DependencyX.class);

            assertThat(constructorArgument.getArgument()).isEqualTo(dependencyX);
            assertThat(constructorArgument.getArgumentClass()).isEqualTo(DependencyX.class);
        }

    }

}