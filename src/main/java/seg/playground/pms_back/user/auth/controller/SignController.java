package seg.playground.pms_back.user.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seg.playground.pms_back.common.jwt.JwtToken;
import seg.playground.pms_back.user.auth.domain.SignInDTO;
import seg.playground.pms_back.user.auth.domain.SignUpDTO;
import seg.playground.pms_back.user.auth.service.SignService;
import seg.playground.pms_back.user.domain.UserDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class SignController {

    private final SignService signService;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@Validated @RequestBody SignUpDTO signUpDto) {
        UserDTO savedMemberDto = signService.signUp(signUpDto);
        return ResponseEntity.ok(savedMemberDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@Validated @RequestBody SignInDTO signInDTO) {
        JwtToken jwtToken = signService.signIn(signInDTO);
        return ResponseEntity.ok(jwtToken);
    }
}
