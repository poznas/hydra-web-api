package com.agh.hydra.wiki.model;

import com.agh.hydra.common.model.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;

@Setter
@Getter
@NoArgsConstructor
public class InformationDetails extends RecruitmentInformation {

    @Builder
    public InformationDetails(InformationId id, UserId authorId, CompanyId companyId, RecruitmentType recruitmentType,
                              ProgrammingLanguage programmingLanguage, InformationContent content, int upVotes,
                              int downVotes, Username username, String userImageUrl, CompanyName companyName,
                              Double userReliabilityRatio) {
        super(id, authorId, companyId, recruitmentType, programmingLanguage, content, upVotes, downVotes);
        this.username = username;
        this.userImageUrl = userImageUrl;
        this.companyName = companyName;
        this.userReliabilityRatio = userReliabilityRatio;
    }

    /**
     * Username
     */
    @Valid
    private Username username;

    /**
     * User image URL address
     */
    private String userImageUrl;

    /**
     * Company name
     */
    @Valid
    private CompanyName companyName;

    /**
     * User reliability ratio
     */
    private Double userReliabilityRatio;
}
