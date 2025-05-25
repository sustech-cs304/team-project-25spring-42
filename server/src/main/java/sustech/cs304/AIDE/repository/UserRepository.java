package sustech.cs304.AIDE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sustech.cs304.AIDE.model.User;
import java.util.Optional;

/**
 * Repository interface for managing User entities.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find the user by their platform ID.
     *
     * @param platformId the platform ID of the user
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByPlatformId(String platformId);
}

