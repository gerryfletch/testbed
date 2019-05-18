package dev.testbed.constructors.strategies;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stub.ClassUnderTest;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;

class NoSelectionStrategyTest {

    @Test
    @DisplayName("it should return null")
    void returnsNull() {
        Constructor<ClassUnderTest> classUnderTestConstructor = new NoSelectionStrategy()
                .getConstructor(ClassUnderTest.class);

        assertThat(classUnderTestConstructor).isNull();
    }

}