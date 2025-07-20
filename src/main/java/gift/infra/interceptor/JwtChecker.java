package gift.infra.interceptor;

import gift.exception.ErrorCode;
import gift.jwt.exception.LoginError;
import gift.jwt.JwtAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

@FunctionalInterface
public interface JwtChecker {

    boolean checkRole(String token, JwtAuthService jwtAuthService);

    default String checkJwtAvailable(HttpServletRequest request){
        return Arrays.stream(request.getCookies())
                .filter(c -> "token".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new LoginError(ErrorCode.LOGIN_REQUIRED_FAIL));
    }
}
