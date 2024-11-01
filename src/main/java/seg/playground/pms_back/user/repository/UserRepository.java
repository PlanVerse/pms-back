package seg.playground.pms_back.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import seg.playground.pms_back.user.domain.UserEntity;

/**
 * @author  : seoeungi 
 * @since   : 2024.11.01
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
