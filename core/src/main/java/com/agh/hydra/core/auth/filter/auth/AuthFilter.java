package com.agh.hydra.core.auth.filter.auth;

import javax.servlet.http.HttpServletResponse;

import static com.agh.hydra.core.auth.TokenProvider.getToken;

/**
 * squid:S1214
 *
 * Internal maven module, will not leak into the class's exported API
 */
@SuppressWarnings("squid:S1214")
interface AuthFilter {

    String HEADER_AUTHORIZATION = "Authorization";
    String HEADER_USER_ID = "X-User-Id";
    String TOKEN_PREFIX = "Bearer";
    String ATTRIBUTE_USER_ID = "userId";

    default void addAuthHeaders(String userId, HttpServletResponse response) {
        response.addHeader(HEADER_AUTHORIZATION, TOKEN_PREFIX + " " + getToken(userId));
        response.addHeader(HEADER_USER_ID, userId);
    }
}
