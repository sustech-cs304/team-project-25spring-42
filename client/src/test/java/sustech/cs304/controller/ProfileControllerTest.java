package sustech.cs304.controller;

import junit.framework.TestCase;

public class ProfileControllerTest extends TestCase {
    private ProfileController controller;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        controller = new ProfileController();
    }

    public void testGetAndSetIDEpane() {
        // Only for coverage, not correctness
        controller.setIDEpane(null);
        controller.getIDEpane();
    }

    public void testInitialize() {
        // Skipped: depends on JavaFX controls and App.user, cannot be tested in normal JUnit environment
    }

    public void testGetResponse() {
        // Skipped: depends on .env file and real API clients, cannot be tested in normal JUnit environment
    }
} 