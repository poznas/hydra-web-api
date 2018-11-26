package com.agh.hydra.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
@RequiredArgsConstructor
public enum BusinessException {

    UNPRIVILEGED(exception("User is not privileged to perform this action")),
    INVALID_TOKEN(exception("Invalid ID token"));

    private final ResponseStatusException exception;

    private static ResponseStatusException exception(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
