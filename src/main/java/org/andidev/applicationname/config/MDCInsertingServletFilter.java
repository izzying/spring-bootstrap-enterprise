package org.andidev.applicationname.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.andidev.applicationname.config.audit.UserHolder;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class MDCInsertingServletFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put("username", getUsername());
        MDC.put("session", getSession());
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("username");
            MDC.remove("session");
        }
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
        // Do nothing
    }

    @Override
    public void destroy() {
        // Do nothing
    }

    private String getUsername() {
        return UserHolder.getUsername();
    }

    private String getSession() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            return attrs.getSessionId();
        }
        return "noSession";
    }
}
