package seg.playground.pms_back.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seg.playground.pms_back.user.entity.UserEntity;

/**
 * @author  : seoeungi 
 * @since   : 2024.11.01
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
