package seg.playground.pms_back.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seg.playground.pms_back.auth.domain.SignInDTO;
import seg.playground.pms_back.auth.service.SignService;
import seg.playground.pms_back.common.jwt.JwtToken;
import seg.playground.pms_back.common.util.SecurityUtil;

/**
 * @author  : seoeungi 
 * @since   : 2024.11.01
 */
@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@RequestBody SignInDTO signInDTO) {
        JwtToken jwtToken = signService.signIn(signInDTO);
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok(SecurityUtil.getCurrentEmail());
    }
}
