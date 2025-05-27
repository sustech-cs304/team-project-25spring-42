package sustech.cs304.utils;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.*;
import sustech.cs304.entity.Query;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpUtilsTest {

    private String originalServerUrl;
    @Test
    void testGetBaseUrl() {
        assertEquals(ServerConfig.SERVER_URL, HttpUtils.getBaseUrl());
    }

    @Test
    void testGetUserApiUrl() {
        assertEquals(ServerConfig.SERVER_URL + "/self", HttpUtils.getUserApiUrl());
    }

    @Test
    void testGetProjectApiUrl() {
        assertEquals(ServerConfig.SERVER_URL + "/project", HttpUtils.getProjectApiUrl());
    }

    @Test
    void testGetFileApiUrl() {
        assertEquals(ServerConfig.SERVER_URL + "/file", HttpUtils.getFileApiUrl());
    }
}