package seg.playground.pms_back.common.jwt;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRefreshToken {

    @NotBlank
    private String refreshToken;
}