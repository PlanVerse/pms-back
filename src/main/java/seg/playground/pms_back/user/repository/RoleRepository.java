package seg.playground.pms_back.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import seg.playground.pms_back.user.domain.RoleEntity;

/**
 * @author  : seoeungi
 * @since   : 2024.11.01
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByRoleName(String roleName);
}
