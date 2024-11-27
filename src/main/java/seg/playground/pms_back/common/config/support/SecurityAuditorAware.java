package seg.playground.pms_back.common.config.support;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import seg.playground.pms_back.common.util.SecurityUtil;
import seg.playground.pms_back.user.user.domain.UserDTO;
import seg.playground.pms_back.user.user.domain.UserEntity;
import seg.playground.pms_back.user.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class SecurityAuditorAware implements AuditorAware<Long> {

    private final UserRepository userRepository;

    @Override
    public Optional<Long> getCurrentAuditor() {
        String currentEmail = SecurityUtil.getCurrentEmail();
        Optional<UserEntity> optionalUser = userRepository.findByEmail(currentEmail);

        if (optionalUser.isPresent()) {
            UserDTO user = UserDTO.toDto(optionalUser.get());
            return Optional.of(user.getId());
        }

        return Optional.empty();
    }
}