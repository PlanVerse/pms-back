package seg.playground.pms_back.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @ColumnDefault("'Y'")
    @Column(nullable = false)
    private String deleteYn;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDate updatedDt;
}