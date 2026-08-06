package org.neatore.onamnotifier;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.neatore.onamnotifier.annotation.PublicAccess;
import org.neatore.onamnotifier.service.AuthService;

import org.springframework.core.annotation.AnnotatedElementUtils;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod hm) {
            PublicAccess annotation = AnnotatedElementUtils.findMergedAnnotation(hm.getMethod(), PublicAccess.class);
            if (annotation != null) return true;

            String headerCsrf = response.getHeader("X-Csrf-Token");
            Cookie tokenCookie = WebUtils.getCookie(request, "ONN_ACCESS");

           /*
            - 다음 중 하나라도 false일 시 401 Unauthorized 반환
            1. headerCsrf가 null인가?
            2. tokenCookie가 null인가?
            3. tokenCookie가 올바르지 않은가?
            4. tokenCookie안의 CSRF Token과 header의 CSRF Token이 서로 일치하는가?
             */
            if (
                    headerCsrf == null || tokenCookie == null || !authService.validateToken(tokenCookie.getValue()) || !authService.getCsrfToken(tokenCookie.getValue()).equals(headerCsrf)
            ) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }

        return true;
    }
}
