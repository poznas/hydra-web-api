package com.agh.hydra.common.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TechnicalException {

    COMPANY_NOT_FOUND("Company '", "' does not exist");

    private final String prefix;
    private final String suffix;

    public RuntimeException throwWith(Object value) {
        return new RuntimeException(prefix + String.valueOf(value) + suffix);
    }
}
