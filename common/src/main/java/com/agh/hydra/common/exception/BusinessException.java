package com.agh.hydra.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
@RequiredArgsConstructor
public enum BusinessException {

    INVALID_TOKEN("Invalid ID token"),
    UNPRIVILEGED("User is not privileged to perform this action"),
    INVALID_REFERRAL_CLOSING_DATE("Referral announcement closing date cannot be ahead of job closing date"),
    ACTIVE_REFERRAL_EXISTS("User has active referral announcement referencing that job"),
    REFERRAL_AUTHOR_APPLICATION("Author can not apply for own referral announcement"),
    REFERRAL_NOT_FOUND("Referral announcement does not exist"),
    REFERRAL_APPLICATION_EXISTS("User has already applied for that referral announcement");

    private final String message;

    public ResponseStatusException getException() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }

    public void throwIf(boolean raise) {
        if (raise) {
            throw getException();
        }
    }
}
