package gift.infra.interceptor;

import gift.exception.ErrorCode;
import gift.exception.member.LoginError;
import gift.service.JwtAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;

@FunctionalInterface
public interface JwtChecker {

    org.slf4j.Logger log = LoggerFactory.getLogger(JwtChecker.class);

    boolean checkrole(String token, JwtAuthService jwtAuthService);

    default String checkJwtAvaliable(HttpServletRequest request, JwtAuthService jwtAuthService){
        log.info("[FunctionalInterface.checkJwtAvaliable]");
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
                return token;
            }
        }

        log.warn("유효한 토큰이 존재하지 않습니다...");
        throw new LoginError(ErrorCode.LOGIN_REQUIRED_FAIL);
    }
}
