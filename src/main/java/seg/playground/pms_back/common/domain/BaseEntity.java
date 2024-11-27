package seg.playground.pms_back.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @ColumnDefault("'N'")
    @Column(nullable = false)
    private String deleteYn;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;

    @LastModifiedBy
    @Column(nullable = false)
    private Long updatedBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDate updatedAt;
}
