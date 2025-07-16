package gift.infra.interceptor;

import gift.dto.Role;
import gift.exception.ErrorCode;
import gift.exception.member.JWTAuthException;
import gift.exception.member.LoginError;
import gift.service.JwtAuthService;
import jakarta.servlet.http.Cookie;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //컨트롤러가 호출되기 전에 실행됨
        log.info("[adminchecker] preHandle");

        if(request.getCookies() == null){
            log.warn("쿠키가 존재하지 않음");
            throw new LoginError(ErrorCode.LOGIN_REQUIRED_FAIL);
        }

        for(Cookie c : request.getCookies()){
            if(c.getName().equals("token")){
                String token = c.getValue();
                log.info("로그인 정보를 확인 중,,,");
                jwtAuthService.checkValidation(token);
                log.info("JWT 토큰 검증 성공,,,");
                if(Role.valueOf(jwtAuthService.getMemberRole(token)).equals(Role.ADMIN)){
                    return true;
                }
                throw new JWTAuthException(ErrorCode.ADMIN_PAGE);
            }
        }
        log.warn("유효한 토큰이 존재하지 않습니다...");
        throw new LoginError(ErrorCode.LOGIN_REQUIRED_FAIL);

    }
}