package com.agh.hydra.config;

import com.agh.hydra.core.auth.filter.BearerFilter;
import com.agh.hydra.core.auth.filter.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginFilter loginFilter;
    private final BearerFilter bearerFilter;

    @Bean
    public FilterRegistrationBean loginRegistrationBean() {
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(loginFilter);
        filterRegistrationBean.setUrlPatterns(List.of("/auth/login/*"));
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean bearerRegistrationBean() {
        FilterRegistrationBean<BearerFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(bearerFilter);
        filterRegistrationBean.setUrlPatterns(List.of("/*"));
        bearerFilter.setPublicResourcePaths(List.of("/auth/login", "/v2/api-docs"));
        return filterRegistrationBean;
    }
}
