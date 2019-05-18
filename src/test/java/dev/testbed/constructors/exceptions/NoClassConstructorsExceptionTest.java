package dev.testbed.constructors.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stub.ClassUnderTest;

import static org.assertj.core.api.Assertions.assertThat;

class NoClassConstructorsExceptionTest {

    @Test
    @DisplayName("it should write the name of the class in the exception message")
    void messageStoresClassName() {
        NoClassConstructorsException exception = new NoClassConstructorsException(ClassUnderTest.class);

        assertThat(exception.getMessage()).isEqualTo("There are no public constructors in class stub.ClassUnderTest.");
    }

}