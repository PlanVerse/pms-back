package seg.playground.pms_back.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import seg.playground.pms_back.common.exception.BaseException;
import seg.playground.pms_back.common.exception.code.StatusCode;

@Component
public class SecurityUtil {

    public static String getCurrentEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new BaseException(StatusCode.UNAUTHORIZED);
        }
        return authentication.getName();
    }
}