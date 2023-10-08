package weShare.sharehappy.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.user.UserSummary;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Slf4j
public class UserAuthCheckFilter implements Filter {

    private static final String[] commonAuthPathList = {"/donationPostComment/add","/donationPostComment/remove",
            "/donationPostComment/update", "/donationPostComment/reply/add","/donation/form","/myPage"};
    private static final String[] organizationAuthPathList = {"/donationPost/make/form","/donationPost/temp/image/upload"};
    private static final String[] donorAuthPathList = {"/donation/form","/donation/prepare","/donation/verify","/myPage/myDonations","myPage/myDonations/cancle"};


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            log.info("계정 권한 체크 경로 확인 {}", requestURI);
            //common
            if(isCommonAuthPath(requestURI)){
                log.info("계정 권한 체크 로직 실행 {}", requestURI);
                if(!doCommonAuthProcess(httpRequest,httpResponse)){
                    log.info("미인증 사용자 요청 {}", requestURI);
                    return;
                }
            }
            //organization
            else if(isOranizationAuthPath(requestURI)){
                log.info("기부 단체 계정 권한 체크 로직 실행 {}", requestURI);
                if(!doOrganizaitonAuthProcess(httpRequest,httpResponse)){
                    log.info("미인증 사용자 요청 {}", requestURI);
                    return;
                }
            }
            //common
            else if(isDonorAuthPath(requestURI)){
                log.info("기부자 계정 권한 체크 로직 실행 {}", requestURI);
                if(!doDonorAuthProcess(httpRequest,httpResponse)){
                    log.info("미인증 사용자 요청 {}", requestURI);
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("계정 권한 체크 종료 {}", requestURI);
        }
    }

    private boolean doCommonAuthProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String key = SessionKey.USER_AUTH.name();
        if (session == null || session.getAttribute(key) == null) {
            log.info("미인증 사용자 요청 {}", request.getRequestURI());
            if(request.getHeader("Redirect-Location") != null){
                response.sendRedirect("/login/form?redirectURL="+request.getHeader("redirect-location") );
            }
            else{
                response.sendRedirect("/login/form?redirectURL="+request.getRequestURI());
            }
            return false;
        }
        return true;
    }
    private boolean doOrganizaitonAuthProcess(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession(false);
        String key = SessionKey.USER_AUTH.name();
        if (session == null || session.getAttribute(key) == null || !((UserSummary)session.getAttribute(key)).getIsOrganization()) {
            log.info("미인증 사용자 요청 {}", request.getRequestURI());
            if(request.getHeader("Redirect-Location") != null){
                response.sendRedirect("/login/form?redirectURL="+request.getHeader("redirect-location") );
            }
            else{
                response.sendRedirect("/login/form?redirectURL="+request.getRequestURI());
            }
            return false;
        }
        return true;
    }
    private boolean doDonorAuthProcess(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession(false);
        String key = SessionKey.USER_AUTH.name();
        if (session == null || session.getAttribute(key) == null || ((UserSummary)session.getAttribute(key)).getIsOrganization()) {
            log.info("미인증 사용자 요청 {}", request.getRequestURI());
            if(request.getHeader("Redirect-Location") != null){
                response.sendRedirect("/login/form?redirectURL="+request    .getHeader("redirect-location") );
            }
            else{
                response.sendRedirect("/login/form?redirectURL="+request.getRequestURI());
            }
            return false;
        }
        return true;
    }
    private boolean isCommonAuthPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(commonAuthPathList, requestURI);
    }
    private boolean isOranizationAuthPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(organizationAuthPathList, requestURI);
    }
    private boolean isDonorAuthPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(donorAuthPathList, requestURI);
    }
}
