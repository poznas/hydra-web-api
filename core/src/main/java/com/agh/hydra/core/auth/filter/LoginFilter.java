package com.agh.hydra.core.auth.filter;

import com.agh.hydra.api.register.model.User;
import com.agh.hydra.api.register.service.IPrivilegeService;
import com.agh.hydra.api.register.service.IUserService;
import com.agh.hydra.core.auth.service.TokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.agh.hydra.common.util.ValueObjectUtil.getValue;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFilter implements Filter, AuthFilter {

    private static final String HEADER_ID_TOKEN = "X-ID-TOKEN";

    private final TokenVerifier tokenVerifier;
    private final IUserService userService;
    private final IPrivilegeService privilegeService;

    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("init " + this.getClass().getName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.debug("Apply login filter : {}", request.getServletPath());

        Optional<User> user;
        try {
            user = ofNullable(request.getHeader(HEADER_ID_TOKEN))
                    .map(tokenVerifier::verifyTokenId);
        } catch (ResponseStatusException ex) {
            response.sendError(BAD_REQUEST.value(), ex.getReason());
            return;
        }

        if (user.isPresent()) {
            boolean isNewUser = !userService.userExists(user.get().getId());
            userService.updateUser(user.get());
            if (isNewUser) {
                privilegeService.assignDefaultPrivileges(user.get().getId());
            }

            request.setAttribute(ATTRIBUTE_USER_ID, user.get().getId());
            addAuthorizationHeader(getValue(user.get().getId()), response);
            filterChain.doFilter(servletRequest, response);
        } else {
            response.sendError(UNAUTHORIZED.value());
        }
    }

    @Override
    public void destroy() {
        log.debug("destroy " + this.getClass().getName());
    }
}
