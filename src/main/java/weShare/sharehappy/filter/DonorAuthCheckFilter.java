package weShare.sharehappy.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import weShare.sharehappy.constant.SessionKey;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class DonorAuthCheckFilter implements Filter {

    private static final String[] pathList = {"/donationPostComment/add"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            log.info("기부자 계정 권한 체크 경로 확인 {}", requestURI);
            if (isDonorAuthPath(requestURI)) {
                log.info("가부자 계정 권한 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                String key = SessionKey.USER_AUTH.name();
                if (session == null || session.getAttribute(key) == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    httpResponse.sendRedirect("/login/form?redirectURL=" + requestURI);
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("기부자 계정 권한 체크 종료 {}", requestURI);
        }
    }

    private boolean isDonorAuthPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(pathList, requestURI);
    }
}
