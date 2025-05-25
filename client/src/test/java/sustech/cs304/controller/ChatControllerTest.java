package sustech.cs304.controller;

import junit.framework.TestCase;

public class ChatControllerTest extends TestCase {
    private ChatController controller;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        controller = new ChatController();
    }

    public void testConstructor() {
        // No public logic to test without JavaFX or network
    }

    public void testInitialize() {
        // Skipped: depends on JavaFX controls, cannot be tested in normal JUnit environment
    }
} 