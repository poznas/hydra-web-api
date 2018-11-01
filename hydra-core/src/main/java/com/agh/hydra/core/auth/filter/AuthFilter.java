package com.agh.hydra.core.auth.filter;

import javax.servlet.http.HttpServletResponse;

import static com.agh.hydra.core.auth.TokenProvider.getToken;

interface AuthFilter {

    String HEADER_AUTHORIZATION = "Authorization";
    String TOKEN_PREFIX = "Bearer";
    String ATTRIBUTE_USER_ID = "userId";

    default void addAuthorizationHeader(String userId, HttpServletResponse response) {
        response.addHeader(HEADER_AUTHORIZATION, TOKEN_PREFIX + " " + getToken(userId));
    }
}
