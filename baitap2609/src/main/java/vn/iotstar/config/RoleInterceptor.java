package vn.iotstar.config;

import jakarta.servlet.http.*;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.stereotype.Component;

@Component
public class RoleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        String role = (session != null) ? (String) session.getAttribute("ROLE") : null;

        if (uri.startsWith("/admin")) {
            if (!"ADMIN".equals(role)) {
                response.sendRedirect("/login?error=noaccess");
                return false;
            }
        } else if (uri.startsWith("/user")) {
            if (!("USER".equals(role) || "ADMIN".equals(role))) {
                response.sendRedirect("/login?error=noaccess");
                return false;
            }
        }
        return true;
    }
}
