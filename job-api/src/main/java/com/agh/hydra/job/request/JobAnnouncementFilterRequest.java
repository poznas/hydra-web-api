package com.agh.hydra.job.request;

import com.agh.hydra.common.model.CompanyId;
import com.agh.hydra.common.model.JobId;
import com.agh.hydra.common.model.ProgrammingLanguage;
import lombok.*;

import javax.validation.Valid;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobAnnouncementFilterRequest {

    /**
     * Job announcement entry identifiers
     */
    @Valid
    private Set<JobId> includeIds;

    /**
     * Include company identifiers
     */
    @Valid
    private Set<CompanyId> companyIds;

    /**
     * Include cities
     */
    @Valid
    private Set<String> cities;

    /**
     * Include programming languages
     */
    @Valid
    private Set<ProgrammingLanguage> languages;
}
