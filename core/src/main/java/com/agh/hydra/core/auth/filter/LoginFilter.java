package com.agh.hydra.core.auth.filter;

import com.agh.hydra.api.register.model.User;
import com.agh.hydra.api.register.service.IPrivilegeService;
import com.agh.hydra.api.register.service.IUserService;
import com.agh.hydra.core.auth.service.TokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.agh.hydra.common.util.ValueObjectUtil.getValue;
import static java.util.Optional.ofNullable;
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
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.debug("Apply login filter : {}", request.getServletPath());

        Optional<User> user = ofNullable(request.getHeader(HEADER_ID_TOKEN)).map(tokenVerifier::verifyTokenId);

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
    }
}
