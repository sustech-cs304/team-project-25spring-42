package sustech.cs304.AIDE.Elements;
import sustech.cs304.AIDE.Elements.User;
import sustech.cs304.AIDE.Elements.UserRepository;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import java.util.Map;
import java.util.Optional;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;


@RestController
@RequestMapping("/self")
public class SelfRelatedController {

    private int currentSequence = 0;

    private final UserRepository userRepository;

    public SelfRelatedController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    @GetMapping(value = "/setUserAvatar", produces = "application/json")
    @Transactional
    public ResponseEntity<SetResponse> setUserAvatar(@RequestParam String platformId, @RequestParam String newAvatarUrl) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            user.setAvatarUrl(newAvatarUrl);
            userRepository.save(user);
            return ResponseEntity.ok(new SetResponse(true));
        } else {
            return ResponseEntity.ok(new SetResponse(false));
        }
    }

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
    public String getPlatformId() {
        return platformId;
    }
    public String getUsername() {
        return username;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }
    public String getRegisterTime() {
        return registerTime;
    }
    public String getLastLoginTime() {
        return lastLoginTime;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public String getBio() {
        return bio;
    }
}
class UserResponse {
    private String content;
    UserResponse(String content) {
        this.content = content;
    }
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
