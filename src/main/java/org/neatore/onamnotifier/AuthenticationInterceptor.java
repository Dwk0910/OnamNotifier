package org.neatore.onamnotifier;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.neatore.onamnotifier.annotation.AdminAccess;
import org.neatore.onamnotifier.annotation.PublicAccess;
import org.neatore.onamnotifier.service.TokenService;

import org.springframework.core.annotation.AnnotatedElementUtils;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import java.lang.annotation.Annotation;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final TokenService tokenService;

    private <T extends Annotation> boolean doesExistAnnotation(HandlerMethod hm, Class<T> clazz) {
        T annotation = Optional.ofNullable(AnnotatedElementUtils.findMergedAnnotation(hm.getMethod(), clazz))
                .orElseGet(() -> AnnotatedElementUtils.findMergedAnnotation(hm.getClass(), clazz));
        return annotation != null;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod hm) {
            if (doesExistAnnotation(hm, PublicAccess.class)) return true;

            String headerCsrf = request.getHeader("X-Csrf-Token");
            Cookie tokenCookie = WebUtils.getCookie(request, "ONN_ACCESS");

           /*
            - 다음 중 하나라도 false일 시 401 Unauthorized 반환
            1. headerCsrf가 null인가?
            2. tokenCookie가 null인가?
            3. tokenCookie안의 CSRF Token과 header의 CSRF Token이 서로 일치하는가?
               (과정 중 올바른 JWT token인지 검사도 포함함)
            4. AdminAccess가 붙어있는 한편, tokenCookie가 admin 검사에 통과했는가?
             */
            if (
                    headerCsrf == null || tokenCookie == null || !tokenService.getCsrfToken(tokenCookie.getValue()).equals(headerCsrf)
                    || (doesExistAnnotation(hm, AdminAccess.class) && !tokenService.validateAdminToken(tokenCookie.getValue()))
            ) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }

        return true;
    }
}
