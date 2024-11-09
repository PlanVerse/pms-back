package seg.playground.pms_back.common.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Jwt {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}