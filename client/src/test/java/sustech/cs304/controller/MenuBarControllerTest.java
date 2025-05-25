package sustech.cs304.controller;

import junit.framework.TestCase;

public class MenuBarControllerTest extends TestCase {
    private MenuBarController controller;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        controller = new MenuBarController();
    }

    public void testSetIdeController() {
        controller.setIdeController(null);
    }

    public void testChangeMode() {
        // Skipped: depends on JavaFX controls, cannot be tested in normal JUnit environment
    }

    public void testOpenFolder() {
        // Skipped: depends on IDEController and JavaFX, cannot be tested in normal JUnit environment
    }
} 