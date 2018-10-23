package com.agh.hydra.common.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value(staticConstructor = "of")
public class CompanyName implements ValueObject<String> {

    @NotBlank
    @Size(max = 100)
    String value;
}
