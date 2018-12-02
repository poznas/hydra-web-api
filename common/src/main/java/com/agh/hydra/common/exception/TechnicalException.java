package com.agh.hydra.common.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
public enum TechnicalException {

    COMPANY_NOT_FOUND("Company '", "' does not exist"),
    JOB_ANNOUNCEMENT_NOT_FOUND("Job announcement '", "' does not exist");

    private final String prefix;
    private final String suffix;

    public RuntimeException throwWith(Object value) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, prefix + String.valueOf(value) + suffix);
    }
}
