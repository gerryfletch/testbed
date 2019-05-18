package dev.testbed.constructors.exceptions;

public class NoClassConstructorsException extends RuntimeException {

    public NoClassConstructorsException(Class classUnderTest) {
        super("There are no public constructors in " + classUnderTest + ".");
    }

}
