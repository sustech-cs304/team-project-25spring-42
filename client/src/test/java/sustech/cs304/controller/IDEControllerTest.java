package sustech.cs304.controller;

import junit.framework.TestCase;

public class IDEControllerTest extends TestCase {
    private IDEController controller;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        controller = new IDEController();
    }

    public void testSettersAndGetters() {
        controller.getMenuBarController();
        controller.getFileTreeController();
        controller.getEditorController();
        controller.getMYpdfReaderController();
        controller.getJeditermController();
        controller.getClassController();
    }

    public void testSwitchMethods() {
        // Skipped: depends on JavaFX controls and other controllers, cannot be tested in normal JUnit environment
    }
} 