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

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/github")
    public String githubLogin() {
        return "https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=http://107.173.91.140:8080/auth/callback";
    }

    @GetMapping("/callback")
    public User githubCallback(@RequestParam("code") String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> accessTokenResponse = restTemplate.postForObject(
            "https://github.com/login/oauth/access_token?client_id=" + clientId +
            "&client_secret=" + clientSecret +
            "&code=" + code,
            null,
            Map.class
        );

        String accessToken = accessTokenResponse.get("access_token");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
            "https://api.github.com/user",
            HttpMethod.GET,
            entity,
            Map.class
        );

        Map<String, Object> userResponse = response.getBody();

        String platformId = "github"+String.valueOf(userResponse.get("id"));
        String username = (String) userResponse.get("login");
        String avatarUrl = (String) userResponse.get("avatar_url");

        // Save or update user in database
        Optional<User> existingUser = userRepository.findByPlatformId(platformId);
        User user = existingUser.orElseGet(() -> new User(platformId, username, avatarUrl));

        userRepository.save(user);
        return user;
    }   
}

