package sustech.cs304.controller;

import junit.framework.TestCase;

public class EditorControllerTest extends TestCase {
    private EditorController controller;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        controller = new EditorController();
    }

    public void testSetIdeController() {

        controller.setIdeController(null);
    }

    public void testSetBackground() {

        controller.setBackground("vs");
    }

    public void testSetTheme() {
        // Skipped: depends on MonacoFX, cannot be tested in normal JUnit environment
    }

    public void testSetText() {
        // Skipped: depends on MonacoFX, cannot be tested in normal JUnit environment
    }
} 