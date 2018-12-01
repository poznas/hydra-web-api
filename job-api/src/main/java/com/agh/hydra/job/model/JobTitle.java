package com.agh.hydra.job.model;

import com.agh.hydra.common.model.ValueObject;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value(staticConstructor = "of")
public class JobTitle implements ValueObject<String> {

    @NotBlank
    @Size(max = 100)
    String value;
}
