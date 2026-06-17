package com.obai.platform.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obai.platform.common.ApiResponse;
import com.obai.platform.common.RequestContext;
import com.obai.platform.entity.SysUser;
import com.obai.platform.repository.SysUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final SysUserRepository userRepository;

    public AuthFilter(JwtUtil jwtUtil, ObjectMapper objectMapper, SysUserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            try {
                RequestContext.setUserId(jwtUtil.verifyAndGetUserId(authorization.substring(7)));
            } catch (Exception ignored) {
                // A malformed token is handled as unauthenticated for protected endpoints.
            }
        }
        if (request.getRequestURI().startsWith("/api/admin")) {
            if (RequestContext.userId() == null) {
                writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "未登录或登录已过期");
                return;
            }
            if (!hasAdminRole(RequestContext.userId())) {
                writeError(response, HttpServletResponse.SC_FORBIDDEN, "无后台管理权限");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean hasAdminRole(Long userId) {
        return userRepository.findById(userId)
                .filter(user -> "ENABLED".equals(user.status))
                .map(this::hasAdminRole)
                .orElse(false);
    }

    private boolean hasAdminRole(SysUser user) {
        return user.roles.stream().anyMatch(role -> "ADMIN".equals(role.code) && Boolean.TRUE.equals(role.enabled));
    }

    private void writeError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.fail(status, message)));
    }
}
