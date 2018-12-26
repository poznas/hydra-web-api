package com.agh.hydra.referral.request;

import com.agh.hydra.common.model.CompanyId;
import com.agh.hydra.common.model.JobId;
import com.agh.hydra.common.model.ProgrammingLanguage;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.referral.model.ReferralId;
import lombok.*;

import javax.validation.Valid;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferralAnnouncementFilterRequest {

    /**
     * Job announcement entry identifiers
     */
    @Valid
    private Set<ReferralId> includeIds;

    /**
     * Include author identifiers
     */
    @Valid
    private Set<UserId> authorIds;

    /**
     * Include job identifiers
     */
    @Valid
    private Set<JobId> jobIds;

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
