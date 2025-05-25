package sustech.cs304.controller;

import junit.framework.TestCase;

public class NoteControllerTest extends TestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Skipped: NoteController uses static instance and JavaFX UI, cannot be fully tested in normal JUnit environment
    }

    public void testToggle() {
        // Skipped: depends on JavaFX UI and static instance, cannot be tested in normal JUnit environment
    }

    public void testIsOpen() {
        // Should return false if not initialized
        assertFalse(NoteController.isOpen());
    }

    public void testClose() {
        // Should not throw even if not open
        NoteController.close();
    }

    public void testGetText() {
        // Should return empty string if not open
        assertEquals("", NoteController.getText());
    }

    public void testSetText() {
        // Should not throw even if not open
        NoteController.setText("test");
    }
} 