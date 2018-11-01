package com.agh.hydra.core.auth.filter;

import com.agh.hydra.common.model.UserId;
import com.agh.hydra.core.auth.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Component
public class BearerFilter extends OncePerRequestFilter implements AuthFilter {

    private static final List<String> PUBLIC_RESOURCE_PATHS = List.of("/auth/login", "/v2/api-docs");
    private AntPathMatcher matcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.debug("Apply bearer filter : {}", request.getServletPath());

        Optional<String> userId = ofNullable(request.getHeader(HEADER_AUTHORIZATION))
                .map(header -> header.replace(TOKEN_PREFIX, ""))
                .map(TokenProvider::getUserId);

        if(userId.isPresent()) {
            request.setAttribute(ATTRIBUTE_USER_ID, UserId.of(userId.get()));
            addAuthorizationHeader(userId.get(), response);
            filterChain.doFilter(request, response);
        } else {
            response.sendError(UNAUTHORIZED.value());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return PUBLIC_RESOURCE_PATHS.stream()
                .anyMatch(pattern -> matcher.match(pattern, request.getServletPath()));
    }
}
