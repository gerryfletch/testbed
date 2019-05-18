package stub;

public class ClassUnderTest {

    private DependencyX dependencyX;
    private DependencyY dependencyY;

    public ClassUnderTest(DependencyX dependencyX, DependencyY dependencyY) {
        this.dependencyX = dependencyX;
        this.dependencyY = dependencyY;
    }

    public void triggerX() {
        this.dependencyX.action();
    }

    public void triggerY() {
        this.dependencyY.action();
    }

}
