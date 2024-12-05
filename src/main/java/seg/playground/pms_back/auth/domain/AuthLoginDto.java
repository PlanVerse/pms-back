package seg.playground.pms_back.auth.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author  : seoeungi 
 * @since   : 2024.11.01
 */
@Getter
@Setter
public class AuthLoginDto {
    private String email;
    private String password;
}
