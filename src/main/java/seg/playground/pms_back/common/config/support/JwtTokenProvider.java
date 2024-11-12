package seg.playground.pms_back.common.config.support;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import seg.playground.pms_back.common.exception.BaseException;
import seg.playground.pms_back.common.exception.code.StatusCode;
import seg.playground.pms_back.common.jwt.Jwt;
import seg.playground.pms_back.common.util.RedisUtil;
import seg.playground.pms_back.user.user.service.CustomUserDetailsService;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenProvider(@Value("${spring.jwt.secret}") String secret, CustomUserDetailsService customUserDetailsService) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.customUserDetailsService = customUserDetailsService;
    }

    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public Jwt generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();
        // 30분
        Date accessTokenExpr = new Date(now + 1800000);
        // 대략 1주일 :: 정확한 수치는 소수점이므로 올림 적용
        Date refreshTokenExpr = new Date(now + 604800017);

        // Access Token 생성
        String accessToken = Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .expiration(accessTokenExpr)
                .signWith(secretKey)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .subject(authentication.getName())
                .expiration(refreshTokenExpr)
                .signWith(secretKey)
                .compact();

        // Refresh Token Redis에 저장 및 자동 파기
        RedisUtil.setWithExpiry(
                authentication.getName(),
                refreshToken,
                refreshTokenExpr.getTime(),
                TimeUnit.MILLISECONDS
        );

        return Jwt.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = this.parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new BaseException(StatusCode.NO_AUTHORITY_TMP_REDIRECT, HttpStatus.TEMPORARY_REDIRECT);
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication return
        // UserDetails: interface, User: UserDetails를 구현한 class
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Authentication getRefreshAuthentication(String refreshToken) {
        try {
            Claims claims = this.parseClaims(refreshToken);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (Exception e) {
            throw new BaseException(StatusCode.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
        }
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
        return true;
    }


    // accessToken
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}