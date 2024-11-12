package seg.playground.pms_back.user.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.UniqueElements;
import seg.playground.pms_back.common.domain.BaseEntity;
import seg.playground.pms_back.team.domain.TeamEntity;
import seg.playground.pms_back.team.domain.TeamMemberEntity;

@Entity
@Getter
@Builder
@DynamicInsert
@AllArgsConstructor
@Table(name = "user_info",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_info_uk01",
                        columnNames = {"email"}
                )
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String nickname;

    @Column
    private String role;

    @OneToMany(mappedBy = "userInfo")
    private List<TeamMemberEntity> teamMemberInfo;
}