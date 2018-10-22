package com.agh.hydra.common.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
public class CompanyName implements ValueObject<String> {

    @NotBlank
    @Size(max = 100)
    String value;
}
