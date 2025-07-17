package gift.infra.interceptor;

import gift.dto.Role;
import gift.exception.ErrorCode;
import gift.exception.member.JWTAuthException;
import gift.service.JwtAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminCheckInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AdminCheckInterceptor.class);
    private final JwtAuthService jwtAuthService;

    public AdminCheckInterceptor(JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        //컨트롤러가 호출되기 전에 실행됨
        log.info("[adminchecker] preHandle");
        JwtChecker jwtChecker = (token, adminChecker) -> Role.valueOf(jwtAuthService.getMemberRole(token)).equals(Role.ADMIN);
        String token = jwtChecker.checkJwtAvaliable(request, jwtAuthService);
        if(!jwtChecker.checkrole(token, jwtAuthService)){
            throw new JWTAuthException(ErrorCode.ADMIN_PAGE);
        }
        return true;
    }
}