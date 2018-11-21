package com.agh.hydra.api.register.request;

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
public class CreateCompanyRequest {

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
    private Language language;
}
