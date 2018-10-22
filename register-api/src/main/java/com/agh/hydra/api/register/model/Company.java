package com.agh.hydra.api.register.model;

import com.agh.hydra.common.model.CompanyId;
import com.agh.hydra.common.model.CompanyName;
import com.agh.hydra.common.model.Language;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    /**
     * Company identifier
     */
    @Valid
    @NotNull
    private CompanyId companyId;

    /**
     * Company name
     */
    @Valid
    @NotNull
    private CompanyName companyName;

    /**
     * Company address
     */
    private String address;

    /**
     * language
     */
    @Valid
    private Language language;

    /**
     * Indicates whether company is active
     */
    private Boolean active;
}
