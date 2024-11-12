package seg.playground.pms_back.user.auth.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.playground.pms_back.common.config.support.JwtTokenProvider;
import seg.playground.pms_back.common.exception.BaseException;
import seg.playground.pms_back.common.exception.code.StatusCode;
import seg.playground.pms_back.common.jwt.Jwt;
import seg.playground.pms_back.common.jwt.JwtRefreshToken;
import seg.playground.pms_back.common.util.RedisUtil;
import seg.playground.pms_back.user.auth.constant.Role;
import seg.playground.pms_back.user.auth.domain.SignInDTO;
import seg.playground.pms_back.user.auth.domain.SignUpDTO;
import seg.playground.pms_back.user.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public void signUp(SignUpDTO signUpDto) {
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new BaseException(StatusCode.ALREADY_USE_EMAIL, HttpStatus.PERMANENT_REDIRECT);
        }

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        userRepository.save(signUpDto.toEntity(encodedPassword, Role.USER));
    }

    public Jwt signIn(SignInDTO signInDTO) {
        try {
            // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
            // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword());

            // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
            // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            return jwtTokenProvider.generateToken(authentication);
        } catch (IllegalArgumentException iae) {
            throw new BaseException(StatusCode.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new BaseException(StatusCode.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    public Jwt reissueToken(JwtRefreshToken jwtRefreshToken) {
        String refreshToken = jwtRefreshToken.getRefreshToken();

        // Refresh Token 검증
        jwtTokenProvider.validateToken(refreshToken);

        // Access Token 에서 Authentication 조회
        Authentication authentication = jwtTokenProvider.getRefreshAuthentication(refreshToken);

        // Redis에서 저장된 Refresh Token 값을 가져옴
        String redisRefreshToken = RedisUtil.get(authentication.getName());
        if (StringUtils.isBlank(redisRefreshToken) || !redisRefreshToken.equals(refreshToken)) {
            throw new BaseException(StatusCode.EXPIRED_TOKEN, HttpStatus.UNAUTHORIZED);
        }

        // 토큰 재발행
        return jwtTokenProvider.generateToken(authentication);
    }

    public void signOut() {
    }
}
