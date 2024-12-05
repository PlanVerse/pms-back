package seg.playground.pms_back.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author  : seoeungi 
 * @since   : 2024.11.01
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long userId;
    private String email;
    private String password;
    private String username;
    private String role;
    private String profileUrl;
    private String refreshToken;
    private LocalDate createdDt;

    // refreshToken 재설정
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // 비밀번호 암호화
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}