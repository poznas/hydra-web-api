package com.agh.hydra.referral.model;

import com.agh.hydra.common.model.PageData;
import com.agh.hydra.common.model.ProgrammingLanguage;
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
public class ReferralAnnouncementFilter extends PageData {

    /**
     * Job announcement identifiers
     */
    @Valid
    private Set<Long> includeIds;

    /**
     * Include company identifiers
     */
    @Valid
    private Set<String> companyIds;

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
