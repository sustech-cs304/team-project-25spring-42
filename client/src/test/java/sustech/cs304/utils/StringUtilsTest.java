package sustech.cs304.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void testValidEmails() {
        assertTrue(StringUtils.checkIfValidEmail("test@example.com"));
        assertTrue(StringUtils.checkIfValidEmail("user.name+tag+sorting@example.co.uk"));
        assertTrue(StringUtils.checkIfValidEmail("user_name@example-domain.com"));
        assertTrue(StringUtils.checkIfValidEmail("user123@sub.domain.org"));
        assertTrue(StringUtils.checkIfValidEmail("a@b.co"));
    }

    @Test
    void testInvalidEmails() {
        assertFalse(StringUtils.checkIfValidEmail("plainaddress"));
        assertFalse(StringUtils.checkIfValidEmail("@missingusername.com"));
        assertFalse(StringUtils.checkIfValidEmail("username@.com"));
        assertFalse(StringUtils.checkIfValidEmail("username@com"));
        assertFalse(StringUtils.checkIfValidEmail("username@domain.c"));
        assertFalse(StringUtils.checkIfValidEmail("username@domain,com"));
        assertFalse(StringUtils.checkIfValidEmail("username@domain#com"));
        assertFalse(StringUtils.checkIfValidEmail("user name@domain.com"));
    }

    @Test
    void testNullAndEmpty() {
        assertThrows(NullPointerException.class, () -> StringUtils.checkIfValidEmail(null));
        assertFalse(StringUtils.checkIfValidEmail(""));
    }
}