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

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${github.client.id}")
    private String clientIdGithub;

    @Value("${github.client.secret}")
    private String clientSecretGithub;

    @Value("${x.client.id}")
    private String clientIdX;

    @Value("${x.client.secret}")
    private String clientSecretX;

    @Value("${google.client.id}")
    private String clientIdGoogle;

    @Value("${google.client.secret}")
    private String clientSecretGoogle;

    private int[] loginStatus = new int[100000];
    
    private int currentSequence = 0;


    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/github")
    public String githubLogin() {
        String url = "https://github.com/login/oauth/authorize?client_id=" + clientIdGithub + "&redirect_uri=http://107.173.91.140:8080/auth/callback/github"+"&state="+ currentSequence;
        currentSequence+=1;
        if (currentSequence == 100000) {
            currentSequence = 0;
        }
        return url;
    }

    @GetMapping("/x")
    public String xLohin() {
        return "https://twitter.com/i/oauth2/authorize?response_type=code&client_id=" + clientIdX + "&redirect_uri=http://107.173.91.140:8080/auth/callback/x&scope=tweet.readuser.read";
    }

    @GetMapping("/google")
    public String googleLogin(){
        String url =  "https://accounts.google.com/o/oauth2/v2/auth?scope=openid%20email%20profile&response_type=code&redirect_uri=http://JingqiSUN.christmas:8080/auth/callback/google&client_id="+clientIdGoogle+"&state="+ currentSequence;
        currentSequence+=1;
        if (currentSequence == 100000) {
            currentSequence = 0;
        }
        return url;
    }

    @GetMapping("/callback/github")
    public User githubCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
        RestTemplate restTemplate = new RestTemplate();
        int sequenceNumber = Integer.parseInt(state);
        Map<String, String> accessTokenResponse = restTemplate.postForObject(
            "https://github.com/login/oauth/access_token?client_id=" + clientIdGithub +
            "&client_secret=" + clientSecretGithub +
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
        loginStatus[sequenceNumber] = 1;
        System.out.print("The user is successfully logged in " + sequenceNumber);
        if (sequenceNumber -100 >= 0) {
            loginStatus[sequenceNumber-100] = 0;
            System.out.print("The user is successfully logged out " + sequenceNumber);
        } else {
            loginStatus[sequenceNumber-100+100000] = 0;
            System.out.print("The user is successfully logged out " + sequenceNumber);
        }
        return user;
    }   
    
    @GetMapping("/callback/x")
    public User xCallback(@RequestParam("code") String code) {
        RestTemplate restTemplate = new RestTemplate();

        // Exchange code for access token
        Map<String, String> accessTokenResponse = restTemplate.postForObject(
                "https://api.twitter.com/oauth2/token?client_id=" + clientIdX +
                        "&client_secret=" + clientSecretX +
                        "&code=" + code + 
                        "&redirect_uri=http://107.173.91.140:8080/auth/callback/x&grant_type=authorization_code",
                null,
                Map.class
        );

        String accessToken = accessTokenResponse.get("access_token");
        
        System.out.println("tag2");
        // Fetch Twitter user info
        Map<String, Object> userResponse = restTemplate.getForObject(
                "https://api.twitter.com/2/users/me?access_token=" + accessToken,
                Map.class
        );

        String platformId = "Twitter"+ String.valueOf(userResponse.get("id"));
        String username = (String) userResponse.get("username");
        String avatarUrl = (String) userResponse.get("profile_image_url");

        // Save or update user in database
        Optional<User> existingUser = userRepository.findByPlatformId(platformId);
        User user = existingUser.orElseGet(() -> new User(platformId, username, avatarUrl));

        userRepository.save(user);
        return user;
    }
    @GetMapping("/callback/google")
    public User googleCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
        RestTemplate restTemplate = new RestTemplate();
        int sequenceNumber = Integer.parseInt(state);

        System.out.println(clientSecretGoogle);
        System.out.println(clientIdGoogle);
        // Request access token from Google
        Map<String, String> accessTokenResponse = restTemplate.postForObject(
            "https://oauth2.googleapis.com/token?" +
            "client_id=" + clientIdGoogle +
            "&client_secret=" + clientSecretGoogle +
            "&code=" + code +
            "&grant_type=authorization_code" +
            "&redirect_uri=" + "http://JingqiSUN.christmas:8080/auth/callback/google",
            null,
            Map.class
        );

        String accessToken = accessTokenResponse.get("access_token");

        // Set up headers for user info request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Get user information
        ResponseEntity<Map> response = restTemplate.exchange(
            "https://www.googleapis.com/oauth2/v3/userinfo",
            HttpMethod.GET,
            entity,
            Map.class
        );

        Map<String, Object> userResponse = response.getBody();

        // Extract user details
        String platformId = "google" + String.valueOf(userResponse.get("sub")); // 'sub' is Google's user ID
        String username = (String) userResponse.get("name"); // or "given_name" for first name only
        String avatarUrl = (String) userResponse.get("picture");

        // Save or update user in database
        Optional<User> existingUser = userRepository.findByPlatformId(platformId);
        User user = existingUser.orElseGet(() -> new User(platformId, username, avatarUrl));

        userRepository.save(user);
        loginStatus[sequenceNumber] = 1; 
        System.out.print("The user is successfully logged in " + sequenceNumber);
        if (sequenceNumber -100 >= 0) {
            loginStatus[sequenceNumber-100] = 0;
            System.out.print("The user is successfully logged out " + sequenceNumber);
        } else {
            loginStatus[sequenceNumber-100+100000] = 0;
            System.out.print("The user is successfully logged out " + sequenceNumber);
        }
        return user;
    }
    @GetMapping("/callback/loginStatus/{x}")
    public String handleCallback(@PathVariable("x") String x) {
        int statusInt = loginStatus[Integer.parseInt(x)];
        String status = String.valueOf(statusInt);
        return status; 
    }
}

