package dev.testbed.dependencies.exceptions;

public class UnknownDependencyException extends RuntimeException {
    public UnknownDependencyException(Class dependency) {
        super(String.format(
                "The dependency %s has not been provided.\n" +
                        "This dependency does not exist in the Class Under Test constructor.",
                dependency
        ));
    }
}
