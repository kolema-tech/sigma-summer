package com.sigma.logging;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

import static com.sigma.logging.SigmaLoggerMdcConstants.TRACE_ID;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/16-23:16
 * desc:
 **/
@Component
public class LogMdcFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean bInsertMDC = insertMDC();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (bInsertMDC) {
                MDC.remove(TRACE_ID);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private boolean insertMDC() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        MDC.put(TRACE_ID, uniqueId);
        return true;
    }
}
