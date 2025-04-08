package sustech.cs304.AIDE.Elements;
import sustech.cs304.AIDE.Elements.User;
import sustech.cs304.AIDE.Elements.UserRepository;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ClientUser> getAllUserInfo(@RequestParam String platformId) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        System.out.println("User ID: " + platformId); 
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateLastLoginTime(); 
            userRepository.save(user); 
            System.out.println("User found: " + user.getUsername());
            user = userOptional.get();
            ClientUser clientUser = new ClientUser(user.getPlatformId(), user.getUsername(), user.getAvatarUrl(), user.getRegisterTime(), user.getLastLoginTime(), user.getPhoneNumber(), user.getEmail());
            return ResponseEntity.ok(clientUser);
        } else {
            return null; 
        }
    }

    @GetMapping(value = "/test", produces = "application/json")
    public ResponseEntity<Map<String, String>> testJson() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("message", "Hello, JSON!");
        return ResponseEntity.ok(testMap);
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

    public ClientUser(String platformId, String username, String avatarUrl, LocalDateTime registerTime, LocalDateTime lastLoginTime, String phoneNumber, String email) {
        this.platformId = platformId;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.registerTime = registerTime.toString();
        this.lastLoginTime = lastLoginTime.toString();
        this.phoneNumber = phoneNumber;
        this.email = email;
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
}
