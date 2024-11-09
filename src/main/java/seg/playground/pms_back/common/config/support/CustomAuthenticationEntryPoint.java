package seg.playground.pms_back.common.config.support;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import seg.playground.pms_back.common.exception.code.StatusCode;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        StatusCode statusCode = (StatusCode) request.getAttribute("exception");
        if (ObjectUtils.isEmpty(statusCode)) {
            statusCode = StatusCode.UNKNOWN_ERROR;
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", false);
        responseMap.put("code", statusCode.getCode());
        responseMap.put("message", statusCode.getMessage());

        response.getWriter().print(new JSONObject(responseMap));
    }
}