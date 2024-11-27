package seg.playground.pms_back.team.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import seg.playground.pms_back.common.domain.BaseEntity;

@Entity
@Getter
@Builder
@DynamicInsert
@AllArgsConstructor
@Table(name = "team_info",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "team_info_uk01",
                        columnNames = {"team_key"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 855094253272731174L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "description")
    private String description;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "team_key", updatable = false, unique = true, nullable = false)
    private String teamKey;

    @OneToMany(mappedBy = "teamInfo")
    private List<TeamMemberEntity> teamMemberInfo;

    @PrePersist
    public void prePersist() {
        this.teamKey = UUID.randomUUID().toString();
    }
}
