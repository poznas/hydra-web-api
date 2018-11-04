package com.agh.hydra.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Optional.ofNullable;

@Getter
@RequiredArgsConstructor
public enum Language {

    EN("en"),
    DE("de"),
    PL("pl");

    private final String lowerCase;

    public static Language from(String value) {
        return ofNullable(value)
                .map(String::toUpperCase)
                .map(Language::valueOf)
                .orElse(null);
    }
}
