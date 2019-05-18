package stub.classes;

import stub.DependencyX;
import stub.DependencyY;

/**
 * Stub class for testing.
 * Public dependency: X
 * Private dependency: Y
 */
public class SimilarPublicAndPrivateConstructorsClass {

    public SimilarPublicAndPrivateConstructorsClass(DependencyX x) {

    }

    private SimilarPublicAndPrivateConstructorsClass(DependencyY y) {

    }

}
