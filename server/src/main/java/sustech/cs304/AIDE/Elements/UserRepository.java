package sustech.cs304.AIDE.Elements;

import sustech.cs304.AIDE.Elements.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByGithubId(String githubId);
}

