package com.agh.hydra.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {

    EN("en"),
    DE("de"),
    PL("pl");

    private final String lowerCase;
}
