package dev.testbed.dependencies.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnknownDependencyExceptionTest {

    @Test
    @DisplayName("it should hold the unknown dependency in the message")
    void unknownDepInMessage() {
        UnknownDependencyException unknownDependencyException = new UnknownDependencyException(DepStub.class);

        assertThat(unknownDependencyException.getMessage()).contains(
                "The dependency",
                "DepStub",
                "has not been provided.",
                "This dependency does not exist in the Class Under Test constructor."
        );

    }

    class DepStub {

    }
}