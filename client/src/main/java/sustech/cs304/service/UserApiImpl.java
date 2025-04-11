package sustech.cs304.service;

import com.google.gson.Gson;

import okhttp3.Response;
import sustech.cs304.entity.Query;
import sustech.cs304.entity.User;
import sustech.cs304.entity.UserServerSide;
import sustech.cs304.utils.HttpUtils;
import sustech.cs304.utils.UserUtils;

public class UserApiImpl implements UserApi {
    public User getUserById(String userId) {
        UserServerSide userServerSide = null;
        Query[] queries = {
            new Query("platformId", userId)
        };
        try {
            Response response = HttpUtils.get("/self", "/allInfo", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                userServerSide = gson.fromJson(responseBody, UserServerSide.class);
            } else {
                System.err.println("Error fetching user data: " + response.message());
            }
        } catch(Exception e) {
            System.err.println("Error fetching user data: " + e.getMessage());
            userServerSide = new UserServerSide("test010100101", "tester" , "https://test.com" , "2025-04-07T18:11:20.059626" , "2025-04-08T14:37:04.979725072" , "1231123212" , "test@test.com", "test bio");
        }

        return UserUtils.loadUser(userServerSide);
    } 

    public String getUsernameById(String userId) {
        Query [] queries = {
            new Query("platformId", userId)
        };
        try {
            Response response = HttpUtils.get("/self", "/getUserName", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                UserResponse username = gson.fromJson(responseBody, UserResponse.class);
                return username.getContent();
            } else {
                System.err.println("Error fetching username: " + response.message());
                return null;
            }
        } catch(Exception e) {
            System.err.println("Error fetching username: " + e.getMessage());
            return "tester";
        }
    }

    public String getUserAvatarById(String userId) {
        Query [] queries = {
            new Query("platformId", userId)
        };
        try {
            Response response = HttpUtils.get("/self", "/getUserAvatar", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                UserResponse avatar = gson.fromJson(responseBody, UserResponse.class);
                return avatar.getContent();
            } else {
                System.err.println("Error fetching avatar: " + response.message());
                return null;
            }
        } catch(Exception e) {
            System.err.println("Error fetching avatar: " + e.getMessage());
            return null;
        }
    }

    public String getUserBioById(String userId) {
        Query [] queries = {
            new Query("platformId", userId)
        };
        try {
            Response response = HttpUtils.get("/self", "/getUserBio", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                UserResponse bio = gson.fromJson(responseBody, UserResponse.class);
                return bio.getContent();
            } else {
                System.err.println("Error fetching bio: " + response.message());
                return null;
            }
        } catch(Exception e) {
            System.err.println("Error fetching bio: " + e.getMessage());
            return null;
        }
    }

    public String getUserEmailById(String userId) {
        Query [] queries = {
            new Query("platformId", userId)
        };
        try {
            Response response = HttpUtils.get("/self", "/getUserEmail", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                UserResponse email = gson.fromJson(responseBody, UserResponse.class);
                return email.getContent();
            } else {
                System.err.println("Error fetching email: " + response.message());
                return null;
            }
        } catch(Exception e) {
            System.err.println("Error fetching email: " + e.getMessage());
            return null;
        }
    }

    public String getUserPhoneById(String userId) {
        Query [] queries = {
            new Query("platformId", userId)
        };
        try {
            Response response = HttpUtils.get("/self", "/getUserPhoneNumber", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                UserResponse phone = gson.fromJson(responseBody, UserResponse.class);
                return phone.getContent();
            } else {
                System.err.println("Error fetching phone: " + response.message());
                return null;
            }
        } catch(Exception e) {
            System.err.println("Error fetching phone: " + e.getMessage());
            return null;
        }
    }

    public String getUserLastLoginTimeById(String userId) {
        Query [] queries = {
            new Query("platformId", userId)
        };
        try {
            Response response = HttpUtils.get("/self", "/getUserLastLoginTime", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                UserResponse lastLoginTime = gson.fromJson(responseBody, UserResponse.class);
                return lastLoginTime.getContent();
            } else {
                System.err.println("Error fetching last login time: " + response.message());
                return null;
            }
        } catch(Exception e) {
            System.err.println("Error fetching last login time: " + e.getMessage());
            return null;
        }
    }

    public String getUserRegisterTimeById(String userId) {
        Query [] queries = {
            new Query("platformId", userId)
        };
        try {
            Response response = HttpUtils.get("/self", "/getUserRegisterTime", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                UserResponse registerTime = gson.fromJson(responseBody, UserResponse.class);
                return registerTime.getContent();
            } else {
                System.err.println("Error fetching register time: " + response.message());
                return null;
            }
        } catch(Exception e) {
            System.err.println("Error fetching register time: " + e.getMessage());
            return null;
        }
    }

    public boolean updateUsernameById(String userId, String newUsername) {
        Query [] queries = {
            new Query("platformId", userId),
            new Query("newUserName", newUsername)
        };
        try {
            Response response = HttpUtils.get("/self", "/setUserName", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                SetResponse result = gson.fromJson(responseBody, SetResponse.class);
                return result.getResult();
            } else {
                System.err.println("Error updating username: " + response.message());
                return false;
            }
        } catch(Exception e) {
            System.err.println("Error updating username: " + e.getMessage());
            return false;
        }
    }

    public boolean updateAvatarById(String userId, String newAvatarUrl) {
        Query [] queries = {
            new Query("platformId", userId),
            new Query("newAvatarUrl", newAvatarUrl)
        };
        try {
            Response response = HttpUtils.get("/self", "/setUserAvatar", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                SetResponse result = gson.fromJson(responseBody, SetResponse.class);
                return result.getResult();
            } else {
                System.err.println("Error updating avatar: " + response.message());
                return false;
            }
        } catch(Exception e) {
            System.err.println("Error updating avatar: " + e.getMessage());
            return false;
        }
    }

    public boolean updateMailById(String userId, String newMail) {
        Query [] queries = {
            new Query("platformId", userId),
            new Query("newEmail", newMail)
        };
        try {
            Response response = HttpUtils.get("/self", "/setUserEmail", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                SetResponse result = gson.fromJson(responseBody, SetResponse.class);
                return result.getResult();
            } else {
                System.err.println("Error updating email: " + response.message());
                return false;
            }
        } catch(Exception e) {
            System.err.println("Error updating email: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePhoneById(String userId, String newPhone) {
        Query [] queries = {
            new Query("platformId", userId),
            new Query("newPhoneNumber", newPhone)
        };
        try {
            Response response = HttpUtils.get("/self", "/setUserPhoneNumber", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                SetResponse result = gson.fromJson(responseBody, SetResponse.class);
                return result.getResult();
            } else {
                System.err.println("Error updating phone: " + response.message());
                return false;
            }
        } catch(Exception e) {
            System.err.println("Error updating phone: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBioById(String userId, String newBio) {
        Query [] queries = {
            new Query("platformId", userId),
            new Query("newBio", newBio)
        };
        try {
            Response response = HttpUtils.get("/self", "/setUserBio", queries);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                SetResponse result = gson.fromJson(responseBody, SetResponse.class);
                return result.getResult();
            } else {
                System.err.println("Error updating bio: " + response.message());
                return false;
            }
        } catch(Exception e) {
            System.err.println("Error updating bio: " + e.getMessage());
            return false;
        }
    }
}

class UserResponse {
    private String content;

    public String getContent() {
        return content;
    }
}

class SetResponse {
    private boolean result;
    SetResponse(boolean result) {
        this.result = result;
    }
    public boolean getResult() {
        return result;
    }
}
