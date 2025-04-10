package sustech.cs304.service;

import sustech.cs304.entity.User;

public interface UserApi {
    User getUserById(String userId);
    String getUsernameById(String userId);
    String getUserAvatarById(String userId);
    String getUserBioById(String userId);
    String getUserEmailById(String userId);
    String getUserPhoneById(String userId);
    String getUserLastLoginTimeById(String userId);
    String getUserRegisterTimeById(String userId);
    boolean updateUsernameById(String userId, String newUsername);
    boolean updateAvatarById(String userId, String newAvatarUrl);
    boolean updateMailById(String userId, String newMail);
    boolean updatePhoneById(String userId, String newPhone);
    boolean updateBioById(String userId, String newBio);
}
