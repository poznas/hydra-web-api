package com.agh.hydra.api.register.request;

import com.agh.hydra.common.model.CompanyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CompaniesRequest {

    /**
     * Company identifiers
     */
    @Valid
    @Size(min = 1)
    private Set<CompanyId> companyIds;
}
