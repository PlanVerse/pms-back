package seg.playground.pms_back.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seg.playground.pms_back.team.domain.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

}