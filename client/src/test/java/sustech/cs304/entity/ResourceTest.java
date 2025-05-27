package sustech.cs304.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    @Test
    void testConstructorAndGetters() {
        Long id = 101L;
        String address = "http://example.com/resource/101";
        String resourceName = "Lecture1.pdf";
        String type = "pdf";
        String uploadTime = "2025-05-27T10:00:00";
        Long groupId = 5L;
        String size = "2MB";
        boolean visible = true;

        Resource resource = new Resource(id, address, resourceName, type, uploadTime, groupId, size, visible);

        assertEquals(id, resource.getId());
        assertEquals(address, resource.getAddress());
        assertEquals(resourceName, resource.getResourceName());
        assertEquals(type, resource.getType());
        assertEquals(uploadTime, resource.getUploadTime());
        assertEquals(groupId, resource.getGroupId());
        assertEquals(size, resource.getSize());
        assertTrue(resource.getVisible());
    }

    @Test
    void testInvisibleAndNullFields() {
        Resource resource = new Resource(
                null, null, null, null, null, null, null, false
        );

        assertNull(resource.getId());
        assertNull(resource.getAddress());
        assertNull(resource.getResourceName());
        assertNull(resource.getType());
        assertNull(resource.getUploadTime());
        assertNull(resource.getGroupId());
        assertNull(resource.getSize());
        assertFalse(resource.getVisible());
    }

    @Test
    void testEmptyStrings() {
        Resource resource = new Resource(
                2L, "", "", "", "", 0L, "", true
        );

        assertEquals(2L, resource.getId());
        assertEquals("", resource.getAddress());
        assertEquals("", resource.getResourceName());
        assertEquals("", resource.getType());
        assertEquals("", resource.getUploadTime());
        assertEquals(0L, resource.getGroupId());
        assertEquals("", resource.getSize());
        assertTrue(resource.getVisible());
    }
}