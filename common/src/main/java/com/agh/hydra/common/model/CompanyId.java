package com.agh.hydra.common.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
public class CompanyId implements ValueObject<String> {

    @NotBlank
    @Size(max = 10)
    private String value;
}
