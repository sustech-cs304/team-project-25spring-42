package sustech.cs304.utils;

import java.io.File;
import java.io.IOException;

import sustech.cs304.entity.User;
import sustech.cs304.entity.UserServerSide;
import sustech.cs304.service.UserApiImpl;
import sustech.cs304.service.UserApi;

/**
 * Utility class for user-related operations, such as loading user data from
 * a server-side representation or from persistent storage.
 */
public class UserUtils {

    /**
     * Converts a UserServerSide object to a User object.
     *
     * @param serverSideUser the user data as received from the server
     * @return a User object initialized with the data from serverSideUser
     */
    public static User loadUser(UserServerSide serverSideUser) {
        User user = new User(serverSideUser.getPlatformId());
        user.setUsername(serverSideUser.getUsername());
        user.setAvatarPath(serverSideUser.getAvatarUrl());
        user.setRegisterDate(serverSideUser.getRegisterTime());
        user.setLastLogin(serverSideUser.getLastLoginTime());
        user.setPhoneNumber(serverSideUser.getPhoneNumber());
        user.setEmail(serverSideUser.getEmail());
        user.setBio(serverSideUser.getBio());
        return user;
    }

    /**
     * Loads the current user using the saved user ID from persistent storage.
     * If no saved user ID is found, returns null.
     *
     * @return the User object for the saved user ID, or null if not found
     */
    public static User loadUser() {
        UserApi userApi = new UserApiImpl();
        String userId = getSavedUserId();
        if (userId == null) {
            return null;
        }
        return userApi.getUserById(userId);
    }

    /**
     * Retrieves the saved user ID from the application's data directory.
     * Reads the user ID from the file "savedUserId.txt".
     *
     * @return the saved user ID as a String, or null if not found or error occurs
     */
    private static String getSavedUserId() {
        String projectRoot = FileUtils.getAppDataPath().toString();
        String filePath = projectRoot + File.separator + "savedUserId.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
