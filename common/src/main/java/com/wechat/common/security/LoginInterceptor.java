package com.wechat.common.security;


import com.wechat.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) return true;
        HandlerMethod method = (HandlerMethod) handler;
        LoginRequired loginRequired = method.getMethodAnnotation(LoginRequired.class);
        if (loginRequired == null) {
            loginRequired = method.getBeanType().getAnnotation(LoginRequired.class);
        }

        if (loginRequired == null) return true;
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized - missing token");
            return false;
        }
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim(); // ✅ strip 掉 "Bearer "
        }
        try {
            Claims claims = JwtUtil.parseToken(token);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            // 角色比对（如方法标注了 @LoginRequired(Role.ADMIN)）
            if (!role.equals(loginRequired.value().name())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Forbidden - role mismatch");
                return false;
            }
            // 可选：将 username / role 存入 ThreadLocal，供后续使用
            UserContext.set(username, role);  // 需自定义 UserContext 类

            return true;

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized - invalid token");
            return false;
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }


}
