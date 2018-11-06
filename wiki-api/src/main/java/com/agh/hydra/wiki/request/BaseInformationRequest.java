package com.agh.hydra.wiki.request;

import com.agh.hydra.common.model.InformationId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseInformationRequest {

    /**
     * Recruitment information entry identifiers
     */
    @Valid
    @NotNull
    private Set<InformationId> ids;
}
