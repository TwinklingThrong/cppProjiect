package com.cpp.interceptor;

import com.cpp.utils.LingPai;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

@Slf4j
@Component
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("拦截器拦截请求：" + requestURI);
        log.info("request为：" + request);

        // 处理预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Token, Content-Type");
            response.setHeader("Access-Control-Max-Age", "3600");
            return true;
        }

//        log.info("All headers:");
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            log.info("{}: {}", headerName, headerValue);
//        }

        if (requestURI.contains("/login") || requestURI.contains("/register") || requestURI.contains("/users/*") || requestURI.contains("/information")) {
            log.info("登录请求，放行");
            return true;
        }

        String token = request.getHeader("Token");
        if (token == null || token.isEmpty()) {
            log.info("token为空，拦截401");
            response.setStatus(401);
            return false;
        }

        try {
            LingPai.parseToken(token);
        } catch (Exception e) {
            log.info("token解析失败，拦截401");
            response.setStatus(401);
            return false;
        }

        log.info("token验证成功，放行");
        return true;
    }
}
