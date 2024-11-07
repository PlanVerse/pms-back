package seg.playground.pms_back.user.auth.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import seg.playground.pms_back.user.domain.UserEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignUpDTO {

    @NotBlank
    private String username;
    @Pattern(regexp = "^[A-Z]+(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$", message = "영문 숫자 특수기호 조합 8자리 이상 20자리 이하여야 합니다.")
    private String password;
    @Email
    private String email;
    @NotBlank
    @Size(min = 3, max = 10)
    private String nickname;
    private List<String> roles;

    public UserEntity toEntity(String encodedPassword, List<String> roles) {

        return UserEntity.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .nickname(nickname)
                .roles(roles)
                .build();
    }
}