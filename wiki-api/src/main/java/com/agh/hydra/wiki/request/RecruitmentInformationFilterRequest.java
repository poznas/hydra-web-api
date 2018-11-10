package com.agh.hydra.wiki.request;

import com.agh.hydra.common.model.CompanyId;
import com.agh.hydra.common.model.InformationId;
import com.agh.hydra.common.model.ProgrammingLanguage;
import com.agh.hydra.common.model.RecruitmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentInformationFilterRequest {

    /**
     * Recruitment information entry identifiers
     */
    @Valid
    private Set<InformationId> includeIds;

    /**
     * Include company identifiers
     */
    @Valid
    private Set<CompanyId> companyIds;

    /**
     * Include recruitment types
     */
    @Valid
    private Set<RecruitmentType> types;

    /**
     * Include programming languages
     */
    @Valid
    private Set<ProgrammingLanguage> languages;
}
