package seg.playground.pms_back.team.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.SecondaryTables;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import seg.playground.pms_back.common.domain.BaseEntity;
import seg.playground.pms_back.team.constant.Position;
import seg.playground.pms_back.user.user.domain.UserEntity;

@Entity
@Getter
@Builder
@DynamicInsert
@AllArgsConstructor
@Table(name = "team_member_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SecondaryTables({
        @SecondaryTable(name = "user_info",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"),
                foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
        ),
        @SecondaryTable(name = "team_info",
                pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"),
                foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
        )
})
public class TeamMemberEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -363235389464583980L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private UserEntity userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private TeamEntity teamInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private Position position;
}
