package com.agh.hydra.common.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value(staticConstructor = "of")
public class CompanyId implements ValueObject<String> {

    @NotBlank
    @Size(max = 10)
    private String value;
}
