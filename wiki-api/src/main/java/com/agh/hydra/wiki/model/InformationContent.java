package com.agh.hydra.wiki.model;

import com.agh.hydra.common.model.ValueObject;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value(staticConstructor = "of")
public class InformationContent implements ValueObject<String> {

    @NotNull
    @Size(min = 1, max = 2000)
    private String value;
}
