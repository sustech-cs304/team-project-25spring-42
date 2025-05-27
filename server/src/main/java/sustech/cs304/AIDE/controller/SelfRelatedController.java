package sustech.cs304.AIDE.controller;

import org.springframework.web.bind.annotation.*;

import sustech.cs304.AIDE.model.User;
import sustech.cs304.AIDE.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import org.springframework.web.multipart.MultipartFile;


/**
 * Controller for handling user-related operations.
 *
 * This class provides endpoints for retrieving and updating user information.
 */
@RestController
@RequestMapping("/self")
public class SelfRelatedController {

    private final UserRepository userRepository;

    /**
     * Constructor for SelfRelatedController.
     *
     * @param userRepository the repository for users
     */
    public SelfRelatedController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all user information for a given platform ID.
     *
     * @param platformId the platform ID of the user
     * @return a ResponseEntity containing the user's information
     */
    @GetMapping(value = "/allInfo", produces = "application/json")
    @Transactional
    public ResponseEntity<ClientUser> getAllUserInfo(@RequestParam String platformId) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        System.out.println("User ID: " + platformId); 
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateLastLoginTime(); 
            userRepository.save(user); 
            System.out.println("User found: " + user.getUsername());
            user = userOptional.get();
            ClientUser clientUser = new ClientUser(user.getPlatformId(), user.getUsername(), user.getAvatarUrl(), user.getRegisterTime(), user.getLastLoginTime(), user.getPhoneNumber(), user.getEmail(), user.getBio());
            return ResponseEntity.ok(clientUser);
        } else {
            return null; 
        }
    }

    /**
     * Retrieves the username for a given platform ID.
     *
     * @param platformId the platform ID of the user
     * @return a ResponseEntity containing the user's username
     */
    @GetMapping(value = "/getUserName", produces = "application/json")
    @Transactional
    public ResponseEntity<UserResponse> getUserName(@RequestParam String platformId) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            return ResponseEntity.ok(new UserResponse(user.getUsername()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the avatar URL for a given platform ID.
     * 
     * @param platformId the platform ID of the user
     * @return a ResponseEntity containing the user's avatar URL
     */
    @GetMapping(value = "/getUserAvatar", produces = "application/json")
    @Transactional
    public ResponseEntity<UserResponse> getUserAvatar(@RequestParam String platformId) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            return ResponseEntity.ok(new UserResponse(user.getAvatarUrl()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the bio for a given platform ID.
     *
     * @param platformId the platform ID of the user
     * @return a ResponseEntity containing the user's bio
     */
    @GetMapping(value = "/getUserBio", produces = "application/json")
    @Transactional
    public ResponseEntity<UserResponse> getUserBio(@RequestParam String platformId) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            return ResponseEntity.ok(new UserResponse(user.getBio()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the phone number for a given platform ID.
     *
     * @param platformId the platform ID of the user
     * @return a ResponseEntity containing the user's phone number
     */
    @GetMapping(value = "/getUserPhoneNumber", produces = "application/json")
    @Transactional
    public ResponseEntity<UserResponse> getUserPhoneNumber(@RequestParam String platformId) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            return ResponseEntity.ok(new UserResponse(user.getPhoneNumber()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the email for a given platform ID.
     *
     * @param platformId the platform ID of the user
     * @return a ResponseEntity containing the user's email
     */
    @GetMapping(value = "/getUserEmail", produces = "application/json")
    @Transactional
    public ResponseEntity<UserResponse> getUserEmail(@RequestParam String platformId) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            return ResponseEntity.ok(new UserResponse(user.getEmail()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the registration time for a given platform ID.
     *
     * @param platformId the platform ID of the user
     * @return a ResponseEntity containing the user's registration time
     */
    @GetMapping(value = "/getUserRegisterTime", produces = "application/json")
    @Transactional
    public ResponseEntity<UserResponse> getUserRegisterTime(@RequestParam String platformId) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            return ResponseEntity.ok(new UserResponse(user.getRegisterTime().toString()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the last login time for a given platform ID.
     *
     * @param platformId the platform ID of the user
     * @return a ResponseEntity containing the user's last login time
     */
    @GetMapping(value = "/getUserLastLoginTime", produces = "application/json")
    @Transactional
    public ResponseEntity<UserResponse> getUserLastLoginTime(@RequestParam String platformId) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            return ResponseEntity.ok(new UserResponse(user.getLastLoginTime().toString()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Sets the username for a given platform ID.
     *
     * @param platformId the platform ID of the user
     * @param newUserName the new username to set
     * @return a ResponseEntity indicating success or failure
     */
    @GetMapping(value = "/setUserName", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> setUserName(@RequestParam String platformId, @RequestParam String newUserName) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            user.setUsername(newUserName);
            userRepository.save(user);
            return ResponseEntity.ok(new SetResponse(true));
        } else {
            return ResponseEntity.ok(new SetResponse(false));
        }
    }

    /**
     * Sets the avatar for a given platform ID.
     *
     * @param userId the platform ID of the user
     * @param file the avatar file to set
     * @return a ResponseEntity containing the new avatar URL
     */
    @PostMapping(value = "/setUserAvatar", produces = "application/json")
    @Transactional
    public ResponseEntity<String> setUserAvatar(@RequestParam String userId, @RequestParam("file") MultipartFile file) {
        Optional<User> userOptional = userRepository.findByPlatformId(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            String home = System.getProperty("user.home");
            String originalFilename = file.getOriginalFilename();
            String savePath = Paths.get(home, "Documents", "Save", userId, "avatar",originalFilename).toString();
            File directory = new File(Paths.get(home, "Documents", "Save", userId, "avatar").toString());
            if (!directory.exists()) {
                directory.mkdirs();
            }
            try {
                file.transferTo(new File(savePath));
            } catch (IOException e) {
                return ResponseEntity.ok(null);
            } 
            String filePath = Paths.get(userId, "avatar", originalFilename).toString();
            String newAvatarUrl = "http://139.180.143.70:8080/static/" + filePath;
            user.setAvatarUrl(newAvatarUrl);
            userRepository.save(user);
            return ResponseEntity.ok(newAvatarUrl);
        } else {
            return ResponseEntity.ok(null);
        }
    }

    /**
     * Sets the bio for a given platform ID.
     *
     * @param platformId the platform ID of the user
     * @param newBio the new bio to set
     * @return a ResponseEntity indicating success or failure
     */
    @GetMapping(value = "/setUserBio", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> setUserBio(@RequestParam String platformId, @RequestParam String newBio) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            user.setBio(newBio);
            userRepository.save(user);
            return ResponseEntity.ok(new SetResponse(true));
        } else {
            return ResponseEntity.ok(new SetResponse(false));
        }
    }

    /**
     * Sets the phone number for a given platform ID.
     *
     * @param platformId the platform ID of the user
     * @param newPhoneNumber the new phone number to set
     * @return a ResponseEntity indicating success or failure
     */
    @GetMapping(value = "/setUserPhoneNumber", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> setUserPhoneNumber(@RequestParam String platformId, @RequestParam String newPhoneNumber) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            user.setPhoneNumber(newPhoneNumber);
            userRepository.save(user);
            return ResponseEntity.ok(new SetResponse(true));
        } else {
            return ResponseEntity.ok(new SetResponse(false));
        }
    }

    /**
     * Sets the email for a given platform ID.
     *
     * @param platformId the platform ID of the user
     * @param newEmail the new email to set
     * @return a ResponseEntity indicating success or failure
     */
    @GetMapping(value = "/setUserEmail", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> setUserEmail(@RequestParam String platformId, @RequestParam @Email String newEmail) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            user.setEmail(newEmail);
            userRepository.save(user);
            return ResponseEntity.ok(new SetResponse(true));
        } else {
            return ResponseEntity.ok(new SetResponse(false));
        }
    }
}
class ClientUser{
    private String platformId;
    private String username;
    private String avatarUrl;
    private String registerTime;
    private String lastLoginTime;
    private String phoneNumber;
    private String email;
    private String bio;

    /**
     * Constructor for ClientUser.
     *
     * @param platformId the platform ID of the user
     * @param username the username of the user
     * @param avatarUrl the avatar URL of the user
     * @param registerTime the registration time of the user
     * @param lastLoginTime the last login time of the user
     * @param phoneNumber the phone number of the user
     * @param email the email of the user
     * @param bio the bio of the user
     */
    public ClientUser(String platformId, String username, String avatarUrl, LocalDateTime registerTime, LocalDateTime lastLoginTime, String phoneNumber, String email, String bio) {
        this.platformId = platformId;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.registerTime = registerTime.toString();
        this.lastLoginTime = lastLoginTime.toString();
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bio = bio;
    }

    /**
     * get the platformId
     */
    public String getPlatformId() {
        return platformId;
    }

    /**
     * get the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * get the avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * get the registerTime
     */
    public String getRegisterTime() {
        return registerTime;
    }

    /**
     * get the lastLoginTime
     */
    public String getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * get the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * get the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * get the bio
     */
    public String getBio() {
        return bio;
    }
}
