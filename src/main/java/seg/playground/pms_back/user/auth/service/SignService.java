package seg.playground.pms_back.user.auth.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.playground.pms_back.common.config.support.JwtTokenProvider;
import seg.playground.pms_back.common.exception.BaseException;
import seg.playground.pms_back.common.jwt.JwtToken;
import seg.playground.pms_back.user.auth.domain.SignInDTO;
import seg.playground.pms_back.user.auth.domain.SignUpDTO;
import seg.playground.pms_back.user.domain.UserDTO;
import seg.playground.pms_back.user.repository.UserRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public JwtToken signIn(SignInDTO signInDTO) {
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
            throw new BaseException(HttpStatus.BAD_REQUEST, "신뢰할 수 없는 토큰입니다.");
        } catch (Exception e) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "인증 정보가 없습니다.");
        }
    }

    @Transactional
    public UserDTO signUp(SignUpDTO signUpDto) {
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        List<String> roles = List.of("USER");
        return UserDTO.toDto(userRepository.save(signUpDto.toEntity(encodedPassword, roles)));
    }
}
