package sustech.cs304.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Response;
import sustech.cs304.entity.Query;
import sustech.cs304.entity.User;
import sustech.cs304.entity.UserServerSide;
import sustech.cs304.utils.HttpUtils;
import sustech.cs304.utils.UserUtils;


public class FriendApiImpl implements FriendApi {
    @Override
    public void applyFriendship(String applicantId, String targetId) {
        Query[] queries = {
            new Query("applicantId", applicantId),
            new Query("targetId", targetId),
        };
        try {
            Response response = HttpUtils.get("/friendship", "/apply", queries);
            if (response.isSuccessful()) {
                System.out.println("Friendship request sent from " + applicantId + " to " + targetId);
            } else {
                System.out.println("Failed to send friendship request: " + response.message());
            }
        } catch (Exception e) {
            System.out.println("Error while sending friendship request: " + e.getMessage());
        }
    }

    @Override
    public void acceptFriendship(String applicantId, String targetId) {
        Query[] queries = {
            new Query("applicantId", applicantId),
            new Query("targetId", targetId),
        };
        try {
            Response response = HttpUtils.get("/friendship", "/accept", queries);
            if (response.isSuccessful()) {
                System.out.println("Friendship request accepted between " + applicantId + " and " + targetId);
            } else {
                System.out.println("Failed to accept friendship request: " + response.message());
            }
        } catch (Exception e) {
            System.out.println("Error while accepting friendship request: " + e.getMessage());
        }
    }

    @Override
    public void rejectFriendship(String applicantId, String targetId) {
        Query[] queries = {
            new Query("applicantId", applicantId),
            new Query("targetId", targetId),
        };
        try {
            Response response = HttpUtils.get("/friendship", "/reject", queries);
            if (response.isSuccessful()) {
                System.out.println("Friendship request rejected between " + applicantId + " and " + targetId);
            } else {
                System.out.println("Failed to reject friendship request: " + response.message());
            }
        } catch (Exception e) {
            System.out.println("Error while rejecting friendship request: " + e.getMessage());
        }
    }

    @Override
    public List<User> getFriendList(String userId) {
        List<UserServerSide> friendList = null;
        Query[] queries = {
            new Query("userId", userId),
        };
        try {
            Response response = HttpUtils.get("/friendship", "/getFriendList", queries);
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<List<UserServerSide>>() {}.getType();
                friendList = new Gson().fromJson(responseBody, listType);
            } else {
                System.out.println("Failed to get friend list: " + response.message());
            }
        } catch (Exception e) {
            System.out.println("Error while getting friend list: " + e.getMessage());
        }
        List<User> userList = new ArrayList<>();
        if (friendList != null) {
            for (UserServerSide userServerSide : friendList) {
                User user = UserUtils.loadUser(userServerSide);
                userList.add(user);
            }
        }
        return userList;
    }

    @Override
    public List<User> getFriendRequestList(String userId) {
        List<UserServerSide> friendRequestList = null;
        Query[] queries = {
            new Query("userId", userId),
        };
        try {
            Response response = HttpUtils.get("/friendship", "/getFriendRequestList", queries);
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Type listType = new TypeToken<List<UserServerSide>>() {}.getType();
                friendRequestList = new Gson().fromJson(responseBody, listType);
            } else {
                System.out.println("Failed to get friend request list: " + response.message());
            }
        } catch (Exception e) {
            System.out.println("Error while getting friend request list: " + e.getMessage());
        }
        List<User> userList = new ArrayList<>();
        if (friendRequestList != null) {
            for (UserServerSide userServerSide : friendRequestList) {
                User user = UserUtils.loadUser(userServerSide);
                userList.add(user);
            }
        }
        return userList;
    }
}
