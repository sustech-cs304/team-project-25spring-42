package sustech.cs304.AIDE.Elements;
import sustech.cs304.AIDE.Elements.User;
import sustech.cs304.AIDE.Elements.UserRepository;
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

    @GetMapping("/allInfo")
    public ClientUser getAllUserInfo(@RequestParam String platformId) {
        Optional<User> userOptional = userRepository.findByPlatformId(platformId);
        System.out.println("User ID: " + platformId); 
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateLastLoginTime(); 
            userRepository.save(user); 
            return new ClientUser(user.getPlatformId(), user.getUsername(), user.getAvatarUrl(), user.getRegisterTime(), user.getLastLoginTime(), user.getPhoneNumber(), user.getEmail());
        } else {
            return null; 
        }
    }
}
class ClientUser{
    private String platformId;
    private String username;
    private String avatarUrl;
    private LocalDateTime registerTime;
    private LocalDateTime lastLoginTime;
    private String phoneNumber;
    private String email;

    public ClientUser(String platformId, String username, String avatarUrl, LocalDateTime registerTime, LocalDateTime lastLoginTime, String phoneNumber, String email) {
        this.platformId = platformId;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
