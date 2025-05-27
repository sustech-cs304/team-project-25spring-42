package sustech.cs304.utils;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HttpUtilsTest {

    @Test
    void testGetBaseUrl() {
        String baseUrl = HttpUtils.getBaseUrl();
        assertNotNull(baseUrl);
    }

    @Test
    void testGetUserApiUrl() {
        String userApiUrl = HttpUtils.getUserApiUrl();
        assertNotNull(userApiUrl);
    }

    @Test
    void testGetProjectApiUrl() {
        String projectApiUrl = HttpUtils.getProjectApiUrl();
        assertNotNull(projectApiUrl);
    }

    @Test
    void testGetFileApiUrl() {
        String fileApiUrl = HttpUtils.getFileApiUrl();
        assertNotNull(fileApiUrl);
    }

}
