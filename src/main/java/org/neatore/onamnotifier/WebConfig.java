package org.neatore.onamnotifier;

import lombok.RequiredArgsConstructor;

import org.neatore.onamnotifier.service.AuthService;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthService authService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new AuthenticationInterceptor(this.authService))
               .addPathPatterns("/**");
    }
}
