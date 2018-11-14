package com.agh.hydra.common.model;

import com.fasterxml.jackson.annotation.JsonValue;

public interface ValueObject<T> {

    @JsonValue
    T getValue();
}
