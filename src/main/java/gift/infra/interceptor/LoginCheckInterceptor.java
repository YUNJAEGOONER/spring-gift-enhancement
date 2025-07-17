package gift.infra.interceptor;

import gift.exception.ErrorCode;
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
//로그인이 되어 있는지를 확인하기 위한 인터셉터
public class LoginCheckInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoginCheckInterceptor.class);
    private final JwtAuthService jwtAuthService;

    public LoginCheckInterceptor(JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    //컨트롤러에 호출 전에 동작
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //컨트롤러가 호출되기 전에 실행됨
        log.info("[LoginChecker] preHandle");

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
                return true;
            }
        }

        log.warn("유효한 토큰이 존재하지 않습니다...");
        throw new LoginError(ErrorCode.LOGIN_REQUIRED_FAIL);
    }

}