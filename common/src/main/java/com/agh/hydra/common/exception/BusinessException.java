package com.agh.hydra.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessException {

    UNPRIVILEGED(exception("User is not privileged to perform this action")),
    INVALID_TOKEN(exception("Invalid ID token"));

    private final RuntimeException exception;

    private static RuntimeException exception(String message) {
        return new RuntimeException(message);
    }
}
