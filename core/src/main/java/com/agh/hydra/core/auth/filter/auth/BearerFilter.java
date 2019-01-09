package com.agh.hydra.core.auth.filter.auth;

import com.agh.hydra.common.model.UserId;
import com.agh.hydra.core.auth.TokenProvider;
import lombok.Setter;
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

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Setter
@Component
public class BearerFilter extends OncePerRequestFilter implements AuthFilter {

    private List<String> publicResourcePaths;
    private AntPathMatcher matcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.debug("Apply bearer filter : {}", request.getServletPath());

        var userId = ofNullable(request.getHeader(HEADER_AUTHORIZATION))
                .map(header -> header.replace(TOKEN_PREFIX, ""))
                .map(TokenProvider::getUserId);

        if(userId.isPresent()) {
            request.setAttribute(ATTRIBUTE_USER_ID, UserId.of(userId.get()));
            addAuthHeaders(userId.get(), response);
            filterChain.doFilter(request, response);
        } else {
            response.sendError(UNAUTHORIZED.value());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return ofNullable(publicResourcePaths).orElse(emptyList()).stream()
                .anyMatch(pattern -> matcher.match(pattern, request.getServletPath()));
    }
}
