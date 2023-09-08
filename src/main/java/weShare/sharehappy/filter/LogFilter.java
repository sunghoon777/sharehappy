package weShare.sharehappy.filter;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        try {
            log.info("REQUEST [{}][{}][{}]", uuid, requestURI, LocalDateTime.now());
            filterChain.doFilter(servletRequest,servletResponse);
        } catch (Exception e) {
            log.info("exception occur [{}],[{}] [{}]",uuid,requestURI,e);
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}][{}]", uuid, requestURI,LocalDateTime.now());
        }
    }
}
