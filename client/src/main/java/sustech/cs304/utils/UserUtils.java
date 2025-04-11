package sustech.cs304.utils;

import java.io.File;
import java.io.IOException;

import sustech.cs304.entity.User;
import sustech.cs304.entity.UserServerSide;
import sustech.cs304.service.UserApiImpl;
import sustech.cs304.service.UserApi;

public class UserUtils {
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

    public static User loadUser() {
        UserApi userApi = new UserApiImpl();
        String userId = getSavedUserId();
        if (userId == null) {
            return null;
        }
        return userApi.getUserById(userId);
    }

    private static String getSavedUserId() {
        String projectRoot = System.getProperty("user.dir");
        String filePath = projectRoot + "/src/main/resources/txt/savedUserId.txt";
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
