package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sustech.cs304.AIDE.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPlatformId(String platformId);
}

