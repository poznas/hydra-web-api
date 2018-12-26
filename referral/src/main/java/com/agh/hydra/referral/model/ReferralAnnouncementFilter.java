package com.agh.hydra.referral.model;

import com.agh.hydra.common.model.PageData;
import com.agh.hydra.common.model.ProgrammingLanguage;
import lombok.*;

import javax.validation.Valid;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReferralAnnouncementFilter extends PageData {

    @Builder
    public ReferralAnnouncementFilter(int pageSize, long offset, @Valid Set<Long> includeIds,
                                      @Valid Set<String> authorIds, @Valid Set<Long> jobIds,
                                      @Valid Set<String> companyIds, @Valid Set<String> cities,
                                      @Valid Set<ProgrammingLanguage> languages) {
        super(pageSize, offset);
        this.includeIds = includeIds;
        this.authorIds = authorIds;
        this.jobIds = jobIds;
        this.companyIds = companyIds;
        this.cities = cities;
        this.languages = languages;
    }

    /**
     * Job announcement identifiers
     */
    @Valid
    private Set<Long> includeIds;

    /**
     * Include author identifiers
     */
    @Valid
    private Set<String> authorIds;

    /**
     * Include job identifiers
     */
    @Valid
    private Set<Long> jobIds;

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
