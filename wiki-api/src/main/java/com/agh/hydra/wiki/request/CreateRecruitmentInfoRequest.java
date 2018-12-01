package com.agh.hydra.wiki.request;

import com.agh.hydra.common.model.CompanyId;
import com.agh.hydra.common.model.InformationContent;
import com.agh.hydra.common.model.ProgrammingLanguage;
import com.agh.hydra.common.model.RecruitmentType;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecruitmentInfoRequest {

    /**
     * company identifier
     */
    @Valid
    @NotNull
    private CompanyId companyId;

    /**
     * Recruitment type
     */
    @NotNull
    private RecruitmentType recruitmentType;

    /**
     * Information content
     */
    @Valid
    @NotNull
    private InformationContent content;

    /**
     * Programming language
     */
    private ProgrammingLanguage programmingLanguage;
}
