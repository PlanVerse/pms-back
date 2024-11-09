package seg.playground.pms_back.user.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seg.playground.pms_back.common.exception.code.BaseResponse;
import seg.playground.pms_back.common.jwt.Jwt;
import seg.playground.pms_back.common.jwt.JwtRefreshToken;
import seg.playground.pms_back.user.auth.domain.SignInDTO;
import seg.playground.pms_back.user.auth.domain.SignUpDTO;
import seg.playground.pms_back.user.auth.service.SignService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class SignController {

    private final SignService signService;

    @PostMapping("/sign-up")
    public BaseResponse<Object> signUp(@Validated @RequestBody SignUpDTO signUpDto) {
        signService.signUp(signUpDto);
        return BaseResponse.success();
    }

    @PostMapping("/sign-in")
    public BaseResponse<Object> signIn(@Validated @RequestBody SignInDTO signInDTO) {
        Jwt jwt = signService.signIn(signInDTO);
        return BaseResponse.success(jwt);
    }

    @PostMapping("/refresh")
    public BaseResponse<Object> refresh(@Validated @RequestBody JwtRefreshToken jwtRefreshToken) {
        Jwt jwt = signService.reissueToken(jwtRefreshToken);
        return BaseResponse.success(jwt);
    }
}
