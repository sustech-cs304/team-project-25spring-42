package sustech.cs304.controller;

import junit.framework.TestCase;

public class FileTreeControllerTest extends TestCase {
    private FileTreeController controller;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        controller = new FileTreeController();
    }

    public void testSetIdeController() {

        controller.setIdeController(null);
    }

    public void testRefresh() {
        // Skipped: depends on JavaFX controls and file system, cannot be tested in normal JUnit environment
    }

    public void testInitialize() {
        // Skipped: depends on JavaFX controls, cannot be tested in normal JUnit environment
    }
} 