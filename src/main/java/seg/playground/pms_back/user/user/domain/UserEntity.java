package seg.playground.pms_back.user.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import seg.playground.pms_back.common.domain.BaseEntity;
import seg.playground.pms_back.team.domain.TeamMemberEntity;

@Entity
@Getter
@SuperBuilder
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
public class UserEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7811188394631428199L;

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