package dev.testbed;

import stub.ClassUnderTest;

/**
 * Unit tests for TestBed integrating with stubbed dependencies and CUT.
 *
 * @see stub.ClassUnderTest
 * @see stub.DependencyX
 * @see stub.DependencyY
 */
class TestBedTest {


    class TestBuilderStub extends TestBed<ClassUnderTest, TestBuilderStub> {

        TestBuilderStub() {
            super(ClassUnderTest.class);
        }

    }

}