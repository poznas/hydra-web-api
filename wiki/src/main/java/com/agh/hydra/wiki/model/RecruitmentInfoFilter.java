package com.agh.hydra.wiki.model;

import com.agh.hydra.common.model.PageData;
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
public class RecruitmentInfoFilter extends PageData {

    /**
     * Recruitment information entry identifiers
     */
    @Valid
    private Set<Long> includeIds;

    /**
     * Include company identifiers
     */
    @Valid
    private Set<String> companyIds;

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
