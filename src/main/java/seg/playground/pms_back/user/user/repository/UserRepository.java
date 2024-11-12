package seg.playground.pms_back.user.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import seg.playground.pms_back.user.user.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
