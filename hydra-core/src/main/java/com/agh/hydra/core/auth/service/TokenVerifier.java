package com.agh.hydra.core.auth.service;

import com.agh.hydra.api.register.model.User;

import javax.validation.constraints.NotBlank;

public interface TokenVerifier {

    /**
     * Validates ID token against external credential provider
     * @param tokenId authentication provider success response ID token
     * @return user data
     */
    User verifyTokenId(@NotBlank String tokenId);
}
