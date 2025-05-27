package sustech.cs304.utils;

import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

class HttpUtilsTest {

    private String originalServerUrl;
    @Test
    void testGetBaseUrl() {
        assertEquals(ServerConfig.SERVER_URL, HttpUtils.getBaseUrl());
    }

    @Test
    void testGetUserApiUrl() {
        String userApiUrl = HttpUtils.getUserApiUrl();
        assertNotNull(userApiUrl);
        assertEquals(ServerConfig.SERVER_URL + "/self", HttpUtils.getUserApiUrl());
    }

    @Test
    void testGetProjectApiUrl() {
        String projectApiUrl = HttpUtils.getProjectApiUrl();
        assertNotNull(projectApiUrl);
        assertEquals(ServerConfig.SERVER_URL + "/project", HttpUtils.getProjectApiUrl());
    }

    @Test
    void testGetFileApiUrl() {
        String fileApiUrl = HttpUtils.getFileApiUrl();
        assertNotNull(fileApiUrl);
    }
}
