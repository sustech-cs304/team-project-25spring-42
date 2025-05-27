package sustech.cs304.controller;

import junit.framework.TestCase;

public class PDFReaderControllerTest extends TestCase {
    private PDFReaderController controller;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        controller = new PDFReaderController();
    }

    public void testSetIdeController() {
        controller.setIdeController(null);
    }

    public void testOtherMethods() {
        // Skipped: depends on JavaFX UI and PDFBox, cannot be tested in normal JUnit environment
    }
} 