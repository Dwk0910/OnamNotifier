package org.neatore.onamnotifier;

import lombok.RequiredArgsConstructor;

import org.neatore.onamnotifier.service.TokenService;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final TokenService tokenService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new AuthenticationInterceptor(this.tokenService))
               .addPathPatterns("/**")
               .excludePathPatterns("/error");
    }
}
