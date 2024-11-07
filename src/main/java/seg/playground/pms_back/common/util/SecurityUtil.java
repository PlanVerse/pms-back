package seg.playground.pms_back.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import seg.playground.pms_back.common.exception.BaseException;

@Component
public class SecurityUtil {

    public static String getCurrentEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new BaseException(HttpStatus.UNAUTHORIZED, "인증 정보가 없습니다.");
        }
        return authentication.getName();
    }
}