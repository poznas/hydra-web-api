package com.agh.hydra.config;

import com.agh.hydra.core.auth.filter.HttpMethodOverrideHeaderFilter;
import com.agh.hydra.core.auth.filter.auth.BearerFilter;
import com.agh.hydra.core.auth.filter.auth.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginFilter loginFilter;
    private final BearerFilter bearerFilter;
    private final HttpMethodOverrideHeaderFilter headerFilter;

    @Bean
    public FilterRegistrationBean loginRegistrationBean() {
        return registerFilter(loginFilter, List.of("/auth/login/*"));
    }

    @Bean
    public FilterRegistrationBean bearerRegistrationBean() {
        bearerFilter.setPublicResourcePaths(List.of("/auth/login", "/v2/api-docs"));
        return registerFilter(bearerFilter, List.of("/*"));
    }

    @Bean
    public FilterRegistrationBean methodOverrideHeaderFilter() {
        return registerFilter(headerFilter, List.of("/*"));
    }

    private <F extends Filter> FilterRegistrationBean<F> registerFilter(F filter, Collection<String> urlPatterns) {
        FilterRegistrationBean<F> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        return filterRegistrationBean;
    }
}
