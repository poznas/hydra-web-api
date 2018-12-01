package com.agh.hydra.common.model;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value(staticConstructor = "of")
public class JobId implements ValueObject<Long>{

    @Min(0)
    @NotNull
    Long value;
}
