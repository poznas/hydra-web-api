package com.agh.hydra.wiki.model;

import com.agh.hydra.common.model.*;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentInformation {

    /**
     * Database identifier
     */
    @Valid
    @NotNull
    private InformationId id;

    /**
     * Author user identifier
     */
    @Valid
    @NotNull
    private UserId authorId;

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
     * Programming language
     */
    private ProgrammingLanguage programmingLanguage;

    /**
     * Information content
     */
    @Valid
    @NotNull
    private InformationContent content;

    /**
     * Up votes count
     */
    private int upVotes;

    /**
     * Down votes count
     */
    private int downVotes;
}
