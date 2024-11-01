package seg.playground.pms_back.user.service;

import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import seg.playground.pms_back.user.entity.UserEntity;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    // role값 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) userEntity::getRole);
        return collection;
    }

    // 비밀번호 반환
    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    // username 반환
    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    // 아래 전부 true로 설정
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}