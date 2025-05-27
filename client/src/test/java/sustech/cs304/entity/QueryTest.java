package sustech.cs304.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QueryTest {

    @Test
    void testConstructorAndGetters() {
        Query query = new Query("courseId", "CS304");

        assertEquals("courseId", query.getKey());
        assertEquals("CS304", query.getValue());
    }

    @Test
    void testNullKeyAndValue() {
        Query query = new Query(null, null);

        assertNull(query.getKey());
        assertNull(query.getValue());
    }

    @Test
    void testEmptyStringKeyAndValue() {
        Query query = new Query("", "");

        assertEquals("", query.getKey());
        assertEquals("", query.getValue());
    }

    @Test
    void testSpecialCharacters() {
        Query query = new Query("!@#", "$%^");

        assertEquals("!@#", query.getKey());
        assertEquals("$%^", query.getValue());
    }
}