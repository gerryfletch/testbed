package dev.testbed.build;

public class ConstructorArgument {

    private final Object argument;
    private final Class argumentClass;

    public ConstructorArgument(Object argument) {
        this.argument = argument;

        Class argumentClass = argument.getClass();
        Class argumentSuperClass = argumentClass.getSuperclass();

        if (argumentSuperClass.equals(Object.class)) {
            this.argumentClass = argumentClass;
        } else {
            this.argumentClass = argumentSuperClass;
        }
    }

    public ConstructorArgument(Object argument, Class argumentClass) {
        this.argument = argument;
        this.argumentClass = argumentClass;
    }

    /**
     * @return the constructor argument.
     */
    public Object getArgument() {
        return argument;
    }

    /**
     * @return the class representing the constructor argument.
     */
    public Class getArgumentClass() {
        return argumentClass;
    }
}
